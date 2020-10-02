package Zone

import Authorization.AuthorizationResources
import BaseTest
import Core.Handler.AuthorizedRequest
import Core.NotAuthorizedException
import org.junit.Test
import org.junit.Assert.*

class TestGetZonesHandler : BaseTest() {

    @Test
    fun testGetZonesHandler() {
        val getZonesHandler = GetZonesHandler()

        val group1ZonesResponse =
            getZonesHandler.handleRequest(AuthorizationResources.GROUP_1_GROUP_AUTHTOKEN_REQUEST, null)
        assertEquals(
            "Should have gotten zones for the group with this authToken",
            ZoneResources.GROUP_1_GET_ZONES_RESPONSE,
            group1ZonesResponse
        )


        val group2ZonesResponse =
            getZonesHandler.handleRequest(AuthorizationResources.GROUP_2_USER_AUTHTOKEN_REQUEST, null)
        assertEquals(
            "Should have gotten zones for the group that the user with this authToken belongs to",
            ZoneResources.GROUP_2_GET_ZONES_RESPONSE,
            group2ZonesResponse
        )


        val invalidAuthTokenResponse =
            getZonesHandler.handleRequest(AuthorizationResources.INVALID_AUTHTOKEN_REQUEST, null)
        assertEquals(
            "Should return 'Not Authorized' due to invalid AuthToken",
            AuthorizationResources.NOT_AUTHORIZED_RESPONSE,
            invalidAuthTokenResponse
        )
    }
}
