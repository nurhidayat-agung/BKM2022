package com.nur_hidayat_agung.bkmmobile.repositories;

import com.nur_hidayat_agung.bkmmobile.model.general.GeneralResponse;
import com.nur_hidayat_agung.bkmmobile.model.history.DetailHistory;
import com.nur_hidayat_agung.bkmmobile.model.history.History;
import com.nur_hidayat_agung.bkmmobile.model.service.RespGetListPart;
import com.nur_hidayat_agung.bkmmobile.model.service.RespPartReplacementHistory;
import com.nur_hidayat_agung.bkmmobile.model.service.RespServiceHistory;
import com.nur_hidayat_agung.bkmmobile.model.service.RespServiceReminder;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ServiceService {

    @GET("book_service/list_sparepart")
    @Headers({
            "Client-Service: driver-client",
            "Auth-Key: bkmrestapi",
            "Content-Type: application/json"
    })
    Observable<RespGetListPart> getListSparePart();



    @GET("book_service/replacement_sparepart")
    @Headers({
            "Client-Service: driver-client",
            "Auth-Key: bkmrestapi",
            "Content-Type: application/json"
    })
    Observable<RespPartReplacementHistory> getReplacementPartHistory(
            @Query("vehicle") String vehicle,
            @Query("item") String item
    );

    @GET("book_service")
    @Headers({
            "Client-Service: driver-client",
            "Auth-Key: bkmrestapi",
            "Content-Type: application/json"
    })
    Observable<RespServiceHistory> loadServiceHistory(@Query("vehicle_id") String vehicleID);

    @GET("book_service/service_schedule")
    @Headers({
            "Client-Service: driver-client",
            "Auth-Key: bkmrestapi",
            "Content-Type: application/json"
    })
    Observable<RespServiceReminder> getServiceAlert(@Query("vehicle_id") String vehicleID);

    @FormUrlEncoded
    @POST("book_service/save")
    @Headers({
            "Client-Service: driver-client",
            "Auth-Key: bkmrestapi",
            "Content-Type: application/x-www-form-urlencoded"
    })
    Observable<RespServiceHistory> saveService(@Field("vehicle_id") String vehicle_id,
                                               @Field("service_date") String dtService,
                                               @Field("description") String desc);

    @GET("vehicle_km/remind_leter")
    @Headers({
            "Client-Service: driver-client",
            "Auth-Key: bkmrestapi",
            "Content-Type: application/json"
    })
    Observable<RespServiceHistory> remindLater(@Query("vehicle_id") String vehicle_id);
}
