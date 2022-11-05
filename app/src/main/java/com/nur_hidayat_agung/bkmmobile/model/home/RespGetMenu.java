package com.nur_hidayat_agung.bkmmobile.model.home;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class RespGetMenu{

	@SerializedName("data")
	public List<DataItemMenu> data;

	@SerializedName("message")
	public String message;

	@SerializedName("status")
	public int status;
}