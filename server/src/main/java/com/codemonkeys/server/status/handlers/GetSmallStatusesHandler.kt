package com.codemonkeys.server.status.handlers

import com.codemonkeys.shared.core.requests.AuthorizedRequest
import com.codemonkeys.server.core.handler.BaseHandler
import com.codemonkeys.shared.status.responses.GetSmallStatusesResponse
import com.codemonkeys.server.status.ServerStatusService
import com.codemonkeys.shared.status.SmallStatus
import com.codemonkeys.shared.core.responses.BaseResponse

class GetSmallStatusesHandler : BaseHandler<AuthorizedRequest>() {
    override fun handle(request: AuthorizedRequest): BaseResponse {
        val statusService = ServerStatusService()
        val statuses = statusService.getStatuses(request.authToken)
        val smallStatuses = statuses.map { SmallStatus(it) }
        return GetSmallStatusesResponse(smallStatuses)
    }
}
