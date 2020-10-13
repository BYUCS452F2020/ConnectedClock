package whereabout

class SmallWhereabout(whereabout: Whereabout) {
    val sid: Int
    val chi: Int

    init {
        sid = whereabout.currentStatusID.hashCode()
        chi = whereabout.clockHandIndex
    }
}