package com.codemonkeys.server.authorization

interface IAuthorizationDAO {
    fun deleteAuthToken(authToken: String): Boolean

    fun insertAuthToken(authToken: String, userID: String?, groupID: String?): Boolean

    fun getUserIDFromAuthToken(authToken: String): String?

    fun getGroupIDFromAuthToken(authToken: String): String?
}