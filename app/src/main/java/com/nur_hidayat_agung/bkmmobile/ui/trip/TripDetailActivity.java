package com.nur_hidayat_agung.bkmmobile.ui.trip;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.nur_hidayat_agung.bkmmobile.R;
import com.nur_hidayat_agung.bkmmobile.callback.PictureDetailCallBack;
import com.nur_hidayat_agung.bkmmobile.databinding.ActivityTripDetailBinding;
import com.nur_hidayat_agung.bkmmobile.model.general.GeneralResponse;
import com.nur_hidayat_agung.bkmmobile.model.trip.DDLVM;
import com.nur_hidayat_agung.bkmmobile.model.trip.Trip;
import com.nur_hidayat_agung.bkmmobile.ui.general.DetailPicture;
import com.nur_hidayat_agung.bkmmobile.util.Constant;
import com.nur_hidayat_agung.bkmmobile.util.PDialog;
import com.nur_hidayat_agung.bkmmobile.util.PopUpDialog;
import com.nur_hidayat_agung.bkmmobile.util.SharedPref;
import com.nur_hidayat_agung.bkmmobile.util.UtilFunc;
import com.nur_hidayat_agung.bkmmobile.viewmodel.trip.TripDetailVM;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.reactivex.disposables.Disposable;

public class TripDetailActivity extends AppCompatActivity {

    private ActivityTripDetailBinding tripDetailBinding;
    private Trip trip = new Trip();
    private TripDetailVM tripDetailVM;
    private PDialog pDialog;
    private SharedPref sharedPref;
    private DDLVM ddlvm = new DDLVM();
    private File file;
    private Uri uriFile;
    private PopUpDialog dialog;
    private LinearLayout mainLayout;
    private String userChoosenTask;
    private String pictureImagePath;
    private RxPermissions rxPermissions;
    private Disposable disposable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
        initObserver();
        unHandingAction();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        //throw new RuntimeException("This is a crash");
        Log.d("tripDetLogBKM","request code : " + requestCode);
        Log.d("tripDetLogBKM","result code : " + resultCode);

        if (requestCode == Constant.captImageStat && resultCode == RESULT_OK)
        {
            pictureImagePath = sharedPref.getString(Constant.ImageFilePath);
            if (UtilFunc.isStringNotNull(pictureImagePath))
            {
                Toast.makeText(this,"gambar tersimpan di : "+ pictureImagePath,Toast.LENGTH_SHORT).show();
                tripDetailVM.isCam.setValue(true);
                //cameraHandling();
            }
//            imageHandling(uriFile);
//            Toast.makeText(this,"gambar tersimpan di : "+ uriFile.getPath(),Toast.LENGTH_SHORT).show();
        }

