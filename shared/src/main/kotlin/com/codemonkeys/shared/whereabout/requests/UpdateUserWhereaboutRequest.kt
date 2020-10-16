package com.codemonkeys.shared.whereabout.requests

import com.codemonkeys.shared.core.requests.AuthorizedRequest

class UpdateUserWhereaboutRequest(_authToken: String = "", var currentZoneID: String? = null): AuthorizedRequest(_authToken)