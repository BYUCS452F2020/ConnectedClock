package clockGroup.responses
import clockGroup.ClockGroup
import core.handler.BaseResponse

class GetClockGroupResponse: BaseResponse {
    private val clockGroup: ClockGroup

    constructor(_clockGroup: ClockGroup): super(){
        this.clockGroup = _clockGroup
    }

    constructor(errorMessage: String): super(errorMessage){
        this.clockGroup = ClockGroup()
    }

}