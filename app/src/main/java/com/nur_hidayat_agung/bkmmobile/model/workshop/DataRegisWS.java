package com.nur_hidayat_agung.bkmmobile.model.workshop;

import com.google.gson.annotations.SerializedName;

public class DataRegisWS{

	@SerializedName("reason")
	public String reason;

	@SerializedName("driver_id")
	public String driverId;

	@SerializedName("code")
	public String code;

	@SerializedName("ci_session_")
	public String ciSession;

	@SerializedName("register_at")
	public String registerAt;

	@SerializedName("queue_number")
	public String queueNumber;

	@SerializedName("id")
	public String id;

	@SerializedName("vehicle_id")
	public String vehicleId;

	@SerializedName("seq")
	public int seq;

	@SerializedName("queue_to")
	public int queueTo;

	@SerializedName("status")
	public String status;

}