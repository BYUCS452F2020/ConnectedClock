package ClockGroup.Requests

import core.handler.AuthorizedRequest

class CreateGroupRequest(_authToken: String = "", var groupName: String = "", var groupPassword: String = "", var userID: String = ""): AuthorizedRequest(_authToken)