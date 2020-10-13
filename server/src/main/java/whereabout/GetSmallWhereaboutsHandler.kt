package whereabout

import core.handler.AuthorizedRequest
import core.handler.BaseHandler
import core.handler.BaseResponse

class GetSmallWhereaboutsHandler: BaseHandler<AuthorizedRequest>() {
    override fun handle(request: AuthorizedRequest): BaseResponse {
        val whereaboutService = WhereaboutService()
        val whereabouts = whereaboutService.getWhereabouts(request.authToken)
        val smallWhereabouts = whereabouts.map { SmallWhereabout(it) }
        return GetSmallWhereaboutsResponse(smallWhereabouts)
    }
}
