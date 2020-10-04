package whereabout

import core.handler.AuthorizedRequest

class UpdateUserWhereaboutRequest(_authToken: String = "", var currentZoneID: String? = null): AuthorizedRequest(_authToken)