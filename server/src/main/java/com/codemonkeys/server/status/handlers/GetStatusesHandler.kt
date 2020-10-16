package com.codemonkeys.server.status.handlers

import com.codemonkeys.shared.core.requests.AuthorizedRequest
import com.codemonkeys.server.core.handler.BaseHandler
import com.codemonkeys.shared.status.responses.GetStatusesResponse
import com.codemonkeys.server.status.ServerStatusService
import com.codemonkeys.shared.core.responses.BaseResponse

class GetStatusesHandler : BaseHandler<AuthorizedRequest>() {

    override fun handle(request: AuthorizedRequest): BaseResponse {
        val statusService = ServerStatusService()
        val statuses = statusService.getStatuses(request.authToken)
        return GetStatusesResponse(statuses)
    }
}