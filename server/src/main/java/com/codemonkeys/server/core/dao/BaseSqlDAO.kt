package com.codemonkeys.server.core.dao

import com.codemonkeys.shared.core.dao.ColumnAnnotation
import java.lang.reflect.Field
import java.sql.Connection
import java.sql.DriverManager
import java.sql.PreparedStatement
import java.sql.ResultSet

// https://www.mysqltutorial.org/mysql-jdbc-tutorial/
open class BaseSqlDAO : BaseDAO() {
    // https://docs.aws.amazon.com/elasticbeanstalk/latest/dg/java-rds.html
    protected fun openConnection(): Connection {
        val url = System.getenv("RDS_DB_URL")
        val user = System.getenv("RDS_USERNAME")
        val password = System.getenv("RDS_PASSWORD")
        val connection = DriverManager.getConnection(url, user, password)
        connection.autoCommit = false
        return connection
    }

    protected fun closeConnection(connection: Connection) {
        connection.close()
    }

    // https://stackoverflow.com/a/46359673/6634972 Automatically mapping from ResultSet to Java Object
    protected fun <T> getQueryResults(clazz: Class<T>, resultSet: ResultSet): List<T> {
        val columnNamesAndFields = getColumnNamesAndFields(clazz)
        val resultList = mutableListOf<T>()
        while (resultSet.next()) {
            val resultObject = clazz.getConstructor().newInstance()
            for ((columnName, field) in columnNamesAndFields) {
                val columnValue = resultSet.getObject(columnName)

                field.isAccessible = true
                field.set(resultObject, columnValue)
            }
            resultList.add(resultObject)
        }
        return resultList
    }

    protected inline fun <reified T> getSimpleQueryResults(resultSet: ResultSet): List<T> {
        val resultList = mutableListOf<T>()
        while (resultSet.next()) {
            val resultValue = resultSet.getObject(1) as T?
            resultValue?.let {
                resultList.add(it)
            }
        }
        return resultList
    }

    // If our SQL files have multiple statements, they need to be split up and added together as a batch.
    protected fun executeBatch(connection: Connection, sql: String) {
        val statements = sql.split(";")
        val batchStatement = connection.createStatement()
        statements.forEach {
            if (!it.isBlank()) {
                val statement = "$it;"
                batchStatement.addBatch(statement)
            }
        }
        batchStatement.executeBatch()
    }

    // Inserts a single object into 'tableName' using the ColumnAnnotations to match up fields with columns.
    protected fun <T> insertObject(clazz: Class<T>, connection: Connection, tableName: String, objectToInsert: T) {
        val preparedStatement = createInsertObjectPreparedStatement(clazz, connection, tableName)
        fillPreparedStatement(objectToInsert, preparedStatement, getColumnNamesAndFields(clazz))
        preparedStatement.execute()
    }

    // Inserts a list of objects into 'tableName' using the ColumnAnnotations to match up fields with columns.
    protected fun <T> insertObjects(
        clazz: Class<T>,
        connection: Connection,
        tableName: String,
        objectsToInsert: List<T>
    ) {
        val preparedStatement = createInsertObjectPreparedStatement(clazz, connection, tableName)
        val columnNamesAndFields = getColumnNamesAndFields(clazz)
        objectsToInsert.forEach {
            fillPreparedStatement(it, preparedStatement, columnNamesAndFields)
            preparedStatement.addBatch()
        }
        preparedStatement.executeBatch()
    }

    // Creates a PreparedStatement to insert an object into a table.
    private fun <T> createInsertObjectPreparedStatement(
        clazz: Class<T>,
        connection: Connection,
        tableName: String
    ): PreparedStatement {

        val columnNamesAndFields = getColumnNamesAndFields(clazz)
        val columnNames = columnNamesAndFields.map { (columnName, field) -> columnName }
        val columnNamesString = columnNames.joinToString(", ")
        val valuesQuestionMarks = (List(columnNames.size) { "?" }).joinToString(", ")
        val sqlString = "INSERT INTO $tableName ($columnNamesString) VALUES ($valuesQuestionMarks);"
        return connection.prepareStatement(sqlString)
    }

    // Takes a prepared statement and fills in the ?'s with values from fields that have ColumnAnnotations
    private fun <T> fillPreparedStatement(
        objectToFill: T,
        statement: PreparedStatement,
        columnNamesAndFields: List<Pair<String, Field>>
    ) {
        columnNamesAndFields.forEachIndexed { i, (_, field) ->
            field.isAccessible = true
            statement.setObject(i + 1, field.get(objectToFill)) // For some reason the index for statement ?'s starts at 1...
        }
    }
}