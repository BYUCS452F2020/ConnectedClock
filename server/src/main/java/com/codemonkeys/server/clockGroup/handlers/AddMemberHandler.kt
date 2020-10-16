package com.codemonkeys.server.clockGroup.handlers

import com.codemonkeys.server.clockGroup.ServerClockGroupService
import com.codemonkeys.shared.clockGroup.requests.AddMemberRequest
import com.codemonkeys.server.core.handler.BaseHandler
import com.codemonkeys.shared.core.responses.BaseResponse

class AddMemberHandler: BaseHandler<AddMemberRequest>() {
    override fun handle(request: AddMemberRequest): BaseResponse {
        val clockGroupService = ServerClockGroupService()
        clockGroupService.addMemberToGroup(request.authToken, request.userNameToAdd, request.password)
        return BaseResponse()
    }
}