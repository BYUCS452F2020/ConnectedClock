package com.codemonkeys.shared.user.responses

import com.codemonkeys.shared.core.responses.BaseResponse

class LogoutUserResponse: BaseResponse {

    constructor() : super() {}
    constructor(_error_message: String): super(_error_message) {}

}