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
import com.nur_hidayat_agung.bkmmobile.databinding.ItemHistory2Binding;
import com.nur_hidayat_agung.bkmmobile.model.history.History;
import com.nur_hidayat_agung.bkmmobile.util.SharedPref;
import com.nur_hidayat_agung.bkmmobile.callback.ListHistoryCallback;

import java.util.ArrayList;
import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>{

    private Context context;
    private List<History> histories = new ArrayList<>();
    private SharedPref sharedPref;
    @Nullable
    private final ListHistoryCallback listHistoryCallback;
    private int Year;
    private int Month;

    public HistoryAdapter(Context context, List<History> histories, @Nullable ListHistoryCallback listHistoryCallback) {
        this.context = context;
        this.histories = histories;
        sharedPref = new SharedPref(context);
        this.listHistoryCallback = listHistoryCallback;
    }

    public void setHistories(List<History> histories)
    {
        if (histories == null)
        {
            this.histories = new ArrayList<>();
            notifyItemRangeChanged(0,histories.size());
        }
        else {
            this.histories = histories;
            notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ItemHistory2Binding historyBinding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext())
                , R.layout.item_history2,viewGroup,false);

        historyBinding.setCallback(listHistoryCallback);
        return new HistoryViewHolder(historyBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder historyViewHolder, int i) {
        History history = histories.get(i);
        if (history.flag)
        {
            history.setHeader(View.VISIBLE);
            history.setLine(View.GONE);
        }else
        {
            history.setHeader(View.GONE);
            history.setLine(View.VISIBLE);
        }


        historyViewHolder.historyBinding.setHistory(history);
        historyViewHolder.historyBinding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return histories.size();
    }

    public static class HistoryViewHolder extends RecyclerView.ViewHolder
    {

        private final ItemHistory2Binding historyBinding;

        public HistoryViewHolder(ItemHistory2Binding historyBinding) {
            super(historyBinding.getRoot());
            this.historyBinding = historyBinding;
        }

        public void bind(History history)
        {
            historyBinding.setHistory(history);
            historyBinding.executePendingBindings();
        }
    }
}
