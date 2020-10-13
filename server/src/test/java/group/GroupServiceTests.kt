package group

import BaseTest
import clockGroup.ClockGroupService
import core.NotAuthorizedException
import org.junit.Assert
import org.junit.Test


class GroupServiceTests: BaseTest() {
    val groupService = ClockGroupService()
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

    // tests

}