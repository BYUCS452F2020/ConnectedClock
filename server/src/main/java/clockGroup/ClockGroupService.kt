package clockGroup

import core.NotAuthorizedException
import java.util.*

class ClockGroupService {

    fun createGroup(authToken: String, groupName: String, groupPassword: String){
        val clockGroupDao = ClockGroupDao()
        // generate a unique group id for the group, an create clockGroup Object
        val groupID = UUID.randomUUID().toString()
        // create a ClockGroup Object
        val clockGroup = ClockGroup()
        clockGroup.groupID = groupID
        clockGroup.groupName = groupName
        clockGroup.groupPassword = groupPassword
        // get the userID
        val userID = clockGroupDao.getUserIDByAuthToken(authToken)
        // update groupID in User table
        clockGroupDao.updateGroupInUser(groupID, userID)
        // insert group info in the group table
        clockGroupDao.createNewGroup(clockGroup)
    }

    fun getGroup(authToken: String, groupPassword: String): ClockGroup{
        val clockGroupDao = ClockGroupDao()
        val userID = clockGroupDao.getUserIDByAuthToken(authToken)
        if (userID == ""){
            throw NotAuthorizedException()
        }
        val groupID = clockGroupDao.getGroupIDViaUser(userID)
        val clockGroup = clockGroupDao.getClockGroup(groupID)
        if (clockGroup.groupPassword != groupPassword){
            throw NotAuthorizedException()
        }
        return clockGroup
    }

    fun addMemberToGroup (authToken: String, userNameToAdd: String, password: String) {
        val clockGroupDao = ClockGroupDao()
        // get the current User ID
        val userID = clockGroupDao.getUserIDByAuthToken(authToken)
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
    fun deleteMemberFromGroup (authToken: String) {
        val clockGroupDao = ClockGroupDao()
        // get the current User ID
        val userID = clockGroupDao.getUserIDByAuthToken(authToken)
        // set the string to "" empty
        clockGroupDao.updateGroupInUser("", userID)
    }

    fun deleteGroup(authToken: String, password: String) {
        val clockGroupDao = ClockGroupDao()
        val userID = clockGroupDao.getUserIDByAuthToken(authToken)
        val groupID = clockGroupDao.getGroupIDViaUser(userID)
        val storedPassWord = clockGroupDao.getClockGroup(groupID).groupPassword
        if (password == storedPassWord) {
            // update in user table
            clockGroupDao.deleteGroup(groupID)
        } else {
            throw NotAuthorizedException()
        }
    }
}