package status;

import core.dao.ColumnAnnotation;

// StatusExample was included to show how we can make our object model classes using ColumnAnnotations in JAVA instead of KOTLIN.
// Do not use this class in your code! Use Status instead.
public class StatusExample {
    private @ColumnAnnotation(columnName = "statusID")
    String statusID = "";
    private @ColumnAnnotation(columnName = "clockHandAngle")
    Double clockHandAngle = 0.0;
    private @ColumnAnnotation(columnName = "statusName")
    String statusName = "";
    private @ColumnAnnotation(columnName = "groupID")
    String groupID = "";

    public StatusExample() {

    }

    public String getStatusID() {
        return statusID;
    }

    public void setStatusID(String statusID) {
        this.statusID = statusID;
    }

    public Double getClockHandAngle() {
        return clockHandAngle;
    }

    public void setClockHandAngle(Double clockHandAngle) {
        this.clockHandAngle = clockHandAngle;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }
}

