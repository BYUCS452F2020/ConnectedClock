package com.codemonkeys.shared.whereabout

class SmallWhereabout(whereabout: Whereabout) {
    val cha: Int = whereabout.clockHandAngle.toInt()
    val chi: Int = whereabout.clockHandIndex
}