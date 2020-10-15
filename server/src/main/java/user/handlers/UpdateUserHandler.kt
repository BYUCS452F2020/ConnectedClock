package user.handlers

import core.handler.BaseHandler
import core.handler.BaseResponse
import user.UserService
import user.requests.UpdateUserRequest

class UpdateUserHandler: BaseHandler<UpdateUserRequest>() {

    override fun handle(request: UpdateUserRequest): BaseResponse {
        val userService = UserService()
        return userService.updateUser(request)
    }
}