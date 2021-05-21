package com.nur_hidayat_agung.bkmmobile.app;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.firebase.FirebaseApp;
import com.nur_hidayat_agung.bkmmobile.util.Constant;
import com.nur_hidayat_agung.bkmmobile.util.SharedPref;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

public class BKMApp extends Application {

    private Scheduler scheduler;
    private SharedPref sharedPref;


    private static BKMApp get(Context context) {
        return (BKMApp) context.getApplicationContext();
    }


    public static BKMApp create(Context context) {
        return BKMApp.get(context);
    }

    public Scheduler subscribeScheduler() {
        if (scheduler == null) {
            scheduler = Schedulers.io();
        }

        return scheduler;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sharedPref = new SharedPref(this);
        sharedPref.setString(Constant.urlBase, Constant.baseUrl);
    }

    public void setScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

}
