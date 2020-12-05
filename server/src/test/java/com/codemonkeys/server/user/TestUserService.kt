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

class TestUserService : BaseSqlTest() {
    @Test
    fun testCreateUser() {
        val userService = ServerUserService()
        val request =
            CreateUserRequest(UserTestResources.GROUP_1_NEW_USER_1)
        val response = userService.createUser(request.user)

        assertFalse(response.didErrorOccur)
        assertNull(response.errorMessage)

        val request2 =
            CreateUserRequest(UserTestResources.GROUP_1_NEW_USER_BAD_GROUP)
        val response2 = userService.createUser(request2.user)

//        assertTrue(response2.didErrorOccur)
//        assertNotNull(response2.errorMessage)

        val request3 =
            CreateUserRequest(UserTestResources.GROUP_1_NEW_USER_2)
        val response3 = userService.createUser(request3.user)

        assertFalse(response3.didErrorOccur)
        assertNull(response3.errorMessage)
    }

    @Test
    fun testUpdateUser() {
        val userService = ServerUserService()
        val request = UpdateUserRequest(
            AuthorizationTestResources.GROUP_1_USER_AUTHTOKEN,
            UserTestResources.GROUP_1_USER_1_PASSWORD,
            UserTestResources.GROUP_1_USER_1_NEW_PASSWORD
        )
        val response = userService.updateUser(request.authToken, request.currentPassword, request.newPassword)

        assertFalse(response.didErrorOccur)
        assertNull(response.errorMessage)

        val request2 = UpdateUserRequest(
            AuthorizationTestResources.INVALID_AUTHTOKEN,
            UserTestResources.GROUP_1_USER_1_PASSWORD,
            UserTestResources.GROUP_1_USER_1_NEW_PASSWORD
        )
        val response2 = userService.updateUser(request2.authToken, request2.currentPassword, request2.newPassword)

        assertTrue(response2.didErrorOccur)
        assertNotNull(response2.errorMessage)
    }

    @Test
    fun testLoginUser() {
        val userService = ServerUserService()
        val request = LoginUserRequest(
            UserTestResources.GROUP_1_USER_1_USERNAME,
            UserTestResources.GROUP_1_USER_1_PASSWORD
        )
        val response = userService.loginUser(request.userName, request.password)

        println(response.errorMessage)
        assertFalse(response.didErrorOccur)
        assertNull(response.errorMessage)


        val request2 = LoginUserRequest(
            UserTestResources.GROUP_1_USER_1_USERNAME,
            UserTestResources.INVALID_PASSWORD
        )
        val response2 = userService.loginUser(request2.userName, request2.password)

        assertTrue(response2.didErrorOccur)
        assertNotNull(response2.errorMessage)

        val request3 = LoginUserRequest(
            UserTestResources.INVALID_USERNAME,
            UserTestResources.GROUP_1_USER_1_PASSWORD
        )
        val response3 = userService.loginUser(request3.userName, request3.password)

        assertTrue(response3.didErrorOccur)
        assertNotNull(response3.errorMessage)
    }

    @Test
    fun testLogoutUser() {
        val userService = ServerUserService()
        val request =
            LogoutUserRequest(AuthorizationTestResources.GROUP_1_USER_AUTHTOKEN)
        val response = userService.logoutUser(request.authToken)

        println(response.errorMessage)
        assertFalse(response.didErrorOccur)
        assertNull(response.errorMessage)

        val request2 =
            LogoutUserRequest(AuthorizationTestResources.INVALID_AUTHTOKEN)
        val response2 = userService.logoutUser(request2.authToken)

        assertFalse(response2.didErrorOccur)
        assertNull(response2.errorMessage)
    }
}