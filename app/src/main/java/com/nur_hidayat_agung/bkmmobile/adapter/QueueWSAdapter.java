package com.nur_hidayat_agung.bkmmobile.adapter;

import android.annotation.SuppressLint;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.nur_hidayat_agung.bkmmobile.R;
import com.nur_hidayat_agung.bkmmobile.databinding.ItemHelpBinding;
import com.nur_hidayat_agung.bkmmobile.databinding.ItemHistory2Binding;
import com.nur_hidayat_agung.bkmmobile.databinding.ItemWaitingListBinding;
import com.nur_hidayat_agung.bkmmobile.model.help.Help;
import com.nur_hidayat_agung.bkmmobile.model.workshop.ItemWL;

import java.util.ArrayList;
import java.util.List;

public class QueueWSAdapter extends RecyclerView.Adapter<QueueWSAdapter.QueueWSVH> {
    private List<ItemWL> itemWLList = new ArrayList<>();

    public QueueWSAdapter(List<ItemWL> itemWLList) {
        this.itemWLList = itemWLList;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setItemWLList(List<ItemWL> itemWLList) {
        this.itemWLList = itemWLList;
        notifyDataSetChanged();
    }

    @NonNull
    @androidx.annotation.NonNull
    @Override
    public QueueWSVH onCreateViewHolder(@NonNull @androidx.annotation.NonNull ViewGroup viewGroup, int i) {
        ItemWaitingListBinding itemBinding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext())
                , R.layout.item_waiting_list, viewGroup, false);

        return new QueueWSVH(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull @androidx.annotation.NonNull QueueWSVH queueWSVH, int i) {
        queueWSVH.bind(itemWLList.get(i));
    }

    @Override
    public int getItemCount() {
        return itemWLList.size();
    }

    public class QueueWSVH extends RecyclerView.ViewHolder {
        private final ItemWaitingListBinding waitingListBinding;

        public QueueWSVH(ItemWaitingListBinding waitingListBinding) {
            super(waitingListBinding.getRoot());
            this.waitingListBinding = waitingListBinding;
        }

        public void bind(ItemWL itemWL) {
            waitingListBinding.setWaitlist(itemWL);
            waitingListBinding.executePendingBindings();
        }
    }
}
