package com.nur_hidayat_agung.bkmmobile.model.login;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

public class LoginAnnouncement implements Serializable {

    @SerializedName("id")
    public String id;

    @SerializedName("user_id")
    public String user_id;

    @SerializedName("start_date")
    public Date start_date;

    @SerializedName("end_date")
    public Date end_date;

    @SerializedName("announcement")
    public String announcement;

    @SerializedName("font_size")
    public float font_size;

    @SerializedName("font_color")
    public String font_color;

    @SerializedName("background_img")
    public String background_img;

    @SerializedName("background_color")
    public String background_color;

    @SerializedName("is_img")
    public String is_img;

    public int visibility;
}
