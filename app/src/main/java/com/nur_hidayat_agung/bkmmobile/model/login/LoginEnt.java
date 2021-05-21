package com.nur_hidayat_agung.bkmmobile.model.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class LoginEnt implements Serializable {

    @SerializedName("username")
    @Expose
    public String username;
    @SerializedName("password")
    @Expose
    public String password;

}
