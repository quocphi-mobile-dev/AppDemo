package com.example.appdemo_1704.json_models.request;

import com.google.gson.annotations.SerializedName;

public class RegisterSendForm {
    @SerializedName("username")
    private String username;
    @SerializedName("password")
    private String password;
    @SerializedName("fullName")
    private String fullName;
    @SerializedName("address")
    private String address;
    @SerializedName("phone")
    private String phone;

    public RegisterSendForm(String username, String password, String fullName, String address, String phone) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.address = address;
        this.phone = phone;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
