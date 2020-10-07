package clockGroup.requests

import core.handler.AuthorizedRequest

class CreateGroupRequest(_authToken: String = "", var groupName: String = "", var groupPassword: String = ""): AuthorizedRequest(_authToken)