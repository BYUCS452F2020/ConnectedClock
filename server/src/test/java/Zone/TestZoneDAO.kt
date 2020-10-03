package Zone

import BaseTest
import Status.Status
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

    @Test
    fun testUpdateZones() {
        val zoneDAO = ZoneDAO()

        zoneDAO.updateZones(ZoneResources.GROUP_2_ID, ZoneResources.GROUP_2_UPDATED_ZONES)
        val group2UpdatedZones = zoneDAO.getZones(ZoneResources.GROUP_2_ID)
        assertEquals("The zones for group 2 should be updated", ZoneResources.GROUP_2_UPDATED_ZONES.sortedBy { it.zoneID }, group2UpdatedZones.sortedBy { it.zoneID })


        val group1Zones = zoneDAO.getZones(ZoneResources.GROUP_1_ID)
        assertEquals("The zones for group 1 shouldn't have been affected", ZoneResources.GROUP_1_ZONES, group1Zones)


        zoneDAO.updateZones(ZoneResources.GROUP_2_ID, listOf<Zone>())
        val emptyZones = zoneDAO.getZones(ZoneResources.GROUP_2_ID)
        assertEquals("All the zones for this group should've been deleted.", listOf<Zone>(), emptyZones)
    }
}