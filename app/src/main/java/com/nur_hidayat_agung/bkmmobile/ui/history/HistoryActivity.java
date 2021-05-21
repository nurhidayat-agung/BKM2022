package com.nur_hidayat_agung.bkmmobile.ui.history;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;

import com.nur_hidayat_agung.bkmmobile.R;
import com.nur_hidayat_agung.bkmmobile.adapter.HistoryAdapter;
import com.nur_hidayat_agung.bkmmobile.databinding.ActivityHistory2Binding;
import com.nur_hidayat_agung.bkmmobile.model.history.History;
import com.nur_hidayat_agung.bkmmobile.util.Constant;
import com.nur_hidayat_agung.bkmmobile.util.PDialog;
import com.nur_hidayat_agung.bkmmobile.util.SharedPref;
import com.nur_hidayat_agung.bkmmobile.viewmodel.history.HistoryVM;
import com.nur_hidayat_agung.bkmmobile.callback.ListHistoryCallback;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {

    private ActivityHistory2Binding historyBinding;
    private SharedPref sharedPref;
    private HistoryVM historyVM;
    private PDialog pDialog;
    private RecyclerView.LayoutManager layoutManager;
    private HistoryAdapter historyAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        setRecycleView();
        historyVM.liveDataPdialog.observe(this,aBoolean -> {
            if (aBoolean != null) pDialog.setDialog(aBoolean);
        });
        historyVM.fecthHistory();
        historyVM.liveDataHistory.observe(this, histories -> {
            historyAdapter.setHistories(histories);
        });

    }

    private void setRecycleView() {
        layoutManager = new LinearLayoutManager(this);
        historyAdapter = new HistoryAdapter(this,new ArrayList<History>(),this.listHistoryCallback);
        historyBinding.rvHistory.setLayoutManager(layoutManager);
        historyBinding.rvHistory.setAdapter(historyAdapter);
    }

    private void initView() {
        historyBinding = DataBindingUtil.setContentView(this,R.layout.activity_history2,null);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        historyVM = ViewModelProviders.of(this).get(HistoryVM.class);
        sharedPref = new SharedPref(this);
        pDialog = new PDialog(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.i("toolbar",item.getItemId() + "");
        this.finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    public final ListHistoryCallback listHistoryCallback = history -> {
        // Toast.makeText(this,"history id : " + history.getId(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, HistoryDetailActivity.class);
        intent.putExtra(Constant.detHistory,history);
        startActivity(intent);
    };
}
