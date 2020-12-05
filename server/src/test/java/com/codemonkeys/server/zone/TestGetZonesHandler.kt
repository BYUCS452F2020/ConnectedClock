package com.codemonkeys.server.zone

import com.codemonkeys.server.authorization.AuthorizationTestResources
import com.codemonkeys.server.BaseDynamoTest
import com.codemonkeys.server.BaseSqlTest
import com.codemonkeys.server.zone.handlers.GetZonesHandler
import org.junit.Test
import org.junit.Assert.*

class TestGetZonesHandler : BaseSqlTest() {

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
            "Should have gotten zones for the group that the com.codemonkeys.server.user with this authToken belongs to",
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
