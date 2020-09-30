package Authorization

import Core.DAO.BaseDAO

class AuthorizationDAO : BaseDAO() {
    // If the authToken belongs to a User, returns the userID of that user.
    // If the authToken belongs to either a Group or nobody, returns null.
    fun getUserIDFromAuthToken(authToken: String): String? {
        val connection = this.openConnection()
        TODO("Not Implemented Yet.")
        this.closeConnection(connection)
        return null
    }

    // If the authToken belongs to a Group, returns the Group's groupID.
    // If the authToken belongs to a User, returns the groupID that the user belongs to.
    // If the authToken belongs to neither, returns null.
    fun getGroupIDFromAuthToken(authToken: String): String? {
        val connection = this.openConnection()
        TODO("Not Implemented Yet.")
        this.closeConnection(connection)
        return null
    }
}
