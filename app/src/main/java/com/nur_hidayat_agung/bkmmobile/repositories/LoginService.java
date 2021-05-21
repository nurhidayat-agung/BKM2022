package com.nur_hidayat_agung.bkmmobile.repositories;

import com.nur_hidayat_agung.bkmmobile.model.general.ConfigResponse;
import com.nur_hidayat_agung.bkmmobile.model.login.LoginAnnouncement;
import com.nur_hidayat_agung.bkmmobile.model.login.LoginRes;
import com.nur_hidayat_agung.bkmmobile.model.login.UserDetailRes;
import com.nur_hidayat_agung.bkmmobile.model.general.GeneralResponse;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface LoginService {

    @GET("configure")
    @Headers({
            "Client-Service: driver-client",
            "Auth-Key: bkmrestapi",
            "Content-Type: application/json"
    })
    Observable<ConfigResponse> getConfig();


    @POST("auth/login")
    @Headers({
        "Client-Service: driver-client",
        "Auth-Key: bkmrestapi",
        "Content-Type: application/json"
    })
    Observable<LoginRes> login(@Query("username") String username, @Query("password") String password);

    @GET("transaction/user_info")
    @Headers({
            "Client-Service: driver-client",
            "Auth-Key: bkmrestapi",
            "Content-Type: application/json"
    })
    Observable<UserDetailRes> userDetail(@Query("user_id") String user_id);

    @POST("auth/change_password")
    @Headers({
            "Client-Service: driver-client",
            "Auth-Key: bkmrestapi",
            "Content-Type: application/json"
    })
    Observable<GeneralResponse> changePass(
            @Query("password") String password,
            @Query("password_new") String password_new,
            @Query("password_confirm") String password_confirm);

    @GET("announcement/")
    @Headers({
            "Client-Service: driver-client",
            "Auth-Key: bkmrestapi",
            "Content-Type: application/json"
    })
    Observable<LoginAnnouncement> getAnnouncement();


    @FormUrlEncoded
    @POST("auth/firebasetoken")
    @Headers({
            "Client-Service: driver-client",
            "Auth-Key: bkmrestapi",
            "Content-Type: application/x-www-form-urlencoded"
    })
    Observable<GeneralResponse> pushFireBase(
            @Field("firebase_token") String firebase_token);



    @POST("auth/logout")
    @Headers({
            "Client-Service: driver-client",
            "Auth-Key: bkmrestapi",
            "Content-Type: application/json"
    })
    Observable<GeneralResponse> logout();


}
