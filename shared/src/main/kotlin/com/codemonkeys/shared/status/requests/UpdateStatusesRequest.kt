package com.codemonkeys.shared.status.requests

import com.codemonkeys.shared.core.requests.AuthorizedRequest
import com.codemonkeys.shared.status.Status

class UpdateStatusesRequest(_authToken: String = "", var updatedStatuses: List<Status> = listOf()): AuthorizedRequest(_authToken)