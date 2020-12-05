package com.codemonkeys.server.user

import com.codemonkeys.server.BaseDynamoTest
import com.codemonkeys.server.BaseSqlTest
import com.codemonkeys.server.authorization.AuthorizationTestResources
import org.junit.Assert.*
import org.junit.Test
import com.codemonkeys.shared.user.requests.CreateUserRequest
import com.codemonkeys.shared.user.requests.LoginUserRequest
import com.codemonkeys.shared.user.requests.LogoutUserRequest
import com.codemonkeys.shared.user.requests.UpdateUserRequest

class TestUserSqlDAO : BaseSqlTest() {

    @Test
    fun testCreateUser() {
        val userDAO = UserSqlDAO()
        val request =
            CreateUserRequest(UserTestResources.GROUP_1_NEW_USER_1)
        val response = userDAO.createUser(request)

        assertFalse(response.didErrorOccur)
        assertNull(response.errorMessage)

        val request2 =
            CreateUserRequest(UserTestResources.GROUP_1_NEW_USER_BAD_GROUP)
        val response2 = userDAO.createUser(request2)

        assertTrue(response2.didErrorOccur)
        assertNotNull(response2.errorMessage)

        val request3 =
            CreateUserRequest(UserTestResources.GROUP_1_NEW_USER_2)
        val response3 = userDAO.createUser(request3)

        assertFalse(response3.didErrorOccur)
        assertNull(response3.errorMessage)
    }

    @Test
    fun testUpdateUser() {
        val userDAO = UserSqlDAO()
        val request = UpdateUserRequest(
            AuthorizationTestResources.GROUP_1_USER_AUTHTOKEN,
            UserTestResources.GROUP_1_USER_1_PASSWORD,
            UserTestResources.GROUP_1_USER_1_NEW_PASSWORD
        )
        val response = userDAO.updateUser(request)

        assertFalse(response.didErrorOccur)
        assertNull(response.errorMessage)

        val request2 = UpdateUserRequest(
            AuthorizationTestResources.INVALID_AUTHTOKEN,
            UserTestResources.GROUP_1_USER_1_PASSWORD,
            UserTestResources.GROUP_1_USER_1_NEW_PASSWORD
        )
        val response2 = userDAO.updateUser(request2)

        assertTrue(response2.didErrorOccur)
        assertNotNull(response2.errorMessage)
    }

    @Test
    fun testLoginUser() {
        val userDAO = UserSqlDAO()
        val request = LoginUserRequest(
            UserTestResources.GROUP_1_USER_1_USERNAME,
            UserTestResources.GROUP_1_USER_1_PASSWORD
        )
        val response = userDAO.loginUser(request)

        assertFalse(response.didErrorOccur)
        assertNull(response.errorMessage)
        assertNotNull(response.authToken)

        val request2 = LoginUserRequest(
            UserTestResources.GROUP_1_USER_1_USERNAME,
            UserTestResources.INVALID_PASSWORD
        )
        val response2 = userDAO.loginUser(request2)

        assertTrue(response2.didErrorOccur)
        assertNotNull(response2.errorMessage)
        assertNull(response2.authToken)

        val request3 = LoginUserRequest(
            UserTestResources.INVALID_USERNAME,
            UserTestResources.GROUP_1_USER_1_PASSWORD
        )
        val response3 = userDAO.loginUser(request3)

        assertTrue(response3.didErrorOccur)
        assertNotNull(response3.errorMessage)
        assertNull(response3.authToken)
    }

    @Test
    fun testLogoutUser() {
        val userDAO = UserSqlDAO()
        val request =
            LogoutUserRequest(AuthorizationTestResources.GROUP_1_USER_AUTHTOKEN)
        val response = userDAO.logoutUser(request)

        assertFalse(response.didErrorOccur)
        assertNull(response.errorMessage)

        val request2 =
            LogoutUserRequest(AuthorizationTestResources.INVALID_AUTHTOKEN)
        val response2 = userDAO.logoutUser(request2)

        assertFalse(response2.didErrorOccur)
        assertNull(response2.errorMessage)
    }
}