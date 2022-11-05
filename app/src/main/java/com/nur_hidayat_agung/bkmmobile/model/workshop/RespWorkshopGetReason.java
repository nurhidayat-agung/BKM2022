package com.nur_hidayat_agung.bkmmobile.model.workshop;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class RespWorkshopGetReason{

	@SerializedName("data")
	public List<DataItemWorkshopGetReason> data;

	@SerializedName("message")
	public String message;

	@SerializedName("status")
	public int status;
}