package com.codemonkeys.server.clockGroup

import com.codemonkeys.server.BaseDynamoTest
import com.codemonkeys.server.BaseSqlTest
import com.codemonkeys.server.authorization.AuthorizationService
import com.codemonkeys.server.core.NotAuthorizedException
import org.junit.Assert
import org.junit.Test


class TestGroupService: BaseSqlTest() {
    val groupService = ServerClockGroupService()
    val clockGroupDao = ClockGroupSqlDAO()
    val authorizationService = AuthorizationService()

    // tests for getGroup function
    @Test
    fun getGroup_Success_Test(){
        val authToken = "e00f1c88-1d5b-4d32-be07-1018f39a26b2"
        val password = "pass"
        val clockGroup = groupService.getGroup(authToken, password)
        Assert.assertEquals("98729fce-0809-43fe-b953-f48b14b07616", clockGroup.groupID)
        Assert.assertEquals("test group", clockGroup.groupName)
        Assert.assertEquals("pass", clockGroup.groupPassword)
    }

    @Test
    fun getGroup_Fail_WrongAuthToken_Test(){
        val authToken = "wrongAuthToken"
        val password = "pass"
        assertThrowsException("Thrown 'NotAuthorizedException' for wrong authToken", NotAuthorizedException::class.java
        ) {
            groupService.getGroup(authToken, password)
        }
    }

    @Test
    fun getGroup_Fail_WrongPassWord_Test(){
        val authToken = "e00f1c88-1d5b-4d32-be07-1018f39a26b2"
        val password = "wrong"
        assertThrowsException("Thrown 'NotAuthorizedException' for wrong password", NotAuthorizedException::class.java
        ) {
            groupService.getGroup(authToken, password)
        }
    }

    // tests for createGroup function
    @Test
    fun createGroup_Success_Test(){
        val authToken = "e00f1c88-1d5b-4d32-be07-1018f39a26b2"
        val groupName = "test name"
        val groupPassword = "test pass"
        groupService.createGroup(authToken, groupName, groupPassword)
        val clockGroup = groupService.getGroup(authToken, groupPassword)
        Assert.assertEquals("test name", clockGroup.groupName)
        Assert.assertEquals("test pass", clockGroup.groupPassword)
    }

    @Test
    fun createGroup_Fail_InvalidAuthToken_Test(){
        val authToken = "wrong auth token"
        val groupName = "test name"
        val groupPassword = "test pass"
        assertThrowsException("Thrown 'NotAuthorizedException' for invalid authToken", NotAuthorizedException::class.java
        ) {
            groupService.createGroup(authToken, groupName, groupPassword)
        }
    }

    // tests for addMemberToGroup
    @Test
    fun addMemberToGroup_Success_Test(){
        val authToken = "e00f1c88-1d5b-4d32-be07-1018f39a26b2"
        val userNameToAdd = "test3"
        val groupPassword = "pass"
        groupService.addMemberToGroup(authToken, userNameToAdd, groupPassword)
        val userID = clockGroupDao.getUserIDViaUserName(userNameToAdd)
        val groupId = clockGroupDao.getGroupIDViaUser(userID)
        val groupIdFromUserWhoAdd = clockGroupDao.getGroupIDViaUser(authorizationService.getUserIDFromAuthToken(authToken))
        Assert.assertEquals(groupId, groupIdFromUserWhoAdd)
    }

    @Test
    fun addMemberToGroup_Fail_InvalidPassword_Test(){
        val authToken = "e00f1c88-1d5b-4d32-be07-1018f39a26b2"
        val userNameToAdd = "test3"
        val groupPassword = "wrong pass"
        assertThrowsException("Thrown 'NotAuthorizedException' for invalid password", NotAuthorizedException::class.java
        ) {
            groupService.addMemberToGroup(authToken, userNameToAdd, groupPassword)
        }
    }

    // this actually should never happen
    @Test
    fun addMemberToGroup_Fail_InvalidAuthToken_Test(){
        val authToken = "wrongAuth"
        val userNameToAdd = "test3"
        val groupPassword = "pass"
        assertThrowsException("Thrown 'NotAuthorizedException' for invalid authToken", NotAuthorizedException::class.java
        ) {
            groupService.addMemberToGroup(authToken, userNameToAdd, groupPassword)
        }
    }

    //tests for deleteMemberFromGroup function
    @Test
    fun deleteMemberFromGroup_Success_Test(){
        val authToken = "e00f1c88-1d5b-4d32-be07-1018f39a26b2"
        val userID = authorizationService.getUserIDFromAuthToken(authToken)
        var groupId = clockGroupDao.getGroupIDViaUser(userID)
        Assert.assertEquals("98729fce-0809-43fe-b953-f48b14b07616", groupId)
        groupService.deleteMemberFromGroup(authToken)
        groupId = clockGroupDao.getGroupIDViaUser(userID)
        Assert.assertEquals("", groupId)
    }

    // tests for deleteGroup function
    @Test
    fun deleteGroup_Success_Test(){
        val authToken = "e00f1c88-1d5b-4d32-be07-1018f39a26b2"
        val groupPassword = "pass"
        val userID = authorizationService.getUserIDFromAuthToken(authToken)
        var groupId = clockGroupDao.getGroupIDViaUser(userID)
        Assert.assertEquals("98729fce-0809-43fe-b953-f48b14b07616", groupId)

        groupService.deleteGroup(authToken, groupPassword)
        groupId = clockGroupDao.getGroupIDViaUser(userID)
        Assert.assertEquals("", groupId)
    }

    @Test
    fun deleteGroup_Fail_WrongPassWord_Test(){
        val authToken = "e00f1c88-1d5b-4d32-be07-1018f39a26b2"
        val groupPassword = "wrong pass"
        assertThrowsException("Thrown 'NotAuthorizedException' for wrong password", NotAuthorizedException::class.java
        ) {
            groupService.deleteGroup(authToken, groupPassword)
        }
    }

    // tests for loginGroup
    @Test
    fun loginGroup_Success_Test(){
        val authService = AuthorizationService()
        val authToken = groupService.loginGroup(GroupTestResources.GROUP_1_NAME, GroupTestResources.GROUP_1_PASSWORD)
        val groupID = authService.getGroupIDFromAuthToken(authToken)
        Assert.assertNotNull(groupID)
        Assert.assertNotEquals("", groupID)
        assertThrowsException("Thrown 'NotAuthorizedException' for com.codemonkeys.server.user not stored in Auth table", NotAuthorizedException::class.java
        ) {
            authService.getUserIDFromAuthToken(authToken)
        }
    }

    @Test
    fun loginGroup_Fail_InvalidGroupName_Test(){
        val password = "pass"
        val groupName = "wrong test group"
        assertThrowsException("Thrown 'NotAuthorizedException' for wrong groupName", NotAuthorizedException::class.java
        ) {
            groupService.loginGroup(groupName, password)
        }
    }

    @Test
    fun loginGroup_Fail_InvalidPassword_Test(){
        val password = "wrong pass"
        val groupName = "wrong test group"
        assertThrowsException("Thrown 'NotAuthorizedException' for wrong password", NotAuthorizedException::class.java
        ) {
            groupService.loginGroup(groupName, password)
        }
    }

}