package com.nur_hidayat_agung.bkmmobile.ui.service;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;

import com.google.gson.Gson;
import com.nur_hidayat_agung.bkmmobile.R;
import com.nur_hidayat_agung.bkmmobile.adapter.ListPartAdapter;
import com.nur_hidayat_agung.bkmmobile.databinding.ActivityPartServiceBinding;
import com.nur_hidayat_agung.bkmmobile.util.Constant;
import com.nur_hidayat_agung.bkmmobile.util.PDialog;
import com.nur_hidayat_agung.bkmmobile.util.SharedPref;
import com.nur_hidayat_agung.bkmmobile.viewmodel.service.ServiceVM;

import java.util.ArrayList;

public class PartServiceActivity extends AppCompatActivity {
    private ActivityPartServiceBinding binding;
    private SharedPref sharedPref;
    private ServiceVM serviceVM;
    private PDialog pDialog;
    private RecyclerView.LayoutManager layoutManager;
    private ListPartAdapter listPartAdapter;
    private Gson gson;

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
        serviceVM.fetchPartList();
    }

    private void initListener() {
        binding.edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString() != null)
                {
                    serviceVM.loadFlteredPart(charSequence.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void initObserver() {
        serviceVM.liveDataPdialog.observe(this,aBoolean -> {
            if (aBoolean != null) pDialog.setDialog(aBoolean);
        });

        serviceVM.ldPartList.observe(this, itemListParts -> {
            listPartAdapter.setPartList(itemListParts);
        });
    }

    private void initView() {
        binding = DataBindingUtil.setContentView(this,R.layout.activity_part_service);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        sharedPref = new SharedPref(this);
        pDialog = new PDialog(this);
        serviceVM = ViewModelProviders.of(this).get(ServiceVM.class);
        gson = new Gson();

        layoutManager = new LinearLayoutManager(this);
        listPartAdapter = new ListPartAdapter(this,new ArrayList<>(),part -> {
            Intent intent = new Intent(this,ReplacementPartHistoryActivity.class);
            intent.putExtra(Constant.intentExtraPart, gson.toJson(part));
            intent.putExtra(Constant.intentExtraVehicleID, sharedPref.getUserDetail().vehicle.id);
            startActivity(intent);
        });
        binding.rvPartService.setLayoutManager(layoutManager);
        binding.rvPartService.setAdapter(listPartAdapter);
    }
}