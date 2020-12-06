package com.codemonkeys.server

import com.amazonaws.services.dynamodbv2.document.Item
import com.amazonaws.services.dynamodbv2.document.TableWriteItems
import com.amazonaws.services.dynamodbv2.document.spec.ScanSpec
import com.codemonkeys.server.core.dao.BaseDynamoDAO
import com.fasterxml.jackson.databind.ObjectMapper

class DynamoDatabasePreparer : BaseDynamoDAO() {
    fun prepareDatabase() {
        this.useDynamoConnection {
            val table = it.getTable(TABLE_NAME)
            val outcome = table.scan(ScanSpec())
            val deleteItems = mutableListOf<Item>()
            val iterator = outcome.iterator()
            while (iterator.hasNext()) {
                deleteItems.add(iterator.next())
            }
            deleteItems.forEach { item ->
                table.deleteItem("PK", item.getString("PK"), "SK", item.getString("SK"))
            }

            val itemsBatches = getTestItems()
            itemsBatches.forEach {items ->
                it.batchWriteItem(items)
            }
        }
        Thread.sleep(10000)
    }

    private fun getTestItems(): List<TableWriteItems> {
        val dynamoTableJson = DynamoDatabasePreparer::class.java.getResource("/DatabaseSQL/demo_data.json").readText()
//        val dynamoTableJson = DynamoDatabasePreparer::class.java.getResource("/DatabaseSQL/dynamo_data.json").readText()
        val mapper = ObjectMapper()
        val tree = mapper.readTree(dynamoTableJson)

        val itemsBatches = mutableListOf<TableWriteItems>()
        tree["Items"].forEach { itemNode ->
            val item = Item()
            item.withPrimaryKey("PK", itemNode["PK"].textValue(), "SK", itemNode["SK"].textValue())

            itemNode.fieldNames().forEach { fieldName ->
                when {
                    fieldName == "PK" || fieldName == "SK" -> {
                    }
                    itemNode[fieldName].isInt -> item.withInt(fieldName, itemNode[fieldName].intValue())
                    itemNode[fieldName].isDouble -> item.withDouble(fieldName, itemNode[fieldName].doubleValue())
                    itemNode[fieldName].isTextual -> item.withString(fieldName, itemNode[fieldName].textValue())
                    itemNode[fieldName].isNull -> {} // item.withNull(fieldName)
                    else -> item.withString(fieldName, itemNode[fieldName].textValue())
                }
            }
            val batch =
                if (itemsBatches.isEmpty() || itemsBatches.last().itemsToPut.size >= 24) {
                    val items = TableWriteItems(TABLE_NAME)
                    itemsBatches.add(items)
                    items
                } else {
                    itemsBatches.last()
                }
            batch.addItemToPut(item)
        }

        return itemsBatches
    }
}