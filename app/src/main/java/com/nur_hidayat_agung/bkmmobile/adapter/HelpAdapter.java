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
import com.nur_hidayat_agung.bkmmobile.callback.HelpCallBack;
import com.nur_hidayat_agung.bkmmobile.callback.ListTripCallBack;
import com.nur_hidayat_agung.bkmmobile.databinding.ItemHelpBinding;
import com.nur_hidayat_agung.bkmmobile.databinding.ItemTripBinding;
import com.nur_hidayat_agung.bkmmobile.model.help.Help;
import com.nur_hidayat_agung.bkmmobile.model.trip.Trip;
import com.nur_hidayat_agung.bkmmobile.util.SharedPref;

import java.util.ArrayList;
import java.util.List;

public class HelpAdapter extends RecyclerView.Adapter<HelpAdapter.HelpVH>{

    private Context context;
    private List<Help> helps = new ArrayList<>();
    @Nullable
    private final HelpCallBack helpCallBack;

    public HelpAdapter(Context context, List<Help> helps, @Nullable HelpCallBack helpCallBack) {
        this.context = context;
        this.helps = helps;
        this.helpCallBack = helpCallBack;

    }

    public void setHelps(List<Help> helps)
    {
        if (helps == null)
        {
            this.helps = new ArrayList<>();
            notifyItemRangeChanged(0,helps.size());
        }
        else {
            this.helps = helps;
            notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public HelpVH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ItemHelpBinding itemHelpBinding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext())
                , R.layout.item_help,viewGroup,false);
        itemHelpBinding.setCallback(helpCallBack);
        return new HelpVH(itemHelpBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull HelpVH helpVH, int i) {
        Help help = helps.get(i);
        helpVH.helpBinding.setHelp(help);
        helpVH.helpBinding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return helps.size();
    }

    public class HelpVH extends RecyclerView.ViewHolder
    {
        private final ItemHelpBinding helpBinding;

        public HelpVH(ItemHelpBinding helpBinding) {
            super(helpBinding.getRoot());
            this.helpBinding = helpBinding;
        }

        public void bind(Help help)
        {
            helpBinding.setHelp(help);
            helpBinding.executePendingBindings();
        }
    }
}
