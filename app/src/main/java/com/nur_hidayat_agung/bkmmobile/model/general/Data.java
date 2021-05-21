package com.nur_hidayat_agung.bkmmobile.model.general;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

public class Data implements Serializable {
    @SerializedName("id")
    private String id;

    @SerializedName("status_trip")
    private String status_trip;

    @SerializedName("load_date")
    private Date load_date;

    @SerializedName("unload_date")
    private Date unload_date;

    @SerializedName("spb_number")
    private String spb_number;

    @SerializedName("amount_sent")
    private String amount_sent;

    @SerializedName("amount_received")
    private String amount_received;

    @SerializedName("domaster_id")
    private String domaster_id;

    @SerializedName("trigger")
    private String trigger;

    @SerializedName("spb_id")
    private String spb_id;

    @SerializedName("status")
    private String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus_trip() {
        return status_trip;
    }

    public void setStatus_trip(String status_trip) {
        this.status_trip = status_trip;
    }

    public Date getLoad_date() {
        return load_date;
    }

    public void setLoad_date(Date load_date) {
        this.load_date = load_date;
    }

    public Date getUnload_date() {
        return unload_date;
    }

    public void setUnload_date(Date unload_date) {
        this.unload_date = unload_date;
    }

    public String getSpb_number() {
        return spb_number;
    }

    public void setSpb_number(String spb_number) {
        this.spb_number = spb_number;
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

    public String getDomaster_id() {
        return domaster_id;
    }

    public void setDomaster_id(String domaster_id) {
        this.domaster_id = domaster_id;
    }

    public String getTrigger() {
        return trigger;
    }

    public void setTrigger(String trigger) {
        this.trigger = trigger;
    }

    public String getSpb_id() {
        return spb_id;
    }

    public void setSpb_id(String spb_id) {
        this.spb_id = spb_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
