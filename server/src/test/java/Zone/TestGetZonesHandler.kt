package Zone

import Authorization.AuthorizationTestResources
import BaseTest
import org.junit.Test
import org.junit.Assert.*

class TestGetZonesHandler : BaseTest() {

    @Test
    fun testGetZonesHandler() {
        val getZonesHandler = GetZonesHandler()

        val group1ZonesResponse =
            getZonesHandler.handleRequest(AuthorizationTestResources.GROUP_1_GROUP_AUTHTOKEN_REQUEST, null)
        assertEquals(
            "Should have gotten zones for the group with this authToken",
            ZoneTestResources.GROUP_1_GET_ZONES_RESPONSE,
            group1ZonesResponse
        )


        val group2ZonesResponse =
            getZonesHandler.handleRequest(AuthorizationTestResources.GROUP_2_USER_AUTHTOKEN_REQUEST, null)
        assertEquals(
            "Should have gotten zones for the group that the user with this authToken belongs to",
            ZoneTestResources.GROUP_2_GET_ZONES_RESPONSE,
            group2ZonesResponse
        )


        val invalidAuthTokenResponse =
            getZonesHandler.handleRequest(AuthorizationTestResources.INVALID_AUTHTOKEN_REQUEST, null)
        assertEquals(
            "Should return 'Not Authorized' due to invalid AuthToken",
            AuthorizationTestResources.NOT_AUTHORIZED_RESPONSE,
            invalidAuthTokenResponse
        )
    }
}
