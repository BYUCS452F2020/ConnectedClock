package com.codemonkeys.shared.clockGroup.responses
import com.codemonkeys.shared.clockGroup.ClockGroup
import com.codemonkeys.shared.core.responses.BaseResponse

class GetClockGroupResponse: BaseResponse {
    private val clockGroup: ClockGroup

    constructor(_clockGroup: ClockGroup): super(){
        this.clockGroup = _clockGroup
    }

    constructor(errorMessage: String): super(errorMessage){
        this.clockGroup = ClockGroup()
    }

}