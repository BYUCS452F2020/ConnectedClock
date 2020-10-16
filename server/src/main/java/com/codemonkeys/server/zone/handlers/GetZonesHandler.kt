package com.codemonkeys.server.zone.handlers

import com.codemonkeys.shared.core.requests.AuthorizedRequest
import com.codemonkeys.server.core.handler.BaseHandler
import com.codemonkeys.shared.zone.responses.GetZonesResponse
import com.codemonkeys.server.zone.ServerZoneService
import com.codemonkeys.shared.core.responses.BaseResponse

class GetZonesHandler: BaseHandler<AuthorizedRequest>() {

    override fun handle(request: AuthorizedRequest): BaseResponse {
        val zoneService = ServerZoneService()
        val zones = zoneService.getZones(request.authToken)
        return GetZonesResponse(zones)
    }
}