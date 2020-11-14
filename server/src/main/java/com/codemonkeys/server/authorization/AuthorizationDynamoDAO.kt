package com.codemonkeys.server.authorization

import com.codemonkeys.server.core.dao.BaseDynamoDAO

class AuthorizationDynamoDAO : BaseDynamoDAO(), IAuthorizationDAO {
    override fun deleteAuthToken(authToken: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun insertAuthToken(authToken: String, userID: String, groupID: String?): Boolean {
        TODO("Not yet implemented")
    }

    override fun getUserIDFromAuthToken(authToken: String): String? {
        TODO("Not yet implemented")
    }

    override fun getGroupIDFromAuthToken(authToken: String): String? {
        TODO("Not yet implemented")
    }

}