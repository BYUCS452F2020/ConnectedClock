package Status

import Core.Handler.AuthorizedRequest

class UpdateStatusesRequest(_authToken: String = "", var updatedStatuses: List<Status> = listOf()): AuthorizedRequest(_authToken)