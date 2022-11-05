package com.nur_hidayat_agung.bkmmobile.viewmodel.service;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.nur_hidayat_agung.bkmmobile.app.BKMApp;
import com.nur_hidayat_agung.bkmmobile.app.BaseContext;
import com.nur_hidayat_agung.bkmmobile.model.service.RemindItem;
import com.nur_hidayat_agung.bkmmobile.model.service.ServiceItem;
import com.nur_hidayat_agung.bkmmobile.model.service.ItemListPart;
import com.nur_hidayat_agung.bkmmobile.model.service.ItemPartReplacementHistory;
import com.nur_hidayat_agung.bkmmobile.model.workshop.DataItemWorkshopGetReason;
import com.nur_hidayat_agung.bkmmobile.repositories.ServiceService;
import com.nur_hidayat_agung.bkmmobile.repositories.WorkShopService;
import com.nur_hidayat_agung.bkmmobile.room.AppDatabase;
import com.nur_hidayat_agung.bkmmobile.util.Constant;
import com.nur_hidayat_agung.bkmmobile.util.SharedPref;
import com.nur_hidayat_agung.bkmmobile.util.UtilFunc;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class ServiceVM extends AndroidViewModel {
    private ServiceService service;
    private WorkShopService wsService;
    private BKMApp bkmApp;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    public MutableLiveData<Boolean> liveDataPdialog = new MutableLiveData<>();
    public MutableLiveData<Boolean> isServiceAlert = new MutableLiveData<>();
    public MutableLiveData<Boolean> isSaveSuccess = new MutableLiveData<>();
    public MutableLiveData<List<ItemListPart>> ldPartListAll = new MutableLiveData<>();
    public MutableLiveData<List<ItemListPart>> ldPartList = new MutableLiveData<>();
    public MutableLiveData<List<ItemPartReplacementHistory>> ldReplacementHistory = new MutableLiveData<>();
    public MutableLiveData<List<ServiceItem>> ldServiceHistory = new MutableLiveData<>();
    private Context context;
    private SharedPref sharedPref;
    public RemindItem serviceActive = new RemindItem();
    public AppDatabase roomDB;

    public ServiceVM(@NonNull Application application) {
        super(application);
        bkmApp = BKMApp.create(application);
        service = BaseContext.CreateService(ServiceService.class, application);
        wsService = BaseContext.CreateService(WorkShopService.class, application);
        this.context = application;
        sharedPref = new SharedPref(application);
        roomDB = AppDatabase.getDatabase(application);
    }

    public void fetchPartList() {
        liveDataPdialog.setValue(true);
        Disposable disposable = service.getListSparePart()
                .subscribeOn(bkmApp.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(resp -> {
                    liveDataPdialog.setValue(false);
                    if (resp.getStatus() == Constant.ReqOk) {
                        if (resp.getData() != null)
                        {
                            ldPartList.setValue(resp.getData());
                            ldPartListAll.setValue(resp.getData());
                        }
                    }
                }, ex -> {
                    liveDataPdialog.setValue(false);
                    Log.i("historyLogBkm", "Request Error : " + ex.getMessage());
                });
        compositeDisposable.add(disposable);
    }

    public void fetchReplacementPartHistory(String vehicleID, String itemCode) {
        liveDataPdialog.setValue(true);
        Disposable disposable = service.getReplacementPartHistory(vehicleID,itemCode)
                .subscribeOn(bkmApp.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(resp -> {
                    liveDataPdialog.setValue(false);
                    if (resp.getStatus() == Constant.ReqOk) {
                        if (resp.getData() != null)
                        {
                            ldReplacementHistory.setValue(resp.getData());
                        }
                    }
                }, ex -> {
                    liveDataPdialog.setValue(false);
                    Log.i("historyLogBkm", "Request Error : " + ex.getMessage());
                });
        compositeDisposable.add(disposable);
    }

    public void loadServiceHistory(String vehicleID) {
        liveDataPdialog.setValue(true);
        Disposable disposable = service.loadServiceHistory(vehicleID)
                .subscribeOn(bkmApp.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(resp -> {
                    liveDataPdialog.setValue(false);
                    if (resp.getStatus() == Constant.ReqOk) {
                        if (resp.getData() != null)
                        {
                            ldServiceHistory.setValue(resp.getData());
                        }
                    }
                }, ex -> {
                    liveDataPdialog.setValue(false);
                    Log.i("historyLogBkm", "Request Error : " + ex.getMessage());
                });
        compositeDisposable.add(disposable);
    }

    public void getServiceAlert() {
        String vecId = (sharedPref.getUserDetail().vehicle != null && sharedPref.getUserDetail().vehicle.id != null) ? sharedPref.getUserDetail().vehicle.id : "-1";
        // vecId = "45";
        isServiceAlert.setValue(false);
        liveDataPdialog.setValue(true);
        Disposable disposable = service.getServiceAlert(vecId)
                .subscribeOn(bkmApp.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(resp -> {
                    liveDataPdialog.setValue(false);
                    if (resp.status == Constant.ReqOk) {
                        if (resp.data != null)
                        {
                            if (resp.data != null && !resp.data.isEmpty())
                            {
                                serviceActive = resp.data.get(0);
                                isServiceAlert.setValue(true);
                            }
                        }
                    }
                }, ex -> {
                    liveDataPdialog.setValue(false);
                    Log.i("historyLogBkm", "Request Error : " + ex.getMessage());
                });
        compositeDisposable.add(disposable);
    }

    public void saveService(String idService, String desc, String serviceDate) {
        isSaveSuccess.setValue(false);
        liveDataPdialog.setValue(true);
        String dateNow = UtilFunc.reformatStringDate(serviceDate,Constant.dateFormatUI,Constant.dateFormat);
        Disposable disposable = service.saveService(idService,dateNow,desc)
                .subscribeOn(bkmApp.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(resp -> {
                    liveDataPdialog.setValue(false);
                    if (resp.getStatus() == Constant.ReqCreated) {
                        isSaveSuccess.setValue(true);
                    }
                }, ex -> {
                    liveDataPdialog.setValue(false);
                    Log.i("historyLogBkm", "Request Error : " + ex.getMessage());
                });
        compositeDisposable.add(disposable);
    }

    public void remindLater(String id) {
        isSaveSuccess.setValue(false);
        liveDataPdialog.setValue(true);
        Disposable disposable = service.remindLater(id)
                .subscribeOn(bkmApp.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(resp -> {
                    liveDataPdialog.setValue(false);
                    if (resp.getStatus() == Constant.ReqOk) {
                        isSaveSuccess.setValue(true);
                    }
                }, ex -> {
                    liveDataPdialog.setValue(false);
                    Log.i("historyLogBkm", "Request Error : " + ex.getMessage());
                });
        compositeDisposable.add(disposable);
    }

    public void loadFlteredPart(String toString) {
        List<ItemListPart> tempList = new ArrayList<>();
        for (int a = 0; a < ldPartListAll.getValue().size(); a++)
        {
            if (Pattern.compile(Pattern.quote(toString), Pattern.CASE_INSENSITIVE).matcher(ldPartListAll.getValue().get(a).getItemName()).find())
            {
                tempList.add(ldPartListAll.getValue().get(a));
            }
        }
        ldPartList.setValue(tempList);
    }

    public void getReasonWorkShop() {
        Disposable disposable = wsService.getWSReason()
                .subscribeOn(bkmApp.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(resp -> {
                    if (resp.status == 200)
                    {
                        roomDB.reasonDAO().deleteAll();
                        for (DataItemWorkshopGetReason datum : resp.data) {
                            roomDB.reasonDAO().persists(datum);
                        }
                    }
                }, ex -> {
                    Log.i("WSLogBkm", "Request Error : " + ex.getMessage());
                });
        compositeDisposable.add(disposable);
    }
}
