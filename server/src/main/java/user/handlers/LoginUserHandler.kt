package user.handlers

import core.handler.BaseHandler
import core.handler.BaseResponse
import user.UserService
import user.requests.LoginUserRequest

class LoginUserHandler: BaseHandler<LoginUserRequest>() {

    override fun handle(request: LoginUserRequest): BaseResponse {
        val userService = UserService()
        return userService.loginUser(request)
    }
}