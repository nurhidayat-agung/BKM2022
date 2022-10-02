package com.nur_hidayat_agung.bkmmobile.ui.workshop;

import android.app.Dialog;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.nur_hidayat_agung.bkmmobile.R;
import com.nur_hidayat_agung.bkmmobile.adapter.TabWorkShopFragAdapter;
import com.nur_hidayat_agung.bkmmobile.databinding.ActivityWorkShopBinding;
import com.nur_hidayat_agung.bkmmobile.databinding.DialogFillReasonWsBinding;
import com.nur_hidayat_agung.bkmmobile.databinding.DialogWsCardBinding;
import com.nur_hidayat_agung.bkmmobile.util.Constant;
import com.nur_hidayat_agung.bkmmobile.util.PDialog;
import com.nur_hidayat_agung.bkmmobile.util.SharedPref;
import com.nur_hidayat_agung.bkmmobile.viewmodel.workshop.WorkShopVM;

import java.util.Objects;

public class WorkShopActivity extends AppCompatActivity {

    private ActivityWorkShopBinding binding;
    private DialogFillReasonWsBinding dialogBinding;
    private Dialog dialog;
    private PDialog pDialog;
    private WorkShopVM vm;
    private SharedPref sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_work_shop, null);
        initView();
        initObserver();
        initListener();
    }

    private void initListener() {
        binding.btnRegis.setOnClickListener(v -> {
            showDialogReason();
        });
    }

    private void showDialogReason() {
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogBinding = DialogFillReasonWsBinding.inflate(LayoutInflater.from(this), null);
        dialog.setContentView(dialogBinding.getRoot());
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        Window window = dialog.getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.CENTER;
        double width = sharedPref.getInt(Constant.windowWidth) * 0.95;
        params.width = (int) width;
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialogBinding.btnRegis.setOnClickListener(v -> {
            dialog.dismiss();
            vm.regisWS(dialogBinding.edtReason.getText().toString());
        });
        dialogBinding.btnCancel.setOnClickListener(v -> {
            dialog.dismiss();
        });
        dialog.show();
    }


    private void initObserver() {
        vm.pDialog.observe(this, aBoolean -> {
            pDialog.setDialog(aBoolean);
        });

        vm.txtMessage.observe(this,s -> {
            if (Objects.requireNonNull(s).length() > 0)
            {
                Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
                vm.txtMessage.setValue("");
            }
        });
    }

    private void initView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_work_shop, null);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        vm = ViewModelProviders.of(this).get(WorkShopVM.class);

        pDialog = new PDialog(this);
        setupViewPager(binding.vpMyRequest);
        sharedPref = new SharedPref(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();

    }

    @Override
    public boolean onNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        this.finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        initData();
    }

    private void initData() {

    }

    private void setupViewPager(ViewPager viewPager) {
        TabWorkShopFragAdapter adapter = new TabWorkShopFragAdapter(getSupportFragmentManager());
        adapter.addFragment(QueueFragment.newInstance("", ""), "ANTRIAN");
        adapter.addFragment(WorkShopHistoryFragment.newInstance("", ""), "RIWAYAT");
        viewPager.setAdapter(adapter);

        binding.tabMyRequest.setupWithViewPager(viewPager);
    }
}