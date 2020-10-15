package clockGroup.handlers

import clockGroup.ClockGroupService
import clockGroup.requests.AddMemberRequest
import core.handler.BaseHandler
import core.handler.BaseResponse

class AddMemberHandler: BaseHandler<AddMemberRequest>() {
    override fun handle(request: AddMemberRequest): BaseResponse {
        val clockGroupService = ClockGroupService()
        clockGroupService.addMemberToGroup(request.authToken, request.userNameToAdd, request.password)
        return BaseResponse()
    }
}