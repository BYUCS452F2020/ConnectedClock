package Core.Handler

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestHandler

abstract class BaseHandler<I>: RequestHandler<I, BaseResponse> {
    override fun handleRequest(request: I?, context: Context?): BaseResponse {
        request?.let {
            return handleRequest(it)
        }

        return BaseResponse("Invalid Request")
    }

    abstract fun handleRequest(request: I): BaseResponse
}