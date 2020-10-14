package clockGroup.handlers

import clockGroup.ClockGroupService
import clockGroup.requests.DeleteMemberRequest
import core.handler.BaseHandler
import core.handler.BaseResponse

class DeleteMemberHandler: BaseHandler<DeleteMemberRequest>() {
    override fun handle(request: DeleteMemberRequest): BaseResponse {
        val clockGroupService = ClockGroupService()
        clockGroupService.deleteMemberFromGroup(request.authToken)
        return BaseResponse()
    }
}