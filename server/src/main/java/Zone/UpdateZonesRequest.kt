package Zone

import Core.Handler.AuthorizedRequest

class UpdateZonesRequest(_authToken: String = "", var updatedZones: List<Zone> = listOf()): AuthorizedRequest(_authToken)