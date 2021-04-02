package com.example.g16_listtrip.DoiTuong;

public class Schedule {
    public String timeTo, placeTo, vehicle, note;

    public Schedule(String timeTo, String placeTo, String vehicle, String note) {
        this.timeTo = timeTo;
        this.placeTo = placeTo;
        this.vehicle = vehicle;
        this.note = note;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Schedule() {
    }

    public String getTimeTo() {
        return timeTo;
    }

    public void setTimeTo(String timeTo) {
        this.timeTo = timeTo;
    }

    public String getPlaceTo() {
        return placeTo;
    }

    public void setPlaceTo(String placeTo) {
        this.placeTo = placeTo;
    }

    public String getVehicle() {
        return vehicle;
    }

    public void setVehicle(String vehicle) {
        this.vehicle = vehicle;
    }

    @Override
    public String toString() {
        return "Schedule{" +
                "timeTo='" + timeTo + '\'' +
                ", placeTo='" + placeTo + '\'' +
                ", vehicle='" + vehicle + '\'' +
                ", note='" + note + '\'' +
                '}';
    }
}
