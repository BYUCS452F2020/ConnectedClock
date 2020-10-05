package ClockGroup.Requests

import core.handler.AuthorizedRequest

class GetGroupRequest(_authToken: String = "", var groupPassword: String = "", var userID: String = ""): AuthorizedRequest(_authToken)