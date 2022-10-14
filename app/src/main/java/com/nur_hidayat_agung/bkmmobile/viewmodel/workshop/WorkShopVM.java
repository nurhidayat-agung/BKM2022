package com.nur_hidayat_agung.bkmmobile.viewmodel.workshop;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;
import com.nur_hidayat_agung.bkmmobile.app.BKMApp;
import com.nur_hidayat_agung.bkmmobile.app.BaseContext;
import com.nur_hidayat_agung.bkmmobile.model.login.UserDetailRes;
import com.nur_hidayat_agung.bkmmobile.model.workshop.ItemHistoryWorkShop;
import com.nur_hidayat_agung.bkmmobile.model.workshop.ItemWL;
import com.nur_hidayat_agung.bkmmobile.model.workshop.RespRegisWS;
import com.nur_hidayat_agung.bkmmobile.repositories.TripService;
import com.nur_hidayat_agung.bkmmobile.repositories.WorkShopService;
import com.nur_hidayat_agung.bkmmobile.util.Constant;
import com.nur_hidayat_agung.bkmmobile.util.SharedPref;
import com.nur_hidayat_agung.bkmmobile.util.UtilFunc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import retrofit2.adapter.rxjava2.HttpException;

public class WorkShopVM extends AndroidViewModel {
    private BKMApp bkmApp;
    private WorkShopService service;
    private Context context;
    private SharedPref sharedPref;
    public MutableLiveData<Boolean> pDialog = new MutableLiveData<>();
    public MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    public MutableLiveData<Boolean> isAnyData = new MutableLiveData<>();
    public MutableLiveData<Boolean> backToHome = new MutableLiveData<>();
    public MutableLiveData<String> txtMessage = new MutableLiveData<>();
    public List<ItemWL> itemWLList = new ArrayList<>();
    public List<ItemHistoryWorkShop> itemHistoryWorkShops = new ArrayList<>();
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    public WorkShopVM(@NonNull @androidx.annotation.NonNull Application application) {
        super(application);
        bkmApp = BKMApp.create(application);
        service = BaseContext.CreateService(WorkShopService.class, application);
        this.context = application;
        sharedPref = new SharedPref(application);
    }

    public void getWL() {
        isLoading.setValue(true);
        isAnyData.setValue(false);
        Disposable disposable = service.getWL()
                .subscribeOn(bkmApp.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(respWL -> {
                    isLoading.setValue(false);
                    if (respWL != null) {
                        if (respWL.data != null) {
                            if (respWL.data.size() > 0) {
                                itemWLList = respWL.data;
                                isAnyData.setValue(true);
                            } else {
                                isAnyData.setValue(false);
                            }
                        } else {
                            isAnyData.setValue(false);
                        }
                    } else {
                        isAnyData.setValue(false);
                    }

                    Log.i("tripLogBkm", "wl count : " + respWL.data.size());
                }, throwable -> {
                    isLoading.setValue(false);
                    Log.i("tripLogBkm", "Request Error : " + throwable.getMessage());
                });
        compositeDisposable.add(disposable);
    }

    public void fectHistory() {
        isLoading.setValue(true);
        isAnyData.setValue(false);
        Disposable disposable = service.getHistory(sharedPref.getUserDetail().getDriver_id())
                .subscribeOn(bkmApp.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(respHistory -> {
                    isLoading.setValue(false);
                    if (respHistory != null) {
                        if (respHistory.data != null) {
                            if (respHistory.data.size() > 0) {
                                itemHistoryWorkShops = respHistory.data;
                                isAnyData.setValue(true);
                            } else {
                                isAnyData.setValue(false);
                            }
                        } else {
                            isAnyData.setValue(false);
                        }
                    } else {
                        isAnyData.setValue(false);
                    }

                    Log.i("tripLogBkm", "ws history count : " + respHistory.data.size());
                }, throwable -> {
                    isLoading.setValue(false);
                    Log.i("tripLogBkm", "Request Error : " + throwable.getMessage());
                });
        compositeDisposable.add(disposable);
    }


    public void regisWS(String reason) {
        UserDetailRes userDetailRes = sharedPref.getUserDetail();
        pDialog.setValue(true);
        Disposable disposable = service.regisWS(userDetailRes.getDriver_id(),
                        userDetailRes.vehicle.id,
                        reason)
                .subscribeOn(bkmApp.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response ->
                {
                    pDialog.setValue(false);
                    txtMessage.setValue(response.message);
                    if (response.status == Constant.ReqCreated) {
                        sharedPref.setBool(Constant.isInTheQueue,true);
                        backToHome.setValue(true);
                    }

                }, throwable -> {
                    pDialog.setValue(false);
                    try {
                        retrofit2.adapter.rxjava2.HttpException ex = (HttpException) throwable;
                        Objects.requireNonNull(ex.response().errorBody()).close();
                        String exString = Objects.requireNonNull(ex.response().errorBody()).string();

                        Gson gson = new Gson();
                        RespRegisWS respRegisWS = gson.fromJson(exString,RespRegisWS.class);
                        Log.i("loginLogBkm", ex.response().errorBody().string());
                        txtMessage.setValue(respRegisWS.message);

                    }catch (Exception exception)
                    {

                    }
                });
        compositeDisposable.add(disposable);
    }
}
