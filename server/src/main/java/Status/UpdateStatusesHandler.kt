package Status

import Core.Handler.BaseHandler
import Core.Handler.BaseResponse
import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestHandler

class UpdateStatusesHandler: BaseHandler<UpdateStatusesRequest>() {

    override fun handleRequest(request: UpdateStatusesRequest): BaseResponse {
        val statusService = StatusService()
        statusService.updateStatuses(request.authToken, request.updatedStatuses)
        return BaseResponse()
    }
}