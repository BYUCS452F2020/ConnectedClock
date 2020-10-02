package Zone

import Core.DAO.BaseDAO

class ZoneDAO: BaseDAO() {

    private var GET_ZONES_SQL = """
        SELECT *
            FROM Zone z
                JOIN Status s ON z.statusID=s.statusID
            WHERE groupID = ?
            ORDER BY zoneID;
    """
    fun getZones(groupID: String): List<Zone> {
        val connection = openConnection()
        try {
            val statement = connection.prepareStatement(GET_ZONES_SQL)
            statement.setString(1, groupID)
            val resultSet = statement.executeQuery()
            val zones = this.getQueryResults<Zone>(Zone::class.java, resultSet)
            return zones
        }
        finally {
            closeConnection(connection)
        }
    }
}