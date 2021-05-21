package com.nur_hidayat_agung.bkmmobile.model.trip;

import com.google.gson.annotations.SerializedName;
import com.nur_hidayat_agung.bkmmobile.model.history.DoConnect;
import com.nur_hidayat_agung.bkmmobile.model.history.History;

import java.io.Serializable;
import java.util.Date;

public class Trip extends History implements Serializable {

    @SerializedName("driver_name")
    private String driver_name;

    @SerializedName("amount_sent")
    private String amount_sent;

    @SerializedName("amount_received")
    private String amount_received = null;

    @SerializedName("vehicle_number")
    private String vehicle_number;

    @SerializedName("status")
    private String status;

    @SerializedName("status_trip")
    private String status_trip = null;


    public String getDriver_name() {
        return driver_name;
    }

    public void setDriver_name(String driver_name) {
        this.driver_name = driver_name;
    }


    public String getAmount_sent() {
        return amount_sent;
    }

    public void setAmount_sent(String amount_sent) {
        this.amount_sent = amount_sent;
    }

    public String getAmount_received() {
        return amount_received;
    }

    public void setAmount_received(String amount_received) {
        this.amount_received = amount_received;
    }

    public String getVehicle_number() {
        return vehicle_number;
    }

    public void setVehicle_number(String vehicle_number) {
        this.vehicle_number = vehicle_number;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus_trip() {
        return status_trip;
    }

    public void setStatus_trip(String status_trip) {
        this.status_trip = status_trip;
    }
}
