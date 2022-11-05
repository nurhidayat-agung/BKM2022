package com.nur_hidayat_agung.bkmmobile.model.workshop;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity
public class DataItemWorkshopGetReason{

	@ColumnInfo(name = "reason")
	@SerializedName("reason")
	public String reason;

	@ColumnInfo(name = "created_at")
	@SerializedName("created_at")
	public String createdAt;

	@PrimaryKey
	@ColumnInfo(name = "id")
	@SerializedName("id")
	public String id;

	@ColumnInfo(name = "deleted_at")
	@SerializedName("deleted_at")
	public Object deletedAt;
}