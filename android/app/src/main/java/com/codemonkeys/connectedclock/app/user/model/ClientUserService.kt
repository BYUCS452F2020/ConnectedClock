package com.codemonkeys.connectedclock.app.user.model

import com.codemonkeys.connectedclock.app.core.BaseClientService
import com.codemonkeys.shared.user.IUserService
import com.codemonkeys.shared.user.User
import com.codemonkeys.shared.user.requests.CreateUserRequest
import com.codemonkeys.shared.user.requests.LoginUserRequest
import com.codemonkeys.shared.user.requests.LogoutUserRequest
import com.codemonkeys.shared.user.requests.UpdateUserRequest
import com.codemonkeys.shared.user.responses.CreateUserResponse
import com.codemonkeys.shared.user.responses.LoginUserResponse
import com.codemonkeys.shared.user.responses.LogoutUserResponse
import com.codemonkeys.shared.user.responses.UpdateUserResponse
import retrofit2.Retrofit
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ClientUserService(
    private val retrofit: Retrofit

) : IUserService, @Inject BaseClientService() {

    private val networkAPI = retrofit.create<IUserNetworkAPI>(
        IUserNetworkAPI::class.java
    )

    override fun createUser(username: String, password: String, groupID: String, clockHand: Int): CreateUserResponse {
        User()
        val request = CreateUserRequest(User(UUID.randomUUID().toString(), groupID, username,
            password, clockHand,null))
        return this.executeApiCall(networkAPI.createUser(request))
    }

    override fun loginUser(username: String, password: String): LoginUserResponse {
        val request = LoginUserRequest(username, password)
        return this.executeApiCall(networkAPI.loginUser(request))
    }

    override fun logoutUser(authToken: String): LogoutUserResponse {
        val request = LogoutUserRequest(authToken)
        return this.executeApiCall(networkAPI.logoutUser(request))
    }

    override fun updateUser(
        authToken: String,
        currentPassword: String,
        newPassword: String
    ): UpdateUserResponse {
        val request = UpdateUserRequest(authToken, currentPassword, newPassword)
        return this.executeApiCall(networkAPI.updateUser(request))
    }
}