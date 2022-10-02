package com.nur_hidayat_agung.bkmmobile.model.workshop;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ItemHistoryWorkShop {

	@SerializedName("reason")
	public String reason;

	@SerializedName("list_sparepart")
	public List<ItemSparePartWorkShop> listSparepart;

	@SerializedName("driver_id")
	public String driverId;

	@SerializedName("code")
	public String code;

	@SerializedName("description")
	public String description;

	@SerializedName("checkin")
	public String checkin;

	@SerializedName("register_at")
	public String registerAt;

	@SerializedName("id")
	public String id;

	@SerializedName("vehicle_id")
	public String vehicleId;

	@SerializedName("checkout")
	public Object checkout;

	@SerializedName("seq")
	public String seq;

	@SerializedName("status")
	public String status;

	@SerializedName("sparepart_trx_code")
	public String sparepartTrxCode;


	public String remarkPart;

	@SerializedName("driver")
	public WLListItemDriver driver = new WLListItemDriver();

	@SerializedName("vehicle")
	public WLListItemVehicle vehicle = new WLListItemVehicle();


}