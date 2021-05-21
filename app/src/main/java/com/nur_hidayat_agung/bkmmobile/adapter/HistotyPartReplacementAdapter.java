package com.nur_hidayat_agung.bkmmobile.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.nur_hidayat_agung.bkmmobile.R;
import com.nur_hidayat_agung.bkmmobile.databinding.ItemPartReplacementHistoryBinding;
import com.nur_hidayat_agung.bkmmobile.model.service.ItemPartReplacementHistory;
import com.nur_hidayat_agung.bkmmobile.util.SharedPref;

import java.util.ArrayList;
import java.util.List;

public class HistotyPartReplacementAdapter extends RecyclerView.Adapter<HistotyPartReplacementAdapter.PartReplacementHistoryVH> {
    private Context context;
    private List<ItemPartReplacementHistory> histories = new ArrayList<>();
    private SharedPref sharedPref;

    public HistotyPartReplacementAdapter(Context context, List<ItemPartReplacementHistory> histories) {
        this.context = context;
        this.histories = histories;
        sharedPref = new SharedPref(context);
    }

    public void setHistories(List<ItemPartReplacementHistory> histories) {
        if (histories == null || histories.isEmpty()) {
            this.histories = new ArrayList<>();
        } else {
            this.histories = histories;
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PartReplacementHistoryVH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ItemPartReplacementHistoryBinding itemBinding = DataBindingUtil.inflate(LayoutInflater.
                        from(viewGroup.getContext()), R.layout.item_part_replacement_history,
                viewGroup, false);

        return new PartReplacementHistoryVH(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull PartReplacementHistoryVH partReplacementHistoryVH, int i) {
        partReplacementHistoryVH.bind(histories.get(i));
    }

    @Override
    public int getItemCount() {
        return histories.size();
    }

    public static class PartReplacementHistoryVH extends RecyclerView.ViewHolder {
        private final ItemPartReplacementHistoryBinding historyBinding;

        public PartReplacementHistoryVH(ItemPartReplacementHistoryBinding itemBinding) {
            super(itemBinding.getRoot());
            this.historyBinding = itemBinding;
        }

        public void bind(ItemPartReplacementHistory history) {
            historyBinding.setHistory(history);
            historyBinding.executePendingBindings();
        }
    }
}