        if (requestCode == Constant.lookUpGaleryStat && resultCode == RESULT_OK)
        {
            galleryHandling(data);
        }

    }


    private void initView() {
        rxPermissions = new RxPermissions(this);
        tripDetailBinding = DataBindingUtil.setContentView(this,R.layout.activity_trip_detail);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        pDialog = new PDialog(this);
        sharedPref = new SharedPref(this);
        ddlvm = (DDLVM) this.getIntent().getSerializableExtra(Constant.ddlTrip);
        if (ddlvm != null)
        {
            tripDetailBinding.spTripStatus.setItems(ddlvm.getNames());
        }
        dialog = new PopUpDialog(this,new GeneralResponse());

    }

    private void initData() {
        tripDetailVM = ViewModelProviders.of(this).get(TripDetailVM.class);
        tripDetailVM.ids = ddlvm.getIds();
        trip = (Trip) this.getIntent().getSerializableExtra(Constant.detTrip);
        if (trip != null)
        {
            tripDetailVM.trip = trip;
            tripDetailVM.getDetail();
        }
        int width = sharedPref.getInt(Constant.windowWidth);
        tripDetailVM.setContext(this,width);
        tripDetailBinding.setDetailTripVM(tripDetailVM);

        //Toast.makeText(this, "window Width : " + tripDetailVM.windowWidth,Toast.LENGTH_SHORT).show();
    }

    private void initObserver() {
        tripDetailVM.liveDataPdialog.observe(this, aBoolean -> pDialog.setDialog(aBoolean));

        //region spinerPosition
        tripDetailVM.spinerPosition.observe(this,integer -> {
            Log.d("spinnerLog", "initObserver: " + integer);
            tripDetailBinding.spTripStatus.setSelectedIndex(integer);
        });
        //endregion

        //region LDtripDetail
        tripDetailVM.LDtripDetail.observe(this,tripDetail1 -> {
            Log.d("tripLogBkm", "status trip : " + tripDetail1.getStatus_trip());
            tripDetailBinding.setDetailTripVM(tripDetailVM);
            // tripDetailBinding.ivDetTripQrCode.setPath(tripDetail1.getQrcode());
        });
        //endregion

        //region captImage
        tripDetailVM.captImage.observe(this, integer -> {
            if (integer == Constant.captImageStat)
                selectImage();
        });
        //endregion

        //region resGenLD
        tripDetailVM.resGenLD.observe(this,generalResponse -> {
            if (generalResponse.getMsg() != null)
            {
                if (generalResponse.getMsg().getStatus() != Constant.ReqOk)
                    dialog.showMsg(generalResponse.getMsg().getMessage());
                    //this.finish();
            }

            if (generalResponse.getStatus() == Constant.ReqStat)
                dialog.showMsg(generalResponse.message);
        });
        //endregion

        //region open picture
        tripDetailVM.msgMutableLiveData.observe(this, msg -> {
            if (msg.getStatus() == Constant.ReqOk)
                startActivity(new Intent(this, DetailPicture.class)
                        .putExtra(Constant.qrUrl, msg));
            else dialog.showMsg(Constant.msgQRNA);
        });
        //endregion

        //region backFlag
        tripDetailVM.backFlag.observe(this, aBoolean -> {
            if (aBoolean)
            {
                Toast.makeText(this, "Data Berhasil Disimpan",Toast.LENGTH_SHORT).show();
                this.finish();
            }
        });
        //endregion

        //region isCam
        tripDetailVM.isCam.observe(this, aBoolean -> {
            if (aBoolean == true)
            {
                tripDetailVM.isCam.setValue(false);
                cameraHandling();
            }
        });
        //endregion

        tripDetailVM.isSpinerChange.observe(this, aBoolean -> {
            if (aBoolean != null)
            {
                if (aBoolean)
                {
                    tripDetailBinding.spTripStatus.setItems(tripDetailVM.names);
                    tripDetailBinding.spTripStatus.setSelectedIndex(tripDetailVM.ddlInt);
                    tripDetailVM.isSpinerChange.setValue(false);
                }
            }

        });

        tripDetailVM.txtMsgLD.observe(this,s -> {
            dialog.showMsg(s);
        });
    }

    @SuppressLint("CheckResult")
    private void selectImage() {
        disposable = rxPermissions
                .request(Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribe(granted -> {
                    if (granted) {
                        // All requested permissions are granted
                        final CharSequence[] items = { Constant.takePhoto.get(1), Constant.takePhoto.get(2),
                                Constant.takePhoto.get(3) };
                        AlertDialog.Builder builder = new AlertDialog.Builder(TripDetailActivity.this);
                        builder.setTitle(Constant.takePhoto.get(0));
                        builder.setItems(items, (dialog, item) -> {
                            boolean result = UtilFunc.checkPermission(TripDetailActivity.this);
                            if (items[item].equals(Constant.takePhoto.get(1))) {
                                userChoosenTask = Constant.takePhoto.get(1);
                                if(result)
                                {
                                    Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                    if (takePicture.resolveActivity(getPackageManager()) != null)
                                        openCameraBack();
                                }
                            } else if (items[item].equals(Constant.takePhoto.get(2))) {
                                userChoosenTask = Constant.takePhoto.get(2);
                                if(result) galleryIntent();
                            } else if (items[item].equals(Constant.takePhoto.get(3))) {
                                dialog.dismiss();
                            }
                        });
                        builder.show();
                    } else {
                        // At least one permission is denied
                        dialog.showMsg("Akses Ditolak");
                    }
                });

    }



    private void unHandingAction() {
        tripDetailBinding.spTripStatus.setOnItemSelectedListener((view, position, id, item) -> {
//            Snackbar.make(view, "Position : " + position + " id : " + id + " item : " + item.toString()
//                    , Snackbar.LENGTH_LONG).show();
            tripDetailVM.ddlString = item.toString();
            tripDetailVM.spinerPosition.setValue(position);
            tripDetailVM.saveDDL();
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        this.finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
        //softKeyboard.unRegisterSoftKeyboardCallback();
    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        switch (requestCode) {
//            case Constant.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
//                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    if(userChoosenTask.equals("Take Photo"))
//                        openCameraBack();
//                    else if(userChoosenTask.equals("Choose from Library"))
//                        galleryIntent();
//                } else {
//                    //code for deny
//                }
//            break;
//        }
//
//    }

    private void galleryIntent() {
//        Intent intent = new Intent();
//        intent.setType("image/*");
//        intent.setAction(Intent.ACTION_GET_CONTENT);//
//        startActivityForResult(Intent.createChooser(intent, "Select File"),Constant.lookUpGaleryStat);
        Intent i = new Intent(
                Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(i, Constant.lookUpGaleryStat);
    }

    private void openCameraBack() {
        // cara 1
//        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//        String imageFileName = timeStamp + ".jpg";
//        File storageDir = Environment.getExternalStoragePublicDirectory(
//                Environment.DIRECTORY_PICTURES);
//        pictureImagePath = storageDir.getAbsolutePath() + "/" + imageFileName;
//        sharedPref.setString(Constant.ImageFilePath,pictureImagePath);
//        File file = new File(pictureImagePath);
////        Uri outputFileUri = Uri.fromFile(file);
//        Uri outputFileUri = FileProvider.getUriForFile(this,
//                BuildConfig.APPLICATION_ID + ".provider",file);
//        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
//        cameraIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//        cameraIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
//        startActivityForResult(cameraIntent, Constant.captImageStat);


//        cara 2
//        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        //uriFile = Uri.fromFile(getOutputMediaFile());
//
//        uriFile = FileProvider.getUriForFile(this,
//                BuildConfig.APPLICATION_ID + ".provider",
//                getOutputMediaFile());
//
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, uriFile);
//        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//        startActivityForResult(intent, Constant.captImageStat);

        Intent pictureIntent = new Intent(
                MediaStore.ACTION_IMAGE_CAPTURE);
        File photoFile = null;
        try {
            photoFile = UtilFunc.createImageFile(this);
        } catch (IOException ex) {
            // Error occurred while creating the File
            dialog.showMsg("failed to create file");
        }
        if (photoFile != null)
        {
            Uri photoURI = FileProvider.getUriForFile(this,this.getPackageName()+".provider", photoFile);
            pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                    photoURI);
            startActivityForResult(pictureIntent,
                    Constant.captImageStat);
        }
        else
        {
            dialog.showMsg("failed to create file");
        }
    }


    private static File getOutputMediaFile(){
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "BKMMobile");

        if (!mediaStorageDir.exists()){
            if (!mediaStorageDir.mkdirs()){
                return null;
            }
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        return new File(mediaStorageDir.getPath() + File.separator +
                "IMG_"+ timeStamp + ".jpg");
    }

    private void cameraHandling() {
        Log.d("tripDetLogBKM",pictureImagePath);
        Toast.makeText(this, "membuat file dari camera ...", Toast.LENGTH_SHORT).show();
        File imgFile = new File(pictureImagePath);
        if(imgFile.exists()){
            Bitmap bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
//            LinearLayout.LayoutParams imageViewParams = new LinearLayout.LayoutParams(
//                    LinearLayout.LayoutParams.MATCH_PARENT,
//                    LinearLayout.LayoutParams.MATCH_PARENT);
            //tripDetailBinding.ivCamera.setLayoutParams(imageViewParams);
            tripDetailBinding.ivCamera.setImageBitmap(bitmap);
            tripDetailVM.bitmap = bitmap;
            Log.d("tripDetLogBKM","Size file captured : " + imgFile.length());
            Log.d("tripDetLogBKM","file name captured : " + imgFile.getName());
            //tripDetailVM.file = file;
            tripDetailVM.file = imgFile;
            tripDetailVM.isPicture.setValue(true);
           // pDialog.setDialog(true);
           // new fileFromBitmap(bitmap, getApplicationContext()).execute();
            tripDetailBinding.setIsSubmit(true);
            Toast.makeText(this, "pembuatan file sukses", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this, "file dari camera tidak di temukan...", Toast.LENGTH_SHORT).show();
        }
    }

    private void galleryHandling(Intent data) {
        //File file = new File(data.getData());
        Uri uri = data.getData();
        Toast.makeText(this, "Uri : " + uri, Toast.LENGTH_LONG).show();
        Toast.makeText(this, "Uri Path : " + uri.getPath(), Toast.LENGTH_LONG).show();
        if (uri != null)
        {
            Log.d("tripDetLogBKM","Uri Image Selected : " + uri);
            pictureImagePath = UtilFunc.getPath(this,uri);
            Toast.makeText(this, "Image Path : " + pictureImagePath, Toast.LENGTH_LONG).show();
            if (UtilFunc.isStringNotNull(pictureImagePath))
            {
                Log.d("tripDetLogBKM","pictureImagePath selected : " + pictureImagePath);
                File file = new File(pictureImagePath);
                if (file.exists())
                {
                    tripDetailBinding.setIsSubmit(true);
                    tripDetailVM.isPicture.setValue(true);
                    tripDetailVM.file = file;
                    tripDetailBinding.ivCamera.setImageURI(uri);
                }
            }else {
                dialog.showMsg("mohon pilih gambar dari galery, bukan dari gambar terkini");
            }
        }
    }

    private void imageHandling(Uri uri) {
        //File file = new File(data.getData());
        Toast.makeText(this, "Uri : " + uri, Toast.LENGTH_LONG).show();
        Toast.makeText(this, "Uri Path : " + uri.getPath(), Toast.LENGTH_LONG).show();
        if (uri != null)
        {
            Log.d("tripDetLogBKM","Uri Image Selected : " + uri);
            pictureImagePath = UtilFunc.getPath(this,uri);
            Toast.makeText(this, "Image Path : " + pictureImagePath, Toast.LENGTH_LONG).show();
            if (UtilFunc.isStringNotNull(pictureImagePath))
            {
                Log.d("tripDetLogBKM","pictureImagePath selected : " + pictureImagePath);
                File file = new File(pictureImagePath);
                if (file.exists())
                {
                    tripDetailBinding.setIsSubmit(true);
                    tripDetailVM.isPicture.setValue(true);
                    tripDetailVM.file = file;
                    tripDetailBinding.ivCamera.setImageURI(uri);
                }
            }else {
                dialog.showMsg("mohon pilih gambar dari galery");
            }
        }
    }


    public class fileFromBitmap extends AsyncTask<Void, Integer, String> {

        Context context;
        Bitmap bitmap;
        //String path_external = Environment.getExternalStorageDirectory()+ File.separator + "" + + File.separator + Constant.tmpImg;

        public fileFromBitmap(Bitmap bitmap, Context context) {
            this.bitmap = bitmap;
            this.context= context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // before executing doInBackground
            // update your UI
            // exp; make progressbar visible
        }

        @Override
        protected String doInBackground(Void... params) {

            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            file = new File(context.getCacheDir(),Constant.tmpImg);
            try {
                FileOutputStream fo = new FileOutputStream(file);
                fo.write(bytes.toByteArray());
                fo.flush();
                fo.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            // back to main thread after finishing doInBackground
            // update your UI or take action after
            // exp; make progressbar gone
            tripDetailVM.file = file;
            pDialog.setDialog(false);
            Log.d("tripLogBkm", file.getName());
            tripDetailVM.isPicture.setValue(true);

        }


    }

    private final PictureDetailCallBack pictureCalBack = url -> {
        String s = url;
        if (UtilFunc.isStringNotNull(url))
            startActivity(new Intent(getApplicationContext(), DetailPicture.class)
                    .putExtra(Constant.detPictHist,url));
        else {
            dialog.showMsg("Gambar tidak tersedia");
        }
    };
}
