package com.nur_hidayat_agung.bkmmobile.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

public class PDialog {

    private ProgressDialog progressDialog;
    private Context context;

    public PDialog(Context context) {
        this.context = context;
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Mengambil Data Dari server...");
        progressDialog.setCancelable(false);
    }

    public void setDialog(boolean b)
    {
        if (b)
        {
            showPDialog();
        }
        else
        {
            hidePDialog();
        }
    }

    private void showPDialog(){
        if (!progressDialog.isShowing()){
            progressDialog.show();
        }
    }

    private void hidePDialog(){
        if (progressDialog.isShowing()){
            progressDialog.dismiss();
        }
    }

    public void fail(){
        Toast.makeText(context, "koneksi gagal", Toast.LENGTH_SHORT).show();
    }


}
