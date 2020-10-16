package com.codemonkeys.shared.clockGroup.requests

import com.codemonkeys.shared.core.requests.AuthorizedRequest

class GetGroupRequest(_authToken: String = "", var groupPassword: String = ""): AuthorizedRequest(_authToken)