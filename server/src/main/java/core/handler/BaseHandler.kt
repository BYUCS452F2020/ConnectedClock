package core.handler

import core.NotAuthorizedException
import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestHandler
import java.io.PrintWriter
import java.io.StringWriter

abstract class BaseHandler<T> : RequestHandler<T, BaseResponse> {
    override fun handleRequest(input: T, context: Context?): BaseResponse? {
        return try {
            handle(input)
        } catch (e: NotAuthorizedException) {
            BaseResponse("Not Authorized")
        } catch (e: Exception) {
            val stringWriter = StringWriter()
            val printWriter = PrintWriter(stringWriter)
            e.printStackTrace(printWriter)
            val stackTrace = stringWriter.toString()
            BaseResponse("An uncaught exception occurred: '${e}'\n\n${e.message}\n\n${stackTrace}")
        }
    }

    abstract fun handle(request: T): BaseResponse
}

