package com.codemonkeys.server.whereabout

import com.codemonkeys.server.core.dao.BaseSqlDAO
import com.codemonkeys.shared.whereabout.Whereabout

class WhereaboutSqlDAO: BaseSqlDAO(), IWhereaboutDAO {
    private val GET_WHEREABOUTS_SQL = """
        SELECT userID, username, clockHandIndex, z.statusID AS statusID, IFNULL(s.clockHandAngle, 0) AS clockHandAngle
            FROM User u
                LEFT JOIN Zone z ON u.currentZoneID = z.zoneID
                LEFT JOIN Status s ON z.statusID = s.statusID
        WHERE u.groupID = ?;
    """
    override fun getWhereabouts(groupID: String): List<Whereabout> {
        val connection = this.openConnection()
        try {
            val preparedStatement = connection.prepareStatement(GET_WHEREABOUTS_SQL)
            preparedStatement.setString(1, groupID)
            val resultSet = preparedStatement.executeQuery()
            return this.getQueryResults(Whereabout::class.java, resultSet)
        }
        finally {
            connection.close()
        }
    }

    private val SET_WHEREABOUT_SQL = """
        UPDATE User
            SET currentZoneID = ?
            WHERE userID = ?;
    """
    override fun updateUserWhereabout(userID: String, currentZoneID: String?) {
        val connection = this.openConnection()
        try {
            val preparedStatement = connection.prepareStatement(SET_WHEREABOUT_SQL)
            preparedStatement.setString(1, currentZoneID)
            preparedStatement.setString(2, userID)
            preparedStatement.execute()
            connection.commit()
        } finally {
            connection.close()
        }
    }
}