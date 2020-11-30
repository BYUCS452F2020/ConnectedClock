package com.codemonkeys.server.authorization

import com.codemonkeys.server.BaseDynamoTest
import org.junit.Test
import org.junit.Assert.*

class TestAuthorizationDAO: BaseDynamoTest() {
    @Test
    fun testAuthorizeUserID() {
        val authorizationDAO = AuthorizationSqlDAO()
        val userID = authorizationDAO.getUserIDFromAuthToken("e00f1c88-1d5b-4d32-be07-1018f39a26b2")
        val expectedUserID = "3ea0f56f-7864-4d49-a5ed-5f741a7969c8"
        assertEquals("Should find the authToken and return correct userID", expectedUserID, userID)

        val invalidUserID = authorizationDAO.getUserIDFromAuthToken("invalidauthtoken")
        assertNull("Should not find authToken and should return null", invalidUserID)

        val invalidUserID2 = authorizationDAO.getUserIDFromAuthToken("7af562ed-46bd-429b-8bcd-2d85e76d9a10")
        assertNull("Should find authToken, but since it's for a group, should return null", invalidUserID2)
    }

    @Test
    fun testAuthorizeGroupID() {
        val authorizationDAO = AuthorizationSqlDAO()
        // Group's AuthToken
        val groupID = authorizationDAO.getGroupIDFromAuthToken("7af562ed-46bd-429b-8bcd-2d85e76d9a10")
        val expectedGroupID = "2bc8f348-fce4-4df6-9795-deff8e721c7a"
        assertEquals("Should find the authToken and return correct groupID", expectedGroupID, groupID)

        // User's AuthToken
        val usersGroupID = authorizationDAO.getGroupIDFromAuthToken("e00f1c88-1d5b-4d32-be07-1018f39a26b2")
        val expectedUsersGroupID = "98729fce-0809-43fe-b953-f48b14b07616"
        assertEquals("Should find the authToken, find the user associated with that authToken and return the groupID of that user", expectedUsersGroupID, usersGroupID)

        val invalidGroupID = authorizationDAO.getUserIDFromAuthToken("invalidauthtoken")
        assertNull("Should not find authToken and should return null", invalidGroupID)
    }
}