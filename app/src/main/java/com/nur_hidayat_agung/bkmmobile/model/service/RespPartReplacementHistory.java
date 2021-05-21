package com.nur_hidayat_agung.bkmmobile.model.service;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class RespPartReplacementHistory{

	@SerializedName("data")
	private List<ItemPartReplacementHistory> data;

	@SerializedName("message")
	private String message;

	@SerializedName("status")
	private int status;

	public void setData(List<ItemPartReplacementHistory> data){
		this.data = data;
	}

	public List<ItemPartReplacementHistory> getData(){
		return data;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}

	public void setStatus(int status){
		this.status = status;
	}

	public int getStatus(){
		return status;
	}

	@Override
 	public String toString(){
		return 
			"RespPartReplacementHistory{" + 
			"data = '" + data + '\'' + 
			",message = '" + message + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}