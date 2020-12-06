package com.codemonkeys.server.whereabout.handlers

import com.codemonkeys.server.core.handler.BaseHandler
import com.codemonkeys.shared.whereabout.requests.UpdateUserWhereaboutRequest
import com.codemonkeys.server.whereabout.ServerWhereaboutService
import com.codemonkeys.shared.core.responses.BaseResponse

class UpdateUserWhereaboutHandler: BaseHandler<UpdateUserWhereaboutRequest>() {
    override fun handle(request: UpdateUserWhereaboutRequest): BaseResponse {
        val whereaboutService = ServerWhereaboutService()
        whereaboutService.updateUserWhereabout(request.authToken, request.currentZoneID)
        return BaseResponse()
    }
}