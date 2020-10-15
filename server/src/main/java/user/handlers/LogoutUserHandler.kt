package user.handlers

import core.handler.BaseHandler
import core.handler.BaseResponse
import user.UserService
import user.requests.LogoutUserRequest

class LogoutUserHandler: BaseHandler<LogoutUserRequest>() {

    override fun handle(request: LogoutUserRequest): BaseResponse {
        val userService = UserService()
        return userService.logoutUser(request)
    }
}