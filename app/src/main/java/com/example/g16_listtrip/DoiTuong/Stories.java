package com.example.g16_listtrip.DoiTuong;

import java.io.Serializable;

public class Stories implements Serializable {
    public String accS, imgS, timeS;

    public String getAccS() {
        return accS;
    }

    @Override
    public String toString() {
        return "Stories{" +
                "accS='" + accS + '\'' +
                ", imgS='" + imgS + '\'' +
                ", timeS='" + timeS + '\'' +
                '}';
    }

    public void setAccS(String accS) {
        this.accS = accS;
    }

    public String getImgS() {
        return imgS;
    }

    public void setImgS(String imgS) {
        this.imgS = imgS;
    }

    public String getTimeS() {
        return timeS;
    }

    public void setTimeS(String timeS) {
        this.timeS = timeS;
    }

    public Stories() {
    }

    public Stories(String accS, String imgS, String timeS) {
        this.accS = accS;
        this.imgS = imgS;
        this.timeS = timeS;
    }
}
