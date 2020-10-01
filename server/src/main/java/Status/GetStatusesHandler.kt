package Status

import Core.Handler.AuthorizedRequest
import Core.Handler.BaseHandler
import Core.Handler.BaseResponse

class GetStatusesHandler : BaseHandler<AuthorizedRequest>() {

    override fun handle(request: AuthorizedRequest): BaseResponse {
        val statusService = StatusService()
        val statuses = statusService.getStatuses(request.authToken)
        return GetStatusesResponse(statuses)
    }
}