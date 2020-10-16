package com.codemonkeys.shared.user.responses

import com.codemonkeys.shared.core.responses.BaseResponse

class CreateUserResponse: BaseResponse {
    val authToken: String?

    constructor(_authToken: String?): super() {
        this.authToken = _authToken
    }

    constructor(_authToken: String?, _error_message: String): super(_error_message) {
        this.authToken = _authToken
    }

}