package com.nur_hidayat_agung.bkmmobile.model.service;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ServiceItem implements Serializable {

	@SerializedName("id")
	public String id;

	@SerializedName("vehicle_id")
	public String vehicle_id;

	@SerializedName("service_date")
	public String service_date;

	@SerializedName("km")
	public String km;

	@SerializedName("actual_km")
	public String actual_km;

	@SerializedName("description")
	public String description;

}