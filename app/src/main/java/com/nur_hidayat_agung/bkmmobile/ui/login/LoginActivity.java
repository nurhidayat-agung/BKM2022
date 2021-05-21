package com.nur_hidayat_agung.bkmmobile.ui.login;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.nur_hidayat_agung.bkmmobile.BuildConfig;
import com.nur_hidayat_agung.bkmmobile.R;
import com.nur_hidayat_agung.bkmmobile.databinding.ActivityLogin2Binding;
import com.nur_hidayat_agung.bkmmobile.ui.home.HomeActivity;
import com.nur_hidayat_agung.bkmmobile.util.Constant;
import com.nur_hidayat_agung.bkmmobile.util.PDialog;
import com.nur_hidayat_agung.bkmmobile.util.SharedPref;
import com.nur_hidayat_agung.bkmmobile.viewmodel.login.LoginVM;

import java.io.File;
import java.util.Objects;

import io.fabric.sdk.android.Fabric;

public class LoginActivity extends AppCompatActivity {

    private ActivityLogin2Binding binding;
    private LoginVM loginVM;
    private PDialog pDialog;
    private SharedPref sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Fabric fabric = new Fabric.Builder(this)
                .kits(new Crashlytics())
                .debuggable(true)
                .build();
        Fabric.with(fabric);

        FirebaseApp.initializeApp(this);

        sharedPref = new SharedPref(this);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = (int) (displayMetrics.widthPixels * 0.8);
        sharedPref.setInt(Constant.windowWidth,width);

        initView();
        initData();
        initObserver();
        initLoginStatus();
    }

    private void initView() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (getSupportActionBar() != null) getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_login2);

        File eks = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);

        Log.d("LogBKM", "Absolute Path : " + eks.getAbsolutePath());
        Log.d("LogBKM", " Path : " + eks.getPath());

        File eks2 = getExternalFilesDir(
                Environment.DIRECTORY_PICTURES);

        Log.d("LogBKM", "Absolute Path2 : " + eks.getAbsolutePath());
        Log.d("LogBKM", " Path2 : " + eks.getPath());

        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(this::onComplete);
    }

    private void initData() {
        loginVM = ViewModelProviders.of(this).get(LoginVM.class);
        loginVM.setContext(this);
        binding.setLoginVM(loginVM);
        pDialog = new PDialog(this);

    }

    private void initObserver() {
        loginVM.loginResponseMutableLiveData.observe( this,loginResponse ->{
            Log.i("loginLogBkm","login sukses");
        });
        loginVM.pDialog.observe(this,aBoolean -> { if (aBoolean != null) pDialog.setDialog(aBoolean); });
        loginVM.userDetailResMutableLiveData.observe(this,userDetailRes -> {
            if (userDetailRes.getStatus() == Constant.ReqOk)
            {
                if (sharedPref.setUserDetail(userDetailRes))
                {
                    //Toast.makeText(this,"Token from server : " + userDetailRes.firebase_token,Toast.LENGTH_SHORT).show();
                    //Toast.makeText(this,"Token Device : " + sharedPref.getString(Constant.FireBaseToken),Toast.LENGTH_SHORT).show();

                    startActivity(new Intent(this, HomeActivity.class));
                    this.finish();
                }

            }
        });
    }

    private void initLoginStatus() {
        loginVM.entLogin.username = sharedPref.getString(Constant.spUserName);
        loginVM.entLogin.password = sharedPref.getString(Constant.spPassword);
        if (sharedPref.getBool(Constant.spIsLogin))
        {
            loginVM.login();
        }
    }


    private void onComplete(Task<InstanceIdResult> task) {
        if (task.isSuccessful()) {
            String token = Objects.requireNonNull(task.getResult()).getToken();
            sharedPref.setString(Constant.FireBaseToken,token);
        } else {
            Toast.makeText(this, "Firebase Gagal Terinisialisasi", Toast.LENGTH_SHORT).show();
        }
    }
}
