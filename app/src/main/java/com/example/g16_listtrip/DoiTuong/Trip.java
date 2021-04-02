package com.example.g16_listtrip.DoiTuong;

public class Trip {
    public String time, location;
    public int cost;
    public int amountP;
    public String timeAdd, timeIntend;

    public String getTimeAdd() {
        return timeAdd;
    }

    public void setTimeAdd(String timeAdd) {
        this.timeAdd = timeAdd;
    }

    public String getTimeIntend() {
        return timeIntend;
    }

    public void setTimeIntend(String timeIntend) {
        this.timeIntend = timeIntend;
    }

    public Trip(String time, String location, int cost, int amountP, String timeAdd, String timeIntend) {

        this.time = time;
        this.location = location;
        this.cost = cost;
        this.amountP = amountP;
        this.timeAdd = timeAdd;
        this.timeIntend = timeIntend;
    }

    public Trip() {
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getAmountP() {
        return amountP;
    }

    public void setAmountP(int amountP) {
        this.amountP = amountP;
    }

    @Override
    public String toString() {
        return "Trip{" +
                "time='" + time + '\'' +
                ", location='" + location + '\'' +
                ", cost=" + cost +
                ", amountP=" + amountP +
                ", timeAdd='" + timeAdd + '\'' +
                '}';
    }
}
