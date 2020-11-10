package com.codemonkeys.server.user

import com.codemonkeys.shared.user.User
import com.codemonkeys.shared.user.requests.CreateUserRequest
import com.codemonkeys.shared.user.requests.LoginUserRequest
import com.codemonkeys.shared.user.requests.LogoutUserRequest
import com.codemonkeys.shared.user.requests.UpdateUserRequest
import com.codemonkeys.shared.user.responses.CreateUserResponse
import com.codemonkeys.shared.user.responses.LoginUserResponse
import com.codemonkeys.shared.user.responses.LogoutUserResponse
import com.codemonkeys.shared.user.responses.UpdateUserResponse

interface IUserDAO {
    fun createUser(request: CreateUserRequest): CreateUserResponse

    fun getUser(userID: String): User?

    fun loginUser(request: LoginUserRequest): LoginUserResponse

    fun logoutUser(request: LogoutUserRequest): LogoutUserResponse

    fun updateUser(request: UpdateUserRequest): UpdateUserResponse
}