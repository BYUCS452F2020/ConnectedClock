package Zone

import Core.DAO.BaseDAO
import java.sql.Connection
import java.sql.ResultSet

class ZoneDAO : BaseDAO() {

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
        } finally {
            closeConnection(connection)
        }
    }


    private var DELETE_GROUP_ZONES_SQL = """
        DELETE z
            FROM Zone z
                JOIN Status s ON s.statusID = z.statusID
            WHERE s.groupID = ?;
    """

    fun updateZones(groupID: String, updatedZones: List<Zone>) {
        val connection = this.openConnection()

        try {
            val deleteStatement = connection.prepareStatement(DELETE_GROUP_ZONES_SQL)
            deleteStatement.setString(1, groupID)
            deleteStatement.execute()

            this.insertObjects(Zone::class.java, connection, "Zone", updatedZones)

            connection.commit()
        } catch (e: Exception) {
            connection.rollback()
            throw e
        } finally {
            this.closeConnection(connection)
        }
    }

    private val GET_ZONES_GROUP_ID_SQL = """
        SELECT s.groupID
            FROM Zone z
                JOIN Status s ON s.statusID = z.statusID
            WHERE z.zoneID IN ?;
    """

    fun getZonesGroupID(zoneIDs: List<String>): List<String> {
        val connection = this.openConnection()
        try {
            val preparedStatement = connection.prepareStatement(GET_ZONES_GROUP_ID_SQL)
            preparedStatement.setObject(1, zoneIDs)
            val resultSet = preparedStatement.executeQuery()
            return this.getSimpleQueryResults<String>(resultSet)
        }
        finally {
            connection.close()
        }
    }
}