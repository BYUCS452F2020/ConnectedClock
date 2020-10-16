package com.codemonkeys.server.whereabout.handlers

import com.codemonkeys.shared.core.requests.AuthorizedRequest
import com.codemonkeys.server.core.handler.BaseHandler
import com.codemonkeys.shared.whereabout.responses.GetWhereaboutsResponse
import com.codemonkeys.server.whereabout.WhereaboutService
import com.codemonkeys.shared.core.responses.BaseResponse

class GetWhereaboutsHandler: BaseHandler<AuthorizedRequest>() {
    override fun handle(request: AuthorizedRequest): BaseResponse {
        val whereaboutService = WhereaboutService()
        val whereabouts = whereaboutService.getWhereabouts(request.authToken)
        return GetWhereaboutsResponse(whereabouts)
    }
}