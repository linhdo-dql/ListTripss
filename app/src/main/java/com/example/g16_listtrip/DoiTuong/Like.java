package com.example.g16_listtrip.DoiTuong;

import java.io.Serializable;

public class Like implements Serializable {
    public int statuslike;
    public String userLike;

    public Like(int statuslike, String userLike) {
        this.statuslike = statuslike;
        this.userLike = userLike;
    }

    public Like() {
    }
}
