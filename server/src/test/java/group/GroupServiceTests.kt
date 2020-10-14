package group

import BaseTest
import authorization.AuthorizationService
import clockGroup.ClockGroupDao
import clockGroup.ClockGroupService
import core.NotAuthorizedException
import org.junit.Assert
import org.junit.Test


class GroupServiceTests: BaseTest() {
    val groupService = ClockGroupService()
    val clockGroupDao = ClockGroupDao()
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
        try {
            groupService.getGroup(authToken, password)
        }
        catch (e: NotAuthorizedException){
            Assert.assertNotNull(e)
        }
    }

    @Test
    fun getGroup_Fail_WrongPassWord_Test(){
        val authToken = "e00f1c88-1d5b-4d32-be07-1018f39a26b2"
        val password = "wrong"
        try {
            groupService.getGroup(authToken, password)
        }
        catch (e: NotAuthorizedException){
            Assert.assertNotNull(e)
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
        try {
            groupService.createGroup(authToken, groupName, groupPassword)
        }
        catch (e: NotAuthorizedException){
            Assert.assertNotNull(e)
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
        try {
            groupService.addMemberToGroup(authToken, userNameToAdd, groupPassword)
        }
        catch (e: NotAuthorizedException){
            Assert.assertNotNull(e)
        }
    }

    // this actually should never happen
    @Test
    fun addMemberToGroup_Fail_InvalidAuthToken_Test(){
        val authToken = "wrongAuth"
        val userNameToAdd = "test3"
        val groupPassword = "pass"
        try {
            groupService.addMemberToGroup(authToken, userNameToAdd, groupPassword)
        }
        catch (e: NotAuthorizedException){
            Assert.assertNotNull(e)
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
        val groupPassword = "pass"
        try {
            groupService.deleteGroup(authToken, groupPassword)
        }
        catch (e: NotAuthorizedException){
            Assert.assertNotNull(e)
        }
    }

}