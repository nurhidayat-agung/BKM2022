package com.nur_hidayat_agung.bkmmobile.ui.service;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.gson.Gson;
import com.nur_hidayat_agung.bkmmobile.R;
import com.nur_hidayat_agung.bkmmobile.databinding.ActivityServiceAlertBinding;
import com.nur_hidayat_agung.bkmmobile.model.service.ServiceItem;
import com.nur_hidayat_agung.bkmmobile.util.Constant;
import com.nur_hidayat_agung.bkmmobile.util.PDialog;
import com.nur_hidayat_agung.bkmmobile.util.UtilFunc;
import com.nur_hidayat_agung.bkmmobile.viewmodel.service.ServiceVM;

import java.util.Date;
import java.util.Objects;

public class ServiceAlertActivity extends AppCompatActivity {
    private ActivityServiceAlertBinding binding;
    private ServiceVM serviceVM;
    private ServiceItem serviceActive = new ServiceItem();
    private String dataIntent = "";
    private PDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (getSupportActionBar() != null) getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        binding = DataBindingUtil.setContentView(this,R.layout.activity_service_alert);
        serviceVM = ViewModelProviders.of(this).get(ServiceVM.class);
        pDialog = new PDialog(this);

        dataIntent = getIntent().getStringExtra(Constant.serviceActive);
        if (dataIntent != null && !dataIntent.isEmpty())
        {
            serviceActive = new Gson().fromJson(dataIntent, ServiceItem.class);
            serviceActive.service_date = UtilFunc.getDate(new Date());
            binding.setService(serviceActive);
            binding.executePendingBindings();
        }

        serviceVM.liveDataPdialog.observe(this,aBoolean -> {
            if (aBoolean != null) pDialog.setDialog(aBoolean);
        });

        serviceVM.isSaveSuccess.observe(this,aBoolean -> {
            if (aBoolean)
            {
                serviceVM.isSaveSuccess.setValue(false);
                Toast.makeText(this, "simpan berhasil", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        binding.btnRemindMe.setOnClickListener(view -> {
            if (dataIntent != null && !dataIntent.isEmpty()) {
                serviceVM.remindLater(serviceActive.vehicle_id);
            }
        });

        binding.btnSaveService.setOnClickListener(view -> {
            if (dataIntent != null && !dataIntent.isEmpty())
            {
                showConfirmDialog(serviceActive.vehicle_id, Objects.requireNonNull(binding.edtServiceDesc.getText()).toString());
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    private void showConfirmDialog(String idService, String desc) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Simpan Data Service");
        builder.setMessage("Apakah Anda yakin akan menyimpan data service ker server");
        builder.setPositiveButton("OK", (dialogInterface, i) -> {
            serviceVM.saveService(idService,desc, "");
        });
        builder.setNegativeButton("Tidak", null);
        builder.show();
    }

}