package com.codemonkeys.server.clockGroup

import com.codemonkeys.shared.clockGroup.ClockGroup

interface IClockGroupDAO {
    fun createNewGroup(singleGroup: ClockGroup)

    fun updateGroupInUser(groupID: String, userID: String)

    fun getClockGroup(groupID: String): ClockGroup

    fun getGroupIDViaUser(userID: String): String

    fun getUserIDViaUserName(userName: String): String

    fun deleteGroup(groupID: String)

    fun getGroupIDViaGroupName(groupName: String): String

    fun setAuthTokenTable(authToken: String, groupID: String)
}