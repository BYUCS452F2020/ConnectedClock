package com.codemonkeys.server.clockGroup

import com.codemonkeys.server.BaseDynamoTest
import com.codemonkeys.server.BaseSqlTest
import com.codemonkeys.server.authorization.AuthorizationService
import com.codemonkeys.server.core.NotAuthorizedException
import com.codemonkeys.server.user.UserTestResources
import com.codemonkeys.shared.clockGroup.ClockGroup
import org.junit.Assert
import org.junit.Test


class TestGroupSqlDAO: BaseSqlTest() {
    var clockGroupDao = ClockGroupSqlDAO()
    // tests for getClockGroup function
    @Test
    fun getGroup_Success_Test(){
        val groupID = GroupTestResources.GROUP_1_ID
        val clockGroup = clockGroupDao.getClockGroup(groupID)
        Assert.assertEquals(GroupTestResources.GROUP_1_ID, clockGroup.groupID)
        Assert.assertEquals(GroupTestResources.GROUP_1_NAME, clockGroup.groupName)
        Assert.assertEquals(GroupTestResources.GROUP_1_PASSWORD, clockGroup.groupPassword)
    }

    @Test
    fun getGroup_GroupNotExist_Test(){
        val groupID = GroupTestResources.INVALID_GROUP_ID
        val clockGroup = clockGroupDao.getClockGroup(groupID)
        Assert.assertEquals(groupID, clockGroup.groupID)
        Assert.assertEquals("", clockGroup.groupName)
        Assert.assertEquals("", clockGroup.groupPassword)
    }

    // tests for getGroupIDViaUser
    @Test
    fun getGroupIDViaUser_Success_Test(){
        val userID = UserTestResources.GROUP_1_USER_1_ID
        val groupID = clockGroupDao.getGroupIDViaUser(userID)
        Assert.assertEquals( GroupTestResources.GROUP_1_ID, groupID)
    }

    @Test
    fun getGroupIDViaUser_Fail_NotExist_Test(){
        val userID = UserTestResources.INVALID_USER_ID
        val groupID = clockGroupDao.getGroupIDViaUser(userID)
        Assert.assertEquals( "", groupID)
    }

    // tests for getUserIDViaUserName
    @Test
    fun getUserIDViaUserName_Success_Test(){
        val userName = UserTestResources.GROUP_1_USER_1_USERNAME
        val userID = clockGroupDao.getUserIDViaUserName(userName)
        Assert.assertEquals( UserTestResources.GROUP_2_USER_1_ID, userID)
    }

    @Test
    fun getUserIDViaUserName_Fail_notExit_Test(){
        val userName = UserTestResources.INVALID_USER_ID
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
        val oldGroupID = GroupTestResources.GROUP_2_ID
        val newGroupID = "eec3b172-0c9e-11eb-adc1-0242ac120002"
        val userID = UserTestResources.GROUP_2_USER_1_ID
        val result = clockGroupDao.getGroupIDViaUser(userID)
        Assert.assertEquals(oldGroupID, result)
        clockGroupDao.updateGroupInUser(newGroupID, userID)
        val new_result = clockGroupDao.getGroupIDViaUser(userID)
        Assert.assertEquals(newGroupID, new_result)
    }

    // tests for deleteGroup
    @Test
    fun deleteGroup_Success_Test(){
        val groupID = GroupTestResources.GROUP_1_ID
        val result = clockGroupDao.getClockGroup(groupID)
        Assert.assertEquals(groupID, result.groupID)
        Assert.assertEquals(GroupTestResources.GROUP_1_NAME, result.groupName)
        Assert.assertEquals(GroupTestResources.GROUP_1_PASSWORD, result.groupPassword)

        clockGroupDao.deleteGroup(groupID)
        val emptyResult = clockGroupDao.getClockGroup(groupID)
        Assert.assertEquals(groupID, emptyResult.groupID)
        Assert.assertEquals("", emptyResult.groupName)
        Assert.assertEquals("", emptyResult.groupPassword)
    }

    // tests for getGroupIDViaGroupName function
    @Test
    fun getGroupIDViaGroupName_Success_Test(){
        val groupName = GroupTestResources.GROUP_1_NAME
        val groupID = clockGroupDao.getGroupIDViaGroupName(groupName)
        Assert.assertEquals(GroupTestResources.GROUP_1_ID, groupID)
    }

    @Test
    fun getGroupIDViaGroupName_Fail_InvalidGroupName_Test(){
        val groupName = GroupTestResources.INVALID_GROUP_ID
        val groupID = clockGroupDao.getGroupIDViaGroupName(groupName)
        Assert.assertEquals("", groupID)
    }

    // tests for setAuthTokenTable function
    @Test
    fun setAuthTokenTable_Success_Test(){
        val authService = AuthorizationService()
        val authToken = "newly generated auth token"
        val groupID = "eec3b172-0c9e-11eb-adc1-0242ac120002"
        clockGroupDao.setAuthTokenTable(authToken, groupID)
        val result = authService.getGroupIDFromAuthToken(authToken)
        Assert.assertEquals(groupID, result)
        assertThrowsException("Thrown 'NotAuthorizedException' because userID is ont stored in", NotAuthorizedException::class.java
        ) {
            authService.getUserIDFromAuthToken(authToken)
        }
    }
}