package com.codemonkeys.server.status

import com.codemonkeys.server.BaseTest
import com.codemonkeys.server.clockGroup.GroupTestResources
import com.codemonkeys.shared.status.Status
import org.junit.Test
import org.junit.Assert.*

class TestStatusDAO: BaseTest() {
    @Test
    fun testGetStatuses() {
        val statusDAO = StatusSqlDAO()
        val group2Statuses = statusDAO.getStatuses(GroupTestResources.GROUP_2_ID)
        assertEquals("Should only return statuses belonging to group", StatusTestResources.GROUP_2_STATUSES, group2Statuses)

        val emptyStatuses = statusDAO.getStatuses(GroupTestResources.INVALID_GROUP_ID)
        val expectedEmptyStatuses = listOf<Status>()
        assertEquals("A nonexistent groupID should return an empty list of statuses", expectedEmptyStatuses, emptyStatuses)
    }

    @Test
    fun testUpdateStatuses() {
        val statusDAO = StatusSqlDAO()
        statusDAO.updateStatuses(GroupTestResources.GROUP_2_ID, StatusTestResources.GROUP_2_UPDATED_STATUSES)
        val group2UpdatedStatuses = statusDAO.getStatuses(GroupTestResources.GROUP_2_ID)
        assertEquals("The statuses we get back for this group should be the same as we put in. No more, no less.", StatusTestResources.GROUP_2_UPDATED_STATUSES.sortedBy { it.statusID }, group2UpdatedStatuses)

        val group1Statuses = statusDAO.getStatuses(GroupTestResources.GROUP_1_ID)
        assertEquals("Other statuses shouldn't be affected.", StatusTestResources.GROUP_1_STATUSES, group1Statuses)

        statusDAO.updateStatuses(GroupTestResources.GROUP_2_ID, listOf())
        val emptyStatuses = statusDAO.getStatuses(GroupTestResources.GROUP_2_ID)
        assertEquals("All the statuses for this group should've been deleted.", listOf<Status>(), emptyStatuses)
    }
}