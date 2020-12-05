package com.codemonkeys.server.user.handlers

import com.codemonkeys.server.core.handler.BaseHandler
import com.codemonkeys.shared.core.responses.BaseResponse
import com.codemonkeys.server.user.ServerUserService
import com.codemonkeys.shared.user.requests.LoginUserRequest

class LoginUserHandler: BaseHandler<LoginUserRequest>() {

    override fun handle(request: LoginUserRequest): BaseResponse {
        val userService = ServerUserService()
        return userService.loginUser(request.userName, request.password)
    }
}