package com.nur_hidayat_agung.bkmmobile.model.workshop;

import com.google.gson.annotations.SerializedName;

public class RespQueue{

	@SerializedName("data")
	public DataQueue data;

	@SerializedName("message")
	public String message;

	@SerializedName("status")
	public int status;

}

