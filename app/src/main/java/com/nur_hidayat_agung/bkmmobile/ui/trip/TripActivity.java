package com.nur_hidayat_agung.bkmmobile.ui.trip;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.nur_hidayat_agung.bkmmobile.R;
import com.nur_hidayat_agung.bkmmobile.adapter.TripAdapter;
import com.nur_hidayat_agung.bkmmobile.databinding.ActivityTrip2Binding;
import com.nur_hidayat_agung.bkmmobile.model.trip.DDLVM;
import com.nur_hidayat_agung.bkmmobile.model.trip.Trip;
import com.nur_hidayat_agung.bkmmobile.util.Constant;
import com.nur_hidayat_agung.bkmmobile.util.PDialog;
import com.nur_hidayat_agung.bkmmobile.util.SharedPref;
import com.nur_hidayat_agung.bkmmobile.callback.ListTripCallBack;
import com.nur_hidayat_agung.bkmmobile.viewmodel.trip.TripVM;

import java.util.ArrayList;

public class TripActivity extends AppCompatActivity {

    private ActivityTrip2Binding binding;
    private SharedPref sharedPref;
    private TripVM tripVM;
    private PDialog pDialog;
    private RecyclerView.LayoutManager layoutManager;
    private TripAdapter tripAdapter;
    private DDLVM ddlvm = new DDLVM();
    private Trip trip = new Trip();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initRV();
        initObserver();

    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(this,"Load Data", Toast.LENGTH_SHORT).show();
        // tripVM.fecthDDL();
        initRV();
        tripVM.fecthTrip();
    }

    private void initObserver() {
        tripVM.liveDataPdialog.observe(this, aBoolean -> pDialog.setDialog(aBoolean));
        tripVM.ddlLiveData.observe(this, ddlvm1 -> {
            assert ddlvm1 != null;
            this.ddlvm = ddlvm1;
            Log.i("tripLogBkm","names count : " + this.ddlvm.getNames().size());
            Log.i("tripLogBkm","ids count : " + this.ddlvm.getIds().size());
            Log.i("tripLogBkm","ddl status : " + this.ddlvm.isStatus());
        });
        tripVM.liveDataTrip.observe(this, tripList -> {
            tripAdapter.setTrips(tripList);

        });
        tripVM.goDetail.observe(this, aBoolean -> {
            if (aBoolean != null)
            {
                if (aBoolean == true)
                {
                    tripVM.goDetail.setValue(false);
                    Intent intent = new Intent(this, TripDetailActivity.class);
                    intent.putExtra(Constant.detTrip,trip);
                    intent.putExtra(Constant.ddlTrip,ddlvm);
                    startActivity(intent);
                }
            }
        });
    }

    private void initRV() {
        layoutManager = new LinearLayoutManager(this);
        tripAdapter = new TripAdapter(this,new ArrayList<Trip>(),this.listTripCallBack);
        binding.rvTrip.setLayoutManager(layoutManager);
        binding.rvTrip.setAdapter(tripAdapter);
    }

    private void initView() {
         binding = DataBindingUtil.setContentView(this,R.layout.activity_trip2, null);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        tripVM = ViewModelProviders.of(this).get(TripVM.class);
        sharedPref = new SharedPref(this);
        pDialog = new PDialog(this);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        this.finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigateUp() {
        onBackPressed();
        return true;
    }

    public final ListTripCallBack listTripCallBack = trip -> {
        if (trip.getId() != null)
        {
            this.trip = trip;
            tripVM.getTripDetail(trip.getId());

        }else Toast.makeText(this,"Failed to Load Trip Status",Toast.LENGTH_SHORT).show();
    };
}
