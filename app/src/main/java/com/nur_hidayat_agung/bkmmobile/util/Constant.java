package com.nur_hidayat_agung.bkmmobile.util;

import com.nur_hidayat_agung.bkmmobile.R;
import com.nur_hidayat_agung.bkmmobile.model.general.ItemMenu;

import java.util.ArrayList;
import java.util.List;

public class Constant {

    public static String baseUrl = "https://berkatkarimar.co.id/restapi/";
    public static int conTimeOut = 30;
    public static String appName = "BKMMobile";
    public static String Token = "";
    public static  String user_id = "";
    public static int ReqOk = 200;
    public static int ReqCreated = 201;
    public static int ReqStat = 100;
    public static String FireBaseToken = "FireBaseToken";
    public static String urlBase = "urlBase";


    //
    public static String userDetail = "userDetail";
    public static String detHistory = "detHistory";
    public static String detPictHist = "detPictHist";
    public static String detTrip = "detTrip";
    public static String ddlTrip = "ddlTrip";
    public static Integer captImageStat = 100;
    public static Integer lookUpGaleryStat = 111;
    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;
    public static String tmpImg = "BKMTemp.jpg";
    public static String MULTIPART_FORM_DATA = "multipart/form-data";
    public static String qrUrl = "qrUrl";
    public static String helpDet = "helpDet";
    public static String ImageFilePath = "ImageFilePath";
    public static String windowWidth = "windowWidth";


    // triger
    public static String trigDDL = "ddl_status";
    public static String trigLoad = "btn_load";
    public static String trigUnload = "btn_unload";
    public static String trigFinish = "btn_submit";
    public static String trigSPB = "btn_spb";

    // shared key word
    public static String spIsLogin = "spIsLogin";
    public static String spUserName = "spUserName";
    public static String spPassword = "spPassword";


    public static String msgChangePass = "Anda yakin akan mengubah Password ?";
    public static String msgOldPassWrong = "Password lama salah";
    public static String msgConfrPassWrong = "Password baru dan konfirmasi tidak sama";
    public static String msgChangeFail = "Ganti Password gagal";
    public static String msgChangeOK = "Password berhasil di rubah";
    public static String msgNetworkError = "Gagal menyambung ke server";
    public static String msgLoginFail = "Login Gagal";
    public static String msgQRNA = "QR Code Not Available";
    public static String msgTripSubmit = "Apakah Anda Yakin Menyelesaikan Trip ?";


    // menu constant
    public static String menuHistory = "menuHistory";
    public static String menuTrip = "menuTrip";
    public static String menuSalary = "menuSalary";
    public static String menuHelp = "menuHelp";
    public static String menuPart = "menuPart";
    public static String menuService = "menuService";
    public static String menuWorkShop = "menuWorkShop";

    public static List<ItemMenu> itemMenus = new ArrayList<ItemMenu>(){{
        add(new ItemMenu(1,"Pengangkutan Baru", R.drawable.menu_trip, Constant.menuTrip));
        add(new ItemMenu(2,"Riwayat Pengangkutan", R.drawable.menu_history, Constant.menuHistory));
        add(new ItemMenu(3,"Slip Gaji", R.drawable.menu_salary, Constant.menuSalary));
        add(new ItemMenu(4,"Bengkel", R.drawable.ic_workshop, Constant.menuWorkShop));
        add(new ItemMenu(5,"Buku Servis", R.drawable.menu_service_trans, Constant.menuService));
        add(new ItemMenu(6,"Penggantian Part", R.drawable.menu_part_trans, Constant.menuPart));
        add(new ItemMenu(4,"Bantuan", R.drawable.menu_help, Constant.menuHelp));
    }};

    public static List<String> monthsName = new ArrayList<String>(){{
        add("Januari");
        add("Februari");
        add("Maret");
        add("April");
        add("Mei");
        add("Juni");
        add("Juli");
        add("Agustus");
        add("September");
        add("Oktober");
        add("November");
        add("Desember");
    }};

    public static List<String> takePhoto = new ArrayList<String>(){{
        add("Ambil Gambar");
        add("Ambil Dari Kamera");
        add("Ambil Dari Galeri");
        add("Cancel");
    }};


    public static String RetrofitFail = "RetrofitFail";
    public static String intentExtraPart = "intentExtraPart";
    public static String intentExtraVehicleID = "intentExtraVehicleID";
    public static String serviceActive = "serviceActive";
    public static String dateFormat = "yyyy-MM-dd";
    public static String dateFormatComplete = "yyyy-MM-dd HH:mm:ss";
    public static String dateFormatUI = "dd MMM yyyy";
    public static String dateFormatCompleteUI = "dd MMM yyyy HH:mm:ss";
    public static String detailService = "detailService";
    public static String isInTheQueue = "isInTheQueue";

    public static class Message {
        public static String AlreadyQueue = "Maaf pendaftaran hanya bisa di lakukan sekali dalam satu waktu";
    }
}