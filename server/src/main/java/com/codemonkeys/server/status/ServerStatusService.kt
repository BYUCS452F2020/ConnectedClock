package com.codemonkeys.server.status

import com.codemonkeys.server.authorization.AuthorizationService
import com.codemonkeys.server.core.NotAuthorizedException
import com.codemonkeys.shared.status.IStatusService
import com.codemonkeys.shared.status.Status

class ServerStatusService : IStatusService {
    val authorizationService = AuthorizationService()
//    val statusDAO = StatusSqlDAO()
    val statusDAO = StatusDynamoDAO()

    override fun getStatuses(authToken: String): List<Status> {
        // Any time we use an authToken, we need to either get the user associated with the authToken
        // or the group associated with it.
        // If we aren't authorized, an exception is thrown and our Handler can deal with it.
        val groupID = authorizationService.getGroupIDFromAuthToken(authToken)

        return statusDAO.getStatuses(groupID)
    }

    override fun updateStatuses(authToken: String, updatedStatuses: List<Status>) {
        val groupID = authorizationService.getGroupIDFromAuthToken(authToken)

        // You can only update statuses in your own group.
        updatedStatuses.forEach {
            if (it.groupID != groupID)
                throw NotAuthorizedException()
        }

        statusDAO.updateStatuses(groupID, updatedStatuses)
    }
}