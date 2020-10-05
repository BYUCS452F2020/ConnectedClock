package ClockGroup

import core.dao.ColumnAnnotation

data class ClockGroup (
    @ColumnAnnotation("groupID") var groupID: String = "",
    @ColumnAnnotation("groupName") var groupName: String = "",
    @ColumnAnnotation("groupPassword") var groupPassword: String = "")
