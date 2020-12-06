package com.codemonkeys.server.zone

import com.codemonkeys.server.authorization.AuthorizationTestResources
import com.codemonkeys.server.BaseDynamoTest
import com.codemonkeys.server.BaseSqlTest
import com.codemonkeys.shared.core.responses.BaseResponse
import com.codemonkeys.server.clockGroup.GroupTestResources
import com.codemonkeys.server.zone.handlers.UpdateZonesHandler
import com.codemonkeys.shared.zone.requests.UpdateZonesRequest
import org.junit.Test
import org.junit.Assert.*

//class TestUpdateZonesHandler : BaseSqlTest() {
class TestUpdateZonesHandler : BaseDynamoTest() {

    @Test
    fun testUpdateZones() {
        val zoneDAO = ZoneSqlDAO()
        val updateZonesHandler = UpdateZonesHandler()

        val invalidGroupIDResponse =
            updateZonesHandler.handleRequest(ZoneTestResources.GROUP_2_INVALID_ZONES_REQUEST, null)
        assertEquals(
            "Should return Not Authorized Response",
            AuthorizationTestResources.NOT_AUTHORIZED_RESPONSE,
            invalidGroupIDResponse
        )

        val newZonesResponse =
            updateZonesHandler.handleRequest(ZoneTestResources.GROUP_2_UPDATED_ZONES_REQUEST, null)
        assertEquals("Should be successful",
            BaseResponse(), newZonesResponse)

        val updatedZones = zoneDAO.getZones(GroupTestResources.GROUP_2_ID)
        assertEquals(
            "The zonees we get back for this group should be the same as we put in. No more, no less.",
            ZoneTestResources.GROUP_2_UPDATED_ZONES.sortedBy { it.zoneID },
            updatedZones
        )
        val otherZones = zoneDAO.getZones(GroupTestResources.GROUP_1_ID)
        assertEquals("Other zonees shouldn't be affected.", ZoneTestResources.GROUP_1_ZONES, otherZones)

        val invalidAuthTokenRequest =
            UpdateZonesRequest("invalidAuthToken", listOf())
        val invalidAuthTokenResponse = updateZonesHandler.handleRequest(invalidAuthTokenRequest, null)
        val expectedInvalidAuthTokenResponse =
            BaseResponse("Not Authorized")
        assertEquals(
            "Should return Not Authorized Response since bad AuthToken",
            expectedInvalidAuthTokenResponse,
            invalidAuthTokenResponse
        )
    }
}