package Status

import Authorization.AuthorizationTestResources
import BaseTest
import Core.Handler.AuthorizedRequest
import Core.Handler.BaseResponse
import org.junit.Test
import org.junit.Assert.*

class TestGetStatusesHandler : BaseTest() {
    @Test
    fun testGetStatusesHandler() {
        val getStatusesHandler = GetStatusesHandler()

        val group1StatusesResponse = getStatusesHandler.handleRequest(AuthorizationTestResources.GROUP_1_USER_AUTHTOKEN_REQUEST, null)
        assertEquals("Should return response with list of statuses for user's group", StatusTestResources.GROUP_1_STATUSES_RESPONSE, group1StatusesResponse)


        val validGetStatusesResponseGroupID = getStatusesHandler.handleRequest(AuthorizationTestResources.GROUP_2_GROUP_AUTHTOKEN_REQUEST, null)
        assertEquals("Should return response with list of statuses for group", StatusTestResources.GROUP_2_STATUSES_RESPONSE, validGetStatusesResponseGroupID)


        val invalidGetStatusesResponse = getStatusesHandler.handleRequest(AuthorizationTestResources.INVALID_AUTHTOKEN_REQUEST, null)
        assertEquals("Should return Not Authorized response because of invalid authToken", AuthorizationTestResources.NOT_AUTHORIZED_RESPONSE, invalidGetStatusesResponse)
    }
}