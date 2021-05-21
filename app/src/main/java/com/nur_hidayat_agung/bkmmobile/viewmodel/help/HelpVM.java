package com.nur_hidayat_agung.bkmmobile.viewmodel.help;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.nur_hidayat_agung.bkmmobile.app.BKMApp;
import com.nur_hidayat_agung.bkmmobile.app.BaseContext;
import com.nur_hidayat_agung.bkmmobile.model.help.Help;
import com.nur_hidayat_agung.bkmmobile.model.history.History;
import com.nur_hidayat_agung.bkmmobile.repositories.HelpService;
import com.nur_hidayat_agung.bkmmobile.repositories.HistoryService;
import com.nur_hidayat_agung.bkmmobile.util.SharedPref;
import com.nur_hidayat_agung.bkmmobile.util.UtilFunc;

import java.util.Collections;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class HelpVM extends AndroidViewModel {

    private HelpService helpService;
    private BKMApp bkmApp;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    public MutableLiveData<Boolean> liveDataPdialog = new MutableLiveData<>();
    public MutableLiveData<List<Help>> liveDataHelps = new MutableLiveData<>();

    public HelpVM(@NonNull Application application) {
        super(application);
        bkmApp = BKMApp.create(application);
        helpService = BaseContext.CreateService(HelpService.class, application);
    }

    public void fecthHelps()
    {
        liveDataPdialog.setValue(true);
        Disposable disposable = helpService.getHelp()
                .subscribeOn(bkmApp.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(helps -> {
                    liveDataPdialog.setValue(false);
                    liveDataHelps.setValue(helps);
                },throwable -> {
                    liveDataPdialog.setValue(false);
                    Log.i("historyLogBkm","Request Error : " + throwable.getMessage());
                });
        compositeDisposable.add(disposable);
    }
}
