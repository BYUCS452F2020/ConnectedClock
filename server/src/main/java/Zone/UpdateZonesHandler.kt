package Zone

import Core.Handler.BaseHandler
import Core.Handler.BaseResponse

class UpdateZonesHandler: BaseHandler<UpdateZonesRequest>() {
    override fun handle(request: UpdateZonesRequest): BaseResponse {
        val zoneService = ZoneService()
        zoneService.updateZones(request.authToken, request.updatedZones)
        return BaseResponse()
    }
}