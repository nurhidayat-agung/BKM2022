package com.nur_hidayat_agung.bkmmobile.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nur_hidayat_agung.bkmmobile.R;
import com.nur_hidayat_agung.bkmmobile.databinding.ItemTripBinding;
import com.nur_hidayat_agung.bkmmobile.model.trip.Trip;
import com.nur_hidayat_agung.bkmmobile.util.SharedPref;
import com.nur_hidayat_agung.bkmmobile.callback.ListTripCallBack;

import java.util.ArrayList;
import java.util.List;

public class TripAdapter extends RecyclerView.Adapter<TripAdapter.TripVH>{

    private Context context;
    private List<Trip> trips = new ArrayList<>();
    private SharedPref sharedPref;
    @Nullable
    private final ListTripCallBack listTripCallBack;
    private int Year;
    private int Month;

    public TripAdapter(Context context, List<Trip> trips, @Nullable ListTripCallBack listTripCallBack) {
        this.context = context;
        this.trips = trips;
        this.listTripCallBack = listTripCallBack;
        sharedPref = new SharedPref(context);

    }

    public void setTrips(List<Trip> trips)
    {
        if (trips == null)
        {
            this.trips = new ArrayList<>();
            notifyItemRangeChanged(0,this.trips.size());
        }
        else {
            this.trips = trips;
            notifyItemRangeChanged(0,this.trips.size());
            notifyDataSetChanged();
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TripVH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ItemTripBinding itemTripBinding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext())
                , R.layout.item_trip,viewGroup,false);
        itemTripBinding.setCallback(listTripCallBack);
        return new TripVH(itemTripBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull TripVH tripVH, int i) {
        Trip trip = trips.get(i);
        if (trip.flag)
        {
            trip.setHeader(View.VISIBLE);
        }else
        {
            trip.setHeader(View.GONE);
        }
        tripVH.itemBinding.setTrip(trip);
        tripVH.itemBinding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return trips.size();
    }

    public class TripVH extends RecyclerView.ViewHolder
    {
        private final ItemTripBinding itemBinding;

        public TripVH(ItemTripBinding itemBinding) {
            super(itemBinding.getRoot());
            this.itemBinding = itemBinding;
        }

        public void bind(Trip trip)
        {
            itemBinding.setTrip(trip);
            itemBinding.executePendingBindings();
        }
    }
}
