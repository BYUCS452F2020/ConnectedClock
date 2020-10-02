package Zone

import Authorization.AuthorizationService

class ZoneService {
    fun getZones(authToken: String): List<Zone> {
        val authService = AuthorizationService()
        val groupID = authService.getGroupIDFromAuthToken(authToken)

        val zoneDAO = ZoneDAO()
        return zoneDAO.getZones(groupID)
    }
}