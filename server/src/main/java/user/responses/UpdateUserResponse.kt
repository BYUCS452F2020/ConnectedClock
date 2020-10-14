package user.responses

import core.handler.BaseResponse

class UpdateUserResponse: BaseResponse {

    constructor() : super() {}
    constructor(_error_message: String): super(_error_message) {}

}