package com.codemonkeys.server.user

import com.codemonkeys.server.core.dao.BaseDynamoDAO
import com.codemonkeys.shared.user.User
import com.codemonkeys.shared.user.requests.CreateUserRequest
import com.codemonkeys.shared.user.requests.LoginUserRequest
import com.codemonkeys.shared.user.requests.LogoutUserRequest
import com.codemonkeys.shared.user.requests.UpdateUserRequest
import com.codemonkeys.shared.user.responses.CreateUserResponse
import com.codemonkeys.shared.user.responses.LoginUserResponse
import com.codemonkeys.shared.user.responses.LogoutUserResponse
import com.codemonkeys.shared.user.responses.UpdateUserResponse

class UserDynamoDAO : BaseDynamoDAO(), IUserDAO {
    override fun createUser(request: CreateUserRequest): CreateUserResponse {
        TODO("Not yet implemented")
    }

    override fun getUser(userID: String): User? {
        TODO("Not yet implemented")
    }

    override fun loginUser(request: LoginUserRequest): LoginUserResponse {
        TODO("Not yet implemented")
    }

    override fun logoutUser(request: LogoutUserRequest): LogoutUserResponse {
        TODO("Not yet implemented")
    }

    override fun updateUser(request: UpdateUserRequest): UpdateUserResponse {
        TODO("Not yet implemented")
    }
}