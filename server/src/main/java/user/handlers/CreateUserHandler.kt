package user.handlers

import core.handler.BaseHandler
import core.handler.BaseResponse
import user.UserService
import user.requests.CreateUserRequest

class CreateUserHandler: BaseHandler<CreateUserRequest>() {

    override fun handle(request: CreateUserRequest): BaseResponse {
        val userService = UserService()
        return userService.createUser(request)
    }
}