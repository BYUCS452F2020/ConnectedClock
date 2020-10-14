package clockGroup
import core.handler.BaseResponse

class LoginGroupResponse: BaseResponse {
    private val authToken: String

    constructor(_authToken: String, valid: Boolean): super(){
        this.authToken = _authToken
    }

    constructor(errorMessage: String): super(errorMessage){
        this.authToken = ""
    }

}