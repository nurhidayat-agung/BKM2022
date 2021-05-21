package com.nur_hidayat_agung.bkmmobile.model.trip;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class StatusTrip implements Serializable {

    @SerializedName("id")
    public String id;

    @SerializedName("short_name")
    public String short_name;

    @SerializedName("long_name")
    public String long_name;
}
