package com.nur_hidayat_agung.bkmmobile.viewmodel.salary;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.nur_hidayat_agung.bkmmobile.app.BKMApp;
import com.nur_hidayat_agung.bkmmobile.app.BaseContext;
import com.nur_hidayat_agung.bkmmobile.model.payslip.PaySlip;
import com.nur_hidayat_agung.bkmmobile.repositories.SalaryService;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class SalaryVM extends AndroidViewModel {

    private BKMApp bkmApp;
    private SalaryService salaryService;
    public MutableLiveData<Boolean> liveDataPdialog = new MutableLiveData<>();
    public MutableLiveData<PaySlip> slipMutableLiveData = new MutableLiveData<>();
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    public MutableLiveData<String> msgLiveData = new MutableLiveData<>();


    public SalaryVM(@NonNull Application application) {
        super(application);
        bkmApp = BKMApp.create(application);
        salaryService = BaseContext.CreateService(SalaryService.class,application);
    }

    public void fecthPaySlip(int month, int year)
    {
        liveDataPdialog.setValue(true);
        Disposable disposable = salaryService.getPaySlip(month,year)
                .subscribeOn(bkmApp.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(paySlip -> {
                    liveDataPdialog.setValue(false);
                    if (paySlip != null) slipMutableLiveData.setValue(paySlip);
                    else msgLiveData.setValue("Slip Belum Tersedia");
                },throwable -> {
                    liveDataPdialog.setValue(false);
                    Log.i("salaryLogBKM" , "masuk ke eror");
                    msgLiveData.setValue("Slip Belum Tersedia");
                });
        compositeDisposable.add(disposable);
    }
}
