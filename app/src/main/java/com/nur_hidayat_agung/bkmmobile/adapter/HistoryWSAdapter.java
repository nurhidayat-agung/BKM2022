package com.nur_hidayat_agung.bkmmobile.adapter;

import android.annotation.SuppressLint;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.nur_hidayat_agung.bkmmobile.R;
import com.nur_hidayat_agung.bkmmobile.databinding.ItemHistoryWorkshopBinding;
import com.nur_hidayat_agung.bkmmobile.databinding.ItemWaitingListBinding;
import com.nur_hidayat_agung.bkmmobile.model.workshop.ItemHistoryWorkShop;
import com.nur_hidayat_agung.bkmmobile.model.workshop.ItemWL;

import java.util.ArrayList;
import java.util.List;

public class HistoryWSAdapter extends
        RecyclerView.Adapter<HistoryWSAdapter.HistoryWSVH> {

    private List<ItemHistoryWorkShop> listHistory = new ArrayList<>();

    public HistoryWSAdapter(List<ItemHistoryWorkShop> listHistory) {
        this.listHistory = listHistory;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setListHistory(List<ItemHistoryWorkShop> listHistory) {
        this.listHistory = listHistory;
        notifyDataSetChanged();
    }

    @NonNull
    @androidx.annotation.NonNull
    @Override
    public HistoryWSVH onCreateViewHolder(@NonNull @androidx.annotation.NonNull ViewGroup viewGroup, int i) {
        ItemHistoryWorkshopBinding itemBinding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext())
                , R.layout.item_history_workshop, viewGroup, false);

        return new HistoryWSVH(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull @androidx.annotation.NonNull HistoryWSVH historyWSVH, int i) {
        ItemHistoryWorkShop history = listHistory.get(i);

        String txtRemarkPart = "";

        if (history.listSparepart != null) {
            for (int a = 0; a < history.listSparepart.size(); a++) {
                txtRemarkPart += history.listSparepart.get(a).itemName + " - "
                        + history.listSparepart.get(a).qty + " "
                        + history.listSparepart.get(a).unitName + "\n";
            }

            history.remarkPart = txtRemarkPart;
        }

        historyWSVH.bind(history);
    }

    @Override
    public int getItemCount() {
        return listHistory.size();
    }

    public class HistoryWSVH extends RecyclerView.ViewHolder {
        private final ItemHistoryWorkshopBinding binding;

        public HistoryWSVH(ItemHistoryWorkshopBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(ItemHistoryWorkShop history) {
            binding.setHistory(history);
            binding.executePendingBindings();
        }
    }
}
