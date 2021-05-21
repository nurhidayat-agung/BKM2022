package com.nur_hidayat_agung.bkmmobile.model.service;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class RespServiceReminder{

	@SerializedName("data")
	public List<RemindItem> data;

	@SerializedName("message")
	public String message;

	@SerializedName("status")
	public int status;

}