package com.example.rizk.pharmacysystem;

import java.io.Serializable;

public class User implements Serializable {
    private String UserName;
    private String FirstN;
    private String LastN;
    private String password;
    private String Email;
    private String Phone;

    public User(String Email, String UserName, String FirstN, String LastN, String password, String Phone) {
        this.UserName = UserName;
        this.FirstN = FirstN;
        this.LastN = LastN;
        this.password = password;
        this.Email = Email;
        this.Phone = Phone;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getFirstN() {
        return FirstN;
    }

    public void setFirstN(String firstN) {
        FirstN = firstN;
    }

    public String getLastN() {
        return LastN;
    }

    public void setLastN(String lastN) {
        LastN = lastN;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }
}
