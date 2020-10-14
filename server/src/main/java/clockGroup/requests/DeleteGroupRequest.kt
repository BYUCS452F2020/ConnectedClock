package clockGroup.requests

import core.handler.AuthorizedRequest

class DeleteGroupRequest(_authToken: String = "", val password: String = ""): AuthorizedRequest(_authToken)