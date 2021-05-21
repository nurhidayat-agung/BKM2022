package com.nur_hidayat_agung.bkmmobile.model.general;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Msg implements Serializable {

    @SerializedName("status")
    private int status;

    @SerializedName("message")
    private String message;

    public Msg(int status, String msg) {
        this.status = status;
        this.message = msg;
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
}
