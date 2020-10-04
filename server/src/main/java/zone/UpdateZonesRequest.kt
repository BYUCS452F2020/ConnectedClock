package zone

import core.handler.AuthorizedRequest

class UpdateZonesRequest(_authToken: String = "", var updatedZones: List<Zone> = listOf()): AuthorizedRequest(_authToken)