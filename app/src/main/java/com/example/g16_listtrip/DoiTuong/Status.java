package com.example.g16_listtrip.DoiTuong;

public class Status {
    public String usermaster, contentStt, bitImgStt, locationStt, datetimeStt;
    public int like;
    public Status() {
    }

    public Status(String usermaster, String contentStt, String bitImgStt, String locationStt, String datetimeStt, int like) {
        this.usermaster = usermaster;
        this.contentStt = contentStt;
        this.bitImgStt = bitImgStt;
        this.locationStt = locationStt;
        this.datetimeStt = datetimeStt;
        this.like = like;
    }
}