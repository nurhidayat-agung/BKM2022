package com.nur_hidayat_agung.bkmmobile.ui.login;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.nur_hidayat_agung.bkmmobile.R;
import com.nur_hidayat_agung.bkmmobile.databinding.ActivitySplashScreenBinding;
import com.nur_hidayat_agung.bkmmobile.room.AppDatabase;
import com.nur_hidayat_agung.bkmmobile.util.Constant;
import com.nur_hidayat_agung.bkmmobile.util.PDialog;
import com.nur_hidayat_agung.bkmmobile.util.SharedPref;
import com.nur_hidayat_agung.bkmmobile.viewmodel.login.LoginVM;

public class SplashScreen extends AppCompatActivity {

    private ActivitySplashScreenBinding binding;
    private SharedPref sharedPref;
    private LoginVM loginVM;
    private PDialog pDialog;
    private AppDatabase roomDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (getSupportActionBar() != null) getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_splash_screen);
        sharedPref = new SharedPref(this);
        roomDB = AppDatabase.getDatabase(this);
        loginVM = ViewModelProviders.of(this).get(LoginVM.class);
        pDialog = new PDialog(this);
        loginVM.pDialog.observe(this,aBoolean -> { if (aBoolean != null) pDialog.setDialog(aBoolean); });
        loginVM.isConfig.observe(this,aBoolean -> {
            if (aBoolean != null && aBoolean)
            {
                startActivity(new Intent(this, LoginActivity.class));
                this.finish();
            }
        });
        loginVM.getConfig();
        StartAnimations();
    }

    private void StartAnimations() {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.alpha);
        anim.reset();

        anim = AnimationUtils.loadAnimation(this, R.anim.translate);
        anim.reset();
        TextView iv = (TextView) findViewById(R.id.iv_anim);
        iv.clearAnimation();
        iv.startAnimation(anim);

        anim = AnimationUtils.loadAnimation(this, R.anim.bounce);
        anim.reset();
        binding.imageView2.setBackgroundResource(R.drawable.logo_bkm);
        binding.imageView2.clearAnimation();
        binding.imageView2.startAnimation(anim);
    }
}
