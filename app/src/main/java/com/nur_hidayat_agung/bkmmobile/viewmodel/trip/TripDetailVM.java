package com.nur_hidayat_agung.bkmmobile.viewmodel.trip;

import android.app.Application;
import android.app.Dialog;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.databinding.BindingAdapter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.nur_hidayat_agung.bkmmobile.R;
import com.nur_hidayat_agung.bkmmobile.app.BKMApp;
import com.nur_hidayat_agung.bkmmobile.app.BaseContext;
import com.nur_hidayat_agung.bkmmobile.databinding.PopUpYesnoBinding;
import com.nur_hidayat_agung.bkmmobile.model.general.DebugResponse;
import com.nur_hidayat_agung.bkmmobile.model.general.GeneralResponse;
import com.nur_hidayat_agung.bkmmobile.model.general.Msg;
import com.nur_hidayat_agung.bkmmobile.model.trip.DDLVM;
import com.nur_hidayat_agung.bkmmobile.model.trip.PostTrip;
import com.nur_hidayat_agung.bkmmobile.model.trip.Trip;
import com.nur_hidayat_agung.bkmmobile.model.trip.TripDetail;
import com.nur_hidayat_agung.bkmmobile.repositories.TripService;
import com.nur_hidayat_agung.bkmmobile.util.Constant;
import com.nur_hidayat_agung.bkmmobile.util.PopUpDialog;
import com.nur_hidayat_agung.bkmmobile.util.SharedPref;
import com.nur_hidayat_agung.bkmmobile.util.UtilFunc;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import okhttp3.MultipartBody;


public class TripDetailVM extends AndroidViewModel {

    private TripService tripService;
    private BKMApp bkmApp;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    public MutableLiveData<Boolean> liveDataPdialog = new MutableLiveData<>();
    public MutableLiveData<TripDetail> LDtripDetail = new MutableLiveData<>();
    private Context context;
    private SharedPref sharedPref;
    private int Year, Month = 0;
    public Trip trip = new Trip();
    public MutableLiveData<Integer> spinerPosition = new MutableLiveData<>();
    public MutableLiveData<Integer> doConectLiveData = new MutableLiveData<>();
    public List<String> ids = new ArrayList<>();
    public List<String> names = new ArrayList<>();
    public String ddlString;
    public int ddlInt;
    public MutableLiveData<Integer> captImage = new MutableLiveData<>();
    public Bitmap bitmap;
    public File file;
    public MutableLiveData<DebugResponse> resGenLD = new MutableLiveData<>();
    public MutableLiveData<Msg> msgMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<Boolean> backFlag = new MutableLiveData<>();
    private PopUpYesnoBinding yesnoBinding;
    private GeneralResponse generalResponse;
    private Dialog dialog;
    public MutableLiveData<Boolean> isPicture = new MutableLiveData<>();
    public MutableLiveData<Boolean> isCam = new MutableLiveData<>();
    public MutableLiveData<Boolean> isSpinerChange = new MutableLiveData<>();
    public MutableLiveData<TripDetail> tripDetail = new MutableLiveData<>();
    public MutableLiveData<DDLVM> ddlvmLD = new MutableLiveData<>();
    public MutableLiveData<String> txtMsgLD = new MutableLiveData<>();
    private int windowWidth;


    public static List<String> trigs = new ArrayList<String>(){{
        add("btn_load");
        add("btn_load_do_connect");
        add("btn_spb");
    }};

    public static List<String> preTrigs = new ArrayList<String>(){{
        add("btn_pre_load");
        add("btn_pre_load_do_connect");
    }};


    public TripDetailVM(@NonNull Application application) {
        super(application);
        bkmApp = BKMApp.create(application);
        tripService = BaseContext.CreateService(TripService.class,application);
        sharedPref = new SharedPref(application);
        doConectLiveData.setValue(View.GONE);
        captImage.setValue(0);
        // bitmap = BitmapFactory.decodeResource(application.getResources(), R.drawable.fast_delivery);
        isPicture.setValue(false);
    }

    public void setContext(Context context,int width)
    {
        this.context = context;
        windowWidth = width;
        prepareYesNoDialog();
    }

    private void prepareYesNoDialog() {
        this.generalResponse = new GeneralResponse();
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        yesnoBinding = PopUpYesnoBinding.inflate(LayoutInflater.from(context),null);
        dialog.setContentView(yesnoBinding.getRoot());
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        Window window = dialog.getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.CENTER;
        params.width = windowWidth;
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        yesnoBinding.btnPopUpNo.setOnClickListener(v -> {
            dialog.dismiss();
        });
        yesnoBinding.btnPopUpYes.setOnClickListener(v -> {
            dialog.dismiss();
            saveFinish();
        });
    }

