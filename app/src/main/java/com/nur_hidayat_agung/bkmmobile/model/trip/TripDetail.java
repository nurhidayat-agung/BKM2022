package com.nur_hidayat_agung.bkmmobile.model.trip;

import com.google.gson.annotations.SerializedName;
import com.nur_hidayat_agung.bkmmobile.model.history.DetailHistory;

import java.io.Serializable;
import java.util.List;

public class TripDetail extends DetailHistory implements Serializable {

    private String SPBNo ;

    public String getSPBNo() {
        return SPBNo;
    }

    public void setSPBNo(String SPBNo) {
        this.SPBNo = SPBNo;
    }

    public List<StatusTrip> list_status_trip;

}
