package user.requests

import core.handler.AuthorizedRequest

class LogoutUserRequest(_authToken: String): AuthorizedRequest(_authToken)