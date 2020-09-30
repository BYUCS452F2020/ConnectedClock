package Core.DAO

import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet

// https://www.mysqltutorial.org/mysql-jdbc-tutorial/
open class BaseDAO {
    // https://docs.aws.amazon.com/elasticbeanstalk/latest/dg/java-rds.html
    protected fun openConnection(): Connection {
        // TODO: Connect to AWS RDS
        val url = System.getenv("RDS_DB_URL")
        val user = System.getenv("RDS_USERNAME")
        val password = System.getenv("RDS_PASSWORD")
        return DriverManager.getConnection(url, user, password)
    }

    // https://stackoverflow.com/a/46359673/6634972 Automatically mapping from ResultSet to Java Object
    protected inline fun <reified T> getQueryResults(resultSet: ResultSet): List<T> {
        val resultObjectFields = T::class.java.fields
        val resultList = mutableListOf<T>()
        while (resultSet.next()) {
            val resultObject = T::class.java.getConstructor().newInstance()
            resultObjectFields.forEach { field ->
                val columnAnnotation = field.getAnnotation(ColumnAnnotation::class.java)
                columnAnnotation?.let {
                    val columnName = columnAnnotation.columnName
                    val columnValue = resultSet.getString(columnName)
                    field.set(resultObject, field.type.getConstructor().newInstance(columnValue))
                }
            }
            resultList.add(resultObject)
        }

        return resultList
    }

    protected fun closeConnection(connection: Connection) {
        // TODO: Close connection to AWS RDS
        connection.close()
    }
}