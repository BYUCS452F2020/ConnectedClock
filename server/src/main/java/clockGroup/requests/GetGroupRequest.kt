package clockGroup.requests

import core.handler.AuthorizedRequest

class GetGroupRequest(_authToken: String = "", var groupPassword: String = ""): AuthorizedRequest(_authToken)