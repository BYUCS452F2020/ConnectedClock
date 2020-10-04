package status

import core.handler.AuthorizedRequest
import core.handler.BaseHandler
import core.handler.BaseResponse

class GetStatusesHandler : BaseHandler<AuthorizedRequest>() {

    override fun handle(request: AuthorizedRequest): BaseResponse {
        val statusService = StatusService()
        val statuses = statusService.getStatuses(request.authToken)
        return GetStatusesResponse(statuses)
    }
}