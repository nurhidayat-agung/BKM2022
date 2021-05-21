package com.nur_hidayat_agung.bkmmobile.model.history;

import android.databinding.BindingAdapter;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.gson.annotations.SerializedName;
import com.nur_hidayat_agung.bkmmobile.R;
import com.nur_hidayat_agung.bkmmobile.util.UtilFunc;

import java.io.Serializable;
import java.util.Date;

public class DetailHistory implements Serializable {

    @SerializedName("id")
    private String id;

    @SerializedName("do_connect")
    public DoConnect Do_connectObject;

    @SerializedName("sub_do")
    public String sub_do;

    @SerializedName("spb_number")
    public String spb_number;

    @SerializedName("do_number")
    private String do_number;

    @SerializedName("load_date")
    private String load_date;

    @SerializedName("unload_date")
    private String unload_date;

    @SerializedName("driver_id")
    private String driver_id;

    @SerializedName("driver_name")
    private String driver_name;

    @SerializedName("pks_name")
    private String pks_name;

    @SerializedName("destination_name")
    private String destination_name;

    @SerializedName("amount_sent")
    private String amount_sent;

    @SerializedName("amount_received")
    private String amount_received;

    @SerializedName("commodity_name")
    private String commodity_name;

    @SerializedName("vehicle_number")
    private String vehicle_number;

    @SerializedName("status")
    private String status;

    @SerializedName("status_trip")
    private String status_trip;

    @SerializedName("qrcode")
    private String qrcode;

    @SerializedName("spb")
    private String spb;


    @SerializedName("trvl_expenses")
    private String trvl_expenses;

    public String getTrvl_expenses() {
        return "Rp. " + trvl_expenses;
    }

    public void setTrvl_expenses(String trvl_expenses) {
        this.trvl_expenses = trvl_expenses;
    }

    private int isConnect;

    public int getIsConnect() {
        return isConnect;
    }

    public void setIsConnect(int isConnect) {
        this.isConnect = isConnect;
    }

    private History history;

    public History getHistory() {
        return history;
    }

    public void setHistory(History history) {
        this.history = history;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public DoConnect getDo_connectObject() {
        return Do_connectObject;
    }

    public void setDo_connectObject(DoConnect do_connectObject) {
        Do_connectObject = do_connectObject;
    }

    public String getDo_number() {
        return "DO : " + do_number;
    }

    public String getJustDO()
    {
        return do_number;
    }

    public void setDo_number(String do_number) {
        this.do_number = do_number;
    }

    public String getLoad_date() {
        return load_date;
    }


    public void setLoad_date(String load_date) {
        this.load_date = load_date;
    }

    public String getUnload_date() {
        return unload_date;
    }

    public void setUnload_date(String unload_date) {
        this.unload_date = unload_date;
    }

    public String getDriver_id() {
        return driver_id;
    }

    public void setDriver_id(String driver_id) {
        this.driver_id = driver_id;
    }

    public String getDriver_name() {
        return driver_name;
    }

    public void setDriver_name(String driver_name) {
        this.driver_name = driver_name;
    }

    public String getPks_name() {
        return pks_name;
    }

    public void setPks_name(String pks_name) {
        this.pks_name = pks_name;
    }

    public String getDestination_name() {
        return destination_name;
    }

    public void setDestination_name(String destination_name) {
        this.destination_name = destination_name;
    }

    public String getAmount_sent() {
        if (amount_sent == null || amount_sent.equals("null")  || amount_sent.equals(""))
            return amount_sent;
        else return amount_sent;
    }

    public String getKGAmount_sent() {
        return amount_sent + " Kg";
    }

    public void setAmount_sent(String amount_sent) {
        this.amount_sent = amount_sent;
    }

    public String getKgAmount_received() {
        return amount_received + " Kg";
    }

    public String getAmount_received() {
        if (amount_received == null || amount_received.equals("null")  || amount_received.equals(""))
        return amount_received;
        else return amount_received;
    }

    public void setAmount_received(String amount_received) {
        this.amount_received = amount_received;
    }

    public String getCommodity_name() {
        return commodity_name;
    }

    public void setCommodity_name(String commodity_name) {
        this.commodity_name = commodity_name;
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

    public String getQrcode() {
        return qrcode;
    }

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
    }

    public String getSpb() {
        return spb;
    }

    public void setSpb(String spb) {
        this.spb = spb;
    }

    @BindingAdapter({"imageUrlQR"})
    public static void loadImageQR(ImageView view, String imageUrl){
        Log.d("tripDetLogBkm","Image Url : " + imageUrl);
        if (imageUrl == null || imageUrl.equals(""))
        {
            Glide.with(view.getContext()).load(R.drawable.qr_code).into(view);
        }
        else
        {
            Glide.with(view.getContext()).load(imageUrl).into(view);
        }
    }
}
