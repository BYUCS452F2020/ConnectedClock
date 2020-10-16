package com.codemonkeys.shared.clockGroup

import com.codemonkeys.shared.core.dao.ColumnAnnotation

data class ClockGroup (
    @ColumnAnnotation("groupID") var groupID: String = "",
    @ColumnAnnotation("groupName") var groupName: String = "",
    @ColumnAnnotation("groupPassword") var groupPassword: String = "")
