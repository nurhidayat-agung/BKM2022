package com.nur_hidayat_agung.bkmmobile.model.service;

import com.google.gson.annotations.SerializedName;

public class RemindItem {
    @SerializedName("vehicle_id")
    public String vehicle_id;

    @SerializedName("total_km")
    public String total_km;

    @SerializedName("next_service")
    public String next_service;

    @SerializedName("remind_leter")
    public String remind_leter;

    @SerializedName("top_message")
    public String top_message;

}
