package user

import user.requests.*
import user.responses.*

class UserService {
    fun createUser(request: CreateUserRequest): CreateUserResponse {
        val userDAO = UserDAO()
        return userDAO.createUser(request)
    }

    fun loginUser(request: LoginUserRequest): LoginUserResponse {
        val userDAO = UserDAO()
        return userDAO.loginUser(request)
    }

    fun logoutUser(request: LogoutUserRequest): LogoutUserResponse {
        val userDAO = UserDAO()
        return userDAO.logoutUser(request)
    }

    fun updateUser(request: UpdateUserRequest): UpdateUserResponse {
        val userDAO = UserDAO()
        return userDAO.updateUser(request)
    }
}