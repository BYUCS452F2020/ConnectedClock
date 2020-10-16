package com.codemonkeys.server.user.handlers

import com.codemonkeys.server.core.handler.BaseHandler
import com.codemonkeys.shared.core.responses.BaseResponse
import com.codemonkeys.server.user.ServerUserService
import com.codemonkeys.shared.user.requests.UpdateUserRequest

class UpdateUserHandler: BaseHandler<UpdateUserRequest>() {

    override fun handle(request: UpdateUserRequest): BaseResponse {
        val userService = ServerUserService()
        return userService.updateUser(request)
    }
}