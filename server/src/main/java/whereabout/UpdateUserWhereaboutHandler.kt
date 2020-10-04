package whereabout

import core.handler.BaseHandler
import core.handler.BaseResponse

class UpdateUserWhereaboutHandler: BaseHandler<UpdateUserWhereaboutRequest>() {
    override fun handle(request: UpdateUserWhereaboutRequest): BaseResponse {
        val whereaboutService = WhereaboutService()
        whereaboutService.updateUserWhereabout(request.authToken, request.currentZoneID)
        return BaseResponse()
    }
}