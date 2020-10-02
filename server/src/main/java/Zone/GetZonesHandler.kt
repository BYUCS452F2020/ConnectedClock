package Zone

import Core.Handler.AuthorizedRequest
import Core.Handler.BaseHandler
import Core.Handler.BaseResponse

class GetZonesHandler: BaseHandler<AuthorizedRequest>() {

    override fun handle(request: AuthorizedRequest): BaseResponse {
        val zoneService = ZoneService()
        val zones = zoneService.getZones(request.authToken)
        return GetZonesResponse(zones)
    }
}