package status

import authorization.AuthorizationTestResources
import BaseTest
import core.handler.BaseResponse
import group.GroupTestResources
import org.junit.Test
import org.junit.Assert.*

class TestUpdateStatusesHandler : BaseTest() {

    @Test
    fun testUpdateStatusesHandler() {
        val statusDAO = StatusDAO()
        val updateStatusesHandler = UpdateStatusesHandler()

        val invalidGroupIDResponse = updateStatusesHandler.handleRequest(StatusTestResources.GROUP_2_INVALID_STATUSES_REQUEST, null)
        assertEquals("Should return Not Authorized Response", AuthorizationTestResources.NOT_AUTHORIZED_RESPONSE, invalidGroupIDResponse)

        val newStatusesResponse = updateStatusesHandler.handleRequest(StatusTestResources.GROUP_2_UPDATED_STATUSES_REQUEST, null)
        assertEquals("Should be successful", BaseResponse(), newStatusesResponse)

        val updatedStatuses = statusDAO.getStatuses(GroupTestResources.GROUP_2_ID)
        assertEquals(
            "The statuses we get back for this group should be the same as we put in. No more, no less.",
            StatusTestResources.GROUP_2_UPDATED_STATUSES.sortedBy { it.statusID },
            updatedStatuses
        )
        val otherStatuses = statusDAO.getStatuses(GroupTestResources.GROUP_1_ID)
        assertEquals("Other statuses shouldn't be affected.", StatusTestResources.GROUP_1_STATUSES, otherStatuses)

        val invalidAuthTokenRequest = UpdateStatusesRequest( "invalidAuthToken", listOf())
        val invalidAuthTokenResponse = updateStatusesHandler.handleRequest(invalidAuthTokenRequest, null)
        val expectedInvalidAuthTokenResponse = BaseResponse("Not Authorized")
        assertEquals("Should return Not Authorized Response since bad AuthToken", expectedInvalidAuthTokenResponse, invalidAuthTokenResponse)
    }
}