package com.codemonkeys.server.status

import com.codemonkeys.server.authorization.AuthorizationService
import com.codemonkeys.server.core.NotAuthorizedException
import com.codemonkeys.shared.status.IStatusService
import com.codemonkeys.shared.status.Status

class ServerStatusService : IStatusService {
    override fun getStatuses(authToken: String): List<Status> {
        // Any time we use an authToken, we need to either get the com.codemonkeys.server.user associated with the authToken
        // or the group associated with it.
        // If we aren't authorized, an exception is thrown and our Handler can deal with it.
        val authService = AuthorizationService()
        val groupID = authService.getGroupIDFromAuthToken(authToken)

        val statusDAO = StatusDAO()
        return statusDAO.getStatuses(groupID)
    }

    override fun updateStatuses(authToken: String, updatedStatuses: List<Status>) {
        val authService = AuthorizationService()
        val groupID = authService.getGroupIDFromAuthToken(authToken)

        // You can only update statuses in your own group.
        updatedStatuses.forEach {
            if (it.groupID != groupID)
                throw NotAuthorizedException()
        }

        val statusDAO = StatusDAO()
        statusDAO.updateStatuses(groupID, updatedStatuses)
    }
}