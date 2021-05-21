package com.nur_hidayat_agung.bkmmobile.adapter;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nur_hidayat_agung.bkmmobile.R;
import com.nur_hidayat_agung.bkmmobile.callback.ListMenuCallback;
import com.nur_hidayat_agung.bkmmobile.databinding.ItemAnnouncementBinding;
import com.nur_hidayat_agung.bkmmobile.databinding.ItemMenuBinding;
import com.nur_hidayat_agung.bkmmobile.model.general.ItemMenu;
import com.nur_hidayat_agung.bkmmobile.model.login.LoginAnnouncement;
import com.nur_hidayat_agung.bkmmobile.util.Constant;

import java.util.ArrayList;
import java.util.List;

public class AnnouncementAdapter extends RecyclerView.Adapter<AnnouncementAdapter.AnnVH>{
    private Context context;
    private List<LoginAnnouncement> annList ;

    public AnnouncementAdapter(Context context, List<LoginAnnouncement> Ann) {
        this.context = context;
        this.annList = Ann;
    }

    public void setAnn(List<LoginAnnouncement> anns)
    {
        this.annList = anns;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AnnVH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ItemAnnouncementBinding announcementBinding = DataBindingUtil
                .inflate(LayoutInflater.from(viewGroup.getContext())
                , R.layout.item_announcement,viewGroup,
                false);
        return new AnnVH(announcementBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull AnnVH annVH, int i) {
        LoginAnnouncement ann = annList.get(i);
        if (ann.is_img.equals("1")) ann.visibility = View.VISIBLE;
        else ann.visibility = View.GONE;
        annVH.setAnnouncement(ann,context);
        annVH.announcementBinding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return annList.size();
    }

    public class AnnVH extends RecyclerView.ViewHolder{

        private final ItemAnnouncementBinding announcementBinding;

        public AnnVH(ItemAnnouncementBinding itemMenuBinding) {
            super(itemMenuBinding.getRoot());
            this.announcementBinding = itemMenuBinding;
        }

        public void setAnnouncement(LoginAnnouncement ann, Context context)
        {
            announcementBinding.setAnn(ann);
            announcementBinding.executePendingBindings();
        }
    }

    @BindingAdapter({"imageUrlAnn"})
    public static void loadAnn(ImageView view, String imageUrl){
        Log.d("tripLogBkm","Image Url : " + imageUrl);
        if (imageUrl == null || imageUrl.equals(""))
        {
            Glide.with(view.getContext()).load(R.drawable.bg_header_trip).into(view);
        }
        else
        {
            Glide.with(view.getContext()).load(imageUrl).into(view);
        }
    }

    @BindingAdapter({"BackRLAnn"})
    public static void loadBack(RelativeLayout view, String background){
        if (background == null || background.equals(""))
        {
            background = "#748AB0";
        }
        view.setBackgroundColor(Color.parseColor(background));
    }

    @BindingAdapter({"fontColorHeader"})
    public static void loadFontColor(TextView view, String fontColor){
        if (fontColor == null || fontColor.equals(""))
        {
            fontColor = "#808080";
        }
        view.setTextColor(Color.parseColor(fontColor));
    }

    @BindingAdapter({"fontSizeHeader"})
    public static void loadFontSize(TextView view, float fontSize){
        view.setTextSize(fontSize);
    }
}
