package com.codemonkeys.server.clockGroup.handlers

import com.codemonkeys.server.clockGroup.ServerClockGroupService
import com.codemonkeys.shared.clockGroup.requests.DeleteGroupRequest
import com.codemonkeys.server.core.handler.BaseHandler
import com.codemonkeys.shared.core.responses.BaseResponse

class DeleteGroupHandler: BaseHandler<DeleteGroupRequest>() {

    override fun handle(request: DeleteGroupRequest): BaseResponse {
        val clockGroupService = ServerClockGroupService()
        clockGroupService.deleteGroup(request.authToken, request.password)
        return BaseResponse()
    }
}