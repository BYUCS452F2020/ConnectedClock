package com.codemonkeys.server.clockGroup.handlers

import com.codemonkeys.server.clockGroup.ServerClockGroupService
import com.codemonkeys.shared.clockGroup.responses.LoginGroupResponse
import com.codemonkeys.shared.clockGroup.requests.LoginGroupRequest
import com.codemonkeys.server.core.handler.BaseHandler
import com.codemonkeys.shared.core.responses.BaseResponse

class LoginGroupHandler: BaseHandler<LoginGroupRequest>() {
    override fun handle(request: LoginGroupRequest): BaseResponse {
        val clockGroupService = ServerClockGroupService()
        val newAuthToken = clockGroupService.loginGroup(request.groupName, request.groupPassword)
        return LoginGroupResponse(newAuthToken, true)
    }
}