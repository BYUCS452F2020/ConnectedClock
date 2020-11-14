package com.codemonkeys.server.user

import com.codemonkeys.shared.user.IUserService
import com.codemonkeys.shared.user.requests.CreateUserRequest
import com.codemonkeys.shared.user.requests.LoginUserRequest
import com.codemonkeys.shared.user.requests.LogoutUserRequest
import com.codemonkeys.shared.user.requests.UpdateUserRequest
import com.codemonkeys.shared.user.responses.CreateUserResponse
import com.codemonkeys.shared.user.responses.LoginUserResponse
import com.codemonkeys.shared.user.responses.LogoutUserResponse
import com.codemonkeys.shared.user.responses.UpdateUserResponse

class ServerUserService : IUserService {
    override fun createUser(request: CreateUserRequest): CreateUserResponse {
        val userDAO = UserSqlDAO()
        return userDAO.createUser(request)
    }

    override fun loginUser(request: LoginUserRequest): LoginUserResponse {
        val userDAO = UserSqlDAO()
        return userDAO.loginUser(request)
    }

    override fun logoutUser(request: LogoutUserRequest): LogoutUserResponse {
        val userDAO = UserSqlDAO()
        return userDAO.logoutUser(request)
    }

    override fun updateUser(request: UpdateUserRequest): UpdateUserResponse {
        val userDAO = UserSqlDAO()
        return userDAO.updateUser(request)
    }
}