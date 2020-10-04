package whereabout

import core.handler.AuthorizedRequest
import core.handler.BaseHandler
import core.handler.BaseResponse

class GetWhereaboutsHandler: BaseHandler<AuthorizedRequest>() {
    override fun handle(request: AuthorizedRequest): BaseResponse {
        val whereaboutService = WhereaboutService()
        val whereabouts = whereaboutService.getWhereabouts(request.authToken)
        return GetWhereaboutsResponse(whereabouts)
    }
}