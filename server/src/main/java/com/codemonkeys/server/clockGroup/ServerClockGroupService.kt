package com.codemonkeys.server.clockGroup

import com.codemonkeys.server.authorization.AuthorizationService
import com.codemonkeys.server.core.NotAuthorizedException
import com.codemonkeys.shared.clockGroup.ClockGroup
import com.codemonkeys.shared.clockGroup.IClockGroupService
import java.util.*

class ServerClockGroupService : IClockGroupService {
    private val authorizationService = AuthorizationService()

    override fun createGroup(authToken: String, groupName: String, groupPassword: String){

        val clockGroupDao = ClockGroupSqlDAO()
        // generate a unique group id for the group, and create clockGroup Object
        val groupID = UUID.randomUUID().toString()
        // create a ClockGroup Object
        val clockGroup = ClockGroup()
        clockGroup.groupID = groupID
        clockGroup.groupName = groupName
        clockGroup.groupPassword = groupPassword
        // get the userID
        val userID = authorizationService.getUserIDFromAuthToken(authToken)
        // insert group info in the group table
        clockGroupDao.createNewGroup(clockGroup)
        // update groupID in User table
        clockGroupDao.updateGroupInUser(groupID, userID)
    }

    override fun getGroup(authToken: String, groupPassword: String): ClockGroup {
        val clockGroupDao = ClockGroupSqlDAO()
        val userID = authorizationService.getUserIDFromAuthToken(authToken)
        val groupID = clockGroupDao.getGroupIDViaUser(userID)
        val clockGroup = clockGroupDao.getClockGroup(groupID)
        if (clockGroup.groupPassword != groupPassword){
            throw NotAuthorizedException()
        }
        return clockGroup
    }

    override fun addMemberToGroup (authToken: String, userNameToAdd: String, password: String) {
        val clockGroupDao = ClockGroupSqlDAO()
        // get the current User ID
        val userID = authorizationService.getUserIDFromAuthToken(authToken)
        // get the groupID
        val groupID = clockGroupDao.getGroupIDViaUser(userID)
        // get the userID need to be add
        val userToAddID = clockGroupDao.getUserIDViaUserName(userNameToAdd)
        // compare the passWord
        val storedPassWord = clockGroupDao.getClockGroup(groupID).groupPassword
        if (password == storedPassWord) {
            // update in user table
            clockGroupDao.updateGroupInUser(groupID, userToAddID)
        } else {
            throw NotAuthorizedException()
        }
    }

    // more of you remove yourself from the group
    override fun deleteMemberFromGroup (authToken: String) {
        val clockGroupDao = ClockGroupSqlDAO()
        // get the current User ID
        val userID = authorizationService.getUserIDFromAuthToken(authToken)
        clockGroupDao.updateGroupInUser("", userID)
    }

    override fun deleteGroup(authToken: String, password: String) {
        val clockGroupDao = ClockGroupSqlDAO()
        val userID = authorizationService.getUserIDFromAuthToken(authToken)
        val groupID = clockGroupDao.getGroupIDViaUser(userID)
        val storedPassWord = clockGroupDao.getClockGroup(groupID).groupPassword
        if (password == storedPassWord) {
            // update in user table
            clockGroupDao.deleteGroup(groupID)
        } else {
            throw NotAuthorizedException()
        }
    }

    override fun loginGroup(groupName: String, groupPassword: String): String {
        val clockGroupDao = ClockGroupSqlDAO()
        val newAuthToken = UUID.randomUUID().toString()
        val groupId = clockGroupDao.getGroupIDViaGroupName(groupName)
        if (groupId == ""){
            throw NotAuthorizedException()
        }
        val password = clockGroupDao.getClockGroup(groupId).groupPassword
        if (password != groupPassword){
            throw NotAuthorizedException()
        }
        clockGroupDao.setAuthTokenTable(newAuthToken, groupId)
        return newAuthToken
    }
}