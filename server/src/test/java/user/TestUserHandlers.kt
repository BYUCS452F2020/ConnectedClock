package user

import BaseTest
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

class TestUserHandlers : BaseTest() {

    @Test
    fun testCreateUser() {
        val handler = CreateUserHandler()
        val testUser = User("TEST_USER_ID", "98729fce-0809-43fe-b953-f48b14b07616","TEST_USER_NAME",
            "TEST_PASSWORD", 5, "TEST_ZONE_ID")
        val request = CreateUserRequest(testUser)
        val response = handler.handle(request)

        assertFalse(response.didErrorOccur)
        assertNull(response.errorMessage)

        val testUser2 = User("TEST_USER_ID", "BAD_GROUP_ID","TEST_USER_NAME",
            "TEST_PASSWORD", 5, "TEST_ZONE_ID")
        val request2 = CreateUserRequest(testUser2)
        val response2 = handler.handle(request2)

        assertTrue(response2.didErrorOccur)
        assertNotNull(response2.errorMessage)
    }

    @Test
    fun testUpdateUser() {
        val handler = UpdateUserHandler()
        val request = UpdateUserRequest("e00f1c88-1d5b-4d32-be07-1018f39a26b2", "Ginger4Life", "WhyHarry")
        val response = handler.handle(request)

        assertFalse(response.didErrorOccur)
        assertNull(response.errorMessage)

        val request2 = UpdateUserRequest("BAD TOKEN", "Ginger4Life", "WhyHarry")
        val response2 = handler.handle(request2)

        assertTrue(response2.didErrorOccur)
        assertNotNull(response2.errorMessage)
    }

    @Test
    fun testLoginUser() {
        val handler = LoginUserHandler()
        val request = LoginUserRequest("Ron", "Ginger4Life")
        val response = handler.handle(request)

        assertFalse(response.didErrorOccur)
        assertNull(response.errorMessage)

        val request2 = LoginUserRequest("Ron", "BAD PASSWORD")
        val response2 = handler.handle(request2)

        assertTrue(response2.didErrorOccur)
        assertNotNull(response2.errorMessage)

        val request3 = LoginUserRequest("BAD_USER_NAME", "Ginger4Life")
        val response3 = handler.handle(request3)

        assertTrue(response3.didErrorOccur)
        assertNotNull(response3.errorMessage)
    }

    @Test
    fun testLogoutUser() {
        val handler = LogoutUserHandler()
        val request = LogoutUserRequest("e00f1c88-1d5b-4d32-be07-1018f39a26b2")
        val response = handler.handle(request)

        assertFalse(response.didErrorOccur)
        assertNull(response.errorMessage)

        val request2 = LogoutUserRequest("BAD_TOKEN")
        val response2 = handler.handle(request2)

        assertFalse(response2.didErrorOccur)
        assertNull(response2.errorMessage)
    }
}