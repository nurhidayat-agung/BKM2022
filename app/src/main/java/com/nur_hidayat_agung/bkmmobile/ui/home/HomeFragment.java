package com.nur_hidayat_agung.bkmmobile.ui.home;

import android.app.Dialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.SnapHelper;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.nur_hidayat_agung.bkmmobile.R;
import com.nur_hidayat_agung.bkmmobile.adapter.AnnouncementAdapter;
import com.nur_hidayat_agung.bkmmobile.adapter.MenuAdapter;
import com.nur_hidayat_agung.bkmmobile.callback.ListMenuCallback;
import com.nur_hidayat_agung.bkmmobile.databinding.DialogWsCardBinding;
import com.nur_hidayat_agung.bkmmobile.databinding.FragmentHomeBinding;
import com.nur_hidayat_agung.bkmmobile.model.general.GeneralResponse;
import com.nur_hidayat_agung.bkmmobile.model.login.UserDetailRes;
import com.nur_hidayat_agung.bkmmobile.ui.help.HelpActivity;
import com.nur_hidayat_agung.bkmmobile.ui.history.HistoryActivity;
import com.nur_hidayat_agung.bkmmobile.ui.salary.SalaryActivity;
import com.nur_hidayat_agung.bkmmobile.ui.service.PartServiceActivity;
import com.nur_hidayat_agung.bkmmobile.ui.service.ServiceAlertActivity;
import com.nur_hidayat_agung.bkmmobile.ui.service.ServiceBookActivity;
import com.nur_hidayat_agung.bkmmobile.ui.trip.TripActivity;
import com.nur_hidayat_agung.bkmmobile.ui.workshop.WorkShopActivity;
import com.nur_hidayat_agung.bkmmobile.util.Constant;
import com.nur_hidayat_agung.bkmmobile.util.PDialog;
import com.nur_hidayat_agung.bkmmobile.util.PopUpDialog;
import com.nur_hidayat_agung.bkmmobile.util.SharedPref;
import com.nur_hidayat_agung.bkmmobile.util.UtilFunc;
import com.nur_hidayat_agung.bkmmobile.viewmodel.login.LoginVM;
import com.nur_hidayat_agung.bkmmobile.viewmodel.service.ServiceVM;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class HomeFragment extends Fragment {

    private static final int DIALOG_QUEST_CODE = 121;
    private FragmentHomeBinding binding;
    private SharedPref sharedPref;
    private UserDetailRes userDetailRes;
    private MenuAdapter menuAdapter;
    private AnnouncementAdapter announcementAdapter;
    private List<String> anns = new ArrayList<>();
    private PopUpDialog popUpDialog;
    private LoginVM vm;
    private ServiceVM serviceVM;
    private Dialog dialog;
    private DialogWsCardBinding wsCardBinding;
    private PDialog pDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        binding.swipeContainer.setOnRefreshListener(() -> {
            // Toast.makeText(getActivity(),"refresh ",Toast.LENGTH_SHORT).show();
            vm.getUserDetail(userDetailRes.firebase_token);
        });

        binding.ivShowServiceCard.setOnClickListener(v -> {
            // showDialogFullscreen();
            // showCustomDialog();
            showServiceCard();
        });

        initData();
        initObserver();
        setRV();
        Log.i("homeLogBKM", "json string from home activity: " + sharedPref.getString(Constant.userDetail));
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        vm.getUserDetail(userDetailRes.firebase_token);
        vm.getWorkShopQueue();
        setRV();

        initMenu();
        //Toast.makeText(getActivity(),"restart",Toast.LENGTH_SHORT).show();
    }

    private void initMenu() {
        if (sharedPref.getUserMenu() != null)
        {
            if (sharedPref.getUserMenu().data != null)
            {
                menuAdapter.setItemMenuList(sharedPref.getUserMenu().data);
            }
        }


        vm.getMenu();


    }

    @Override
    public void onStart() {
        super.onStart();
        serviceVM.getServiceAlert();
    }

    private void initData() {
        sharedPref = new SharedPref(getActivity());
        userDetailRes = sharedPref.getUserDetail();

        pDialog = new PDialog(getActivity());
        if (userDetailRes != null) {
            binding.setUserDetail(userDetailRes);
            for (int a = 0; a < userDetailRes.announcement.size(); a++) {
                anns.add(userDetailRes.announcement.get(a).announcement);
            }
        }

        if (userDetailRes == null) userDetailRes = new UserDetailRes();
        if (userDetailRes.announcement == null) userDetailRes.announcement = new ArrayList<>();
        popUpDialog = new PopUpDialog(getActivity(), new GeneralResponse());
        vm = ViewModelProviders.of(this).get(LoginVM.class);
        serviceVM = ViewModelProviders.of(this).get(ServiceVM.class);

        serviceVM.getReasonWorkShop();
    }

    private void initObserver() {
        vm.userDetailResMutableLiveData.observe(this, userDetailRes1 -> {
            binding.swipeContainer.setRefreshing(false);
            if (userDetailRes1 != null) {
                announcementAdapter.setAnn(userDetailRes1.announcement);
            }
        });

        serviceVM.isServiceAlert.observe(this, aBoolean -> {
            if (aBoolean) {
                serviceVM.isServiceAlert.setValue(false);
//                Gson gson = new Gson();
//                Intent intent = new Intent(getActivity(),ServiceAlertActivity.class);
//                intent.putExtra(Constant.serviceActive,gson.toJson(serviceVM.serviceActive));
//                startActivity(intent);
                showDialogDark();
            }
        });

        serviceVM.isSaveSuccess.observe(getActivity(), aBoolean -> {
            if (aBoolean) {
                serviceVM.isSaveSuccess.setValue(false);
                if (dialog != null) {
                    if (dialog.isShowing()) {
                        dialog.dismiss();
                        Toast.makeText(getActivity(), "simpan data berhasil", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        vm.isQueue.observe(getActivity(), aBoolean -> {
            if (aBoolean) {
                binding.llQueue.setVisibility(View.VISIBLE);
                binding.tvQueueStatus.setText(vm.respQueue.data.message);
            } else {
                binding.llQueue.setVisibility(View.GONE);
                binding.tvQueueStatus.setText("");
            }
        });

        vm.pDialog.observe(getActivity(), aBoolean -> {
            pDialog.setDialog(aBoolean);
        });

        vm.ldMenuItem.observe(getActivity(), dataItemMenus -> {
            menuAdapter.setItemMenuList(dataItemMenus);
        });
    }


    private void setRV() {
        userDetailRes = sharedPref.getUserDetail();
        menuAdapter = new MenuAdapter(getActivity(), this.menuCallback, userDetailRes.getNumber_of_trip(), new ArrayList<>());
        binding.rvMenu.addItemDecoration(new DividerItemDecoration(Objects.requireNonNull(getActivity()),
                DividerItemDecoration.HORIZONTAL));
        binding.rvMenu.addItemDecoration(new DividerItemDecoration(Objects.requireNonNull(getActivity()),
                DividerItemDecoration.VERTICAL));
        binding.rvMenu.setAdapter(menuAdapter);

        announcementAdapter = new AnnouncementAdapter(getActivity(), userDetailRes.announcement);
        binding.rvAnn.addItemDecoration(new DividerItemDecoration(getActivity()
                , LinearLayoutManager.HORIZONTAL));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity()
                , LinearLayoutManager.HORIZONTAL, false);

        binding.rvAnn.setLayoutManager(linearLayoutManager);
        binding.rvAnn.setAdapter(announcementAdapter);
        SnapHelper snapHelper = new PagerSnapHelper();
        binding.rvAnn.setOnFlingListener(null);
        snapHelper.attachToRecyclerView(binding.rvAnn);
    }

    public final ListMenuCallback menuCallback = itemMenu -> {
        //Toast.makeText(getActivity(),"keyword : " + itemMenu.keyWord, Toast.LENGTH_SHORT).show();
        if (itemMenu.className.equals(Constant.menuApiTrip))
            startActivity(new Intent(getActivity(), TripActivity.class));
        if (itemMenu.className.equals(Constant.menuApiHistory))
            startActivity(new Intent(getActivity(), HistoryActivity.class));
        if (itemMenu.className.equals(Constant.menuApiHelp))
            startActivity(new Intent(getActivity(), HelpActivity.class));
        if (itemMenu.className.equals(Constant.menuApiSalary))
            startActivity(new Intent(getActivity(), SalaryActivity.class));
        if (itemMenu.className.equals(Constant.menuApiPart))
            startActivity(new Intent(getActivity(), PartServiceActivity.class));
        if (itemMenu.className.equals(Constant.menuApiService))
            startActivity(new Intent(getActivity(), ServiceBookActivity.class));
        if (itemMenu.className.equals(Constant.menuApiWorkShop))
            startActivity(new Intent(getActivity(), WorkShopActivity.class));
    };

    private void showDialogDark() {
        dialog = new Dialog(Objects.requireNonNull(getActivity()));
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.dialog_contact_dark);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(false);
        AppCompatEditText tglJadwalServis = dialog.findViewById(R.id.edt_jadwal_service);
        AppCompatEditText tglServis = dialog.findViewById(R.id.edt_tgl_service);
        TextView tvTopMessage = dialog.findViewById(R.id.tv_top_message);
        String title = serviceVM.serviceActive.top_message;
        tvTopMessage.setText(title);
        // tglJadwalServis.setText(UtilFunc.reformatStringDate(serviceVM.serviceActive.serviceDate.toString(), Constant.dateFormat, Constant.dateFormatUI));
        tglServis.setText(UtilFunc.getDate(new Date()));
        tglServis.setOnClickListener(view -> {
            Dialog dialogDP = new Dialog(Objects.requireNonNull(getActivity()));
            dialogDP.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
            dialogDP.setContentView(R.layout.dialog_date_picker);
            dialogDP.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            dialogDP.setCancelable(true);
            dialogDP.setCanceledOnTouchOutside(false);

            TextView tvOK = dialogDP.findViewById(R.id.btn_save_month);
            TextView tvCancel = dialogDP.findViewById(R.id.btn_cancel_month);
            DatePicker dpService = dialogDP.findViewById(R.id.dp_service);
            Date date = UtilFunc.getDateFromString(tglServis.getText().toString(), Constant.dateFormat);

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);

            int curYear = calendar.get(Calendar.YEAR);
            int curMonth = calendar.get(Calendar.MONTH);
            int curDay = calendar.get(Calendar.DATE);
            dpService.updateDate(curYear, curMonth, curDay);

            tvCancel.setOnClickListener(view1 -> {
                dialogDP.dismiss();
            });

            tvOK.setOnClickListener(view12 -> {
                int day = dpService.getDayOfMonth();
                int month = dpService.getMonth();
                int year = dpService.getYear();
                Calendar c = Calendar.getInstance();
                c.set(year, month, day, 0, 0);
                tglServis.setText(UtilFunc.getDate(c.getTime()));
                dialogDP.dismiss();
            });

            dialogDP.show();
        });
        (dialog.findViewById(R.id.btn_remind_me)).setOnClickListener(v -> {
            // dialog.dismiss();
            serviceVM.remindLater(serviceVM.serviceActive.vehicle_id);
        });
        (dialog.findViewById(R.id.btn_save_service)).setOnClickListener(v -> {
            AppCompatEditText editText = dialog.findViewById(R.id.edt_service_desc);
            showConfirmDialog(serviceVM.serviceActive.vehicle_id, editText.getText().toString(), tglServis.getText().toString());
        });

        dialog.show();
    }

    private void showConfirmDialog(String idService, String desc, String serviceDate) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Simpan Data Service");
        builder.setMessage("Apakah anda sudah benar melakukan service reguler terhadap kendaraan" +
                " anda, tekan tombol \"OK\" jika benar anda sudah melakukan service.");
        builder.setPositiveButton("OK", (dialogInterface, i) -> {
            serviceVM.saveService(idService, desc, serviceDate);
        });
        builder.setNegativeButton("Tidak", null);
        builder.show();
    }

    private void showCustomDialog() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.dialog_ws_card);
        dialog.setCancelable(true);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;


        ((AppCompatButton) dialog.findViewById(R.id.btn_close_service_card)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), ((AppCompatButton) v).getText().toString() + " Clicked", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }

    private void showDialogFullscreen() {
        FragmentManager fragmentManager = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
        DialogFullscreenFragment newFragment = new DialogFullscreenFragment();
        newFragment.setRequestCode(DIALOG_QUEST_CODE);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.add(android.R.id.content, newFragment).addToBackStack(null).commit();
        newFragment.setOnCallbackResult((requestCode, obj) -> {
            if (requestCode == DIALOG_QUEST_CODE) {
                displayDataResult(new Object());
            }
        });
    }

    private void displayDataResult(Object o) {

    }

    private void showServiceCard() {
        dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        wsCardBinding = DialogWsCardBinding.inflate(LayoutInflater.from(getActivity()), null);
        wsCardBinding.setQueue(vm.respQueue.data);
        dialog.setContentView(wsCardBinding.getRoot());
        wsCardBinding.executePendingBindings();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        Window window = dialog.getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.CENTER;
        double width = sharedPref.getInt(Constant.windowWidth) * 0.95;
        params.width = (int) width;
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        wsCardBinding.btnCloseServiceCard.setOnClickListener(v -> {
            dialog.dismiss();
        });
        wsCardBinding.btnCancelWorkshop.setOnClickListener(v -> {
            dialog.dismiss();
            showConfirmDialogCancelRegis();
        });
        dialog.show();
    }

    private void showConfirmDialogCancelRegis() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Batalkan Pendaftaran Service");
        builder.setMessage("Apakah anda yakin akan membatalkan pendaftaran perbaikan" +
                ", tekan tombol \"OK\" jika benar");
        builder.setPositiveButton("OK", (dialogInterface, i) -> {
            vm.cancelWSRegistration();
        });
        builder.setNegativeButton("Tidak", null);
        builder.show();
    }
}
