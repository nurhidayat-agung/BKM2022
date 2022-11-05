package com.nur_hidayat_agung.bkmmobile.viewmodel.login;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.nur_hidayat_agung.bkmmobile.app.BKMApp;
import com.nur_hidayat_agung.bkmmobile.app.BaseContext;
import com.nur_hidayat_agung.bkmmobile.model.home.DataItemMenu;
import com.nur_hidayat_agung.bkmmobile.model.login.LoginAnnouncement;
import com.nur_hidayat_agung.bkmmobile.model.login.LoginEnt;
import com.nur_hidayat_agung.bkmmobile.model.login.LoginRes;
import com.nur_hidayat_agung.bkmmobile.model.login.UserDetailRes;
import com.nur_hidayat_agung.bkmmobile.model.general.GeneralResponse;
import com.nur_hidayat_agung.bkmmobile.model.workshop.RespQueue;
import com.nur_hidayat_agung.bkmmobile.repositories.LoginService;
import com.nur_hidayat_agung.bkmmobile.repositories.WorkShopService;
import com.nur_hidayat_agung.bkmmobile.util.Constant;
import com.nur_hidayat_agung.bkmmobile.util.PopUpDialog;
import com.nur_hidayat_agung.bkmmobile.util.SharedPref;
import com.nur_hidayat_agung.bkmmobile.util.UtilFunc;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class LoginVM extends AndroidViewModel {

    private final LoginService loginService;
    private final WorkShopService workShopService;
    private final BKMApp bkmApp;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    public MutableLiveData<LoginRes> loginResponseMutableLiveData = new MutableLiveData<>();
    public LoginEnt entLogin;
    public MutableLiveData<Boolean> pDialog = new MutableLiveData<>();
    public MutableLiveData<UserDetailRes> userDetailResMutableLiveData = new MutableLiveData<>();
    private final SharedPref sharedPref;
    private PopUpDialog popUpDialog;
    public MutableLiveData<LoginAnnouncement> annLiveData = new MutableLiveData<>();
    public MutableLiveData<Boolean> isLogout = new MutableLiveData<>();
    public MutableLiveData<Boolean> isConfig = new MutableLiveData<>();
    public MutableLiveData<Boolean> isQueue = new MutableLiveData<>();
    public MutableLiveData<List<DataItemMenu>> ldMenuItem = new MutableLiveData<>();
    public RespQueue respQueue = new RespQueue();



    public LoginVM(@NonNull Application application) {
        super(application);
        bkmApp = BKMApp.create(application);
        loginService = BaseContext.CreateService(LoginService.class,application);
        workShopService = BaseContext.CreateService(WorkShopService.class,application);
        entLogin = new LoginEnt();
        sharedPref = new SharedPref(application);
        popUpDialog = new PopUpDialog(application, new GeneralResponse());
    }

    public void getConfig()
    {
        pDialog.setValue(true);
        Disposable disposable = loginService.getConfig()
                .subscribeOn(bkmApp.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response ->
                {
                    if (response != null && response.base_url != null && response.base_url != "")
                    {
                        sharedPref.setString(Constant.urlBase,response.base_url);
                        isConfig.setValue(true);
                    }
                }, throwable -> {
                    pDialog.setValue(false);
                    popUpDialog.showMsg(Constant.msgNetworkError);
                    Log.i("loginLog","Request Error : " + throwable.getMessage());
                });
        compositeDisposable.add(disposable);
    }

    public void login()
    {
        pDialog.setValue(true);
        Log.i("loginLogBkm","userame : " + entLogin.username);
        Log.i("loginLogBkm","password : " + entLogin.password);
        Disposable disposable = loginService.login(entLogin.username,entLogin.password)
                .subscribeOn(bkmApp.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response ->
                {
                    if (response.getStatus() == Constant.ReqOk)
                    {
                        loginResponseMutableLiveData.setValue(response);
                        sharedPref.setAuth(response.getToken());
                        sharedPref.setUserID(response.getUser_id());
                        sharedPref.setBool(Constant.spIsLogin,true);
                        sharedPref.setString(Constant.spUserName, entLogin.username);
                        sharedPref.setString(Constant.spPassword, entLogin.password);
                        pushFireBaseToken(sharedPref.getString(Constant.FireBaseToken));

                    }else
                    {
                        pDialog.setValue(false);
                        popUpDialog.showMsg(response.getMessage()+"");
                    }
                }, throwable -> {
                    pDialog.setValue(false);
                    popUpDialog.showMsg(Constant.msgNetworkError);
                    Log.i("loginLog","Request Error : " + throwable.getMessage());
                });
        compositeDisposable.add(disposable);

//        throw new RuntimeException("This is a crash");

    }

    public void getUserDetail(String firebase_token) {
        Disposable disposable = loginService.userDetail(sharedPref.getUserId())
                .subscribeOn(bkmApp.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response ->
                {
                    if (response != null)
                    {
                        if (UtilFunc.isStringNotNull(response.getUser_id()))
                        {
                            response.setStatus(Constant.ReqOk);
                            response.firebase_token = firebase_token;
                            sharedPref.setUserDetail(response);

                            userDetailResMutableLiveData.setValue(response);
                            Log.i("loginLogBkm","User ID from vm after get: " + response.getUser_id());
                        }
                    }
                    pDialog.setValue(false);
                }, throwable -> {
                    Log.i("loginLogBkm","Request Error : " + throwable.getMessage());
                    pDialog.setValue(false);
                });
        compositeDisposable.add(disposable);
    }

    public void pushFireBaseToken(String token) {

        okhttp3.RequestBody firebase_token = UtilFunc.createScalarRequest("123456");

        Disposable disposable = loginService.pushFireBase(token)
                .subscribeOn(bkmApp.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response ->
                {
                    if (response != null)
                    {
                        if (response.status == Constant.ReqOk)
                        {
                            getUserDetail(sharedPref.getString(Constant.FireBaseToken));
                        }
                    }
                    pDialog.setValue(false);
                }, throwable -> {
                    Log.i("loginLogBkm","Request Error : " + throwable.getMessage());
                    pDialog.setValue(false);
                });
        compositeDisposable.add(disposable);
    }


    private void getAnnouncement()
    {
        Disposable disposable = loginService.getAnnouncement()
                .subscribeOn(bkmApp.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response ->
                {
                    if (response != null)
                    {
                        if (UtilFunc.isStringNotNull(response.announcement))
                        {
                            annLiveData.setValue(response);
                        }
                    }
                    pDialog.setValue(false);
                }, throwable -> {
                    Log.i("loginLogBkm","Request Error : " + throwable.getMessage());
                    pDialog.setValue(false);
                });
        compositeDisposable.add(disposable);
    }

    public void getMenu()
    {
        Disposable disposable = loginService.getMenu()
                .subscribeOn(bkmApp.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response ->
                {
                    if (response != null)
                    {
                        sharedPref.setUserMenu(response);
                        ldMenuItem.setValue(response.data);
                    }
                }, throwable -> {
                    Log.i("loginLogBkm","Request Error : " + throwable.getMessage());
                });
        compositeDisposable.add(disposable);
    }

    public void logout()
    {
        Disposable disposable = loginService.logout()
                .subscribeOn(bkmApp.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(generalResponse ->
                {
                    if (generalResponse != null)
                    {
                        if (generalResponse.status == Constant.ReqOk)
                        {
                            isLogout.setValue(true);
                        }
                    }
                    pDialog.setValue(false);
                }, throwable -> {
                    Log.i("loginLogBkm","Request Error : " + throwable.getMessage());
                    pDialog.setValue(false);
                });
        compositeDisposable.add(disposable);
    }


    public void getWorkShopQueue() {
        isQueue.setValue(false);
        sharedPref.setBool(Constant.isInTheQueue,false);
        Disposable disposable = workShopService.getQueue(sharedPref.getUserDetail().getDriver_id())
                .subscribeOn(bkmApp.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response ->
                {
                    if (response.data != null)
                    {
                        respQueue = response;
                        isQueue.setValue(true);
                        sharedPref.setBool(Constant.isInTheQueue,true);
                    }
                    pDialog.setValue(false);
                }, throwable -> {
                    Log.i("loginLogBkm","Request Error : " + throwable.getMessage());
                    pDialog.setValue(false);
                });
        compositeDisposable.add(disposable);
    }

    public void cancelWSRegistration()
    {
        pDialog.setValue(true);
        Disposable disposable = workShopService.cancelRegis(respQueue.data.id)
                .subscribeOn(bkmApp.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response ->
                {
                    pDialog.setValue(false);
                    if (response.status == Constant.ReqCreated)
                    {
                        respQueue = null;
                        isQueue.setValue(false);
                        getWorkShopQueue();
                    }
                }, throwable -> {
                    Log.i("loginLogBkm","Request Error : " + throwable.getMessage());
                    pDialog.setValue(false);
                });
        compositeDisposable.add(disposable);
    }
}
