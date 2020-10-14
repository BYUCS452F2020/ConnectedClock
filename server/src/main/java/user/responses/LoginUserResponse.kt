package user.responses

import core.handler.BaseResponse

class LoginUserResponse: BaseResponse {
    val authToken: String?

    constructor (_authToken: String?) : super() {
        this.authToken = _authToken
    }

    constructor(_authToken: String?, _error_message: String): super(_error_message) {
        this.authToken = _authToken
    }

}