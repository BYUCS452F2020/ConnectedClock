package com.codemonkeys.shared.zone.requests

import com.codemonkeys.shared.core.requests.AuthorizedRequest
import com.codemonkeys.shared.zone.Zone

class UpdateZonesRequest(_authToken: String = "", var updatedZones: List<Zone> = listOf()): AuthorizedRequest(_authToken)