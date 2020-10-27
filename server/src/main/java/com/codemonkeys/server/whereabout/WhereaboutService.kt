package com.codemonkeys.server.whereabout

import com.codemonkeys.server.authorization.AuthorizationService
import com.codemonkeys.server.core.NotAuthorizedException
import com.codemonkeys.server.zone.ZoneDAO
import com.codemonkeys.shared.whereabout.IWhereaboutService
import com.codemonkeys.shared.whereabout.Whereabout

class WhereaboutService : IWhereaboutService {

    override fun getWhereabouts(authToken: String): List<Whereabout> {
        val authService = AuthorizationService()
        val groupID = authService.getGroupIDFromAuthToken(authToken)

        val whereaboutDAO = WhereaboutDAO()
        return whereaboutDAO.getWhereabouts(groupID)
    }

    override fun updateUserWhereabout(authToken: String, currentZoneID: String?) {
        val authService = AuthorizationService()
        val userID = authService.getUserIDFromAuthToken(authToken)
        val groupID = authService.getUserIDFromAuthToken(authToken)

        // We don't want to allow the user to report being in a zone that is in a different group
//        currentZoneID?.let {
//            val zoneDAO = ZoneDAO()
//            val currentZoneGroupID = zoneDAO.getZonesGroupID(listOf(currentZoneID)).firstOrNull()
//            if (currentZoneGroupID == null || currentZoneGroupID != groupID) {
//                throw NotAuthorizedException()
//            }
//        }

        val whereaboutDAO = WhereaboutDAO()
        whereaboutDAO.updateUserWhereabout(userID, currentZoneID)
    }
}