package com.codemonkeys.server.user.handlers

import com.codemonkeys.server.core.handler.BaseHandler
import com.codemonkeys.shared.core.responses.BaseResponse
import com.codemonkeys.server.user.ServerUserService
import com.codemonkeys.shared.user.requests.CreateUserRequest

class CreateUserHandler: BaseHandler<CreateUserRequest>() {

    override fun handle(request: CreateUserRequest): BaseResponse {
        val userService = ServerUserService()
        return userService.createUser(request)
    }
}