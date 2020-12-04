package com.codemonkeys.server.user.handlers

import com.codemonkeys.server.core.handler.BaseHandler
import com.codemonkeys.shared.core.responses.BaseResponse
import com.codemonkeys.server.user.ServerUserService
import com.codemonkeys.shared.user.requests.LogoutUserRequest

class LogoutUserHandler: BaseHandler<LogoutUserRequest>() {

    override fun handle(request: LogoutUserRequest): BaseResponse {
        val userService = ServerUserService()
        return userService.logoutUser(request.authToken)
    }
}