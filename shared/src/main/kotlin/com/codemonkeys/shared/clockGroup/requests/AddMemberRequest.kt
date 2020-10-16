package com.codemonkeys.shared.clockGroup.requests

import com.codemonkeys.shared.core.requests.AuthorizedRequest

class AddMemberRequest(_authToken: String = "", var userNameToAdd: String = "", var password: String = ""): AuthorizedRequest(_authToken)