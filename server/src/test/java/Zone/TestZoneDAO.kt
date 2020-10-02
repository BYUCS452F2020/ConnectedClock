package Zone

import BaseTest
import org.junit.Test
import org.junit.Assert.*

class TestZoneDAO : BaseTest() {

    @Test
    fun testGetZones() {
        val zoneDAO = ZoneDAO()

        val group1Zones = zoneDAO.getZones(ZoneResources.GROUP_1_ID)
        assertEquals("Should get zones from just this group", ZoneResources.GROUP_1_ZONES, group1Zones)


        val group2Zones = zoneDAO.getZones(ZoneResources.GROUP_2_ID)
        assertEquals("Should zones from just this group", ZoneResources.GROUP_2_ZONES, group2Zones)


        val invalidGroupIDZones = zoneDAO.getZones("invalidGroupID")
        assertEquals("Should return an empty list if invalid groupID", listOf<Zone>(), invalidGroupIDZones)
    }
}