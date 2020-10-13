package status

import core.handler.AuthorizedRequest
import core.handler.BaseHandler
import core.handler.BaseResponse

class GetSmallStatusesHandler : BaseHandler<AuthorizedRequest>() {
    override fun handle(request: AuthorizedRequest): BaseResponse {
        val statusService = StatusService()
        val statuses = statusService.getStatuses(request.authToken)
        val smallStatuses = statuses.map { SmallStatus(it) }
        return GetSmallStatusesResponse(smallStatuses)
    }
}
