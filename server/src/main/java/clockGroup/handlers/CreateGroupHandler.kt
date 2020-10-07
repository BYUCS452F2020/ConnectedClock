package clockGroup.handlers

import clockGroup.ClockGroupService
import clockGroup.requests.CreateGroupRequest
import core.handler.BaseHandler
import core.handler.BaseResponse

class CreateGroupHandler: BaseHandler<CreateGroupRequest>() {
    override fun handle(request: CreateGroupRequest): BaseResponse {
        val clockGroupService = ClockGroupService()
        clockGroupService.createGroup(request.authToken, request.groupName, request.groupPassword)
        return BaseResponse()
    }
}