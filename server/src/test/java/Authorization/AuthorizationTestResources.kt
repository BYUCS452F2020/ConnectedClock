package Authorization

import Core.Handler.AuthorizedRequest
import Core.Handler.BaseResponse

class AuthorizationTestResources {
    companion object {
        val GROUP_1_GROUP_AUTHTOKEN = "663feea8-e1e0-4cf3-89ff-ed4905ec4c7c"
        val GROUP_1_GROUP_AUTHTOKEN_REQUEST = AuthorizedRequest(GROUP_1_GROUP_AUTHTOKEN)
        val GROUP_1_USER_AUTHTOKEN = "cc95e238-1a1d-4238-b19b-0ef1dce406eb"
        val GROUP_1_USER_AUTHTOKEN_REQUEST = AuthorizedRequest(GROUP_1_USER_AUTHTOKEN)

        val GROUP_2_GROUP_AUTHTOKEN = "7af562ed-46bd-429b-8bcd-2d85e76d9a10"
        val GROUP_2_GROUP_AUTHTOKEN_REQUEST = AuthorizedRequest(GROUP_2_GROUP_AUTHTOKEN)
        val GROUP_2_USER_AUTHTOKEN = "dce470b2-0e48-4bf1-9274-0af847dab64b"
        val GROUP_2_USER_AUTHTOKEN_REQUEST = AuthorizedRequest(GROUP_2_USER_AUTHTOKEN)

        val INVALID_AUTHTOKEN = "invalidAuthToken"
        val INVALID_AUTHTOKEN_REQUEST = AuthorizedRequest(INVALID_AUTHTOKEN)
        val NOT_AUTHORIZED_RESPONSE = BaseResponse("Not Authorized")
    }
}