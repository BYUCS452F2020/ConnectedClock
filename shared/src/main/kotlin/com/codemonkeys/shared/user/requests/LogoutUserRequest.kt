package com.codemonkeys.shared.user.requests

import com.codemonkeys.shared.core.requests.AuthorizedRequest

class LogoutUserRequest(_authToken: String): AuthorizedRequest(_authToken)