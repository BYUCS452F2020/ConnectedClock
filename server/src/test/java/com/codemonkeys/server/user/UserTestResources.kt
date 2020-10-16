package com.codemonkeys.server.user

import com.codemonkeys.server.clockGroup.GroupTestResources
import com.codemonkeys.server.zone.ZoneTestResources
import com.codemonkeys.shared.user.User

class UserTestResources {
    companion object {
        val GROUP_1_USER_1_ID = "3ea0f56f-7864-4d49-a5ed-5f741a7969c8"
        val GROUP_1_USER_1_USERNAME = "test1"
        val GROUP_1_USER_1_NEW_PASSWORD = "newPassword"
        val GROUP_1_USER_1_PASSWORD = "pass1"
        val GROUP_1_USER_2_ID = "4c43ddce-64db-4c89-9797-909dcbd0a425"
        val GROUP_2_USER_1_ID = "f3b55f05-c33a-4ab2-9aa7-29ad8f6efa3d"

        val GROUP_1_NEW_USER_1 = User(
            "275d7353-a9a4-4f63-b275-e875f5c44a5f",
            GroupTestResources.GROUP_1_ID,
            "newUserName",
            "newUserPassword",
            2,
            null
        )

        val GROUP_1_NEW_USER_2 = User(
            "9cd57278-8fe1-41af-8fd5-dc4ee4ba82a9",
            GroupTestResources.GROUP_1_ID,
            "newUserName2",
            "newUserPassword2",
            3,
            ZoneTestResources.GROUP_1_ZONES[0].zoneID
        )

        val GROUP_1_NEW_USER_BAD_GROUP = User(
            "5d6ee414-11df-40fe-8101-32e75b13c3e6",
            GroupTestResources.INVALID_GROUP_ID,
            "newUserName2",
            "newUserPassword",
            2,
            null
        )

        val INVALID_USER_ID = "invalidUserID"
        val INVALID_USERNAME = "invalidUserName"
        val INVALID_PASSWORD = "invalidPassword"
    }
}