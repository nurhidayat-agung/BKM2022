package com.nur_hidayat_agung.bkmmobile.viewmodel.history;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.nur_hidayat_agung.bkmmobile.app.BKMApp;
import com.nur_hidayat_agung.bkmmobile.app.BaseContext;
import com.nur_hidayat_agung.bkmmobile.model.history.History;
import com.nur_hidayat_agung.bkmmobile.repositories.HistoryService;
import com.nur_hidayat_agung.bkmmobile.util.SharedPref;
import com.nur_hidayat_agung.bkmmobile.util.UtilFunc;

import java.util.Collections;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class HistoryVM extends AndroidViewModel {

    private HistoryService historyService;
    private BKMApp bkmApp;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    public MutableLiveData<Boolean> liveDataPdialog = new MutableLiveData<>();
    public MutableLiveData<List<History>> liveDataHistory = new MutableLiveData<>();
    private Context context;
    private SharedPref sharedPref;
    private int Year, Month = 0;

    public HistoryVM(@NonNull Application application) {
        super(application);
        bkmApp = BKMApp.create(application);
        historyService = BaseContext.CreateService(HistoryService.class, application);
        this.context = application;
        sharedPref = new SharedPref(application);
    }

    public void fecthHistory()
    {
        liveDataPdialog.setValue(true);
        Disposable disposable = historyService.getHistory()
                .subscribeOn(bkmApp.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(histories -> {
                    liveDataPdialog.setValue(false);
                    //Collections.sort(histories);

                    for (int i = 0; i < histories.size() ; i++)
                    {
                        if (i == 0)
                        {
                            Year = histories.get(i).getLoad_date().getYear();
                            Month = histories.get(i).getLoad_date().getMonth();
                            histories.get(i).flag = true;
                            histories.get(i).setMonthName(UtilFunc.getFullMonthName(histories.get(i).getLoad_date()));
                            Log.i("historyLogBkm", "Month Name : " + histories.get(i).getMonthName());
                            Log.i("historyLogBkm", "Load Date Name : " + UtilFunc.getDate(histories.get(i).getLoad_date()));
                            Log.i("historyLogBkm", "Load Unload Name : " + UtilFunc.getDate(histories.get(i).getUnload_date()));
                        }else
                        {
                            if (histories.get(i).getLoad_date().getYear() == Year && histories.get(i).getLoad_date().getMonth() == Month)
                            {
                                histories.get(i).flag = false;
                                histories.get(i).setMonthName(UtilFunc.getFullMonthName(histories.get(i).getLoad_date()));
                            }
                            else
                            {
                                histories.get(i).flag = true;
                                histories.get(i).setMonthName(UtilFunc.getFullMonthName(histories.get(i).getLoad_date()));
                                Year = histories.get(i).getLoad_date().getYear();
                                Month = histories.get(i).getLoad_date().getMonth();
                            }
                        }
                    }
                    Log.i("historyLogBkm","Histories count : " + histories.size());
                    liveDataHistory.setValue(histories);
                },throwable -> {
                    liveDataPdialog.setValue(false);
                    Log.i("historyLogBkm","Request Error : " + throwable.getMessage());
                });
        compositeDisposable.add(disposable);
    }
}
