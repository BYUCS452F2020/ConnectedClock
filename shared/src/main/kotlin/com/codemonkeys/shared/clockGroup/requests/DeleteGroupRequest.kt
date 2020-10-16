package com.codemonkeys.shared.clockGroup.requests

import com.codemonkeys.shared.core.requests.AuthorizedRequest

class DeleteGroupRequest(_authToken: String = "", val password: String = ""): AuthorizedRequest(_authToken)