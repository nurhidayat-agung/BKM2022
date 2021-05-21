package com.nur_hidayat_agung.bkmmobile.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nur_hidayat_agung.bkmmobile.R;
import com.nur_hidayat_agung.bkmmobile.callback.ItemPartCallBack;
import com.nur_hidayat_agung.bkmmobile.databinding.ItemPartServiceBinding;
import com.nur_hidayat_agung.bkmmobile.model.service.ItemListPart;

import java.util.ArrayList;
import java.util.List;

public class ListPartAdapter extends RecyclerView.Adapter<ListPartAdapter.ListPartVH> {
    private Context context;
    private List<ItemListPart> partList = new ArrayList<>();
    private ItemPartCallBack callBack;

    public ListPartAdapter(Context context, List<ItemListPart> partList,ItemPartCallBack callBack) {
        this.context = context;
        this.partList = partList;
        this.callBack = callBack;
    }

    public void setPartList(List<ItemListPart> partList) {
        if (partList == null || partList.isEmpty()) {
            this.partList = new ArrayList<>();
        } else {
            this.partList = partList;
        }
        notifyDataSetChanged();

    }

    public void setCallBack(ItemPartCallBack callBack) {
        this.callBack = callBack;
    }

    @NonNull
    @Override
    public ListPartVH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ItemPartServiceBinding itemBinding = DataBindingUtil.inflate(LayoutInflater.
                        from(viewGroup.getContext()), R.layout.item_part_service,
                viewGroup, false);

        return new ListPartVH(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ListPartVH listPartVH, int i) {
        listPartVH.bind(partList.get(i),callBack);
    }

    @Override
    public int getItemCount() {
        return partList.size();
    }

    public static class ListPartVH extends RecyclerView.ViewHolder {
        private final ItemPartServiceBinding partBinding;

        public ListPartVH(ItemPartServiceBinding itemBinding) {
            super(itemBinding.getRoot());
            this.partBinding = itemBinding;
        }

        public void bind(ItemListPart part, ItemPartCallBack partCallBack) {
            partBinding.setPart(part);
            partBinding.setCallback(partCallBack);
            partBinding.executePendingBindings();
        }
    }
}
