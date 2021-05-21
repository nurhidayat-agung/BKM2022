package com.nur_hidayat_agung.bkmmobile.repositories;

import com.nur_hidayat_agung.bkmmobile.model.help.Help;
import com.nur_hidayat_agung.bkmmobile.model.history.DetailHistory;
import com.nur_hidayat_agung.bkmmobile.model.history.History;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

public interface HelpService {

    @GET("help")
    @Headers({
            "Client-Service: driver-client",
            "Auth-Key: bkmrestapi",
            "Content-Type: application/json"
    })
    Observable<List<Help>> getHelp();

}
