package clockGroup.handlers

import clockGroup.ClockGroupService
import clockGroup.responses.LoginGroupResponse
import clockGroup.requests.LoginGroupRequest
import core.handler.BaseHandler
import core.handler.BaseResponse

class LoginGroupHandler: BaseHandler<LoginGroupRequest>() {
    override fun handle(request: LoginGroupRequest): BaseResponse {
        val clockGroupService = ClockGroupService()
        val newAuthToken = clockGroupService.loginGroup(request.groupName, request.groupPassword)
        return LoginGroupResponse(newAuthToken, true)
    }
}