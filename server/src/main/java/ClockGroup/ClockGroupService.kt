package ClockGroup

import java.util.*

class ClockGroupService {

    fun createGroup(authToken: String, groupName: String, groupPassword: String, userID: String){
        val clockGroupDao = ClockGroupDao()
        // generate a unique group id for the group, an create clockGroup Object
        val groupID = UUID.randomUUID().toString()
        val clockGroup = ClockGroup()
        clockGroup.groupID = groupID
        clockGroup.groupName = groupName
        clockGroup.groupPassword = groupPassword
        // store the group ID in the AuthToken table
        clockGroupDao.updateGroupAuthToken(groupID, userID, authToken)
        // insert group info in the group table
        val singleGroup = ClockGroup()
        clockGroupDao.createNewGroup(singleGroup)

    }

    fun getGroup(groupPassword: String, userID: String): ClockGroup{
        // may need to use passWord for verification
        val clockGroupDao = ClockGroupDao()
        val groupID = clockGroupDao.getGroupIDViaUser(userID)
        return clockGroupDao.getClockGroup(groupID)
    }

    fun addMemberToGroup (userID: String, userToAddID: String, authToken: String) {
        val clockGroupDao = ClockGroupDao()
        // get the groupID
        val groupID = clockGroupDao.getGroupIDViaUser(userID)
        // update in user table
        clockGroupDao.updateMemberInGroup(groupID, userToAddID)
        // update in AuthToken table
        clockGroupDao.updateGroupAuthToken(groupID, userToAddID, authToken)
    }


}