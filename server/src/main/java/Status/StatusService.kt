package Status

import Authorization.AuthorizationDAO
import Core.NotAuthorizedException

class StatusService {
    fun getStatuses(authToken: String): List<Status> {
        // Any time we use an authToken, we need to either get the user associated with the authToken
        // or the group associated with it.
        val authDAO = AuthorizationDAO()
        val groupID = authDAO.getGroupIDFromAuthToken(authToken)

        // If groupID is null, that means the authToken wasn't valid so we should throw an exception.
        // Our Handler can deal with that exception and return the appropriate response.
        if (groupID == null)
        {
            throw NotAuthorizedException()
        }
        else
        {
            val statusDAO = StatusDAO()
            return statusDAO.getStatuses(groupID)
        }
    }
}