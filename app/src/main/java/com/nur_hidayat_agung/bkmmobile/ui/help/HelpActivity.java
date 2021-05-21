package com.nur_hidayat_agung.bkmmobile.ui.help;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.nur_hidayat_agung.bkmmobile.R;
import com.nur_hidayat_agung.bkmmobile.adapter.HelpAdapter;
import com.nur_hidayat_agung.bkmmobile.callback.HelpCallBack;
import com.nur_hidayat_agung.bkmmobile.databinding.ActivityHelpBinding;
import com.nur_hidayat_agung.bkmmobile.model.help.Help;
import com.nur_hidayat_agung.bkmmobile.util.Constant;
import com.nur_hidayat_agung.bkmmobile.util.PDialog;
import com.nur_hidayat_agung.bkmmobile.util.SharedPref;
import com.nur_hidayat_agung.bkmmobile.viewmodel.help.HelpVM;

import java.util.ArrayList;

public class HelpActivity extends AppCompatActivity {

    //private ActivityPesanBinding binding;
    private ActivityHelpBinding binding;
    private SharedPref sharedPref;
    private HelpVM helpVM;
    private PDialog pDialog;
    private RecyclerView.LayoutManager layoutManager;
    private HelpAdapter helpAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initRV();
        initObserver();
        unhandlingAction();
    }

    private void unhandlingAction() {

    }

    private void initObserver() {
        helpVM.liveDataPdialog.observe(this,aBoolean -> {
            if (aBoolean != null) pDialog.setDialog(aBoolean);
        });
        helpVM.liveDataHelps.observe(this, helps -> {
            helpAdapter.setHelps(helps);
        });
        helpVM.fecthHelps();
    }

    private void initRV() {
        layoutManager = new LinearLayoutManager(this);
        helpAdapter = new HelpAdapter(this,new ArrayList<Help>(),this.helpCallBack);
        binding.rvHelp.setLayoutManager(layoutManager);
        binding.rvHelp.setAdapter(helpAdapter);
    }

    private void initView() {
        binding = DataBindingUtil.setContentView(this,R.layout.activity_help, null);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        helpVM = ViewModelProviders.of(this).get(HelpVM.class);
        sharedPref = new SharedPref(this);
        pDialog = new PDialog(this);
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

    public final HelpCallBack helpCallBack = help -> {
        startActivity(new Intent(this,HelpDetail.class)
        .putExtra(Constant.helpDet,help));
    };
}
