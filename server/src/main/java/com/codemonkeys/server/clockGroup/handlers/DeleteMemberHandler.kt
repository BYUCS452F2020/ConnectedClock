package com.codemonkeys.server.clockGroup.handlers

import com.codemonkeys.server.clockGroup.ServerClockGroupService
import com.codemonkeys.shared.clockGroup.requests.DeleteMemberRequest
import com.codemonkeys.server.core.handler.BaseHandler
import com.codemonkeys.shared.core.responses.BaseResponse

class DeleteMemberHandler: BaseHandler<DeleteMemberRequest>() {
    override fun handle(request: DeleteMemberRequest): BaseResponse {
        val clockGroupService = ServerClockGroupService()
        clockGroupService.deleteMemberFromGroup(request.authToken)
        return BaseResponse()
    }
}