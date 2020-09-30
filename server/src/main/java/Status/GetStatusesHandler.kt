package Status

import Core.NotAuthorizedException
import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestHandler

class GetStatusesHandler : RequestHandler<GetStatusesRequest, GetStatusesResponse> {

    override fun handleRequest(request: GetStatusesRequest?, context: Context?): GetStatusesResponse {
        request?.let {
            val statusService = StatusService()
            try {
                val statuses = statusService.getStatuses(request.authToken)
                return GetStatusesResponse(statuses)
            } catch (e: NotAuthorizedException) {

            }
        }

        return GetStatusesResponse(listOf())
    }
}