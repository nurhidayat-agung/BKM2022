package com.nur_hidayat_agung.bkmmobile.ui.service;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.google.gson.Gson;
import com.nur_hidayat_agung.bkmmobile.R;
import com.nur_hidayat_agung.bkmmobile.adapter.ServiceHistoryAdapter;
import com.nur_hidayat_agung.bkmmobile.databinding.ActivityServiceBookBinding;
import com.nur_hidayat_agung.bkmmobile.util.PDialog;
import com.nur_hidayat_agung.bkmmobile.util.SharedPref;
import com.nur_hidayat_agung.bkmmobile.viewmodel.service.ServiceVM;

import java.util.ArrayList;

public class ServiceBookActivity extends AppCompatActivity {
    private ActivityServiceBookBinding binding;
    private SharedPref sharedPref;
    private ServiceVM serviceVM;
    private PDialog pDialog;
    private RecyclerView.LayoutManager layoutManager;
    private ServiceHistoryAdapter serviceHistoryAdapter;
    private Gson gson;
    private String strPart = "";
    private String vehicleID = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initObserver();
        initListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }

    private void initListener() {

    }

    private void initObserver() {
        serviceVM.liveDataPdialog.observe(this,aBoolean -> {
            if (aBoolean != null) pDialog.setDialog(aBoolean);
        });

        serviceVM.ldServiceHistory.observe(this, dataItems -> {
            serviceHistoryAdapter.setData(dataItems);
        });
    }


    private void initData() {
        if (!vehicleID.isEmpty())
        {
            serviceVM.loadServiceHistory(vehicleID);
        }
    }

    private void initView() {
        binding = DataBindingUtil.setContentView(this,R.layout.activity_service_book);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        gson = new Gson();
        pDialog = new PDialog(this);
        sharedPref = new SharedPref(this);
        serviceVM = ViewModelProviders.of(this).get(ServiceVM.class);
        layoutManager = new LinearLayoutManager(this);
        serviceHistoryAdapter = new ServiceHistoryAdapter(this,new ArrayList<>(),sharedPref);
        binding.rvServiceBook.setLayoutManager(layoutManager);
        binding.rvServiceBook.setAdapter(serviceHistoryAdapter);

        vehicleID = sharedPref.getUserDetail().vehicle.id;
    }
}