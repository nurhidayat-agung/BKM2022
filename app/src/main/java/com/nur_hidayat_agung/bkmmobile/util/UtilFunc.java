package com.nur_hidayat_agung.bkmmobile.util;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.util.Log;
import android.widget.DatePicker;

import com.nur_hidayat_agung.bkmmobile.model.trip.StatusTrip;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.RequestBody;

import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.Format;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import okhttp3.MultipartBody;

public class UtilFunc {

    public static boolean isStringNotNull(String s) {
        if (s != null) {
            return !s.trim().equals("");
        }
        return false;
    }

    public static String getFullMonthName(Date date) {
        String s = "-";
        Format formatter = new SimpleDateFormat("MMMM yyyy");
        if (date != null)
            s = formatter.format(date);
        return s;
    }

    public static String getDate(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constant.dateFormatUI);
        String s = "";
        if (date != null) s = simpleDateFormat.format(date);
        return s;
    }

    @SuppressLint("SimpleDateFormat")
    public static String getStringDate(Date date, String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        String s = "";
        if (date != null) s = simpleDateFormat.format(date);
        return s;
    }

    @SuppressLint("SimpleDateFormat")
    public static String reformatStringDate(String sDate, String oldFormat, String newFormat) {
        String newStringDate = "";
        if (sDate != null) {

            SimpleDateFormat oldFormater = new SimpleDateFormat(oldFormat);
            SimpleDateFormat newFormater = new SimpleDateFormat(newFormat);
            try {
                Date oldDate = oldFormater.parse(sDate);
                newStringDate = newFormater.format(oldDate);
            } catch (ParseException e) {
                e.printStackTrace();
                newStringDate = sDate;
            }
        }
        return newStringDate;
    }

    public static String getDateTime(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMM yyyy HH:mm:ss");
        String s = "";
        if (date != null) s = simpleDateFormat.format(date);
        return s;
    }


    public static RequestBody createPartFromString(String descriptionString) {
        return RequestBody.create(MediaType.parse("text/plain"), descriptionString);
    }

    public static MultipartBody.Part prepareFilePart(String partName, File file) {
        String ext = FilenameUtils.getExtension(file.getAbsolutePath());
        RequestBody requestFile =
                RequestBody.create(
                        MediaType.parse(ext),
                        file
                );

        okhttp3.RequestBody postFile = okhttp3.RequestBody.create(okhttp3.MediaType.parse("image/*"), file);
        return MultipartBody.Part.createFormData(partName, file.getName(), postFile);
        //return MultipartBody.Part.createFormData(partName, file.getName(), requestFile);

    }

    @NonNull
    public static okhttp3.RequestBody _createPartFromString(String descriptionString) {
        return okhttp3.RequestBody.create(
                okhttp3.MultipartBody.FORM, descriptionString);
    }

    @NonNull
    public static okhttp3.RequestBody createScalarRequest(String descriptionString) {
        return okhttp3.RequestBody.create(okhttp3.MediaType.parse("text/plain"), descriptionString);
    }

    public static String currFormat(Double value) {
        if (value == null) value = 0d;
        NumberFormat nf = NumberFormat.getCurrencyInstance();
        DecimalFormatSymbols decimalFormatSymbols = ((DecimalFormat) nf).getDecimalFormatSymbols();
        decimalFormatSymbols.setCurrencySymbol("");
        ((DecimalFormat) nf).setDecimalFormatSymbols(decimalFormatSymbols);
        return nf.format(value).trim();
    }

    public static String currFormat(String string) {
        Double value = 0d;
        if (UtilFunc.isStringNotNull(string)) {
            string = string.trim();
            try {
                value = Double.parseDouble(string);
            } catch (NumberFormatException e) {
                value = 0d;
            }
        }
        NumberFormat nf = NumberFormat.getCurrencyInstance();
        DecimalFormatSymbols decimalFormatSymbols = ((DecimalFormat) nf).getDecimalFormatSymbols();
        decimalFormatSymbols.setCurrencySymbol("");
        ((DecimalFormat) nf).setDecimalFormatSymbols(decimalFormatSymbols);
        return nf.format(value).trim();
    }

    public static Double forceNumber(String string) {
        Double value = 0d;
        if (UtilFunc.isStringNotNull(string)) {
            string = string.trim();
            try {
                value = Double.parseDouble(string);
            } catch (NumberFormatException e) {
                value = 0d;
            }
        }
        return value;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static boolean checkPermission(final Context context) {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= android.os.Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
                    alertBuilder.setCancelable(true);
                    alertBuilder.setTitle("Permission necessary");
                    alertBuilder.setMessage("External storage permission is necessary");
                    alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, Constant.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                        }
                    });
                    AlertDialog alert = alertBuilder.create();
                    alert.show();
                } else {
                    ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, Constant.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                }
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

    public static String getPath(Context context, Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(uri, projection, null, null, null);
        if (cursor == null) return null;
        cursor.moveToFirst();
        int column_index = cursor.getColumnIndexOrThrow(projection[0]);
        String s = cursor.getString(column_index);
        cursor.close();
        return s;
    }

    public static File createImageFile(Context context) throws IOException {
        SharedPref sharedPref = new SharedPref(context);
        String timeStamp =
                new SimpleDateFormat("yyyyMMdd_HHmmss",
                        Locale.getDefault()).format(new Date());
        String imageFileName = "IMG_" + timeStamp + "_";
        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        Log.d("tripDetLogBKM", storageDir.getAbsolutePath());
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        String imageFilePath = image.getAbsolutePath();
        sharedPref.setString(Constant.ImageFilePath, imageFilePath);
        Log.d("tripDetLogBKM", imageFilePath);
        return image;
    }

    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static Date getDateFromString(String text, String dateFormat) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
        try {
            return simpleDateFormat.parse(text);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date();
    }
}
