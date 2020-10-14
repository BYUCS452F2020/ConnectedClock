package clockGroup.requests

import core.handler.AuthorizedRequest

class LoginGroupRequest(_authToken: String = "", var groupName: String = "", var groupPassword: String = ""): AuthorizedRequest(_authToken)