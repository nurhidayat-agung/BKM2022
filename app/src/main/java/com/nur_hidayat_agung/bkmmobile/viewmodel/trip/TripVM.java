package com.nur_hidayat_agung.bkmmobile.viewmodel.trip;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.nur_hidayat_agung.bkmmobile.app.BKMApp;
import com.nur_hidayat_agung.bkmmobile.app.BaseContext;
import com.nur_hidayat_agung.bkmmobile.model.trip.DDLVM;
import com.nur_hidayat_agung.bkmmobile.model.trip.StatusTrip;
import com.nur_hidayat_agung.bkmmobile.model.trip.Trip;
import com.nur_hidayat_agung.bkmmobile.repositories.TripService;
import com.nur_hidayat_agung.bkmmobile.util.SharedPref;
import com.nur_hidayat_agung.bkmmobile.util.UtilFunc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class TripVM extends AndroidViewModel {


    private TripService tripService;
    private BKMApp bkmApp;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    public MutableLiveData<Boolean> liveDataPdialog = new MutableLiveData<>();
    public MutableLiveData<List<Trip>> liveDataTrip = new MutableLiveData<>();
    private Context context;
    private SharedPref sharedPref;
    private int Year, Month = 0;
    private List<StatusTrip> statusTrips = new ArrayList<>();
    public MutableLiveData<DDLVM> ddlLiveData =  new MutableLiveData<>();
    public MutableLiveData<Boolean> goDetail = new MutableLiveData<>();

    public TripVM(@NonNull Application application) {
        super(application);
        bkmApp = BKMApp.create(application);
        tripService = BaseContext.CreateService(TripService.class, application);
        this.context = application;
        sharedPref = new SharedPref(application);
    }

    public void fecthTrip()
    {
        liveDataPdialog.setValue(true);
        Disposable disposable = tripService.getTrip()
            .subscribeOn(bkmApp.subscribeScheduler())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(tripList -> {
                liveDataPdialog.setValue(false);
                Collections.sort(tripList);

                for (int i = 0; i < tripList.size() ; i++)
                {
                    if (i == 0)
                    {
                        Year = tripList.get(i).getLoad_date().getYear();
                        Month = tripList.get(i).getLoad_date().getMonth();
                        tripList.get(i).flag = true;
                        tripList.get(i).setMonthName(UtilFunc.getFullMonthName(tripList.get(i).getLoad_date()));
                    }else
                    {
                        if (tripList.get(i).getLoad_date().getYear() == Year && tripList.get(i).getLoad_date().getMonth() == Month)
                        {
                            tripList.get(i).flag = false;
                            tripList.get(i).setMonthName(UtilFunc.getFullMonthName(tripList.get(i).getLoad_date()));
                        }
                        else
                        {
                            tripList.get(i).flag = true;
                            tripList.get(i).setMonthName(UtilFunc.getFullMonthName(tripList.get(i).getLoad_date()));
                            Year = tripList.get(i).getLoad_date().getYear();
                            Month = tripList.get(i).getLoad_date().getMonth();
                        }
                    }
                }

                Log.i("tripLogBkm","trip count : " + tripList.size());
                liveDataTrip.setValue(tripList);
            },throwable -> {
                liveDataPdialog.setValue(false);
                Log.i("tripLogBkm","Request Error : " + throwable.getMessage());
            });
        compositeDisposable.add(disposable);
    }

    public void fecthDDL()
    {
        liveDataPdialog.setValue(true);
        Disposable disposable = tripService.getDLL()
                .subscribeOn(bkmApp.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(ddlTrips -> {
                    if (ddlTrips != null)
                    {
                        List<String> ids = new ArrayList<>();
                        List<String> strings = new ArrayList<>();
                        for (int i = 0; i < ddlTrips.size(); i++)
                        {
                            ids.add(ddlTrips.get(i).id);
                            strings.add(ddlTrips.get(i).long_name);
                        }
                        DDLVM ddlvm = new DDLVM();
                        if (ids.size() == strings.size())
                        {
                            ddlvm.setStatus(true);
                            ddlvm.setIds(ids);
                            ddlvm.setNames(strings);
                            ddlLiveData.setValue(ddlvm);
                        }
                        else ddlvm.setStatus(false);
                    }
                    liveDataPdialog.setValue(false);
                },throwable -> {
                    liveDataPdialog.setValue(false);
                    Log.i("tripLogBkm","Request Error : " + throwable.getMessage());
                });
        compositeDisposable.add(disposable);
    }

    public void getTripDetail(String id)
    {
        liveDataPdialog.setValue(true);
        Disposable disposable = tripService.getTripDetail(id)
                .subscribeOn(bkmApp.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(tripDetail -> {
                    if (tripDetail.list_status_trip != null)
                    {
                        List<String> ids = new ArrayList<>();
                        List<String> strings = new ArrayList<>();
                        for (int i = 0; i < tripDetail.list_status_trip.size(); i++)
                        {
                            ids.add(tripDetail.list_status_trip.get(i).id);
                            strings.add(tripDetail.list_status_trip.get(i).long_name);
                        }
                        DDLVM ddlvm = new DDLVM();
                        if (ids.size() == strings.size())
                        {
                            ddlvm.setStatus(true);
                            ddlvm.setIds(ids);
                            ddlvm.setNames(strings);
                            ddlLiveData.setValue(ddlvm);
                        }
                        else ddlvm.setStatus(false);
                    }
                    liveDataPdialog.setValue(false);
                    goDetail.setValue(true);
                },throwable -> {
                    liveDataPdialog.setValue(false);
                    Log.i("tripLogBkm","Request Error : " + throwable.getMessage());
                });
        compositeDisposable.add(disposable);
    }

}
