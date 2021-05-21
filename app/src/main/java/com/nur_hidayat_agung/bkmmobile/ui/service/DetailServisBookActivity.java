package com.nur_hidayat_agung.bkmmobile.ui.service;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.nur_hidayat_agung.bkmmobile.R;
import com.nur_hidayat_agung.bkmmobile.databinding.DialogServiceBookFragmentBinding;
import com.nur_hidayat_agung.bkmmobile.model.service.ServiceItem;
import com.nur_hidayat_agung.bkmmobile.util.Constant;

public class DetailServisBookActivity extends AppCompatActivity {

    private DialogServiceBookFragmentBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.dialog_service_book_fragment,null);
        Intent recvIntent = getIntent();
        if (recvIntent != null && recvIntent.getSerializableExtra(Constant.detailService) != null)
        {
            ServiceItem data = (ServiceItem) recvIntent.getSerializableExtra(Constant.detailService);
            binding.setHistory(data);
        }
        binding.btSave.setOnClickListener(v -> {
            finish();
        });
    }
}