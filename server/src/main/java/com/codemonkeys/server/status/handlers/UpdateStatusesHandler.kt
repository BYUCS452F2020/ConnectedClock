package com.codemonkeys.server.status.handlers

import com.codemonkeys.server.core.handler.BaseHandler
import com.codemonkeys.server.status.ServerStatusService
import com.codemonkeys.shared.status.requests.UpdateStatusesRequest
import com.codemonkeys.shared.core.responses.BaseResponse

class UpdateStatusesHandler: BaseHandler<UpdateStatusesRequest>() {

    override fun handle(request: UpdateStatusesRequest): BaseResponse {
        val statusService = ServerStatusService()
        statusService.updateStatuses(request.authToken, request.updatedStatuses)
        return BaseResponse()
    }
}
