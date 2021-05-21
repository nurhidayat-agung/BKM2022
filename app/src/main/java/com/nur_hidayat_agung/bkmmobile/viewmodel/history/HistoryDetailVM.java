package com.nur_hidayat_agung.bkmmobile.viewmodel.history;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;

import com.nur_hidayat_agung.bkmmobile.app.BKMApp;
import com.nur_hidayat_agung.bkmmobile.app.BaseContext;
import com.nur_hidayat_agung.bkmmobile.model.history.DetailHistory;
import com.nur_hidayat_agung.bkmmobile.model.history.History;
import com.nur_hidayat_agung.bkmmobile.repositories.HistoryService;
import com.nur_hidayat_agung.bkmmobile.util.SharedPref;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class HistoryDetailVM extends AndroidViewModel {

    private BKMApp bkmApp;
    private HistoryService historyService;
    private Context context;
    private SharedPref sharedPref;
    public MutableLiveData<DetailHistory> LDDetHistory = new MutableLiveData<>();
    public MutableLiveData<Boolean> liveDataPdialog = new MutableLiveData<>();
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public HistoryDetailVM(@NonNull Application application) {
        super(application);
        bkmApp = BKMApp.create(application);
        historyService = BaseContext.CreateService(HistoryService.class, application);
        this.context = application;
        sharedPref = new SharedPref(application);
    }

    public void fectDetHistory(History history)
    {
        liveDataPdialog.setValue(true);
        Disposable disposable = historyService.getHistoryDetail(history.getId())
                .subscribeOn(bkmApp.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(detailHistory -> {
                    liveDataPdialog.setValue(false);
                    if (detailHistory != null)
                    {

                        if (detailHistory.getDo_connectObject() == null)
                        {
                            detailHistory.setIsConnect(View.GONE);
                            Log.i("historyLogBkm","do : null");
                        }
                        else
                        {
                            detailHistory.setIsConnect(View.VISIBLE);
                            detailHistory.sub_do = detailHistory.sub_do + " & " + detailHistory.getDo_connectObject().sub_do;
                            Log.i("historyLogBkm","do : " + detailHistory.sub_do);
                        }
                        LDDetHistory.setValue(detailHistory);
                    }

                },throwable -> {
                    liveDataPdialog.setValue(false);
                    Log.i("historyLogBkm","Request Error : " + throwable.getMessage());
                });
        compositeDisposable.add(disposable);
    }


}
