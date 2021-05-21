package com.nur_hidayat_agung.bkmmobile.model.general;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DebugResponse implements Serializable {

    @SerializedName("msg")
    private Msg msg;

    @SerializedName("data")
    private Data data;

    private int status;

    public String message;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Msg getMsg() {
        return msg;
    }

    public void setMsg(Msg msg) {
        this.msg = msg;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
}
