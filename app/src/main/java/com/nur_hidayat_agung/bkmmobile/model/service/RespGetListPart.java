package com.nur_hidayat_agung.bkmmobile.model.service;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class RespGetListPart{

	@SerializedName("data")
	private List<ItemListPart> data;

	@SerializedName("message")
	private String message;

	@SerializedName("status")
	private int status;

	public void setData(List<ItemListPart> data){
		this.data = data;
	}

	public List<ItemListPart> getData(){
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
			"RespGetListPart{" + 
			"data = '" + data + '\'' + 
			",message = '" + message + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}