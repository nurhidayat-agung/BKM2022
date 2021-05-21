package com.nur_hidayat_agung.bkmmobile.ui.history;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.nur_hidayat_agung.bkmmobile.R;
import com.nur_hidayat_agung.bkmmobile.callback.DetHistoryCallback;
import com.nur_hidayat_agung.bkmmobile.callback.PictureDetailCallBack;
import com.nur_hidayat_agung.bkmmobile.databinding.DetailHistoryBinding;
import com.nur_hidayat_agung.bkmmobile.model.general.GeneralResponse;
import com.nur_hidayat_agung.bkmmobile.model.history.History;
import com.nur_hidayat_agung.bkmmobile.ui.general.DetailPicture;
import com.nur_hidayat_agung.bkmmobile.util.Constant;
import com.nur_hidayat_agung.bkmmobile.util.PDialog;
import com.nur_hidayat_agung.bkmmobile.util.PopUpDialog;
import com.nur_hidayat_agung.bkmmobile.util.SharedPref;
import com.nur_hidayat_agung.bkmmobile.util.UtilFunc;
import com.nur_hidayat_agung.bkmmobile.viewmodel.history.HistoryDetailVM;

public class HistoryDetailActivity extends AppCompatActivity {

    private DetailHistoryBinding historyDetailBinding;
    private History history = new History();
    private SharedPref sharedPref;
    private PDialog pDialog;
    private HistoryDetailVM detailVM;
    private PopUpDialog popUpDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
        initObserve();
        if (history != null)
        detailVM.fectDetHistory(history);
    }

    private void initView() {
        historyDetailBinding = DataBindingUtil.setContentView(this,R.layout.detail_history);
        popUpDialog = new PopUpDialog(this, new GeneralResponse());
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    private void initData() {
        history = (History) this.getIntent().getSerializableExtra(Constant.detHistory);
        pDialog = new PDialog(this);
        sharedPref = new SharedPref(this);
        detailVM = ViewModelProviders.of(this).get(HistoryDetailVM.class);

    }

    private void initObserve() {
        detailVM.liveDataPdialog.observe(this, aBoolean -> pDialog.setDialog(aBoolean));
        detailVM.LDDetHistory.observe(this,detailHistory -> {
            if (detailHistory != null)
            {
                detailHistory.setHistory(history);
                historyDetailBinding.setDataHist(detailHistory);
                historyDetailBinding.setCallback(detHistoryCallback);
                historyDetailBinding.setPictCallback(pictureCalBack);
                //historyDetailBinding.ivDetHisQrCode.setPath(detailHistory.getQrcode());
                historyDetailBinding.ivDetHistSpb.setPath(detailHistory.getSpb());
            }
        });
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

    private final DetHistoryCallback detHistoryCallback = detailHistory -> {
        if (detailHistory.getQrcode() == null || detailHistory.getQrcode().equals(""))
            popUpDialog.showMsg("QR Code tidak tersedia");
        else {
            startActivity(new Intent(this, DetailPicture.class)
                    .putExtra(Constant.detHistory,detailHistory));
        }
    };

    private final PictureDetailCallBack pictureCalBack = url -> {
        String s = url;
        if (UtilFunc.isStringNotNull(url))
            startActivity(new Intent(this, DetailPicture.class)
                    .putExtra(Constant.detPictHist,url));
        else {
            popUpDialog.showMsg("Gambar tidak tersedia");
        }
    };
}
