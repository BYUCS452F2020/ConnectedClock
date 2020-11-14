package com.codemonkeys.server.zone

import com.codemonkeys.server.authorization.AuthorizationService
import com.codemonkeys.server.core.NotAuthorizedException
import com.codemonkeys.server.status.ServerStatusService
import com.codemonkeys.shared.zone.IZoneService
import com.codemonkeys.shared.zone.Zone

class ServerZoneService : IZoneService {
    override fun getZones(authToken: String): List<Zone> {
        val authService = AuthorizationService()
        val groupID = authService.getGroupIDFromAuthToken(authToken)

        val zoneDAO = ZoneSqlDAO()
        return zoneDAO.getZones(groupID)
    }

    override fun updateZones(authToken: String, updatedZones: List<Zone>) {
        val authService = AuthorizationService()
        val groupID = authService.getGroupIDFromAuthToken(authToken)

        // Ensure the updated zones are only placed in statuses belonging to this group.
        val statusService = ServerStatusService()
        val statusIDs = statusService.getStatuses(authToken).map { it.statusID }.toSet()
        updatedZones.forEach {
            if (it.statusID !in statusIDs) {
                throw NotAuthorizedException()
            }
        }

        val zoneDAO = ZoneSqlDAO()
        zoneDAO.updateZones(groupID, updatedZones)
    }
}