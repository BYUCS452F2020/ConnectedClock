package Zone

import BaseTest
import Group.GroupTestResources
import org.junit.Test
import org.junit.Assert.*

class TestZoneDAO : BaseTest() {

    @Test
    fun testGetZones() {
        val zoneDAO = ZoneDAO()

        val group1Zones = zoneDAO.getZones(GroupTestResources.GROUP_1_ID)
        assertEquals("Should get zones from just this group", ZoneTestResources.GROUP_1_ZONES, group1Zones)


        val group2Zones = zoneDAO.getZones(GroupTestResources.GROUP_2_ID)
        assertEquals("Should zones from just this group", ZoneTestResources.GROUP_2_ZONES, group2Zones)


        val invalidGroupIDZones = zoneDAO.getZones("invalidGroupID")
        assertEquals("Should return an empty list if invalid groupID", listOf<Zone>(), invalidGroupIDZones)
    }

    @Test
    fun testUpdateZones() {
        val zoneDAO = ZoneDAO()

        zoneDAO.updateZones(GroupTestResources.GROUP_2_ID, ZoneTestResources.GROUP_2_UPDATED_ZONES)
        val group2UpdatedZones = zoneDAO.getZones(GroupTestResources.GROUP_2_ID)
        assertEquals("The zones for group 2 should be updated", ZoneTestResources.GROUP_2_UPDATED_ZONES.sortedBy { it.zoneID }, group2UpdatedZones.sortedBy { it.zoneID })


        val group1Zones = zoneDAO.getZones(GroupTestResources.GROUP_1_ID)
        assertEquals("The zones for group 1 shouldn't have been affected", ZoneTestResources.GROUP_1_ZONES, group1Zones)


        zoneDAO.updateZones(GroupTestResources.GROUP_2_ID, listOf<Zone>())
        val emptyZones = zoneDAO.getZones(GroupTestResources.GROUP_2_ID)
        assertEquals("All the zones for this group should've been deleted.", listOf<Zone>(), emptyZones)
    }
}