package com.nur_hidayat_agung.bkmmobile.repositories;

import com.nur_hidayat_agung.bkmmobile.model.general.DebugResponse;
import com.nur_hidayat_agung.bkmmobile.model.trip.StatusTrip;
import com.nur_hidayat_agung.bkmmobile.model.trip.Trip;
import com.nur_hidayat_agung.bkmmobile.model.trip.TripDetail;

import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;

public interface TripService {

    @GET("transaction/trips")
    @Headers({
            "Client-Service: driver-client",
            "Auth-Key: bkmrestapi",
            "Content-Type: application/json"
    })
    Observable<List<Trip>> getTrip();

    @GET("transaction/trip_detail/{id}")
    @Headers({
            "Client-Service: driver-client",
            "Auth-Key: bkmrestapi",
            "Content-Type: application/json"
    })
    Observable<TripDetail> getTripDetail(@Path("id") String id);

    @GET("transaction/ddl_status_trip")
    @Headers({
            "Client-Service: driver-client",
            "Auth-Key: bkmrestapi",
            "Content-Type: application/json"
    })
    Observable<List<StatusTrip>> getDLL();


    @Multipart
    @POST("transaction/save_trip")
    @Headers({
            "Client-Service: driver-client",
            "Auth-Key: bkmrestapi",
            "Content-Type: application/json"
    })
    Observable<DebugResponse> postTrip(
            @PartMap HashMap<String, okhttp3.RequestBody> bodyMap,
            @Part MultipartBody.Part file
    );

    @Multipart
    @POST("transaction/save_trip")
    @Headers({
            "Client-Service: driver-client",
            "Auth-Key: bkmrestapi",
            "Content-Type: application/json"
    })
    Observable<DebugResponse> _postTrip(
            @Part("id") int id,
            @Part("status_trip") int status_trip,
            @Part("number_of_load") int number_of_load,
            @Part("number_of_unload") int number_of_unload,
            @Part("trigger") String trigger,
            @Part("spb_no") int spb_no,
            @Part MultipartBody.Part file
    );

    @Multipart
    @POST("transaction/save_trip")
    @Headers({
            "Client-Service: driver-client",
            "Auth-Key: bkmrestapi"
    })
    Observable<DebugResponse> mPostTrip(
            @Part("id") okhttp3.RequestBody id,
            @Part("status_trip") okhttp3.RequestBody status_trip,
            @Part("number_of_load") okhttp3.RequestBody number_of_load,
            @Part("number_of_unload") okhttp3.RequestBody number_of_unload,
            @Part("trigger") okhttp3.RequestBody trigger,
            @Part("spb_no") okhttp3.RequestBody spb_no,
            @Part MultipartBody.Part file
    );

    @Multipart
    @POST("transaction/save_trip")
    @Headers({
            "Client-Service: driver-client",
            "Auth-Key: bkmrestapi"
    })
    Observable<DebugResponse> mPostTrip(
            @Part("id") okhttp3.RequestBody id,
            @Part("status_trip") okhttp3.RequestBody status_trip,
            @Part("number_of_load") okhttp3.RequestBody number_of_load,
            @Part("number_of_unload") okhttp3.RequestBody number_of_unload,
            @Part("trigger") okhttp3.RequestBody trigger,
            @Part("spb_no") okhttp3.RequestBody spb_no
    );
}
