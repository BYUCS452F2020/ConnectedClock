package clockGroup.requests

import core.handler.AuthorizedRequest

class AddMemberRequest(_authToken: String = "", var userNameToAdd: String = "", var password: String = ""): AuthorizedRequest(_authToken)