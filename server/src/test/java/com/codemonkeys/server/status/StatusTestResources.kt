package com.codemonkeys.server.status

import com.codemonkeys.server.authorization.AuthorizationTestResources
import com.codemonkeys.shared.status.Status
import com.codemonkeys.shared.status.requests.UpdateStatusesRequest
import com.codemonkeys.shared.status.responses.GetStatusesResponse

class StatusTestResources {

    companion object {
        val GROUP_1_STATUSES = listOf(
            Status(
                "04e3b648-c3dd-41da-a430-1dacde995b7d",
                23.2,
                "Work",
                "98729fce-0809-43fe-b953-f48b14b07616"
            ),
            Status(
                "12a20cb0-9e82-4d88-98f2-faaa9ff8c675",
                11.0,
                "Shopping",
                "98729fce-0809-43fe-b953-f48b14b07616"
            ),
            Status(
                "7be3c43c-9f4b-4f6d-bbeb-e639e8331ab9",
                60.0,
                "Home",
                "98729fce-0809-43fe-b953-f48b14b07616"
            ),
            Status(
                "d32b0786-c6f1-4c70-a31f-a9efed1ef1f6",
                75.0,
                "Away",
                "98729fce-0809-43fe-b953-f48b14b07616"
            ),
            Status(
                "ec488303-1152-4d8d-af55-db9b323be17e",
                90.5,
                "School",
                "98729fce-0809-43fe-b953-f48b14b07616"
            )
        )

        val GROUP_1_STATUSES_RESPONSE =
            GetStatusesResponse(GROUP_1_STATUSES)

        val GROUP_2_STATUSES = listOf(
            Status(
                "32f85320-92e0-4382-a5ae-d71b562422c5",
                12.7,
                "Mortal Peril",
                "2bc8f348-fce4-4df6-9795-deff8e721c7a"
            ),
            Status(
                "92a3db1e-99b6-45d1-92e6-2c47720e620e",
                42.9,
                "Home",
                "2bc8f348-fce4-4df6-9795-deff8e721c7a"
            ),
            Status(
                "ed9ceea8-b059-4e00-b35e-83be6e63c497",
                101.0,
                "School",
                "2bc8f348-fce4-4df6-9795-deff8e721c7a"
            )
        )

        val GROUP_2_STATUSES_RESPONSE =
            GetStatusesResponse(GROUP_2_STATUSES)

        val GROUP_2_UPDATED_STATUSES = listOf(
            Status(
                "92a3db1e-99b6-45d1-92e6-2c47720e620e",
                44.4,
                "Homies",
                "2bc8f348-fce4-4df6-9795-deff8e721c7a"
            ),
            Status(
                "32f85320-92e0-4382-a5ae-d71b562422c5",
                12.7,
                "Mortal Peril",
                "2bc8f348-fce4-4df6-9795-deff8e721c7a"
            ),
            Status(
                "95d309b7-039a-4647-8ba6-ff6dd6bb1d99",
                55.5,
                "Visiting Family",
                "2bc8f348-fce4-4df6-9795-deff8e721c7a"
            ),
            Status(
                "970c1d21-f97b-4b35-b715-e397845d6e8f",
                66.6,
                "Woods",
                "2bc8f348-fce4-4df6-9795-deff8e721c7a"
            )
        )

        val GROUP_2_INVALID_STATUSES = listOf(
            Status(
                "92a3db1e-99b6-45d1-92e6-2c47720e620e",
                44.4,
                "Homies",
                "2bc8f348-fce4-4df6-9795-deff8e721c7a"
            ),
            Status(
                "32f85320-92e0-4382-a5ae-d71b562422c5",
                12.7,
                "Mortal Peril",
                "invalidGroupID"
            ),
            Status(
                "95d309b7-039a-4647-8ba6-ff6dd6bb1d99",
                55.5,
                "Visiting Family",
                "2bc8f348-fce4-4df6-9795-deff8e721c7a"
            ),
            Status(
                "970c1d21-f97b-4b35-b715-e397845d6e8f",
                66.6,
                "Woods",
                "2bc8f348-fce4-4df6-9795-deff8e721c7a"
            )
        )

        val GROUP_2_UPDATED_STATUSES_REQUEST =
            UpdateStatusesRequest(
                AuthorizationTestResources.GROUP_2_GROUP_AUTHTOKEN,
                GROUP_2_UPDATED_STATUSES
            )

        val GROUP_2_INVALID_STATUSES_REQUEST =
            UpdateStatusesRequest(
                AuthorizationTestResources.GROUP_2_GROUP_AUTHTOKEN,
                GROUP_2_INVALID_STATUSES
            )

    }
}