package Status

import Core.Handler.BaseHandler
import Core.Handler.BaseResponse

class UpdateStatusesHandler: BaseHandler<UpdateStatusesRequest>() {

    override fun handle(request: UpdateStatusesRequest): BaseResponse {
        val statusService = StatusService()
        statusService.updateStatuses(request.authToken, request.updatedStatuses)
        return BaseResponse()
    }
}
