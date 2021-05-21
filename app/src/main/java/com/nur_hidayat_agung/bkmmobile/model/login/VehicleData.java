package com.nur_hidayat_agung.bkmmobile.model.login;

import com.google.gson.annotations.SerializedName;

public class VehicleData{

	@SerializedName("repair_status")
	public String repairStatus;

	@SerializedName("vehicle_number")
	public String vehicleNumber;

	@SerializedName("id")
	public String id;

	@SerializedName("is_available")
	public String isAvailable;

	@SerializedName("capacity")
	public String capacity;
}