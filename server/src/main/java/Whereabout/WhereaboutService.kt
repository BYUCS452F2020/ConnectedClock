package Whereabout

import Authorization.AuthorizationService
import Core.NotAuthorizedException
import Zone.ZoneDAO

class WhereaboutService {

    fun getWhereabouts(authToken: String): List<Whereabout> {
        val authService = AuthorizationService()
        val groupID = authService.getGroupIDFromAuthToken(authToken)

        val whereaboutDAO = WhereaboutDAO()
        return whereaboutDAO.getWhereabouts(groupID)
    }

    fun updateUserWhereabout(authToken: String, currentZoneID: String?) {
        val authService = AuthorizationService()
        val userID = authService.getUserIDFromAuthToken(authToken)
        val groupID = authService.getUserIDFromAuthToken(authToken)

        // We don't want to allow the user to report being in a zone that is in a different group
        currentZoneID?.let {
            val zoneDAO = ZoneDAO()
            val currentZoneGroupID = zoneDAO.getZonesGroupID(listOf(currentZoneID)).firstOrNull()
            if (currentZoneGroupID == null || currentZoneGroupID != groupID) {
                throw NotAuthorizedException()
            }
        }

        val whereaboutDAO = WhereaboutDAO()
        whereaboutDAO.updateUserWhereabout(userID, currentZoneID)
    }
}