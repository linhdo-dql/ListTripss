package com.example.g16_listtrip.DoiTuong;

import java.io.Serializable;

public class Accounts implements Serializable {
    public String username, password, email;

    public Accounts(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public Accounts() {
    }
}
