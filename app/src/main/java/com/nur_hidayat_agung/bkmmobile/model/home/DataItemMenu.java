package com.nur_hidayat_agung.bkmmobile.model.home;

import com.google.gson.annotations.SerializedName;

public class DataItemMenu {

	@SerializedName("menu_name")
	public String menuName;

	@SerializedName("icon")
	public String icon;

	@SerializedName("class_name")
	public String className;

	@SerializedName("seq")
	public int seq;
}