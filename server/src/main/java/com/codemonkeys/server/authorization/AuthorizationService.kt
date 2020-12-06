package com.codemonkeys.server.authorization

import com.codemonkeys.server.core.NotAuthorizedException
import java.util.*

class AuthorizationService {
//    private val authorizationDAO = AuthorizationSqlDAO()
    private val authorizationDAO = AuthorizationDynamoDAO()

    fun getUserIDFromAuthToken(authToken: String): String {
        // If userID is null, that means the authToken wasn't valid so we should throw an exception.
        return authorizationDAO.getUserIDFromAuthToken(authToken) ?: throw NotAuthorizedException()
    }

    fun getGroupIDFromAuthToken(authToken: String): String {
        // If groupID is null, that means the authToken wasn't valid so we should throw an exception.
        return authorizationDAO.getGroupIDFromAuthToken(authToken) ?: throw NotAuthorizedException()
    }

    fun createAuthToken(userID: String? = null, groupID: String? = null): String {
        val token = UUID.randomUUID().toString()

        authorizationDAO.insertAuthToken(token, userID, groupID)
        return token
    }
}