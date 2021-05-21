package com.nur_hidayat_agung.bkmmobile.model.history;

import com.google.gson.annotations.SerializedName;
import com.nur_hidayat_agung.bkmmobile.util.UtilFunc;

import java.io.Serializable;
import java.util.Date;

public class History implements Serializable, Comparable<History> {

    public boolean flag;

    @SerializedName("driver_id")
    private String driver_id;

    @SerializedName("bonus")
    private String bonus;

    public DoConnect getDo_connect() {
        return do_connect;
    }

    public void setDo_connect(DoConnect do_connect) {
        this.do_connect = do_connect;
    }

    @SerializedName("do_connect")
    private DoConnect do_connect;

    @SerializedName("destination_name")
    private String destination_name;

    @SerializedName("id")
    private String id;

    @SerializedName("do_number")
    private String do_number;

    @SerializedName("load_date")
    private Date load_date;

    @SerializedName("pks_name")
    private String pks_name;

    @SerializedName("unload_date")
    private Date unload_date;

    @SerializedName("commodity_name")
    private String commodity_name;

    private int header;
    private int line;
    private String monthName;


    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public void setHeader(int header) {
        this.header = header;
    }

    public void setMonthName(String monthName) {
        this.monthName = monthName;
    }

    public int getHeader() {
        return header;
    }

    public String getMonthName() {
        return monthName;
    }

    public String getDriver_id() {
        return driver_id;
    }

    public String getBonus() {
        return "+Rp. " + bonus;
    }

    public String getDestination_name() {
        return destination_name;
    }

    public String getId() {
        return id;
    }

    public String getDo_number() {
        return "DO : " + do_number;
    }

    public String fullDO()
    {
        if (this.do_connect == null)
            return do_number;
        else
        {
            int a = Integer.parseInt(id);
            int b = Integer.parseInt(do_connect.getId());
            if (a > b)
            {
                return do_connect.getDo_number() + " & " + do_number;
            }
            else
            {
                return do_number + " & " + do_connect.getDo_number();
            }
        }
    }

    public String getDoFromHistory()
    {
        if (this.do_connect == null)
            return do_number;
        else
        {
            int a = Integer.parseInt(id);
            int b = Integer.parseInt(do_connect.getId());
            if (a > b)
            {
                return do_connect.getDo_number().substring(0,4) + " & " + do_number;
            }
            else
            {
                return do_number.substring(0,4) + " & " + do_connect.getDo_number();
            }
        }
    }

    public String getTravelDate()
    {

        return UtilFunc.getDate(getLoad_date()) + " - " + UtilFunc.getDate(getUnload_date());
    }

    public String getDest()
    {
        return getDestination_name() + " | " + getCommodity_name();
    }

    public Date getLoad_date() {
        return load_date;
    }

    public String getPks_name() {
        return pks_name;
    }

    public Date getUnload_date() {
        return unload_date;
    }

    public String getCommodity_name() {
        return commodity_name;
    }

    @Override
    public int compareTo(History o) {
        if (getLoad_date() == null || o.getLoad_date() == null) {
            return 0;
        }
        return getLoad_date().compareTo(o.getLoad_date());
    }
}
