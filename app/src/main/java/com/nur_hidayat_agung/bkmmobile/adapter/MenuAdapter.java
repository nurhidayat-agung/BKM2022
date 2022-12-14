package com.nur_hidayat_agung.bkmmobile.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.nur_hidayat_agung.bkmmobile.R;
import com.nur_hidayat_agung.bkmmobile.callback.ListMenuCallback;
import com.nur_hidayat_agung.bkmmobile.databinding.ItemMenuBinding;
import com.nur_hidayat_agung.bkmmobile.model.general.ItemMenu;
import com.nur_hidayat_agung.bkmmobile.model.home.DataItemMenu;
import com.nur_hidayat_agung.bkmmobile.util.Constant;

import java.util.ArrayList;
import java.util.List;

public class MenuAdapter  extends RecyclerView.Adapter<MenuAdapter.MenuVH>{
    private Context context;
    private List<DataItemMenu> itemMenuList = new ArrayList<>();
    private final ListMenuCallback listMenuCallback;
    private Integer count;

    @SuppressLint("NotifyDataSetChanged")
    public void setItemMenuList(List<DataItemMenu> itemMenuList) {
        this.itemMenuList = itemMenuList;
        notifyDataSetChanged();
    }

    public MenuAdapter(Context context, ListMenuCallback listMenuCallback, Integer count, List<DataItemMenu> itemMenuList) {
        this.context = context;
        this.listMenuCallback = listMenuCallback;
        this.count = count;
        this.itemMenuList = itemMenuList;
    }

    @NonNull
    @Override
    public MenuVH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ItemMenuBinding itemMenuBinding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext())
                , R.layout.item_menu,viewGroup,false);
        itemMenuBinding.setCallback(listMenuCallback);
        return new MenuVH(itemMenuBinding,context);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuVH menuVH, int i) {
        DataItemMenu itemMenu = itemMenuList.get(i);
        int isCount = View.GONE;
        if (i == 0 && count > 0)
        {
            isCount = View.VISIBLE;
        }
        menuVH.setMenuBinding(itemMenu,isCount,count);

        menuVH.menuBinding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return itemMenuList.size();
    }

    public class MenuVH extends RecyclerView.ViewHolder{

        private final ItemMenuBinding menuBinding;
        private final Context context;

        public MenuVH(ItemMenuBinding itemMenuBinding, Context context) {
            super(itemMenuBinding.getRoot());
            this.menuBinding = itemMenuBinding;
            this.context = context;
        }

        public void setMenuBinding(DataItemMenu menu, Integer isCount, Integer Count)
        {
            menuBinding.setItemMenu(menu);
            menuBinding.setCount(Count.toString());
            menuBinding.setIsCount(isCount);
            Glide.with(context).load(menu.icon).into(menuBinding.ivItemMenu);
            menuBinding.executePendingBindings();
        }
    }
}
