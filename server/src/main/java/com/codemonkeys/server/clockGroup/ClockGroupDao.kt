package com.codemonkeys.server.clockGroup
import com.codemonkeys.server.core.dao.BaseDAO
import com.codemonkeys.shared.clockGroup.ClockGroup

class ClockGroupDao : BaseDAO() {

    // function that insert a single Group info in the Group table
    fun createNewGroup(singleGroup: ClockGroup){
        val connection = openConnection()
        try {
            super.insertObject(ClockGroup::class.java, connection, "ClockGroup", singleGroup)
            connection.commit()
        } finally {
            closeConnection(connection)
        }
    }

    // update the User table for group Info
    private val UPDATE_USER_SQL =
        """
        UPDATE User
            SET groupID=?
            WHERE userID=?
        """
    fun updateGroupInUser(groupID: String, userID: String) {
        val connection = openConnection()
        try{
            val updateStatement = connection.prepareStatement(UPDATE_USER_SQL)
            updateStatement.setString(1, groupID)
            updateStatement.setString(2, userID)
            updateStatement.execute()
            connection.commit()
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
            if (clockGroups.size == 0){
                // return an empty group out
                return ClockGroup(
                    groupID = groupID,
                    groupPassword = "",
                    groupName = ""
                )
            }
            else {
                return clockGroups[0]
            }

        } finally {
            closeConnection(connection)
        }
    }

    // find groupID by com.codemonkeys.server.user
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
            if (results.size == 0){
                return ""
            }
            return results[0]
        } finally {
            this.closeConnection(connection)
        }
    }

    // find userID by userName
    private val GET_USERID_BY_USERNAME_SQL = """
        SELECT User.userID
            FROM User
            WHERE userName=?;
    """
    fun getUserIDViaUserName(userName: String): String{
        val connection = this.openConnection()
        try {
            val preparedStatement = connection.prepareStatement(GET_USERID_BY_USERNAME_SQL)
            preparedStatement.setString(1, userName)
            val resultSet = preparedStatement.executeQuery()
            val results = this.getSimpleQueryResults<String>(resultSet)
            if (results.size == 0){
                return ""
            }
            return results[0]
        } finally {
            this.closeConnection(connection)
        }
    }

    private val DELETE_GROUP_SQL = """
        DELETE FROM ClockGroup
        WHERE ClockGroup.groupID=?;
    """
    fun deleteGroup(groupID: String) {
        val connection = this.openConnection()
        try {
            val deleteStatement = connection.prepareStatement(DELETE_GROUP_SQL)
            deleteStatement.setString(1, groupID)
            deleteStatement.execute()
            connection.commit()
        } finally {
            this.closeConnection(connection)
        }
    }

    // find groupId by groupName
    private val GET_GROUPID_BY_GROUPNAME_SQL = """
        SELECT groupID
            FROM ClockGroup
            WHERE groupName=?;
    """
    fun getGroupIDViaGroupName(groupName: String): String{
        val connection = this.openConnection()
        try {
            val preparedStatement = connection.prepareStatement(GET_GROUPID_BY_GROUPNAME_SQL)
            preparedStatement.setString(1, groupName)
            val resultSet = preparedStatement.executeQuery()
            val results = this.getSimpleQueryResults<String>(resultSet)
            if (results.size == 0){
                return ""
            }
            return results[0]
        } finally {
            this.closeConnection(connection)
        }
    }

    // set authToken table
    private val SET_AUTHTOKEN_SQL =
        """
        INSERT INTO AuthToken (authToken, userID, groupID)
        VALUES (?, ?, ?);
        """
    fun setAuthTokenTable(authToken: String, groupID: String) {
        val connection = this.openConnection()
        try {
            val updateStatement = connection.prepareStatement(SET_AUTHTOKEN_SQL)
            updateStatement.setString(1, authToken)
            updateStatement.setString(2, null)
            updateStatement.setString(3, groupID)
            updateStatement.execute()
            connection.commit()
        } finally {
            this.closeConnection(connection)
        }
    }
}