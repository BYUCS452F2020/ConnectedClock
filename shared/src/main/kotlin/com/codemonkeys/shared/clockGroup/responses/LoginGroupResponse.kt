package com.codemonkeys.shared.clockGroup.responses
import com.codemonkeys.shared.core.responses.BaseResponse

class LoginGroupResponse: BaseResponse {
    private val authToken: String
    private val valid: Boolean

    constructor(_authToken: String, _valid: Boolean): super(){
        this.authToken = _authToken
        this.valid = _valid

    }

    constructor(errorMessage: String): super(errorMessage){
        this.authToken = ""
        this.valid = false
    }

}