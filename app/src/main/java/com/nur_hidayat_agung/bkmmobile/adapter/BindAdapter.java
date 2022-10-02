package com.nur_hidayat_agung.bkmmobile.adapter;

import android.annotation.SuppressLint;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import android.widget.RelativeLayout;
import android.databinding.BindingAdapter;
import android.widget.TextView;

import com.nur_hidayat_agung.bkmmobile.R;
import com.nur_hidayat_agung.bkmmobile.util.Constant;
import com.nur_hidayat_agung.bkmmobile.util.UtilFunc;

public class BindAdapter {

    @BindingAdapter({"tripImageUrl"})
    public static void loadTripImage(ImageView view, String imageUrl){
        if (imageUrl == null || imageUrl.equals(""))
        {
            Glide.with(view.getContext()).load(R.drawable.camera).into(view);
//            LinearLayout.LayoutParams imageViewParams = new LinearLayout.LayoutParams(
//                    LinearLayout.LayoutParams.WRAP_CONTENT,
//                    LinearLayout.LayoutParams.WRAP_CONTENT);
//            view.setLayoutParams(imageViewParams);
        }
        else
        {
            Glide.with(view.getContext()).load(imageUrl).into(view);
        }
    }

    @BindingAdapter({"imageUrlQR"})
    public static void loadImageQR(ImageView view, String imageUrl){
        Log.d("tripLogBkm","Image Url : " + imageUrl);
        if (imageUrl == null || imageUrl.equals(""))
        {
            Glide.with(view.getContext()).load(R.drawable.qr_code).into(view);
        }
        else
        {
            Glide.with(view.getContext()).load(imageUrl).into(view);
        }
    }

    @BindingAdapter({"imageUrl"})
    public static void loadImage(ImageView view, String imageUrl){
        if (imageUrl == null || imageUrl.equals(""))
        {
            Glide.with(view.getContext()).load(R.drawable.camera).into(view);
            RelativeLayout.LayoutParams imageViewParams = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);
            view.setLayoutParams(imageViewParams);
        }
        else
        {
            Glide.with(view.getContext()).load(imageUrl).into(view);
        }
    }

    @BindingAdapter({"imageProfileURL"})
    public static void loadProfile(ImageView view, String imageUrl){
        Log.d("tripLogBkm","Image Url : " + imageUrl);
        if (imageUrl == null || imageUrl.equals(""))
        {
            Glide.with(view.getContext()).load(R.mipmap.ic_launcher_round).into(view);
        }
        else
        {
            Glide.with(view.getContext()).load(imageUrl).into(view);
        }
    }


    @BindingAdapter({"priceFormat"})
    public static void priceFormat(TextView textView, String sPrice)
    {
        String price = UtilFunc.currFormat(sPrice);
        textView.setText("Rp. " + price);
    }

    @BindingAdapter({"statusFormat"})
    public static void statusFormat(TextView textView, String status)
    {
        String txtStatus = "";
        if (status.equalsIgnoreCase("p"))
        {
            txtStatus = "dalam proses";
        }
        else if (status.equalsIgnoreCase(""))
        {
            txtStatus = "menunggu";
        }
        else
        {
            txtStatus = status;
        }
        textView.setText(txtStatus);
    }

    @BindingAdapter({"dateFormat"})
    public static void dateFormat(TextView textView, String sDate)
    {
        String newDate = UtilFunc.reformatStringDate(sDate, Constant.dateFormat, Constant.dateFormatUI);
        textView.setText(newDate);
    }

    @BindingAdapter({"dateFormatComplete"})
    public static void dateFormatComplete(TextView textView, String sDate)
    {
        String newDate = UtilFunc.reformatStringDate(sDate, Constant.dateFormatComplete, Constant.dateFormatCompleteUI);
        textView.setText(newDate);
    }

    @BindingAdapter({"showMore"})
    public static void showMore(TextView textView, String desc)
    {
        if (desc.length() > 15)
        {
            String more = desc.substring(0,15) + " ...";
            textView.setText(more);
        }
        else {
            textView.setText(desc);
        }

    }
}
