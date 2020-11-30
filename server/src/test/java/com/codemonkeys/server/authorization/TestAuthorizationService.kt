package com.codemonkeys.server.authorization

import com.codemonkeys.server.BaseDynamoTest
import com.codemonkeys.server.core.NotAuthorizedException
import org.junit.Assert
import org.junit.Test

class TestAuthorizationService : BaseDynamoTest() {
    @Test
    fun testAuthorizeGroupID() {
        val authService = AuthorizationService()
        val groupID = authService.getGroupIDFromAuthToken("7af562ed-46bd-429b-8bcd-2d85e76d9a10")
        val expectedGroupID = "2bc8f348-fce4-4df6-9795-deff8e721c7a"
        Assert.assertEquals("Should find the authToken and return correct groupID", expectedGroupID, groupID)


        assertThrowsException("Should have thrown 'NotAuthorizedException' because invalid AuthToken",
            NotAuthorizedException::class.java
        ) {
            val invalidUserID = authService.getUserIDFromAuthToken("invalidauthtoken")
        }
    }

    @Test
    fun testAuthorizeUserID() {
        val authService = AuthorizationService()
        val userID = authService.getUserIDFromAuthToken("e00f1c88-1d5b-4d32-be07-1018f39a26b2")
        val expectedUserID = "3ea0f56f-7864-4d49-a5ed-5f741a7969c8"
        Assert.assertEquals("Should find the authToken and return correct userID", expectedUserID, userID)


        assertThrowsException("Should have thrown 'NotAuthorizedException' because invalid AuthToken",
            NotAuthorizedException::class.java
        ) {
            val invalidUserID = authService.getUserIDFromAuthToken("invalidauthtoken")
        }
    }
}