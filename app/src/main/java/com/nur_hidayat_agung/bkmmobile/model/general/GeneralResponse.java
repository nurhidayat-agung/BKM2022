package com.nur_hidayat_agung.bkmmobile.model.general;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class GeneralResponse implements Serializable {

    @SerializedName("status")
    public int status;

    @SerializedName("message")
    public String message;
}
