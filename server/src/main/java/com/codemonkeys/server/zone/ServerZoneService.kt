package com.codemonkeys.server.zone

import com.codemonkeys.server.authorization.AuthorizationService
import com.codemonkeys.server.core.NotAuthorizedException
import com.codemonkeys.server.status.ServerStatusService
import com.codemonkeys.shared.zone.IZoneService
import com.codemonkeys.shared.zone.Zone

class ServerZoneService : IZoneService {
    private val authorizationService = AuthorizationService()
    private val statusService = ServerStatusService()
//    private val zoneDAO = ZoneSqlDAO()
    private val zoneDAO = ZoneDynamoDAO()

    override fun getZones(authToken: String): List<Zone> {
        val groupID = authorizationService.getGroupIDFromAuthToken(authToken)

        return zoneDAO.getZones(groupID)
    }

    override fun updateZones(authToken: String, updatedZones: List<Zone>) {
        val groupID = authorizationService.getGroupIDFromAuthToken(authToken)

        // Ensure the updated zones are only placed in statuses belonging to this group.
        val statusIDs = statusService.getStatuses(authToken).map { it.statusID }.toSet()
        updatedZones.forEach {
            if (it.statusID !in statusIDs) {
                throw NotAuthorizedException()
            }
        }

        zoneDAO.updateZones(groupID, updatedZones)
    }
}