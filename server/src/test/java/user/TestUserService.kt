package user

import BaseTest
import authorization.AuthorizationTestResources
import org.junit.Assert.*
import org.junit.Test
import user.handlers.CreateUserHandler
import user.handlers.LoginUserHandler
import user.handlers.LogoutUserHandler
import user.handlers.UpdateUserHandler
import user.requests.CreateUserRequest
import user.requests.LoginUserRequest
import user.requests.LogoutUserRequest
import user.requests.UpdateUserRequest

class TestUserService : BaseTest() {
    @Test
    fun testCreateUser() {
        val userService = UserService()
        val request = CreateUserRequest(UserTestResources.GROUP_1_NEW_USER_1)
        val response = userService.createUser(request)

        assertFalse(response.didErrorOccur)
        assertNull(response.errorMessage)

        val request2 = CreateUserRequest(UserTestResources.GROUP_1_NEW_USER_BAD_GROUP)
        val response2 = userService.createUser(request2)

        assertTrue(response2.didErrorOccur)
        assertNotNull(response2.errorMessage)

        val request3 = CreateUserRequest(UserTestResources.GROUP_1_NEW_USER_2)
        val response3 = userService.createUser(request3)

        assertFalse(response3.didErrorOccur)
        assertNull(response3.errorMessage)
    }

    @Test
    fun testUpdateUser() {
        val userService = UserService()
        val request = UpdateUserRequest(AuthorizationTestResources.GROUP_1_USER_AUTHTOKEN, UserTestResources.GROUP_1_USER_1_PASSWORD, UserTestResources.GROUP_1_USER_1_NEW_PASSWORD)
        val response = userService.updateUser(request)

        assertFalse(response.didErrorOccur)
        assertNull(response.errorMessage)

        val request2 = UpdateUserRequest(AuthorizationTestResources.INVALID_AUTHTOKEN, UserTestResources.GROUP_1_USER_1_PASSWORD, UserTestResources.GROUP_1_USER_1_NEW_PASSWORD)
        val response2 = userService.updateUser(request2)

        assertTrue(response2.didErrorOccur)
        assertNotNull(response2.errorMessage)
    }

    @Test
    fun testLoginUser() {
        val userService = UserService()
        val request = LoginUserRequest(UserTestResources.GROUP_1_USER_1_USERNAME, UserTestResources.GROUP_1_USER_1_PASSWORD)
        val response = userService.loginUser(request)

        assertFalse(response.didErrorOccur)
        assertNull(response.errorMessage)

        val request2 = LoginUserRequest(UserTestResources.GROUP_1_USER_1_USERNAME, UserTestResources.INVALID_PASSWORD)
        val response2 = userService.loginUser(request2)

        assertTrue(response2.didErrorOccur)
        assertNotNull(response2.errorMessage)

        val request3 = LoginUserRequest(UserTestResources.INVALID_USERNAME, UserTestResources.GROUP_1_USER_1_PASSWORD)
        val response3 = userService.loginUser(request3)

        assertTrue(response3.didErrorOccur)
        assertNotNull(response3.errorMessage)
    }

    @Test
    fun testLogoutUser() {
        val userService = UserService()
        val request = LogoutUserRequest(AuthorizationTestResources.GROUP_1_USER_AUTHTOKEN)
        val response = userService.logoutUser(request)

        assertFalse(response.didErrorOccur)
        assertNull(response.errorMessage)

        val request2 = LogoutUserRequest(AuthorizationTestResources.INVALID_AUTHTOKEN)
        val response2 = userService.logoutUser(request2)

        assertFalse(response2.didErrorOccur)
        assertNull(response2.errorMessage)
    }
}