package com.codemonkeys.server.clockGroup

import com.codemonkeys.server.core.dao.BaseDynamoDAO
import com.codemonkeys.shared.clockGroup.ClockGroup

class ClockGroupDynamoDAO : BaseDynamoDAO(), IClockGroupDAO {
    override fun createNewGroup(singleGroup: ClockGroup) {
        TODO("Not yet implemented")
    }

    override fun updateGroupInUser(groupID: String, userID: String) {
        TODO("Not yet implemented")
    }

    override fun getClockGroup(groupID: String): ClockGroup {
        TODO("Not yet implemented")
    }

    override fun getGroupIDViaUser(userID: String): String {
        TODO("Not yet implemented")
    }

    override fun getUserIDViaUserName(userName: String): String {
        TODO("Not yet implemented")
    }

    override fun deleteGroup(groupID: String) {
        TODO("Not yet implemented")
    }

    override fun getGroupIDViaGroupName(groupName: String): String {
        TODO("Not yet implemented")
    }

    override fun setAuthTokenTable(authToken: String, groupID: String) {
        TODO("Not yet implemented")
    }
}