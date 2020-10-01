package Core.Handler

import Core.NotAuthorizedException
import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestHandler

abstract class BaseHandler<I> : RequestHandler<I, BaseResponse> {
    override fun handleRequest(request: I?, context: Context?): BaseResponse {
        request?.let {
            return try {
                handleRequest(it)
            } catch (e: NotAuthorizedException) {
                BaseResponse("Not Authorized")
            } catch (e: Exception) {
                BaseResponse("An uncaught exception occurred: '${e}'\n\n${e.message}\n\n${e.stackTrace}")
            }
        }

        return BaseResponse("Invalid Request")
    }

    abstract fun handleRequest(request: I): BaseResponse
}