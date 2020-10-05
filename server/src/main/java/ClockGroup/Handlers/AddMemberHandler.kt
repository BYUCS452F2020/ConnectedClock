package ClockGroup.Handlers

import ClockGroup.ClockGroupService
import ClockGroup.Requests.AddMemberRequest
import core.handler.BaseHandler
import core.handler.BaseResponse

class AddMemberHandler: BaseHandler<AddMemberRequest>() {
    override fun handle(request: AddMemberRequest): BaseResponse {
        val clockGroupService = ClockGroupService()
        clockGroupService.addMemberToGroup(request.userID, request.userToAddID, request.authToken)
        return BaseResponse()
    }
}