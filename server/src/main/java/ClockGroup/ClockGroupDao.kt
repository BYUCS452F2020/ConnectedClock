package ClockGroup
import core.dao.BaseDAO
import status.Status

class ClockGroupDao : BaseDAO() {

    // function that insert a single Group info in the table
    fun createNewGroup(singleGroup: ClockGroup): ClockGroup{
        val connection = openConnection()
        try {
            super.insertObject(ClockGroup::class.java, connection, "ClockGroup", singleGroup)
            return singleGroup
        } finally {
            closeConnection(connection)
        }
    }

    // update the AuthToken table for group Info
    private val UPDATE_AUTHTOKEN_SQL =
        """
        UPDATE AuthToken
            SET groupID=?
            WHERE userID=? AND authToken=?
        """
    fun updateGroupAuthToken(groupID: String, userID: String, authToken: String) {
        val connection = openConnection()
        try{
            val updateStatement = connection.prepareStatement(UPDATE_AUTHTOKEN_SQL)
            updateStatement.setString(1, groupID)
            updateStatement.setString(2, userID)
            updateStatement.setString(3, authToken)
            updateStatement.execute()
        } finally {
            closeConnection(connection)
        }
    }

    // get group info by groupID
    private val GET_CLOCK_GROUP_SQL =
        """
        SELECT * FROM ClockGroup
            WHERE groupID=?
        """
    fun getClockGroup(groupID: String): ClockGroup {
        val connection = openConnection()
        try{
            val getStatement = connection.prepareStatement(GET_CLOCK_GROUP_SQL)
            getStatement.setString(1, groupID)
            val resultSet = getStatement.executeQuery()
            val clockGroups = this.getQueryResults(ClockGroup::class.java, resultSet)
            // only one group will be returned at a time
            return clockGroups[0]
        } finally {
            closeConnection(connection)
        }
    }

    // add member in a group, need to update the AuthToken as well
    private val UPDATE_MEMBER_TO_GROUP_SQL =
        """
        UPDATE User
            SET groupID=?
            WHERE userID=?
        """

    fun updateMemberInGroup(groupID: String, userToAddID: String) {
        val connection = openConnection()
        try{
            val updateStatement = connection.prepareStatement(UPDATE_MEMBER_TO_GROUP_SQL)
            updateStatement.setString(1, groupID)
            updateStatement.setString(2, userToAddID)
            updateStatement.execute()
        } finally {
            closeConnection(connection)
        }
    }

    // delete member from authToken then need to update the user
    private val DELETE_MEMBER_SQL = """
        DELETE
            FROM AuthToken
            WHERE groupID=? AND userID=?;
    """
    fun updateStatuses(groupID: String, userToDeleteID: String){
        val connection = this.openConnection()
        try {
            val deleteStatement = connection.prepareStatement(DELETE_MEMBER_SQL)
            deleteStatement.setString(1, groupID)
            deleteStatement.setString(2, userToDeleteID)
            deleteStatement.execute()
        } finally {
            this.closeConnection(connection)
        }
    }

    // delete group

    // find groupID by user
    private val GET_GROUP_BY_USER_SQL = """
        SELECT User.groupID
            FROM User
            WHERE userID=?;
    """
    fun getGroupIDViaUser(userID: String): String{
        val connection = this.openConnection()
        try {
            val preparedStatement = connection.prepareStatement(GET_GROUP_BY_USER_SQL)
            preparedStatement.setString(1, userID)
            val resultSet = preparedStatement.executeQuery()
            val results = this.getSimpleQueryResults<String>(resultSet)
            return results[0]
        } finally {
            this.closeConnection(connection)
        }

    }
}