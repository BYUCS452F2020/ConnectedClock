package com.codemonkeys.server.clockGroup.handlers

import com.codemonkeys.server.clockGroup.ServerClockGroupService
import com.codemonkeys.shared.clockGroup.responses.GetClockGroupResponse
import com.codemonkeys.shared.clockGroup.requests.GetGroupRequest
import com.codemonkeys.server.core.handler.BaseHandler
import com.codemonkeys.shared.core.responses.BaseResponse

class GetGroupHandler: BaseHandler<GetGroupRequest>() {
    override fun handle(request: GetGroupRequest): BaseResponse {
        val clockGroupService = ServerClockGroupService()
        val clockGroup = clockGroupService.getGroup(request.authToken, request.groupPassword)
        return GetClockGroupResponse(clockGroup)
    }
}