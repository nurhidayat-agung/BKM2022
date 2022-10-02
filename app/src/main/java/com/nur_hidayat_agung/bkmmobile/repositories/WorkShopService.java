package com.nur_hidayat_agung.bkmmobile.repositories;

import com.nur_hidayat_agung.bkmmobile.model.trip.Trip;
import com.nur_hidayat_agung.bkmmobile.model.workshop.RespCancelRegistrasion;
import com.nur_hidayat_agung.bkmmobile.model.workshop.RespHistoryWorkShop;
import com.nur_hidayat_agung.bkmmobile.model.workshop.RespQueue;
import com.nur_hidayat_agung.bkmmobile.model.workshop.RespRegisWS;
import com.nur_hidayat_agung.bkmmobile.model.workshop.RespWL;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface WorkShopService {
    @GET("workshops/queue")
    @Headers({
            "Client-Service: driver-client",
            "Auth-Key: bkmrestapi",
            "Content-Type: application/json; charset=UTF-8"
    })
    Observable<RespQueue> getQueue(@Query("driver_id") String driver_id);

    @GET("workshops/cancel")
    @Headers({
            "Client-Service: driver-client",
            "Auth-Key: bkmrestapi",
            "Content-Type: application/json; charset=UTF-8"
    })
    Observable<RespCancelRegistrasion> cancelRegis(@Query("id") String id_queue);

    @FormUrlEncoded
    @POST("workshops/register")
    @Headers({
            "Client-Service: driver-client",
            "Auth-Key: bkmrestapi",
            "Content-Type: application/x-www-form-urlencoded"
    })
    Observable<RespRegisWS> regisWS(
            @Field("driver_id") String driver_id,
            @Field("vehicle_id") String vehicle_id,
            @Field("reason") String reason
    );

    @GET("workshops/history")
    @Headers({
            "Client-Service: driver-client",
            "Auth-Key: bkmrestapi",
            "Content-Type: application/json; charset=UTF-8"
    })
    Observable<RespHistoryWorkShop> getHistory(@Query("driver_id") String driver_id);

    @GET("workshops/waiting_list")
    @Headers({
            "Client-Service: driver-client",
            "Auth-Key: bkmrestapi",
            "Content-Type: application/json; charset=UTF-8"
    })
    Observable<RespWL> getWL();
}
