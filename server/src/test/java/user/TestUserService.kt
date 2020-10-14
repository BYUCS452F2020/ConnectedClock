package user

import BaseTest
import org.junit.Assert.*
import org.junit.Test
import user.requests.CreateUserRequest
import user.requests.LoginUserRequest
import user.requests.LogoutUserRequest
import user.requests.UpdateUserRequest

class TestUserService : BaseTest() {
    @Test
    fun testCreateUser() {
        val userDAO = UserService()
        val testUser = User("TEST_USER_ID", "98729fce-0809-43fe-b953-f48b14b07616","TEST_USER_NAME",
            "TEST_PASSWORD", 5, "TEST_ZONE_ID")
        val request = CreateUserRequest(testUser)
        val response = userDAO.createUser(request)

        assertFalse(response.didErrorOccur)
        assertNull(response.errorMessage)

        val testUser2 = User("TEST_USER_ID", "BAD_GROUP_ID","TEST_USER_NAME",
            "TEST_PASSWORD", 5, "TEST_ZONE_ID")
        val request2 = CreateUserRequest(testUser2)
        val response2 = userDAO.createUser(request2)

        assertTrue(response2.didErrorOccur)
        assertNotNull(response2.errorMessage)
    }

    @Test
    fun testUpdateUser() {
        val userDAO = UserService()
        val request = UpdateUserRequest("e00f1c88-1d5b-4d32-be07-1018f39a26b2", "Ginger4Life", "WhyHarry")
        val response = userDAO.updateUser(request)

        assertFalse(response.didErrorOccur)
        assertNull(response.errorMessage)

        val request2 = UpdateUserRequest("BAD TOKEN", "Ginger4Life", "WhyHarry")
        val response2 = userDAO.updateUser(request2)

        assertTrue(response2.didErrorOccur)
        assertNotNull(response2.errorMessage)
    }

    @Test
    fun testLoginUser() {
        val userDAO = UserService()
        val request = LoginUserRequest("Ron", "Ginger4Life")
        val response = userDAO.loginUser(request)

        assertFalse(response.didErrorOccur)
        assertNull(response.errorMessage)
        assertNotNull(response.authToken)

        val request2 = LoginUserRequest("Ron", "BAD PASSWORD")
        val response2 = userDAO.loginUser(request2)

        assertTrue(response2.didErrorOccur)
        assertNotNull(response2.errorMessage)
        assertNull(response2.authToken)

        val request3 = LoginUserRequest("BAD_USER_NAME", "Ginger4Life")
        val response3 = userDAO.loginUser(request3)

        assertTrue(response3.didErrorOccur)
        assertNotNull(response3.errorMessage)
        assertNull(response3.authToken)
    }

    @Test
    fun testLogoutUser() {
        val userDAO = UserService()
        val request = LogoutUserRequest("e00f1c88-1d5b-4d32-be07-1018f39a26b2")
        val response = userDAO.logoutUser(request)

        assertFalse(response.didErrorOccur)
        assertNull(response.errorMessage)

        val request2 = LogoutUserRequest("BAD_TOKEN")
        val response2 = userDAO.logoutUser(request2)

        assertFalse(response2.didErrorOccur)
        assertNull(response2.errorMessage)
    }
}