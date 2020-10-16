package com.codemonkeys.connectedclock.app.core.network

import com.codemonkeys.shared.core.responses.BaseResponse

open class BaseNetworkAPI {
    protected fun checkResponseForError(response: BaseResponse) {
        if (response.didErrorOccur) {
            throw ServerException(response.errorMessage)
        }
    }
}