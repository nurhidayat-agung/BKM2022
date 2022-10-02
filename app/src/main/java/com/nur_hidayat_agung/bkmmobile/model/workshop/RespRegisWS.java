package com.nur_hidayat_agung.bkmmobile.model.workshop;

import com.google.gson.annotations.SerializedName;

public class RespRegisWS{

	@SerializedName("data")
	public DataRegisWS data;

	@SerializedName("message")
	public String message;

	@SerializedName("status")
	public int status;

}