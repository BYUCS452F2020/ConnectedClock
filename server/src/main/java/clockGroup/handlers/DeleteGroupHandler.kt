package clockGroup.handlers

import clockGroup.ClockGroupService
import clockGroup.requests.AddMemberRequest
import clockGroup.requests.DeleteGroupRequest
import core.handler.BaseHandler
import core.handler.BaseResponse

class DeleteGroupHandler: BaseHandler<DeleteGroupRequest>() {

    override fun handle(request: DeleteGroupRequest): BaseResponse {
        val clockGroupService = ClockGroupService()
        clockGroupService.deleteGroup(request.authToken, request.password)
        return BaseResponse()
    }
}