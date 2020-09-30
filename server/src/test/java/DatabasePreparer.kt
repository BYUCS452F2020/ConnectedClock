import Core.DAO.BaseDAO
import java.sql.Connection

class DatabasePreparer: BaseDAO() {
    // https://stackoverflow.com/a/42740416/6634972
    fun prepareDatabase() {
        val createDatabaseSQL = DatabasePreparer::class.java.getResource("DatabaseSQL/create_database.sql").readText()
        val createTestDataSQL = DatabasePreparer::class.java.getResource("DatabaseSQL/create_test_data.sql").readText()

        val connection = this.openConnection()
        this.executeBatch(connection, createDatabaseSQL)
        this.executeBatch(connection, createTestDataSQL)
        this.closeConnection(connection)
    }
}