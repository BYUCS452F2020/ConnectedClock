package Whereabout

import Core.Handler.BaseHandler
import Core.Handler.BaseResponse

class UpdateUserWhereaboutHandler: BaseHandler<UpdateUserWhereaboutRequest>() {
    override fun handle(request: UpdateUserWhereaboutRequest): BaseResponse {
        val whereaboutService = WhereaboutService()
        whereaboutService.updateUserWhereabout(request.authToken, request.currentZoneID)
        return BaseResponse()
    }
}