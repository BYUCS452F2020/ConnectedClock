package ClockGroup.Requests

import core.handler.AuthorizedRequest

class AddMemberRequest(_authToken: String = "", var userID: String = "", var userToAddID: String = ""): AuthorizedRequest(_authToken)