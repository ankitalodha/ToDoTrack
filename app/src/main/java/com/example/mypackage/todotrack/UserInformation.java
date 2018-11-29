package com.example.mypackage.todotrack;

public class UserInformation {

    public String taskName;
    public String taskDesc;
    public String taskLocation;
    public Double lat, lng;

    public UserInformation() {
    }


    public UserInformation(String taskName, String taskDesc, String taskLocation, Double lat, Double lng) {
        this.taskName = taskName;
        this.taskDesc = taskDesc;
        this.taskLocation = taskLocation;
        this.lat = lat;
        this.lng = lng;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskDesc() {
        return taskDesc;
    }

    public void setTaskDesc(String taskDesc) {
        this.taskDesc = taskDesc;
    }

    public String getTaskLocation() {
        return taskLocation;
    }

    public void setTaskLocation(String taskLocation) {
        this.taskLocation = taskLocation;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    @Override
    public String toString() {
        return "UserInformation{" +
                "taskName='" + taskName + '\'' +
                ", taskDesc='" + taskDesc + '\'' +
                ", taskLocation='" + taskLocation + '\'' +
                ", lat=" + lat +
                ", lng=" + lng +
                '}';
    }
}
