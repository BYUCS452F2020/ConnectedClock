package Core.DAO

import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet

// https://www.mysqltutorial.org/mysql-jdbc-tutorial/
open class BaseDAO {
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
        val resultObjectFields = clazz.declaredFields
        val resultList = mutableListOf<T>()
        while (resultSet.next()) {
            val resultObject = clazz.getConstructor().newInstance()
            resultObjectFields.forEach { field ->
                val columnAnnotation = field.getAnnotation(ColumnAnnotation::class.java)
                columnAnnotation?.let {
                    val columnName = columnAnnotation.columnName
                    val columnValue = resultSet.getObject(columnName)

                    field.isAccessible = true
                    field.set(resultObject, columnValue)
                }
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
}