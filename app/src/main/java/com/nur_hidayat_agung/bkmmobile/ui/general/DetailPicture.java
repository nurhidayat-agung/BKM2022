package com.nur_hidayat_agung.bkmmobile.ui.general;

import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.bumptech.glide.Glide;
import com.nur_hidayat_agung.bkmmobile.R;
import com.nur_hidayat_agung.bkmmobile.databinding.ActivityDetailPictureBinding;
import com.nur_hidayat_agung.bkmmobile.model.general.Msg;
import com.nur_hidayat_agung.bkmmobile.model.history.DetailHistory;
import com.nur_hidayat_agung.bkmmobile.util.Constant;
import com.nur_hidayat_agung.bkmmobile.util.UtilFunc;

public class DetailPicture extends AppCompatActivity {

    private ActivityDetailPictureBinding binding;
    private DetailHistory detailHistory;
    private Msg msg;
    private String Url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();

    }

    private void initView() {
        binding = DataBindingUtil.setContentView(this,R.layout.activity_detail_picture);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    private void initData() {
        detailHistory = (DetailHistory) getIntent().getSerializableExtra(Constant.detHistory);
        if (detailHistory != null)
        {
            Glide.with(this).load(detailHistory.getQrcode()).into(binding.ivDetQrCode);
        }
        msg = (Msg) getIntent().getSerializableExtra(Constant.qrUrl);
        if (msg != null)
        {
             Glide.with(this).load(msg.getMessage()).into(binding.ivDetQrCode);
        }

        if(getIntent().getSerializableExtra(Constant.detPictHist) != null)
        {
            if (UtilFunc.isStringNotNull((String) getIntent().getSerializableExtra(Constant.detPictHist)))
            {

                Glide.with(this).load(getIntent().getSerializableExtra(Constant.detPictHist)).into(binding.ivDetQrCode);
            }
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        this.finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
