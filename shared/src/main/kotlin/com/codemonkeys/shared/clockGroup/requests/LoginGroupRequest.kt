package com.codemonkeys.shared.clockGroup.requests

import com.codemonkeys.shared.core.requests.AuthorizedRequest

class LoginGroupRequest(_authToken: String = "", var groupName: String = "", var groupPassword: String = ""): AuthorizedRequest(_authToken)