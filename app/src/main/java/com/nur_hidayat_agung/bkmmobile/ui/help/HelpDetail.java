package com.nur_hidayat_agung.bkmmobile.ui.help;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.nur_hidayat_agung.bkmmobile.R;
import com.nur_hidayat_agung.bkmmobile.databinding.ActivityHelpDetailBinding;
import com.nur_hidayat_agung.bkmmobile.model.help.Help;
import com.nur_hidayat_agung.bkmmobile.util.Constant;

public class HelpDetail extends AppCompatActivity {

    private ActivityHelpDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        binding = DataBindingUtil.setContentView(this,R.layout.activity_help_detail);
        Help help = (Help) getIntent().getSerializableExtra(Constant.helpDet);
        if (help != null)
        {
            binding.setHelpDet(help);
        }
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
