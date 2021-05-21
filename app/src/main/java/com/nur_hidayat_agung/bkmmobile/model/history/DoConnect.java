package com.nur_hidayat_agung.bkmmobile.model.history;

import com.google.gson.annotations.SerializedName;
import com.nur_hidayat_agung.bkmmobile.util.UtilFunc;

import java.io.Serializable;
import java.util.Date;

public class DoConnect implements Serializable {

    @SerializedName("id")
    private String id;
    @SerializedName("do_number")
    private String do_number;
    @SerializedName("amount_sent")
    private String amount_sent;
    @SerializedName("amount_received")
    private String amount_received;
    @SerializedName("load_date")
    private Date load_date;
    @SerializedName("unload_date")
    private Date unload_date;
    @SerializedName("sub_do")
    public String sub_do;

    public String getLoad_date() {
        return UtilFunc.getDate(load_date);
    }

    public void setLoad_date(Date load_date) {
        this.load_date = load_date;
    }

    public String getUnload_date() {
        return UtilFunc.getDate(unload_date);
    }

    public void setUnload_date(Date unload_date) {
        this.unload_date = unload_date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDo_number() {
        return do_number;
    }

    public void setDo_number(String do_number) {
        this.do_number = do_number;
    }

    public String getAmount_sent() {
        return amount_sent;
    }

    public String getKgAmount_sent() {
        return amount_sent;
    }

    public void setAmount_sent(String amount_sent) {
        this.amount_sent = amount_sent;
    }

    public String getAmount_received() {
        return amount_received;
    }

    public String getKgAmount_received() {
        return amount_received;
    }

    public void setAmount_received(String amount_received) {
        this.amount_received = amount_received;
    }
}
