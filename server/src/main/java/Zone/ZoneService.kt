package Zone

import Authorization.AuthorizationService
import Core.NotAuthorizedException
import Status.StatusService

class ZoneService {
    fun getZones(authToken: String): List<Zone> {
        val authService = AuthorizationService()
        val groupID = authService.getGroupIDFromAuthToken(authToken)

        val zoneDAO = ZoneDAO()
        return zoneDAO.getZones(groupID)
    }

    fun updateZones(authToken: String, updatedZones: List<Zone>) {
        val authService = AuthorizationService()
        val groupID = authService.getGroupIDFromAuthToken(authToken)

        // Ensure the updated zones are only placed in statuses belonging to this group.
        val statusService = StatusService()
        val statusIDs = statusService.getStatuses(authToken).map { it.statusID }.toSet()
        updatedZones.forEach {
            if (it.statusID !in statusIDs) {
                throw NotAuthorizedException()
            }
        }

        val zoneDAO = ZoneDAO()
        zoneDAO.updateZones(groupID, updatedZones)
    }
}