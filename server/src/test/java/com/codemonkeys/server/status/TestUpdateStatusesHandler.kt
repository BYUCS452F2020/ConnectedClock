package com.codemonkeys.server.status

import com.codemonkeys.server.authorization.AuthorizationTestResources
import com.codemonkeys.server.BaseDynamoTest
import com.codemonkeys.server.BaseSqlTest
import com.codemonkeys.shared.core.responses.BaseResponse
import com.codemonkeys.server.clockGroup.GroupTestResources
import com.codemonkeys.server.status.handlers.UpdateStatusesHandler
import com.codemonkeys.shared.status.requests.UpdateStatusesRequest
import org.junit.Test
import org.junit.Assert.*

class TestUpdateStatusesHandler : BaseSqlTest() {

    @Test
    fun testUpdateStatusesHandler() {
        val statusDAO = StatusSqlDAO()
        val updateStatusesHandler = UpdateStatusesHandler()

        val invalidGroupIDResponse = updateStatusesHandler.handleRequest(StatusTestResources.GROUP_2_INVALID_STATUSES_REQUEST, null)
        assertEquals("Should return Not Authorized Response", AuthorizationTestResources.NOT_AUTHORIZED_RESPONSE, invalidGroupIDResponse)

        val newStatusesResponse = updateStatusesHandler.handleRequest(StatusTestResources.GROUP_2_UPDATED_STATUSES_REQUEST, null)
        assertEquals("Should be successful",
            BaseResponse(), newStatusesResponse)

        val updatedStatuses = statusDAO.getStatuses(GroupTestResources.GROUP_2_ID)
        assertEquals(
            "The statuses we get back for this group should be the same as we put in. No more, no less.",
            StatusTestResources.GROUP_2_UPDATED_STATUSES.sortedBy { it.statusID },
            updatedStatuses
        )
        val otherStatuses = statusDAO.getStatuses(GroupTestResources.GROUP_1_ID)
        assertEquals("Other statuses shouldn't be affected.", StatusTestResources.GROUP_1_STATUSES, otherStatuses)

        val invalidAuthTokenRequest = UpdateStatusesRequest(
            "invalidAuthToken",
            listOf()
        )
        val invalidAuthTokenResponse = updateStatusesHandler.handleRequest(invalidAuthTokenRequest, null)
        val expectedInvalidAuthTokenResponse =
            BaseResponse("Not Authorized")
        assertEquals("Should return Not Authorized Response since bad AuthToken", expectedInvalidAuthTokenResponse, invalidAuthTokenResponse)
    }
}