package com.example.g16_listtrip.DoiTuong;

public class Status {
    public String usermaster, contentStt, bitImgStt, locationStt, datetimeStt;

    public String getUsermaster() {
        return usermaster;
    }

    public void setUsermaster(String usermaster) {
        this.usermaster = usermaster;
    }

    public String getContentStt() {
        return contentStt;
    }

    public void setContentStt(String contentStt) {
        this.contentStt = contentStt;
    }

    public String getBitImgStt() {
        return bitImgStt;
    }

    public void setBitImgStt(String bitImgStt) {
        this.bitImgStt = bitImgStt;
    }

    public String getLocationStt() {
        return locationStt;
    }

    public void setLocationStt(String locationStt) {
        this.locationStt = locationStt;
    }

    public String getDatetimeStt() {
        return datetimeStt;
    }

    public void setDatetimeStt(String datetimeStt) {
        this.datetimeStt = datetimeStt;
    }

    public Status() {
    }

    public Status(String usermaster, String contentStt, String bitImgStt, String locationStt, String datetimeStt) {
        this.usermaster = usermaster;
        this.contentStt = contentStt;
        this.bitImgStt = bitImgStt;
        this.locationStt = locationStt;
        this.datetimeStt = datetimeStt;
    }
}
