package zone

import core.handler.AuthorizedRequest
import core.handler.BaseHandler
import core.handler.BaseResponse

class GetZonesHandler: BaseHandler<AuthorizedRequest>() {

    override fun handle(request: AuthorizedRequest): BaseResponse {
        val zoneService = ZoneService()
        val zones = zoneService.getZones(request.authToken)
        return GetZonesResponse(zones)
    }
}