    public void getDetail()
    {
        liveDataPdialog.setValue(true);
        Disposable disposable = tripService.getTripDetail(trip.getId())
                .subscribeOn(bkmApp.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(tripDetail1 -> {
                    liveDataPdialog.setValue(false);
                    if (tripDetail1 != null)
                    {
                        if (tripDetail1.Do_connectObject != null)
                        {
                            doConectLiveData.setValue(View.VISIBLE);
                            tripDetail1.sub_do = tripDetail1.sub_do + " & " + tripDetail1.Do_connectObject.sub_do;
                        }

                        LDtripDetail.setValue(tripDetail1);
                        assert tripDetail1.getStatus_trip() != null;
                        if (tripDetail1.getStatus_trip() != "")
                        {
                            int selected = ids.indexOf(tripDetail1.getStatus_trip());
                            Log.d("spinnerLog", "index : " + selected);
                            if (selected > -1)
                                spinerPosition.setValue(selected);
                        }
                    }
                },throwable -> {
                    liveDataPdialog.setValue(false);
                    Log.i("tripDetLogBkm","Request Error : " + throwable.getMessage());
                });
        compositeDisposable.add(disposable);
    }

    public void saveDDL()
    {
        PostTrip postTrip = new PostTrip();
        postTrip.id = LDtripDetail.getValue().getId();

        postTrip.status_trip = ids.get((spinerPosition.getValue()
                == null ? 0 : spinerPosition.getValue()));

        if (names.contains(ddlString))
        {
            int indexDll = names.indexOf(ddlString);
            postTrip.status_trip = ids.get(indexDll);
        }

        postTrip.number_of_load = LDtripDetail.getValue().getAmount_sent() == null ? ""
                : LDtripDetail.getValue().getAmount_sent();
        postTrip.number_of_unload = LDtripDetail.getValue().getAmount_received() == null ? ""
                : LDtripDetail.getValue().getAmount_received();
        postTrip.trigger = Constant.trigDDL;
        postTrip.spb_no = LDtripDetail.getValue().spb_number == null ? ""
                : LDtripDetail.getValue().spb_number;
        pushTrip(postTrip, false,false);

        if (LDtripDetail.getValue().getDo_connectObject() != null)
        {
            postTrip.id = LDtripDetail.getValue().getDo_connectObject().getId();
            postTrip.status_trip = ids.get((spinerPosition.getValue()
                    == null ? 0 : spinerPosition.getValue()));
            postTrip.number_of_load = LDtripDetail.getValue().getDo_connectObject()
                    .getAmount_sent() == null ? ""
                    : LDtripDetail.getValue().getDo_connectObject().getAmount_sent();
            postTrip.number_of_unload = LDtripDetail.getValue().getDo_connectObject()
                    .getAmount_received() == null ? ""
                    : LDtripDetail.getValue().getDo_connectObject().getAmount_received();
            postTrip.trigger = Constant.trigDDL;
            postTrip.spb_no = LDtripDetail.getValue().spb_number == null ? ""
                    : LDtripDetail.getValue().spb_number;
            pushTrip(postTrip,false,false);
        }
    }

    public void saveSpb(String spb)
    {
        PostTrip postTrip = new PostTrip();
        postTrip.id = LDtripDetail.getValue().getId();
        postTrip.status_trip = ids.get((spinerPosition.getValue()
                == null ? 0 : spinerPosition.getValue()));
        postTrip.number_of_load = LDtripDetail.getValue().getAmount_sent() == null ? ""
                : LDtripDetail.getValue().getAmount_sent();
        postTrip.number_of_unload = LDtripDetail.getValue().getAmount_received() == null ? ""
                : LDtripDetail.getValue().getAmount_received();
        postTrip.trigger = Constant.trigSPB;
        postTrip.spb_no = spb;
        pushTrip(postTrip, false,false);

        if (LDtripDetail.getValue().getDo_connectObject() != null)
        {
            postTrip.id = LDtripDetail.getValue().getDo_connectObject().getId();
            postTrip.status_trip = ids.get((spinerPosition.getValue()
                    == null ? 0 : spinerPosition.getValue()));
            postTrip.number_of_load = LDtripDetail.getValue().getDo_connectObject()
                    .getAmount_sent() == null ? ""
                    : LDtripDetail.getValue().getDo_connectObject().getAmount_sent();
            postTrip.number_of_unload = LDtripDetail.getValue().getDo_connectObject()
                    .getAmount_received() == null ? ""
                    : LDtripDetail.getValue().getDo_connectObject().getAmount_received();
            postTrip.trigger = Constant.trigSPB;
            postTrip.spb_no = spb;
            pushTrip(postTrip,false,false);
        }
    }

    public void savePreDo(String s)
    {
        PostTrip postTrip = new PostTrip();
        postTrip.id = LDtripDetail.getValue().getId();
        postTrip.status_trip = ids.get((spinerPosition.getValue()
                == null ? 0 : spinerPosition.getValue()));
        postTrip.number_of_load = s;
        postTrip.number_of_unload = LDtripDetail.getValue().getAmount_received() == null ? ""
                : LDtripDetail.getValue().getAmount_received();
        postTrip.trigger = Constant.trigLoad;
        postTrip.spb_no = LDtripDetail.getValue().spb_number == null ? ""
                : LDtripDetail.getValue().spb_number;
        pushTrip(postTrip,false,false);
    }

    public void saveDo(String s)
    {
        PostTrip postTrip = new PostTrip();
        postTrip.id = LDtripDetail.getValue().getId();
        postTrip.status_trip = ids.get((spinerPosition.getValue()
                == null ? 0 : spinerPosition.getValue()));
        postTrip.number_of_load = LDtripDetail.getValue().getAmount_sent() == null ? ""
                : LDtripDetail.getValue().getAmount_sent();
        postTrip.number_of_unload = s;
        postTrip.trigger = Constant.trigLoad;
        postTrip.spb_no = LDtripDetail.getValue().spb_number == null ? ""
                : LDtripDetail.getValue().spb_number;
        pushTrip(postTrip,false,false);
    }


    public void savePreSecDO(String s)
    {
        Log.d("tripLogBkm", "save sec do");
        PostTrip postTrip = new PostTrip();
        postTrip.id = LDtripDetail.getValue().getDo_connectObject().getId();
        postTrip.status_trip = ids.get((spinerPosition.getValue()
                == null ? 0 : spinerPosition.getValue()));
        postTrip.number_of_load = s;
        postTrip.number_of_unload = LDtripDetail.getValue().getDo_connectObject()
                .getAmount_received() == null ? ""
                : LDtripDetail.getValue().getDo_connectObject().getAmount_received();
        postTrip.trigger = Constant.trigLoad;
        postTrip.spb_no = LDtripDetail.getValue().spb_number == null ? ""
                : LDtripDetail.getValue().spb_number;
        pushTrip(postTrip,false,false);
    }

    public void saveSecDO(String s)
    {
        Log.d("tripLogBkm", "save sec do");
        PostTrip postTrip = new PostTrip();
        postTrip.id = LDtripDetail.getValue().getDo_connectObject().getId();
        postTrip.status_trip = ids.get((spinerPosition.getValue()
                == null ? 0 : spinerPosition.getValue()));
        postTrip.number_of_load = LDtripDetail.getValue().getDo_connectObject()
                .getAmount_sent() == null ? ""
                : LDtripDetail.getValue().getDo_connectObject().getAmount_sent();
        postTrip.number_of_unload = s;
        postTrip.trigger = Constant.trigLoad;
        postTrip.spb_no = LDtripDetail.getValue().spb_number == null ? ""
                : LDtripDetail.getValue().spb_number;
        pushTrip(postTrip,false,false);
    }

    public void confirmFinish()
    {
        Log.d("logConfirm","isPicture : " + isPicture.getValue());
        Log.d("logConfirm","isCam : " + isCam.getValue());
        if (isPicture.getValue() == null) isPicture.setValue(false);
        if (isCam.getValue() == null) isCam.setValue(false);

        if ((!isPicture.getValue() && !isCam.getValue()) ||
                !UtilFunc.isStringNotNull(LDtripDetail.getValue().spb_number) ||
                !ids.get((spinerPosition.getValue() == null ? 0 : spinerPosition.getValue())).equals("5"))
        {
            DebugResponse response = new DebugResponse();
            response.setStatus(Constant.ReqStat);
            response.message = "Anda Harus ";
            if (!UtilFunc.isStringNotNull(LDtripDetail.getValue().spb_number)) response.message += "memasukan nomer SPB, ";
            if ((!isPicture.getValue() && !isCam.getValue())) response.message += "mengupload bukti foto(klik icon kamera di atas tombol ini), ";
            if (!ids.get((spinerPosition.getValue() == null ? 0 : spinerPosition.getValue())).equals("5"))
                response.message += "ubah status perjalanan menjadi bongkar";
            resGenLD.setValue(response);
        }
        else
        {
            GeneralResponse response = new GeneralResponse();
            response.message = Constant.msgTripSubmit;
            yesnoBinding.setMsg(response);
            dialog.show();
        }
    }

    public void saveFinish()
    {
        PostTrip postTrip = new PostTrip();
        postTrip.id = LDtripDetail.getValue().getId();
        postTrip.status_trip = ids.get((spinerPosition.getValue()
                == null ? 0 : spinerPosition.getValue()));
        postTrip.number_of_load = LDtripDetail.getValue().getAmount_sent() == null ? ""
                : LDtripDetail.getValue().getAmount_sent();
        postTrip.number_of_unload = LDtripDetail.getValue().getAmount_received() == null ? ""
                : LDtripDetail.getValue().getAmount_received();
        postTrip.trigger = Constant.trigFinish;
        postTrip.spb_no = LDtripDetail.getValue().spb_number == null ? ""
                : LDtripDetail.getValue().spb_number;
        boolean isBack = ((LDtripDetail.getValue().getDo_connectObject() == null) && (postTrip.status_trip.equals("5")));
        pushTrip(postTrip,(LDtripDetail.getValue().getDo_connectObject() == null),isBack);

        if (LDtripDetail.getValue().getDo_connectObject() != null)
        {
            postTrip.id = LDtripDetail.getValue().getDo_connectObject().getId();
            postTrip.status_trip = ids.get((spinerPosition.getValue()
                    == null ? 0 : spinerPosition.getValue()));
            postTrip.number_of_load = LDtripDetail.getValue().getDo_connectObject()
                    .getAmount_sent() == null ? ""
                    : LDtripDetail.getValue().getDo_connectObject().getAmount_sent();
            postTrip.number_of_unload = LDtripDetail.getValue().getDo_connectObject()
                    .getAmount_received() == null ? ""
                    : LDtripDetail.getValue().getDo_connectObject().getAmount_received();
            postTrip.trigger = Constant.trigFinish;
            postTrip.spb_no = LDtripDetail.getValue().spb_number == null ? ""
                    : LDtripDetail.getValue().spb_number;

            //if (spinerPosition)
            pushTrip(postTrip,true, (postTrip.status_trip.equals("5")));
        }
    }

    public void takePicture()
    {
        captImage.setValue(Constant.captImageStat);
    }

    public void pushTrip(PostTrip postTrip, boolean showPopUp, boolean backTripList)
    {
        if (postTrip.trigger.equals(Constant.trigFinish))
        liveDataPdialog.setValue(true);
        //File file = new File()
        Log.d("tripLogBkm", "id trip : " + postTrip.id);
        Log.d("tripLogBkm", "spb no : " + postTrip.spb_no);
        Log.d("tripLogBkm", "load : " + postTrip.number_of_load);
        Log.d("tripLogBkm", "unload : " + postTrip.number_of_unload);
        Log.d("tripLogBkm", "status_trip : " + postTrip.status_trip);
        Log.d("tripLogBkm", "Trigger : " + postTrip.trigger);
       // Log.d("tripLogBkm", "spb_img : " + file.getAbsoluteFile());

        Log.d("tripLogBkm", "user id : " + sharedPref.getUserId());
        Log.d("tripLogBkm", "token : " + sharedPref.getAuth());

        // create a map of data to pass along
        okhttp3.RequestBody id = UtilFunc.createScalarRequest(postTrip.id);
        okhttp3.RequestBody status_trip = UtilFunc.createScalarRequest(postTrip.status_trip);
        okhttp3.RequestBody number_of_load = UtilFunc.createScalarRequest(postTrip.number_of_load);
        okhttp3.RequestBody number_of_unload = UtilFunc.createScalarRequest(postTrip.number_of_unload);
        okhttp3.RequestBody Trigger = UtilFunc.createScalarRequest(postTrip.trigger);
        okhttp3.RequestBody spb_no = UtilFunc.createScalarRequest(postTrip.spb_no);

        if (file != null && postTrip.trigger.equals(Constant.trigFinish))
        {
            // create part for file (photo, video, ...)
            MultipartBody.Part body = UtilFunc.prepareFilePart("spb_img", file);

            Disposable disposable = tripService.mPostTrip(
                    id,
                    status_trip,
                    number_of_load,
                    number_of_unload,
                    Trigger,
                    spb_no,
                    body
            )
            .subscribeOn(bkmApp.subscribeScheduler())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(debugResponse -> {
                liveDataPdialog.setValue(false);
                Log.i("tripLogBkm","message : " + debugResponse.getMsg().getMessage());
                if (showPopUp) {
                    resGenLD.setValue(debugResponse);
                }

                if (postTrip.trigger.equals(Constant.trigFinish))
                {
                    if (debugResponse != null)
                    {
                        if (debugResponse.getMsg() != null)
                        {
                            if (debugResponse.getMsg().getStatus() == Constant.ReqOk)
                            {
                                backFlag.setValue(backTripList);
                            }
                            else
                            {

                            }
                        }
                    }
                }

            },throwable -> {
                liveDataPdialog.setValue(false);
                Log.i("tripLogBkm","Request Error : " + throwable.getMessage());
            });
            compositeDisposable.add(disposable);
        }
        else {
            Disposable disposable = tripService.mPostTrip(
                    id,
                    status_trip,
                    number_of_load,
                    number_of_unload,
                    Trigger,
                    spb_no
            )
            .subscribeOn(bkmApp.subscribeScheduler())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(response -> {
                liveDataPdialog.setValue(false);
                Log.i("tripLogBkm","message : " + response.getMsg().getMessage());
                if (showPopUp) {
                    resGenLD.setValue(response);
                }
                if(postTrip.trigger.equals(Constant.trigDDL))
                {
                    refreshSpiner();
                }
//                backFlag.setValue(backTripList);
            },throwable -> {
                liveDataPdialog.setValue(false);
                Log.i("tripLogBkm","Request Error : " + throwable.getMessage());
            });
            compositeDisposable.add(disposable);
        }
    }

    private void refreshSpiner() {
        liveDataPdialog.setValue(true);
        Disposable disposable = tripService.getTripDetail(trip.getId())
                .subscribeOn(bkmApp.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(tripDetail1 -> {
                    if (tripDetail1.list_status_trip != null)
                    {
                        List<String> ids = new ArrayList<>();
                        List<String> strings = new ArrayList<>();
                        for (int i = 0; i < tripDetail1.list_status_trip.size(); i++)
                        {
                            ids.add(tripDetail1.list_status_trip.get(i).id);
                            strings.add(tripDetail1.list_status_trip.get(i).long_name);
                        }
                        DDLVM ddlvm = new DDLVM();
                        if (ids.size() == strings.size())
                        {
                            ddlvm.setStatus(true);
                            ddlvm.setIds(ids);
                            ddlvm.setNames(strings);
                            ddlvmLD.setValue(ddlvm);
                            this.ids = ddlvm.getIds();
                            ddlInt = ids.indexOf(tripDetail1.getStatus_trip());
                            names = ddlvm.getNames();
                            isSpinerChange.setValue(true);
                        }
                        else ddlvm.setStatus(false);

                    }
                    liveDataPdialog.setValue(false);
                },throwable -> {
                    liveDataPdialog.setValue(false);
                    Log.i("tripLogBkm","Request Error : " + throwable.getMessage());
                });
        compositeDisposable.add(disposable);
    }

    public void onUsernameTextChanged(CharSequence text, String trigger) {
        // TODO do something with text
        Log.i("tripLogBkm","Text : " + text.toString() + ", Trigger : " + trigger);
        if (trigger.equals(trigs.get(0))) saveDo(text.toString());
        else if (trigger.equals(trigs.get(1))) saveSecDO(text.toString());
        else if (trigger.equals(trigs.get(2))) saveSpb(text.toString());
        else if (trigger.equals(preTrigs.get(0))) savePreDo(text.toString());
        else if (trigger.equals(preTrigs.get(1))) savePreSecDO(text.toString());

    }



    public void openQRDetail()
    {
        int status = 0;
        String msg = "";
        if (UtilFunc.isStringNotNull(LDtripDetail.getValue().getQrcode()))
        {
            status = 200;
            msg = LDtripDetail.getValue().getQrcode();
        }
        Msg msg1 = new Msg(status, msg);
        msgMutableLiveData.setValue(msg1);
    }
}
