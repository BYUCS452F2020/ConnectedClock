package com.codemonkeys.server.whereabout.handlers

import com.codemonkeys.shared.core.requests.AuthorizedRequest
import com.codemonkeys.server.core.handler.BaseHandler
import com.codemonkeys.shared.whereabout.responses.GetSmallWhereaboutsResponse
import com.codemonkeys.shared.whereabout.SmallWhereabout
import com.codemonkeys.server.whereabout.ServerWhereaboutService
import com.codemonkeys.shared.core.responses.BaseResponse

class GetSmallWhereaboutsHandler: BaseHandler<AuthorizedRequest>() {
    override fun handle(request: AuthorizedRequest): BaseResponse {
        val whereaboutService = ServerWhereaboutService()
        val whereabouts = whereaboutService.getWhereabouts(request.authToken)
        val smallWhereabouts = whereabouts.map { SmallWhereabout(it) }
        return GetSmallWhereaboutsResponse(
            smallWhereabouts
        )
    }
}
