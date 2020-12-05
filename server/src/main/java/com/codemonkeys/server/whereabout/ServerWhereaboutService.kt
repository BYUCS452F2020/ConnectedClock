package com.codemonkeys.server.whereabout

import com.codemonkeys.server.authorization.AuthorizationService
import com.codemonkeys.shared.whereabout.IWhereaboutService
import com.codemonkeys.shared.whereabout.Whereabout

class WhereaboutService : IWhereaboutService {
    private val authorizationService = AuthorizationService()
    private val whereaboutDAO = WhereaboutSqlDAO()

    override fun getWhereabouts(authToken: String): List<Whereabout> {
        val groupID = authorizationService.getGroupIDFromAuthToken(authToken)

        return whereaboutDAO.getWhereabouts(groupID)
    }

    override fun updateUserWhereabout(authToken: String, currentZoneID: String?) {
        val authService = AuthorizationService()
        val userID = authorizationService.getUserIDFromAuthToken(authToken)
        val groupID = authorizationService.getUserIDFromAuthToken(authToken)

        // We don't want to allow the user to report being in a zone that is in a different group
//        currentZoneID?.let {
//            val zoneDAO = ZoneDAO()
//            val currentZoneGroupID = zoneDAO.getZonesGroupID(listOf(currentZoneID)).firstOrNull()
//            if (currentZoneGroupID == null || currentZoneGroupID != groupID) {
//                throw NotAuthorizedException()
//            }
//        }

        whereaboutDAO.updateUserWhereabout(userID, currentZoneID)
    }
}