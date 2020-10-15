package whereabout

import BaseTest
import group.GroupTestResources
import org.junit.Test
import org.junit.Assert.*
import user.UserDAO
import user.UserTestResources
import zone.ZoneTestResources

class TestWhereaboutDAO: BaseTest() {

    @Test
    fun testGetWhereabouts() {
        val whereaboutDAO = WhereaboutDAO()
        val group1Whereabouts = whereaboutDAO.getWhereabouts(GroupTestResources.GROUP_1_ID)
        assertEquals("Should return the whereabouts for all users in this group", WhereaboutTestResources.GROUP_1_WHEREABOUTS, group1Whereabouts)

        val group2Whereabouts = whereaboutDAO.getWhereabouts(GroupTestResources.GROUP_2_ID)
        assertEquals("Should return the whereabouts for all users in this group", WhereaboutTestResources.GROUP_2_WHEREABOUTS, group2Whereabouts)

        val invalidIDWhereabouts = whereaboutDAO.getWhereabouts(GroupTestResources.INVALID_GROUP_ID)
        assertEquals("Should return no results for invalid groupID", listOf<Whereabout>(), invalidIDWhereabouts)
    }

    @Test
    fun testUpdateUserWhereabout() {
        val whereaboutDAO = WhereaboutDAO()
        val userDAO = UserDAO()
        whereaboutDAO.updateUserWhereabout(UserTestResources.GROUP_1_USER_1_ID, WhereaboutTestResources.GROUP_1_ZONE_3_ID)
        val user1 = userDAO.getUser(UserTestResources.GROUP_1_USER_1_ID)
        assertEquals("User's current zone should be changed", WhereaboutTestResources.GROUP_1_ZONE_3_ID, user1?.currentZoneID)
        val user2 = userDAO.getUser(UserTestResources.GROUP_1_USER_2_ID)
        assertEquals("Other user's current zone should remain unchanged", null, user2?.currentZoneID)
        whereaboutDAO.updateUserWhereabout(UserTestResources.GROUP_1_USER_2_ID, WhereaboutTestResources.GROUP_1_ZONE_2_ID)
        val user2updated = userDAO.getUser(UserTestResources.GROUP_1_USER_2_ID)
        assertEquals("Other user's current zone should be updated", WhereaboutTestResources.GROUP_1_ZONE_2_ID, user2updated?.currentZoneID)


        whereaboutDAO.updateUserWhereabout(UserTestResources.GROUP_1_USER_1_ID, null)
        val user1Null = userDAO.getUser(UserTestResources.GROUP_1_USER_1_ID)
        assertNull("User's current zone should be changed to null",  user1Null?.currentZoneID)
        val user2Null = userDAO.getUser(UserTestResources.GROUP_1_USER_2_ID)
        assertEquals("Other user's current zone should remain unchanged", WhereaboutTestResources.GROUP_1_ZONE_2_ID, user2Null?.currentZoneID)


        whereaboutDAO.updateUserWhereabout(UserTestResources.INVALID_USER_ID, WhereaboutTestResources.GROUP_1_ZONE_1_ID)
        val user1Invalid = userDAO.getUser(UserTestResources.GROUP_1_USER_1_ID)
        assertEquals("Since userID was invalid, nothing should be affected", null,  user1Invalid?.currentZoneID)
        val user2Invalid = userDAO.getUser(UserTestResources.GROUP_1_USER_2_ID)
        assertEquals("Since userID was invalid, nothing should be affected", WhereaboutTestResources.GROUP_1_ZONE_2_ID, user2Invalid?.currentZoneID)
    }
}