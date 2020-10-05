package ClockGroup.Handlers

import ClockGroup.ClockGroupService
import ClockGroup.GetClockGroupResponse
import ClockGroup.Requests.GetGroupRequest
import core.handler.BaseHandler
import core.handler.BaseResponse

class GetGroupHandler: BaseHandler<GetGroupRequest>() {
    override fun handle(request: GetGroupRequest): BaseResponse {
        val clockGroupService = ClockGroupService()
        val clockGroup = clockGroupService.getGroup(request.groupPassword, request.userID)
        return GetClockGroupResponse(clockGroup)
    }
}