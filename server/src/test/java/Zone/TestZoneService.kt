package Zone

import Authorization.AuthorizationResources
import BaseTest
import Core.NotAuthorizedException
import org.junit.Test
import org.junit.Assert.*

class TestZoneService : BaseTest() {

    @Test
    fun testGetZones() {
        val zoneService = ZoneService()

        val group1Zones = zoneService.getZones(AuthorizationResources.GROUP_1_GROUP_AUTHTOKEN)
        assertEquals(
            "Should return zones for the group that used this authToken",
            ZoneResources.GROUP_1_ZONES,
            group1Zones
        )


        val group2Zones = zoneService.getZones(AuthorizationResources.GROUP_2_USER_AUTHTOKEN)
        assertEquals(
            "Should return zones for the group of the user that used this authToken",
            ZoneResources.GROUP_2_ZONES,
            group2Zones
        )


        this.assertThrowsException(
            "Should throw Not Authorized Exception since a bad AuthToken was used",
            NotAuthorizedException::class.java
        )
        {
            zoneService.getZones(AuthorizationResources.INVALID_AUTHTOKEN)
        }
    }
}
