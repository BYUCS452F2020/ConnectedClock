package ClockGroup.Handlers

import ClockGroup.ClockGroupService
import ClockGroup.Requests.CreateGroupRequest
import core.handler.BaseHandler
import core.handler.BaseResponse

class CreateGroupHandler: BaseHandler<CreateGroupRequest>() {
    override fun handle(request: CreateGroupRequest): BaseResponse {
        val clockGroupService = ClockGroupService()
        clockGroupService.createGroup(request.authToken, request.groupName, request.groupPassword, request.userID)
        return BaseResponse()
    }
}