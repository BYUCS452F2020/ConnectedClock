package com.codemonkeys.server.core.dao

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder
import com.amazonaws.services.dynamodbv2.document.*
import java.math.BigDecimal

open class BaseDynamoDAO : BaseDAO() {
    companion object {
        const val TABLE_NAME = "ConnectedClock"
    }

    protected inline fun useDynamoConnection(block: (DynamoDB) -> Unit) {
        val client = AmazonDynamoDBClientBuilder
            .standard()
            .build()
        val db = DynamoDB(client)

        try {
            block(db)
        } finally {
            client.shutdown()
        }
    }

    // https://stackoverflow.com/a/46359673/6634972 Automatically mapping from ResultSet to Java Object
    protected fun <T> getQueryResults(clazz: Class<T>, items: ItemCollection<QueryOutcome>): List<T> {
        val columnNamesAndFields = getColumnNamesAndFields(clazz)
        val resultList = mutableListOf<T>()
        val iterator = items.iterator()
        while (iterator.hasNext()) {
            val item = iterator.next()
            val resultObject = clazz.getConstructor().newInstance()
            for ((columnName, field) in columnNamesAndFields) {
                val columnValue = item.get(columnName)
                val convertedColumnValue =
                    when {
                        columnValue == null -> null
                        field.type == Int::class.java -> (columnValue as BigDecimal).toInt()
                        field.type == java.lang.Double::class.java -> (columnValue as BigDecimal).toDouble()
                        field.type == String::class.java -> columnValue as String
                        else -> columnValue
                    }

                field.isAccessible = true
                field.set(resultObject, convertedColumnValue)
            }
            resultList.add(resultObject)
        }
        return resultList
    }

    private fun <T> mapObjectToDynamoItem(clazz: Class<T>, itemToConvert: T, pk: String, sk: String): Item {
        val columnNamesAndFields = getColumnNamesAndFields(clazz)
        val convertedItem = Item()
        convertedItem.withPrimaryKey("PK", pk, "SK", sk)

        columnNamesAndFields.forEach { (columnName, field) ->
            field.isAccessible = true
            val fieldValue = field.get(itemToConvert)
            convertedItem.with(columnName, fieldValue)
        }

        return convertedItem
    }

    protected fun <T> updateItems(
        clazz: Class<T>,
        tableName: String,
        oldItems: List<T>,
        newItems: List<T>,
        pk: String,
        skGetter: (T) -> String
    ) {
        val alternatingPkSk = mutableListOf<String>()
        oldItems.forEach {
            val sk = skGetter(it)
            alternatingPkSk.add(pk)
            alternatingPkSk.add(sk)
        }

        val itemsToPut = newItems.map {
            val sk = skGetter(it)
            mapObjectToDynamoItem(clazz, it, pk, sk)
        }

        // We can't delete and insert items of the same key in a single operation, so we've gotta do it in 2
        val deleteItems = TableWriteItems(tableName)
            .withHashAndRangeKeysToDelete("PK", "SK", *alternatingPkSk.toTypedArray())
        val putItems = TableWriteItems(tableName)
            .withItemsToPut(itemsToPut)

        this.useDynamoConnection {
            if (deleteItems.primaryKeysToDelete.isNotEmpty()) {
                it.batchWriteItem(deleteItems)
            }
            if (putItems.itemsToPut.isNotEmpty()) {
                it.batchWriteItem(putItems)
            }
        }
    }
}