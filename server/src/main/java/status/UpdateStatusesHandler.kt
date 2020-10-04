package status

import core.handler.BaseHandler
import core.handler.BaseResponse

class UpdateStatusesHandler: BaseHandler<UpdateStatusesRequest>() {

    override fun handle(request: UpdateStatusesRequest): BaseResponse {
        val statusService = StatusService()
        statusService.updateStatuses(request.authToken, request.updatedStatuses)
        return BaseResponse()
    }
}
