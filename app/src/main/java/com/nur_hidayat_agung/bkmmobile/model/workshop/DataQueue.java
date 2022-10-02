package com.nur_hidayat_agung.bkmmobile.model.workshop;

import com.google.gson.annotations.SerializedName;

public class DataQueue {

    @SerializedName("id")
    public String id;

    @SerializedName("code")
    public String code;

    @SerializedName("register_at")
    public String registerAt;

    @SerializedName("queue_number")
    public String queueNumber;

    @SerializedName("message")
    public String message;

    @SerializedName("seq")
    public String seq;

    @SerializedName("queue_to")
    public int queueTo;

    @SerializedName("status")
    public String status;

    public String dtRegister;

    public String txtStatus;
}
