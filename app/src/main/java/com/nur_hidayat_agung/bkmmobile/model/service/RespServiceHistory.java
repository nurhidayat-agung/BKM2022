package com.nur_hidayat_agung.bkmmobile.model.service;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class RespServiceHistory{

	@SerializedName("data")
	private List<ServiceItem> data;

	@SerializedName("message")
	private String message;

	@SerializedName("status")
	private int status;

	public List<ServiceItem> getData(){
		return data;
	}

	public String getMessage(){
		return message;
	}

	public int getStatus(){
		return status;
	}
}