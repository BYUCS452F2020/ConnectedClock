package com.codemonkeys.shared.user

import com.codemonkeys.shared.user.responses.CreateUserResponse
import com.codemonkeys.shared.user.responses.LoginUserResponse
import com.codemonkeys.shared.user.responses.LogoutUserResponse
import com.codemonkeys.shared.user.responses.UpdateUserResponse

interface IUserService {
    fun createUser(user: User): CreateUserResponse

    fun loginUser(username: String, password: String): LoginUserResponse

    fun logoutUser(authToken: String): LogoutUserResponse

    fun updateUser(
        authToken: String,
        currentPassword: String,
        newPassword: String
    ): UpdateUserResponse
}