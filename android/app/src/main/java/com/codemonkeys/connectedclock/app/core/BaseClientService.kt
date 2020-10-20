package com.codemonkeys.connectedclock.app.core

import com.codemonkeys.connectedclock.app.core.network.ServerException
import com.codemonkeys.shared.core.responses.BaseResponse
import retrofit2.Call

open class BaseClientService {
    // Executes retrofit network call synchronously then checks the response to see if any error
    // occurred. If so, throws ServerException, otherwise returns the response body.
    protected fun <T : BaseResponse> executeApiCall(apiCall: Call<T>): T {
        val response = apiCall.execute()
        val body = response.body()
        if (!response.isSuccessful || body == null) {
            throw ServerException(response.errorBody()?.string() ?: "")
        }

        if (body.didErrorOccur) {
            throw ServerException(body.errorMessage)
        }

        return body
    }
}