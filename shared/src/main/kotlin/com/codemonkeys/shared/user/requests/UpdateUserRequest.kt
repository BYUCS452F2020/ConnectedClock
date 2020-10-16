package com.codemonkeys.shared.user.requests

import com.codemonkeys.shared.core.requests.AuthorizedRequest

class UpdateUserRequest(_authToken: String = "",
                        var currentPassword: String,
                        var newPassword: String): AuthorizedRequest(_authToken)