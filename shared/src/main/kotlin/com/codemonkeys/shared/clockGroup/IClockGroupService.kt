package com.codemonkeys.shared.clockGroup

interface IClockGroupService {
    fun createGroup(groupName: String, groupPassword: String)

    fun getGroup(authToken: String, groupPassword: String): ClockGroup

    fun addMemberToGroup (authToken: String, userNameToAdd: String, password: String)

    fun deleteMemberFromGroup (authToken: String)

    fun deleteGroup(authToken: String, password: String)

    fun loginGroup(groupName: String, groupPassword: String): String

}