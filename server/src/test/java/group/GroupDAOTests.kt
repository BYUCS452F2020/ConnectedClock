package group

import BaseTest
import clockGroup.ClockGroup
import clockGroup.ClockGroupDao
import org.junit.Assert
import org.junit.Test


class GroupDAOTests: BaseTest() {
    var clockGroupDao = ClockGroupDao()

    // tests for getClockGroup function
    @Test
    fun getGroup_Success_Test(){
        val groupID = "eec3b172-0c9e-11eb-adc1-0242ac120002"
        val clockGroup = clockGroupDao.getClockGroup(groupID)
        Assert.assertEquals(groupID, clockGroup.groupID)
        Assert.assertEquals("Fun Group", clockGroup.groupName)
        Assert.assertEquals("funpassword", clockGroup.groupPassword)
    }

    @Test
    fun getGroup_GroupNotExist_Test(){
        val groupID = "notExistID"
        val clockGroup = clockGroupDao.getClockGroup(groupID)
        Assert.assertEquals(groupID, clockGroup.groupID)
        Assert.assertEquals("", clockGroup.groupName)
        Assert.assertEquals("", clockGroup.groupPassword)
    }

    // tests for getUserIDByAuthToken
    @Test
    fun getUserIDByAuthToken_Success_Test(){
        val authToken = "e00f1c88-1d5b-4d32-be07-1018f39a26b2"
        val userID = clockGroupDao.getUserIDByAuthToken(authToken)
        Assert.assertEquals("3ea0f56f-7864-4d49-a5ed-5f741a7969c8", userID)
    }

    @Test
    fun getUserIDByAuthToken_Fail_NotExist_Test(){
        val authToken = "notExit"
        val userID = clockGroupDao.getUserIDByAuthToken(authToken)
        Assert.assertEquals( "", userID)
    }

    // tests for getGroupIDViaUser
    @Test
    fun getGroupIDViaUser_Success_Test(){
        val userID = "3ea0f56f-7864-4d49-a5ed-5f741a7969c8"
        val groupID = clockGroupDao.getGroupIDViaUser(userID)
        Assert.assertEquals( "98729fce-0809-43fe-b953-f48b14b07616", groupID)
    }

    @Test
    fun getGroupIDViaUser_Fail_NotExist_Test(){
        val userID = "notexiststring"
        val groupID = clockGroupDao.getGroupIDViaUser(userID)
        Assert.assertEquals( "", groupID)
    }

    // tests for getUserIDViaUserName
    @Test
    fun getUserIDViaUserName_Success_Test(){
        val userName = "test1"
        val userID = clockGroupDao.getUserIDViaUserName(userName)
        Assert.assertEquals( "3ea0f56f-7864-4d49-a5ed-5f741a7969c8", userID)
    }

    @Test
    fun getUserIDViaUserName_Fail_notExit_Test(){
        val userName = "testNotExist"
        val userID = clockGroupDao.getUserIDViaUserName(userName)
        Assert.assertEquals( "", userID)
    }

    // tests for createNewGroup
    @Test
    fun createNewGroup_Success_Test(){
        val clockGroup = ClockGroup()
        val emptyResult = clockGroupDao.getClockGroup("testID")
        Assert.assertEquals( "testID", emptyResult.groupID)
        Assert.assertEquals( "", emptyResult.groupName)
        Assert.assertEquals( "", emptyResult.groupPassword)
        clockGroup.groupID = "testID"
        clockGroup.groupName = "testGroup"
        clockGroup.groupPassword = "secret"
        clockGroupDao.createNewGroup(clockGroup)
        val result = clockGroupDao.getClockGroup("testID")
        Assert.assertEquals( "testID", result.groupID)
        Assert.assertEquals( "testGroup", result.groupName)
        Assert.assertEquals( "secret", result.groupPassword)
    }

    // tests for updateGroupInUser
    @Test
    fun updateGroupInUser_Success_Test(){
        val oldGroupID = "2bc8f348-fce4-4df6-9795-deff8e721c7a"
        val newGroupID = "eec3b172-0c9e-11eb-adc1-0242ac120002"
        val userID = "f3b55f05-c33a-4ab2-9aa7-29ad8f6efa3d"
        val result = clockGroupDao.getGroupIDViaUser(userID)
        Assert.assertEquals(oldGroupID, result)
        clockGroupDao.updateGroupInUser(newGroupID, userID)
        val new_result = clockGroupDao.getGroupIDViaUser(userID)
        Assert.assertEquals(newGroupID, new_result)
    }

    // tests for deleteGroup, need test that it was deleted on all other place as well
    @Test
    fun deleteGroup_Success_Test(){
        val groupID = "eec3b172-0c9e-11eb-adc1-0242ac120002"
        val result = clockGroupDao.getClockGroup(groupID)
        Assert.assertEquals(groupID, result.groupID)
        Assert.assertEquals("Fun Group", result.groupName)
        Assert.assertEquals("funpassword", result.groupPassword)

        clockGroupDao.deleteGroup(groupID)
        val emptyResult = clockGroupDao.getClockGroup(groupID)
        Assert.assertEquals(groupID, emptyResult.groupID)
        Assert.assertEquals("", emptyResult.groupName)
        Assert.assertEquals("", emptyResult.groupPassword)
    }
}