package com.nur_hidayat_agung.bkmmobile.model.workshop;

import com.google.gson.annotations.SerializedName;

public class ItemWL {

    @SerializedName("reason")
    public String reason;

    @SerializedName("driver_id")
    public String driverId;

    @SerializedName("code")
    public String code;

    @SerializedName("checkin")
    public String checkin;

    @SerializedName("checkout")
    public String checkout;

    @SerializedName("register_at")
    public String registerAt;

    @SerializedName("description")
    public String description;

    @SerializedName("id")
    public String id;

    @SerializedName("vehicle_id")
    public String vehicleId;

    @SerializedName("seq")
    public String seq;

    @SerializedName("status")
    public String status;

    @SerializedName("sparepart_trx_code")
    public String sparepartTrxCode;

    @SerializedName("driver")
    public WLListItemDriver driver = new WLListItemDriver();

    @SerializedName("vehicle")
    public WLListItemVehicle vehicle = new WLListItemVehicle();
}
