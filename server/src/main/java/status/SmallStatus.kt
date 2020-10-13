package status

class SmallStatus(status: Status) {
    val sid: Int
    val cha: Int

    init {
        sid = status.statusID.hashCode()
        cha = status.clockHandAngle.toInt()
    }
}