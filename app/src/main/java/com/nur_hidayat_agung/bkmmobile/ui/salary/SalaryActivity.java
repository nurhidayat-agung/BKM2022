package com.nur_hidayat_agung.bkmmobile.ui.salary;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.nur_hidayat_agung.bkmmobile.R;
import com.nur_hidayat_agung.bkmmobile.databinding.ActivitySalaryBinding;
import com.nur_hidayat_agung.bkmmobile.model.general.GeneralResponse;
import com.nur_hidayat_agung.bkmmobile.model.payslip.PaySlip;
import com.nur_hidayat_agung.bkmmobile.util.Constant;
import com.nur_hidayat_agung.bkmmobile.util.PDialog;
import com.nur_hidayat_agung.bkmmobile.util.PopUpDialog;
import com.nur_hidayat_agung.bkmmobile.util.UtilFunc;
import com.nur_hidayat_agung.bkmmobile.viewmodel.salary.SalaryVM;
import com.whiteelephant.monthpicker.MonthPickerDialog;

import java.util.Calendar;

public class SalaryActivity extends AppCompatActivity {

    private ActivitySalaryBinding salaryBinding;
    private int selecetdMonth = 0, selectedYear = 0;
    private PDialog pDialog;
    private SalaryVM salaryVM;
    private Calendar today;
    private PopUpDialog popUpDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
        initObserver();
        unhandleAction();
    }

    private void initView() {
        salaryBinding = DataBindingUtil.setContentView(this,R.layout.activity_salary);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        today = Calendar.getInstance();
        selecetdMonth = today.get(Calendar.MONTH);
        selectedYear = today.get(Calendar.YEAR);
        if (selecetdMonth == 0)
        {
            salaryBinding.tvMonthYearSalary.setText(Constant.monthsName.get((11)) + " " + (selectedYear - 1));
        }
        else {
            salaryBinding.tvMonthYearSalary.setText(Constant.monthsName.get((selecetdMonth - 1)) + " " + selectedYear);
        }

    }

    private void initData() {
        pDialog = new PDialog(this);
        salaryVM = ViewModelProviders.of(this).get(SalaryVM.class);
        popUpDialog = new PopUpDialog(this, new GeneralResponse());

        if (selecetdMonth == 0)
        {
            salaryVM.fecthPaySlip(12,(selectedYear - 1));
        }else
        {
            salaryVM.fecthPaySlip((selecetdMonth),selectedYear);
        }

    }

    private void initObserver() {
        salaryVM.liveDataPdialog.observe(this,aBoolean -> {if (aBoolean != null) pDialog.setDialog(aBoolean);});
        salaryVM.slipMutableLiveData.observe(this,paySlip -> {

            // paySlip.adjustment = "-100000";
            if (paySlip != null)
            {
                paySlip.isMinus = View.GONE;
                paySlip.isPlus = View.GONE;

                if (UtilFunc.isStringNotNull(paySlip.adjustment))
                {
                    if (UtilFunc.isNumeric((paySlip.adjustment)))
                    {
                        double adjustment = Double.parseDouble(paySlip.adjustment);



                        if (adjustment > 0)
                        {
                            paySlip.isPlus = View.VISIBLE;
                            paySlip.isMinus = View.GONE;
                        }
                        if (adjustment < 0)
                        {
                            paySlip.isMinus = View.VISIBLE;
                            paySlip.isPlus = View.GONE;
                        }
                    }
                }

                salaryBinding.setPayslip(paySlip);
            }
        });

        salaryVM.msgLiveData.observe(this,s -> {
            if (UtilFunc.isStringNotNull(s))
            {
                PaySlip paySlip1 = new PaySlip();
                salaryBinding.setPayslip(paySlip1);
                popUpDialog.showMsg(s);
            }
        });

    }

    private void unhandleAction() {
        salaryBinding.btnSelectMonth.setOnClickListener(v -> {

            MonthPickerDialog.Builder builder = new MonthPickerDialog.Builder(SalaryActivity.this, (_selectedMonth, _selectedYear) -> {
                Log.d("MonthPicker", "selectedMonth : " + _selectedMonth + " selectedYear : " + _selectedYear);
                //Toast.makeText(SalaryActivity.this, "Date set with month" + _selectedMonth + " year " + _selectedYear, Toast.LENGTH_SHORT).show();
                selecetdMonth = _selectedMonth;
                selectedYear = _selectedYear;
                salaryBinding.tvMonthYearSalary.setText(Constant.monthsName.get(selecetdMonth) + " " + selectedYear);
                salaryVM.fecthPaySlip((selecetdMonth+1),selectedYear);
            }, today.get(Calendar.YEAR), today.get(Calendar.MONTH));

            builder.setActivatedMonth(selecetdMonth)
                .setMinYear(1990)
                .setActivatedYear(selectedYear)
                .setMaxYear(2030)
                .setMinMonth(Calendar.JANUARY)
                .setTitle("Select payslip month")
                .setMonthRange(Calendar.JANUARY, Calendar.DECEMBER)
                .setMaxMonth(Calendar.DECEMBER)
                // .setYearRange(1890, 1890)
                // .setMonthAndYearRange(Calendar.FEBRUARY, Calendar.OCTOBER, 1890, 1890)
                //.showMonthOnly()
                // .showYearOnly()
                .setOnMonthChangedListener(selectedMonth -> {
                    Log.d("MonthPicker", "Selected month : " + selectedMonth);
                    // Toast.makeText(MainActivity.this, " Selected month : " + selectedMonth, Toast.LENGTH_SHORT).show();

                })
                .setOnYearChangedListener(selectedYear -> {
                    Log.d("MonthPicker", "Selected year : " + selectedYear);
                    // Toast.makeText(MainActivity.this, " Selected year : " + selectedYear, Toast.LENGTH_SHORT).show();
                })
                .build()
                .show();
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        this.finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
