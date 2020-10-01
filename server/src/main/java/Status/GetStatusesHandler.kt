package Status

import Core.Handler.AuthorizedRequest
import Core.Handler.BaseHandler
import Core.Handler.BaseResponse
import Core.NotAuthorizedException
import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestHandler

class GetStatusesHandler : BaseHandler<AuthorizedRequest>() {

    override fun handleRequest(request: AuthorizedRequest): BaseResponse {
        val statusService = StatusService()
        val statuses = statusService.getStatuses(request.authToken)
        return GetStatusesResponse(statuses)
    }
}