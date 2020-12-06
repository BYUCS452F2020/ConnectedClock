package com.codemonkeys.server.clockGroup.handlers

import com.codemonkeys.server.clockGroup.ServerClockGroupService
import com.codemonkeys.shared.clockGroup.requests.CreateGroupRequest
import com.codemonkeys.server.core.handler.BaseHandler
import com.codemonkeys.shared.core.responses.BaseResponse

class CreateGroupHandler: BaseHandler<CreateGroupRequest>() {
    override fun handle(request: CreateGroupRequest): BaseResponse {
        val clockGroupService = ServerClockGroupService()
        clockGroupService.createGroup(request.groupName, request.groupPassword)
        return BaseResponse()
    }
}