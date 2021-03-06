package com.codemonkeys.server.status

import com.codemonkeys.server.authorization.AuthorizationTestResources
import com.codemonkeys.server.BaseDynamoTest
import com.codemonkeys.server.core.NotAuthorizedException
import com.codemonkeys.shared.status.Status
import org.junit.Test
import org.junit.Assert.*

class TestStatusService : BaseDynamoTest() {
    @Test
    fun testGetStatuses() {
        val statusService = ServerStatusService()

        val group1Statuses = statusService.getStatuses(AuthorizationTestResources.GROUP_1_USER_AUTHTOKEN)
        assertEquals(
            "Should retrieve all the statuses for the group the com.codemonkeys.server.user belongs to",
            StatusTestResources.GROUP_1_STATUSES,
            group1Statuses
        )

        val group2Statuses = statusService.getStatuses(AuthorizationTestResources.GROUP_2_GROUP_AUTHTOKEN)
        assertEquals(
            "Should retrieve all the statuses for the group",
            StatusTestResources.GROUP_2_STATUSES,
            group2Statuses
        )

        assertThrowsException(
            "Should have thrown 'NotAuthorizedException' because invalid AuthToken",
            NotAuthorizedException::class.java
        ) {
            val invalidStatuses = statusService.getStatuses(AuthorizationTestResources.INVALID_AUTHTOKEN)
        }
    }

    @Test
    fun testUpdateStatuses() {
        val statusService = ServerStatusService()

        assertThrowsException(
            "Should throw Not Authorized Exception since we're trying to change a com.codemonkeys.server.status in a different groupID.",
            NotAuthorizedException::class.java
        ) {
            statusService.updateStatuses(
                AuthorizationTestResources.GROUP_2_GROUP_AUTHTOKEN,
                StatusTestResources.GROUP_2_INVALID_STATUSES
            )
        }

        statusService.updateStatuses(
            AuthorizationTestResources.GROUP_2_GROUP_AUTHTOKEN,
            StatusTestResources.GROUP_2_UPDATED_STATUSES
        )
        val group2UpdatedStatuses = statusService.getStatuses(AuthorizationTestResources.GROUP_2_GROUP_AUTHTOKEN)
        assertEquals(
            "The statuses we get back for this group should be the same as we put in. No more, no less.",
            StatusTestResources.GROUP_2_UPDATED_STATUSES.sortedBy { it.statusID },
            group2UpdatedStatuses
        )
        val group1Statuses = statusService.getStatuses(AuthorizationTestResources.GROUP_1_GROUP_AUTHTOKEN)
        assertEquals("Other statuses shouldn't be affected.", StatusTestResources.GROUP_1_STATUSES, group1Statuses)

        statusService.updateStatuses(AuthorizationTestResources.GROUP_2_USER_AUTHTOKEN, listOf())
        val emptyStatuses = statusService.getStatuses(AuthorizationTestResources.GROUP_2_USER_AUTHTOKEN)
        assertEquals("All the statuses for this group should've been deleted.", listOf<Status>(), emptyStatuses)
    }
}