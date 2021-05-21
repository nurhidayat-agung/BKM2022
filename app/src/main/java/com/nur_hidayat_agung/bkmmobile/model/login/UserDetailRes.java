package com.nur_hidayat_agung.bkmmobile.model.login;

import com.google.gson.annotations.SerializedName;
import com.nur_hidayat_agung.bkmmobile.model.history.History;

import java.io.Serializable;
import java.util.List;

public class UserDetailRes implements Serializable {

    private int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @SerializedName("user_id")
    private String user_id;

    @SerializedName("driver_id")
    private String driver_id;

    @SerializedName("full_name")
    private String full_name;

    @SerializedName("mobile_number")
    private String mobile_number;

    @SerializedName("DOB")
    private String DOB;

    @SerializedName("address")
    private String address;

    @SerializedName("blood_type")
    private String blood_type;

    @SerializedName("profil_img")
    public String profil_img;

    @SerializedName("active_working_date")
    private String active_working_date;

    @SerializedName("dedication")
    private String dedication;

    @SerializedName("number_of_trip")
    private int number_of_trip;

    @SerializedName("announcement")
    public List<LoginAnnouncement> announcement;

    @SerializedName("vehicle")
    public VehicleData vehicle;

    public String firebase_token;

    public int getNumber_of_trip() {
        return number_of_trip;
    }

    public void setNumber_of_trip(int number_of_trip) {
        this.number_of_trip = number_of_trip;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getDriver_id() {
        return driver_id;
    }

    public void setDriver_id(String driver_id) {
        this.driver_id = driver_id;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getMobile_number() {
        return mobile_number;
    }

    public void setMobile_number(String mobile_number) {
        this.mobile_number = mobile_number;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBlood_type() {
        return blood_type;
    }

    public void setBlood_type(String blood_type) {
        this.blood_type = blood_type;
    }


    public String getActive_working_date() {
        return active_working_date;
    }

    public void setActive_working_date(String active_working_date) {
        this.active_working_date = active_working_date;
    }

    public String getDedication() {
        return dedication;
    }

    public void setDedication(String dedication) {
        this.dedication = dedication;
    }

    public String getCount()
    {
        return "Pengabdian : " + dedication;
    }

    public String welcomeMessage()
    {
        return "Hi... "+full_name+" Berkendaralah dengan aman, ingat keluarga menannti di rumah";
        //return announcement;
    }
}
