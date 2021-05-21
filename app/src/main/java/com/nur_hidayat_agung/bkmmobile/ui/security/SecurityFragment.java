package com.nur_hidayat_agung.bkmmobile.ui.security;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nur_hidayat_agung.bkmmobile.R;
import com.nur_hidayat_agung.bkmmobile.databinding.FragmentSecurityBinding;
import com.nur_hidayat_agung.bkmmobile.util.PDialog;
import com.nur_hidayat_agung.bkmmobile.util.SharedPref;
import com.nur_hidayat_agung.bkmmobile.viewmodel.security.ChangePassVM;

public class SecurityFragment extends Fragment {

    private FragmentSecurityBinding binding;
    private PDialog pDialog;
    private ChangePassVM changePassVM;
    private SharedPref sharedPref;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initView(inflater,container);
        initData();
        initObserver();
        return binding.getRoot();
    }

    private void initView(LayoutInflater inflater, ViewGroup container) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_security, container, false);
    }

    private void initData() {
        changePassVM = ViewModelProviders.of(this).get(ChangePassVM.class);
        changePassVM.setContext(getContext());
        binding.setVm(changePassVM);
        pDialog = new PDialog(getActivity());
        sharedPref = new SharedPref(getActivity());
    }

    private void initObserver() {
        changePassVM.pDialog.observe(this,aBoolean -> { if (aBoolean != null) pDialog.setDialog(aBoolean); });
        changePassVM.updatePass.observe(this, aBoolean -> {
            if (aBoolean){
                changePassVM.updatePass.setValue(false);
                binding.setVm(changePassVM);
            }
        });
    }




}
