package clockGroup.handlers

import clockGroup.ClockGroupService
import clockGroup.GetClockGroupResponse
import clockGroup.requests.GetGroupRequest
import core.NotAuthorizedException
import core.handler.BaseHandler
import core.handler.BaseResponse

class GetGroupHandler: BaseHandler<GetGroupRequest>() {
    override fun handle(request: GetGroupRequest): BaseResponse {
        val clockGroupService = ClockGroupService()
        val clockGroup = clockGroupService.getGroup(request.authToken, request.groupPassword)
        return GetClockGroupResponse(clockGroup)
    }
}