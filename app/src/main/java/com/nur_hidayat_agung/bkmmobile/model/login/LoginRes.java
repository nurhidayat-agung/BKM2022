package com.nur_hidayat_agung.bkmmobile.model.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class LoginRes implements Serializable {

    @SerializedName("status")
    @Expose
    private int status;

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("user_id")
    @Expose
    private String user_id;

    @SerializedName("token")
    @Expose
    private String token;

    @SerializedName("firebase_token")
    @Expose
    private String firebase_token;

    public String getFirebase_token() {
        return firebase_token;
    }

    public void setFirebase_token(String firebase_token) {
        this.firebase_token = firebase_token;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
