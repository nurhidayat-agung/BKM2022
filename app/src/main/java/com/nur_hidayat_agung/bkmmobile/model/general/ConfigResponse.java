package com.nur_hidayat_agung.bkmmobile.model.general;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ConfigResponse implements Serializable {
    @SerializedName("base_url")
    public String base_url;
}
