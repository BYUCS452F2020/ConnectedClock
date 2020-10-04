package zone

import core.handler.BaseHandler
import core.handler.BaseResponse

class UpdateZonesHandler: BaseHandler<UpdateZonesRequest>() {
    override fun handle(request: UpdateZonesRequest): BaseResponse {
        val zoneService = ZoneService()
        zoneService.updateZones(request.authToken, request.updatedZones)
        return BaseResponse()
    }
}