package clockGroup
import core.handler.BaseResponse

class GetClockGroupResponse: BaseResponse {
    val clockGroup: ClockGroup

    constructor(_clockGroup: ClockGroup): super(){
        this.clockGroup = _clockGroup
    }

    constructor(errorMessage: String): super(errorMessage){
        this.clockGroup = ClockGroup()
    }

}