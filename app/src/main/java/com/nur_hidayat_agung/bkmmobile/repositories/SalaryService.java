package com.nur_hidayat_agung.bkmmobile.repositories;

import com.nur_hidayat_agung.bkmmobile.model.general.GeneralResponse;
import com.nur_hidayat_agung.bkmmobile.model.payslip.PaySlip;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface SalaryService {

    @GET("finance/payslip")
    @Headers({
            "Client-Service: driver-client",
            "Auth-Key: bkmrestapi",
            "Content-Type: application/json"
    })
    Observable<PaySlip> getPaySlip(
            @Query("month") int month,
            @Query("year") int year);
}
