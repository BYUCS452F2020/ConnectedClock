package com.codemonkeys.server.clockGroup

import com.codemonkeys.server.BaseDynamoTest
import com.codemonkeys.shared.clockGroup.ClockGroup
import org.junit.Assert
import org.junit.Test

class GroupDynamoDAOTests: BaseDynamoTest() {

    val groupDAO = ClockGroupDynamoDAO()

    @Test
    fun getGroup_GroupNotExist_Test() {
        val groupID = "createGroupTestWhenNotExistID"

        val result = groupDAO.getClockGroup(groupID)
        Assert.assertEquals("", result.groupPassword)
        Assert.assertEquals("", result.groupName)
        Assert.assertEquals("", result.groupID)
    }

    @Test
    fun getGroup_GroupExist_Test() {
        val groupID = "98729fce-0809-43fe-b953-f48b14b07616"

        val result = groupDAO.getClockGroup(groupID)
        Assert.assertEquals("Group1", result.groupPassword)
        Assert.assertEquals("group1password", result.groupName)
        Assert.assertEquals(groupID, result.groupID)
    }

    @Test
    fun createGroup_Test() {
        val groupID = "createGroupTestID"
        val groupName = "createGroupTestName"
        val groupPassword = "createGroupTestPassword"
        // get the group first, should be nothing
        val initialResult = groupDAO.getClockGroup(groupID)
        Assert.assertEquals("", initialResult.groupPassword)
        Assert.assertEquals("", initialResult.groupName)
        Assert.assertEquals("", initialResult.groupID)
        // create group on DynamoDB
        val clockGroup = ClockGroup()
        clockGroup.groupID = groupID
        clockGroup.groupName = groupName
        clockGroup.groupPassword = groupPassword
        groupDAO.createNewGroup(clockGroup)
        // assert if the group get created
        val afterResult = groupDAO.getClockGroup(groupID)
        Assert.assertEquals(groupPassword, initialResult.groupPassword)
        Assert.assertEquals(groupName, initialResult.groupName)
        Assert.assertEquals(groupID, initialResult.groupID)
    }
}