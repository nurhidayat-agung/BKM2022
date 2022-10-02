package com.nur_hidayat_agung.bkmmobile.ui.workshop;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nur_hidayat_agung.bkmmobile.R;
import com.nur_hidayat_agung.bkmmobile.adapter.HistoryWSAdapter;
import com.nur_hidayat_agung.bkmmobile.adapter.QueueWSAdapter;
import com.nur_hidayat_agung.bkmmobile.databinding.FragmentWorkShopHistoryBinding;
import com.nur_hidayat_agung.bkmmobile.util.SharedPref;
import com.nur_hidayat_agung.bkmmobile.viewmodel.workshop.WorkShopVM;

import java.util.ArrayList;
import java.util.Objects;


public class WorkShopHistoryFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private FragmentWorkShopHistoryBinding binding;
    private SharedPref sharedPref;
    private WorkShopVM vm;
    private HistoryWSAdapter adapter;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public WorkShopHistoryFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static WorkShopHistoryFragment newInstance(String param1, String param2) {
        WorkShopHistoryFragment fragment = new WorkShopHistoryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_work_shop_history, container, false);

        initView();
        initObserver();
        initRV();

        return binding.getRoot();
    }


    private void initRV() {
        adapter = new HistoryWSAdapter(new ArrayList<>());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity()
                , LinearLayoutManager.VERTICAL, false);

        binding.rvWsHistory.setLayoutManager(linearLayoutManager);
        binding.rvWsHistory.setAdapter(adapter);
    }

    private void initObserver() {
        vm.isLoading.observe(this, aBoolean -> {
            if (Boolean.TRUE.equals(aBoolean)) {
                binding.pbLoading.setVisibility(View.VISIBLE);
                binding.ivNoData.setVisibility(View.GONE);
                binding.rvWsHistory.setVisibility(View.GONE);
            } else {
                binding.pbLoading.setVisibility(View.GONE);
                binding.ivNoData.setVisibility(View.VISIBLE);
                binding.rvWsHistory.setVisibility(View.VISIBLE);
            }
        });

        vm.isAnyData.observe(this, aBoolean -> {
            if (Boolean.TRUE.equals(aBoolean))
            {
                binding.ivNoData.setVisibility(View.GONE);
                binding.rvWsHistory.setVisibility(View.VISIBLE);
                adapter.setListHistory(vm.itemHistoryWorkShops);
            }
            else
            {
                binding.ivNoData.setVisibility(View.VISIBLE);
                binding.rvWsHistory.setVisibility(View.GONE);
            }
        });
    }

    private void initView() {
        vm = ViewModelProviders.of(this).get(WorkShopVM.class);
        sharedPref = new SharedPref(Objects.requireNonNull(getActivity()));
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    private void initData() {
        vm.fectHistory();
    }
}