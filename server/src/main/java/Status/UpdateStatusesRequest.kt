package Status

import Core.Handler.AuthorizedRequest

class UpdateStatusesRequest(_authToken: String, val updatedStatuses: List<Status>): AuthorizedRequest(_authToken)