package Whereabout

import Core.Handler.AuthorizedRequest

class UpdateUserWhereaboutRequest(_authToken: String = "", var currentZoneID: String? = null): AuthorizedRequest(_authToken)