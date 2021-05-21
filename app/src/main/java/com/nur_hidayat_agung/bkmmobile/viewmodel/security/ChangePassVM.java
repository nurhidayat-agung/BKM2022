package com.nur_hidayat_agung.bkmmobile.viewmodel.security;

import android.app.Application;
import android.app.Dialog;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Window;
import android.view.WindowManager;

import com.nur_hidayat_agung.bkmmobile.app.BKMApp;
import com.nur_hidayat_agung.bkmmobile.app.BaseContext;
import com.nur_hidayat_agung.bkmmobile.databinding.PopUpYesnoBinding;
import com.nur_hidayat_agung.bkmmobile.model.general.GeneralResponse;
import com.nur_hidayat_agung.bkmmobile.model.security.ChangePass;
import com.nur_hidayat_agung.bkmmobile.repositories.LoginService;
import com.nur_hidayat_agung.bkmmobile.util.Constant;
import com.nur_hidayat_agung.bkmmobile.util.PopUpDialog;
import com.nur_hidayat_agung.bkmmobile.util.SharedPref;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class ChangePassVM extends AndroidViewModel {

    public MutableLiveData<ChangePass> changePass = new MutableLiveData<>();
    private Context context;
    private GeneralResponse generalResponse;
    private Dialog dialog;
    private PopUpYesnoBinding yesnoBinding;
    private PopUpDialog popUpDialog;
    private SharedPref sharedPref;
    private LoginService loginService;
    private BKMApp bkmApp;
    public MutableLiveData<Boolean> pDialog = new MutableLiveData<>();
    public MutableLiveData<Boolean> updatePass = new MutableLiveData<>();
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public ChangePassVM(@NonNull Application application) {
        super(application);
        sharedPref = new SharedPref(application);
        ChangePass changePasss = new ChangePass();
        changePasss.oldPass = sharedPref.getString(Constant.spPassword);
        changePass.setValue(changePasss);
        bkmApp = BKMApp.create(application);
        loginService = BaseContext.CreateService(LoginService.class,application);
        updatePass.setValue(false);
    }

    public void setContext(Context context) {
        this.context = context;
        prepareDialog();
        popUpDialog = new PopUpDialog(context, new GeneralResponse());
    }

    private void prepareDialog() {
        this.generalResponse = new GeneralResponse();
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        yesnoBinding = PopUpYesnoBinding.inflate(LayoutInflater.from(context),null);
        dialog.setContentView(yesnoBinding.getRoot());
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        Window window = dialog.getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.CENTER;
        int width = sharedPref.getInt(Constant.windowWidth);
        params.width = width;
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        yesnoBinding.btnPopUpNo.setOnClickListener(v -> {
            dialog.dismiss();
        });
        yesnoBinding.btnPopUpYes.setOnClickListener(v -> {
            if (!changePass.getValue().newPass.equals(changePass.getValue().confirmPass))
            {
                dialog.dismiss();
                popUpDialog.showMsg(Constant.msgConfrPassWrong);
            }
            else if (!changePass.getValue().oldPass.equals(sharedPref.getString(Constant.spPassword)))
            {
                dialog.dismiss();
                popUpDialog.showMsg(Constant.msgOldPassWrong);
            }
            else {
                dialog.dismiss();
                submitToServer();
            }
        });
    }

    private void submitToServer() {
        pDialog.setValue(true);
        Log.i("changePassLogBkm","old Pass : " + changePass.getValue().oldPass);
        Log.i("changePassLogBkm","new Pass : " + changePass.getValue().newPass);
        Log.i("changePassLogBkm","confirm Pass : " + changePass.getValue().confirmPass);
        Disposable disposable = loginService.changePass(changePass.getValue().oldPass,
                changePass.getValue().newPass,
                changePass.getValue().confirmPass)
                .subscribeOn(bkmApp.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response ->
                {
                    pDialog.setValue(false);
                    if (response.status == Constant.ReqOk)
                    {
                        popUpDialog.showMsg(Constant.msgChangeOK);
                        sharedPref.setString(Constant.spPassword, changePass.getValue().newPass);
                        changePass.getValue().oldPass = changePass.getValue().newPass;
                        updatePass.setValue(true);
                    }
                    else
                    {
                        popUpDialog.showMsg(Constant.msgChangeFail);
                    }
                }, throwable -> {
                    pDialog.setValue(false);
                    popUpDialog.showMsg(Constant.msgNetworkError);
                    Log.i("changePassLogBkm","Request Error : " + throwable.getMessage());
                });
        compositeDisposable.add(disposable);
    }

    public void submit()
    {
        if (generalResponse == null) generalResponse = new GeneralResponse();
        generalResponse.message = Constant.msgChangePass;
        yesnoBinding.setMsg(generalResponse);
        dialog.show();
    }
}
