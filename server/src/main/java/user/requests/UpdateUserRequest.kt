package user.requests

import core.handler.AuthorizedRequest

class UpdateUserRequest(_authToken: String = "",
                        var currentPassword: String,
                        var newPassword: String): AuthorizedRequest(_authToken)