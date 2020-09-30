package Status

import Core.DAO.BaseDAO

class StatusDAO : BaseDAO() {
    private val GET_STATUSES_SQL =
        "SELECT *" +
        "FROM Status" +
        "WHERE groupID = ?"

    // https://www.mysqltutorial.org/mysql-jdbc-tutorial/
    public fun getStatuses(groupID: String): List<Status> {
        val connection = this.openConnection()

        val statement = connection.prepareStatement(GET_STATUSES_SQL)
        statement.setString(0, groupID)
        val resultSet = statement.executeQuery()
        val statuses = this.getQueryResults<Status>(resultSet)

        this.closeConnection(connection)

        return statuses
    }
}