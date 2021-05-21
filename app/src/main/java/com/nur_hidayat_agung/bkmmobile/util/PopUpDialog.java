package com.nur_hidayat_agung.bkmmobile.util;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Window;
import android.view.WindowManager;

import com.nur_hidayat_agung.bkmmobile.databinding.PopUpMessageBinding;
import com.nur_hidayat_agung.bkmmobile.model.general.GeneralResponse;

public class PopUpDialog {

    private Context context;
    private GeneralResponse generalResponse;
    private Dialog dialog;
    private PopUpMessageBinding messageBinding;
    private  SharedPref sharedPref;

    public GeneralResponse getGeneralResponse() {
        return generalResponse;
    }

    public void setGeneralResponse(GeneralResponse generalResponse) {
        this.generalResponse = generalResponse;
    }

    public PopUpDialog(Context context, GeneralResponse generalResponse) {
        sharedPref = new SharedPref(context);
        this.context = context;
        this.generalResponse = generalResponse;
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        messageBinding = PopUpMessageBinding.inflate(LayoutInflater.from(context),null);
        dialog.setContentView(messageBinding.getRoot());
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        Window window = dialog.getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.CENTER;
        params.width = sharedPref.getInt(Constant.windowWidth);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        messageBinding.btnPopUpOk.setOnClickListener(v -> {
            dialog.dismiss();
        });
    }

    public void showMsg(String msg)
    {
        if (generalResponse == null) generalResponse = new GeneralResponse();
        generalResponse.message = msg;
        messageBinding.setData(generalResponse);
        dialog.show();
    }


}
