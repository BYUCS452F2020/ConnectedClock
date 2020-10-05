package status

import core.dao.BaseDAO

class StatusDAO : BaseDAO() {
    // We can include string literals by using triple quotes. It makes our SQL statements easy to read.
    // https://www.journaldev.com/17318/kotlin-string#raw-strings-8211-multiline-string
    private val GET_STATUSES_SQL =
        """
        SELECT *
            FROM Status
            WHERE groupID=?
            ORDER BY statusID;
        """

    // https://www.mysqltutorial.org/mysql-jdbc-tutorial/
    fun getStatuses(groupID: String): List<Status> {
        val connection = this.openConnection()
        try {
            val statement = connection.prepareStatement(GET_STATUSES_SQL)
            statement.setString(1, groupID)
            val resultSet = statement.executeQuery()
            val statuses = this.getQueryResults<Status>(Status::class.java, resultSet)
            return statuses
        }
        finally {
            this.closeConnection(connection)
        }
    }

    private val DELETE_GROUP_STATUSES_SQL = """
        DELETE
            FROM Status
            WHERE groupID = ?;
    """
    fun updateStatuses(groupID: String, updatedStatuses: List<Status>){
        val connection = this.openConnection()
        try {
            val deleteStatement = connection.prepareStatement(DELETE_GROUP_STATUSES_SQL)
            deleteStatement.setString(1, groupID)
            deleteStatement.execute()

            this.insertObjects(Status::class.java, connection, "Status", updatedStatuses)

            connection.commit()
        } catch (e: Exception) {
            connection.rollback()
            throw e
        }
        finally {
            this.closeConnection(connection)
        }
    }
}