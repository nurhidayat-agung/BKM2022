package com.nur_hidayat_agung.bkmmobile.model.service;

import com.google.gson.annotations.SerializedName;

public class ItemListPart {

	@SerializedName("item_code")
	private String itemCode;

	@SerializedName("price")
	private String price;

	@SerializedName("item_name")
	private String itemName;

	@SerializedName("id")
	private String id;

	@SerializedName("unit_id")
	private String unitId;

	@SerializedName("brand_id")
	private String brandId;

	public void setItemCode(String itemCode){
		this.itemCode = itemCode;
	}

	public String getItemCode(){
		return itemCode;
	}

	public void setPrice(String price){
		this.price = price;
	}

	public String getPrice(){
		return price;
	}

	public void setItemName(String itemName){
		this.itemName = itemName;
	}

	public String getItemName(){
		return itemName;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setUnitId(String unitId){
		this.unitId = unitId;
	}

	public String getUnitId(){
		return unitId;
	}

	public void setBrandId(String brandId){
		this.brandId = brandId;
	}

	public String getBrandId(){
		return brandId;
	}

	@Override
 	public String toString(){
		return 
			"DataItem{" + 
			"item_code = '" + itemCode + '\'' + 
			",price = '" + price + '\'' + 
			",item_name = '" + itemName + '\'' + 
			",id = '" + id + '\'' + 
			",unit_id = '" + unitId + '\'' + 
			",brand_id = '" + brandId + '\'' + 
			"}";
		}
}