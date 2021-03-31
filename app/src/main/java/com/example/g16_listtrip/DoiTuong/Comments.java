package com.example.g16_listtrip.DoiTuong;

public class Comments {

    public String getContentcmt() {
        return contentcmt;
    }

    public void setContentcmt(String contentcmt) {
        this.contentcmt = contentcmt;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getDattimeCmt() {
        return dattimeCmt;
    }

    public void setDattimeCmt(String dattimeCmt) {
        this.dattimeCmt = dattimeCmt;
    }

    public String getAvtUcmt() {
        return avtUcmt;
    }

    public void setAvtUcmt(String avtUcmt) {
        this.avtUcmt = avtUcmt;
    }

    public Comments() {
    }

    public Comments(String contentcmt, String user, String dattimeCmt, String avtUcmt) {
        this.contentcmt = contentcmt;
        this.user = user;
        this.dattimeCmt = dattimeCmt;
        this.avtUcmt = avtUcmt;
    }

    public String contentcmt, user, dattimeCmt, avtUcmt;


}
