package com.nur_hidayat_agung.bkmmobile.model.service;

import com.google.gson.annotations.SerializedName;

public class ItemPartReplacementHistory {

	@SerializedName("transaction_id")
	private String transactionId;

	@SerializedName("transaction_date")
	private String transactionDate;

	@SerializedName("transaction_code")
	private String transactionCode;

	@SerializedName("item_code")
	private String itemCode;

	@SerializedName("request_by")
	private String requestBy;

	@SerializedName("days")
	private String days;

	@SerializedName("brand_name")
	private String brandName;

	@SerializedName("item_name")
	private String itemName;

	public void setTransactionId(String transactionId){
		this.transactionId = transactionId;
	}

	public String getTransactionId(){
		return transactionId;
	}

	public void setTransactionDate(String transactionDate){
		this.transactionDate = transactionDate;
	}

	public String getTransactionDate(){
		return transactionDate;
	}

	public void setTransactionCode(String transactionCode){
		this.transactionCode = transactionCode;
	}

	public String getTransactionCode(){
		return transactionCode;
	}

	public void setItemCode(String itemCode){
		this.itemCode = itemCode;
	}

	public String getItemCode(){
		return itemCode;
	}

	public void setRequestBy(String requestBy){
		this.requestBy = requestBy;
	}

	public String getRequestBy(){
		return requestBy;
	}

	public void setDays(String days){
		this.days = days;
	}

	public String getDays(){
		return days;
	}

	public void setBrandName(String brandName){
		this.brandName = brandName;
	}

	public String getBrandName(){
		return brandName;
	}

	public void setItemName(String itemName){
		this.itemName = itemName;
	}

	public String getItemName(){
		return itemName;
	}

	@Override
 	public String toString(){
		return 
			"DataItem{" + 
			"transaction_id = '" + transactionId + '\'' + 
			",transaction_date = '" + transactionDate + '\'' + 
			",transaction_code = '" + transactionCode + '\'' + 
			",item_code = '" + itemCode + '\'' + 
			",request_by = '" + requestBy + '\'' + 
			",days = '" + days + '\'' + 
			",brand_name = '" + brandName + '\'' + 
			",item_name = '" + itemName + '\'' + 
			"}";
		}
}