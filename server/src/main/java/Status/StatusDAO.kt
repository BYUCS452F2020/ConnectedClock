package Status

import Core.DAO.BaseDAO

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
    public fun getStatuses(groupID: String): List<Status> {
        val connection = this.openConnection()

        val statement = connection.prepareStatement(GET_STATUSES_SQL)
        statement.setString(1, groupID)
        val resultSet = statement.executeQuery()
        val statuses = this.getQueryResults<Status>(Status::class.java, resultSet)

        this.closeConnection(connection)

        return statuses
    }


}