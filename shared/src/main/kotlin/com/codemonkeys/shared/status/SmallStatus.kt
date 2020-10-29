package com.codemonkeys.shared.status

import com.codemonkeys.shared.status.Status

class SmallStatus(status: Status) {
    val sid: Int
    val cha: Int

    init {
        sid = status.statusID.hashCode()
        cha = status.clockHandAngle
    }
}