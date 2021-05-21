package com.nur_hidayat_agung.bkmmobile.adapter;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.nur_hidayat_agung.bkmmobile.R;
import com.nur_hidayat_agung.bkmmobile.databinding.ItemServiceBookHistoryBinding;
import com.nur_hidayat_agung.bkmmobile.model.service.ServiceItem;
import com.nur_hidayat_agung.bkmmobile.ui.service.DetailServiceBookFragment;
import com.nur_hidayat_agung.bkmmobile.ui.service.DetailServisBookActivity;
import com.nur_hidayat_agung.bkmmobile.util.Constant;
import com.nur_hidayat_agung.bkmmobile.util.SharedPref;

import java.util.ArrayList;
import java.util.List;


public class ServiceHistoryAdapter extends RecyclerView.Adapter<ServiceHistoryAdapter.ServiceHistoryVH> {
    private Context context;
    private List<ServiceItem> dataItems = new ArrayList<>();
    private SharedPref sharedPref;

    public ServiceHistoryAdapter(Context context, List<ServiceItem> dataItems, SharedPref sharedPref) {
        this.context = context;
        this.dataItems = dataItems;
        this.sharedPref = sharedPref;
    }

    public void setData(List<ServiceItem> datas)
    {
        if (datas == null || datas.isEmpty()) {
            this.dataItems = new ArrayList<>();
        } else {
            this.dataItems = datas;
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ServiceHistoryVH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ItemServiceBookHistoryBinding itemBinding = DataBindingUtil.inflate(LayoutInflater.
                        from(viewGroup.getContext()), R.layout.item_service_book_history,
                viewGroup, false);

        return new ServiceHistoryVH(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceHistoryVH servicehistoryVH, int i) {
        servicehistoryVH.bind(dataItems.get(i),context);
    }

    @Override
    public int getItemCount() {
        return dataItems.size();
    }

    public static class ServiceHistoryVH extends RecyclerView.ViewHolder {
        private final ItemServiceBookHistoryBinding historyBinding;

        public ServiceHistoryVH(ItemServiceBookHistoryBinding itemBinding) {
            super(itemBinding.getRoot());
            this.historyBinding = itemBinding;
        }

        public void bind(ServiceItem history, Context context) {
            historyBinding.setHistory(history);
            historyBinding.llDetail.setOnClickListener(v -> {
                Intent toDetail = new Intent(context, DetailServisBookActivity.class);
                toDetail.putExtra(Constant.detailService,history);
                context.startActivity(toDetail);
            });

            historyBinding.executePendingBindings();
        }
    }
}
