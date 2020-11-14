package com.codemonkeys.server.authorization

import com.codemonkeys.server.core.NotAuthorizedException

class AuthorizationService {
    fun getUserIDFromAuthToken(authToken: String): String {
        // If userID is null, that means the authToken wasn't valid so we should throw an exception.
        val authorizationDAO = AuthorizationSqlDAO()
        return authorizationDAO.getUserIDFromAuthToken(authToken) ?: throw NotAuthorizedException()
    }

    fun getGroupIDFromAuthToken(authToken: String): String {
        // If groupID is null, that means the authToken wasn't valid so we should throw an exception.
        val authorizationDAO = AuthorizationSqlDAO()
        return authorizationDAO.getGroupIDFromAuthToken(authToken) ?: throw NotAuthorizedException()
    }
}