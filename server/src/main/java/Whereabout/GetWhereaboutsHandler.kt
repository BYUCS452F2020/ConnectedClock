package Whereabout

import Core.Handler.AuthorizedRequest
import Core.Handler.BaseHandler
import Core.Handler.BaseResponse

class GetWhereaboutsHandler: BaseHandler<AuthorizedRequest>() {
    override fun handle(request: AuthorizedRequest): BaseResponse {
        val whereaboutService = WhereaboutService()
        val whereabouts = whereaboutService.getWhereabouts(request.authToken)
        return GetWhereaboutsResponse(whereabouts)
    }
}