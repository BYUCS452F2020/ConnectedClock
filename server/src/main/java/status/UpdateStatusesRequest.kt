package status

import core.handler.AuthorizedRequest

class UpdateStatusesRequest(_authToken: String = "", var updatedStatuses: List<Status> = listOf()): AuthorizedRequest(_authToken)