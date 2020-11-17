package com.codemonkeys.connectedclock.app.user.model

import com.codemonkeys.shared.user.requests.CreateUserRequest
import com.codemonkeys.shared.user.requests.LoginUserRequest
import com.codemonkeys.shared.user.requests.LogoutUserRequest
import com.codemonkeys.shared.user.requests.UpdateUserRequest
import com.codemonkeys.shared.user.responses.CreateUserResponse
import com.codemonkeys.shared.user.responses.LoginUserResponse
import com.codemonkeys.shared.user.responses.LogoutUserResponse
import com.codemonkeys.shared.user.responses.UpdateUserResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface IUserNetworkAPI {
    @POST("LoginUser")
    fun loginUser(
        @Body request: LoginUserRequest
    ) : Call<LoginUserResponse>

    @POST("LogoutUser")
    fun logoutUser(
        @Body request: LogoutUserRequest
    ) : Call<LogoutUserResponse>

    @POST("CreateUser")
    fun createUser(
        @Body request: CreateUserRequest
    ) : Call<CreateUserResponse>

    @POST("UpdateUser")
    fun updateUser(
        @Body request: UpdateUserRequest
    ) : Call<UpdateUserResponse>
}