package com.codemonkeys.server.clockGroup

import com.codemonkeys.server.authorization.AuthorizationService
import com.codemonkeys.server.core.NotAuthorizedException
import com.codemonkeys.shared.clockGroup.ClockGroup
import com.codemonkeys.shared.clockGroup.IClockGroupService
import java.util.*

class ServerClockGroupService : IClockGroupService {
    private val authorizationService = AuthorizationService()
//    private val clockGroupDAO = ClockGroupSqlDAO()
    private val clockGroupDAO = ClockGroupDynamoDAO()

    override fun createGroup(groupName: String, groupPassword: String){
        // generate a unique group id for the group, and create clockGroup Object
        val groupID = UUID.randomUUID().toString()
        // create a ClockGroup Object
        val clockGroup = ClockGroup()
        clockGroup.groupID = groupID
        clockGroup.groupName = groupName
        clockGroup.groupPassword = groupPassword
        // get the userID
        // insert group info in the group table
        clockGroupDAO.createNewGroup(clockGroup)
    }

    override fun getGroup(authToken: String, groupPassword: String): ClockGroup {
        val userID = authorizationService.getUserIDFromAuthToken(authToken)
        val groupID = clockGroupDAO.getGroupIDViaUser(userID)
        val clockGroup = clockGroupDAO.getClockGroup(groupID)
        if (clockGroup.groupPassword != groupPassword){
            throw NotAuthorizedException()
        }
        return clockGroup
    }

    override fun addMemberToGroup (authToken: String, userNameToAdd: String, password: String) {
        // get the current User ID
        val userID = authorizationService.getUserIDFromAuthToken(authToken)
        // get the groupID
        val groupID = clockGroupDAO.getGroupIDViaUser(userID)
        // get the userID need to be add
        val userToAddID = clockGroupDAO.getUserIDViaUserName(userNameToAdd)
        // compare the passWord
        val storedPassWord = clockGroupDAO.getClockGroup(groupID).groupPassword
        if (password == storedPassWord) {
            // update in user table
            clockGroupDAO.updateGroupInUser(groupID, userToAddID)
        } else {
            throw NotAuthorizedException()
        }
    }

    // more of you remove yourself from the group
    override fun deleteMemberFromGroup (authToken: String) {
        // get the current User ID
        val userID = authorizationService.getUserIDFromAuthToken(authToken)
        clockGroupDAO.updateGroupInUser("", userID)
    }

    override fun deleteGroup(authToken: String, password: String) {
        val userID = authorizationService.getUserIDFromAuthToken(authToken)
        val groupID = clockGroupDAO.getGroupIDViaUser(userID)
        val storedPassWord = clockGroupDAO.getClockGroup(groupID).groupPassword
        if (password == storedPassWord) {
            // update in user table
            clockGroupDAO.deleteGroup(groupID)
        } else {
            throw NotAuthorizedException()
        }
    }

    override fun loginGroup(groupName: String, groupPassword: String): String {
        val newAuthToken = UUID.randomUUID().toString()
        val groupId = clockGroupDAO.getGroupIDViaGroupName(groupName)
        if (groupId == ""){
            throw NotAuthorizedException()
        }
        val password = clockGroupDAO.getClockGroup(groupId).groupPassword
        if (password != groupPassword){
            throw NotAuthorizedException()
        }
        clockGroupDAO.setAuthTokenTable(newAuthToken, groupId)
        return newAuthToken
    }
}