package com.nur_hidayat_agung.bkmmobile.ui.service;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.google.gson.Gson;
import com.nur_hidayat_agung.bkmmobile.R;
import com.nur_hidayat_agung.bkmmobile.adapter.HistotyPartReplacementAdapter;
import com.nur_hidayat_agung.bkmmobile.adapter.ListPartAdapter;
import com.nur_hidayat_agung.bkmmobile.databinding.ActivityPartServiceBinding;
import com.nur_hidayat_agung.bkmmobile.databinding.ActivityReplacementPartHistoryBinding;
import com.nur_hidayat_agung.bkmmobile.model.service.ItemListPart;
import com.nur_hidayat_agung.bkmmobile.util.Constant;
import com.nur_hidayat_agung.bkmmobile.util.PDialog;
import com.nur_hidayat_agung.bkmmobile.util.SharedPref;
import com.nur_hidayat_agung.bkmmobile.viewmodel.service.ServiceVM;

import java.util.ArrayList;

public class ReplacementPartHistoryActivity extends AppCompatActivity {
    private ActivityReplacementPartHistoryBinding binding;
    private SharedPref sharedPref;
    private ServiceVM serviceVM;
    private PDialog pDialog;
    private RecyclerView.LayoutManager layoutManager;
    private HistotyPartReplacementAdapter partReplacementAdapter;
    private Gson gson;
    private String strPart = "";
    private String vehicleID = "";
    private ItemListPart part = new ItemListPart();

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
        Log.i("toolbar",item.getItemId() + "");
        this.finish();
        return super.onOptionsItemSelected(item);
    }

    private void initData() {
        if (!strPart.equals("") || !vehicleID.equals(""))
        {
            serviceVM.fetchReplacementPartHistory(
                vehicleID,
                part.getId()
            );
        }
    }

    private void initListener() {

    }

    private void initObserver() {
        serviceVM.liveDataPdialog.observe(this,aBoolean -> {
            if (aBoolean != null) pDialog.setDialog(aBoolean);
        });

        serviceVM.ldReplacementHistory.observe(this, itemPartReplacementHistories -> {
            partReplacementAdapter.setHistories(itemPartReplacementHistories);
            if (itemPartReplacementHistories != null && itemPartReplacementHistories.size() > 0)
            {
                binding.llNoResult.setVisibility(View.GONE);
            }
            else {
                binding.llNoResult.setVisibility(View.VISIBLE);
            }
        });
    }

    private void initView() {
        binding = DataBindingUtil.setContentView(this,R.layout.activity_replacement_part_history);
        gson = new Gson();
        if (getIntent().getStringExtra(Constant.intentExtraPart) != null)
        {
            strPart = getIntent().getStringExtra(Constant.intentExtraPart);
            part = gson.fromJson(strPart,ItemListPart.class);
        }

        if (getIntent().getStringExtra(Constant.intentExtraVehicleID) != null)
        {
            vehicleID = getIntent().getStringExtra(Constant.intentExtraVehicleID);
        }


        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        pDialog = new PDialog(this);
        sharedPref = new SharedPref(this);
        serviceVM = ViewModelProviders.of(this).get(ServiceVM.class);
        layoutManager = new LinearLayoutManager(this);
        partReplacementAdapter = new HistotyPartReplacementAdapter(this, new ArrayList<>());

        binding.rvPartReplaceHistory.setLayoutManager(layoutManager);
        binding.rvPartReplaceHistory.setAdapter(partReplacementAdapter);

        binding.setPart(part);
        binding.executePendingBindings();
    }
}