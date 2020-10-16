package com.codemonkeys.server.zone.handlers

import com.codemonkeys.server.core.handler.BaseHandler
import com.codemonkeys.server.zone.ServerZoneService
import com.codemonkeys.shared.zone.requests.UpdateZonesRequest
import com.codemonkeys.shared.core.responses.BaseResponse

class UpdateZonesHandler: BaseHandler<UpdateZonesRequest>() {
    override fun handle(request: UpdateZonesRequest): BaseResponse {
        val zoneService = ServerZoneService()
        zoneService.updateZones(request.authToken, request.updatedZones)
        return BaseResponse()
    }
}