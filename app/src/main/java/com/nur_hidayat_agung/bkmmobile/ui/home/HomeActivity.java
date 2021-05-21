package com.nur_hidayat_agung.bkmmobile.ui.home;

import android.app.Dialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.nur_hidayat_agung.bkmmobile.R;
import com.nur_hidayat_agung.bkmmobile.databinding.ActivityHomeBinding;
import com.nur_hidayat_agung.bkmmobile.databinding.NavHeaderHomeBinding;
import com.nur_hidayat_agung.bkmmobile.databinding.PopUpLogOutBinding;
import com.nur_hidayat_agung.bkmmobile.model.login.UserDetailRes;
import com.nur_hidayat_agung.bkmmobile.ui.login.LoginActivity;
import com.nur_hidayat_agung.bkmmobile.ui.security.SecurityFragment;
import com.nur_hidayat_agung.bkmmobile.util.Constant;
import com.nur_hidayat_agung.bkmmobile.util.SharedPref;
import com.nur_hidayat_agung.bkmmobile.viewmodel.login.LoginVM;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ActivityHomeBinding binding;
    private HomeFragment homeFragment;
    private FragmentManager fragmentManager;
    private UserDetailRes userDetailRes = new UserDetailRes();
    private SharedPref sharedPref;
    private Dialog dialog;
    private PopUpLogOutBinding logOutBinding;
    private LoginVM vm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_home,null);
        sharedPref = new SharedPref(this);
        userDetailRes = sharedPref.getUserDetail();
        setSupportActionBar(binding.appBarHome.toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, binding.drawerLayout, binding.appBarHome.toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        binding.drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        binding.navView.setNavigationItemSelectedListener(this);
        // inisiate fragment
        homeFragment = new HomeFragment();
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(binding.appBarHome.contentHome.container.getId(), new HomeFragment()).commit();
        View headerView = binding.navView.getHeaderView(0);
        NavHeaderHomeBinding headerBinding = NavHeaderHomeBinding.bind(headerView);
        if (userDetailRes!= null) headerBinding.setUserdetail(userDetailRes);
        prepareDialogLogOut();
        vm = ViewModelProviders.of(this).get(LoginVM.class);
        vm.setContext(this);

        vm.isLogout.observe(this, aBoolean -> {
            if (aBoolean != null)
            {
                if (aBoolean)
                {
                    vm.isLogout.setValue(false);
                    sharedPref.setBool(Constant.spIsLogin,false);
                    startActivity(new Intent(this, LoginActivity.class));
                    this.finish();
                }
            }
        });
    }



    @Override
    public void onBackPressed() {
        //DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        // getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment = null;
        if (id == R.id.nav_change_pass) {
            fragment = new SecurityFragment();
        }
//        else if (id == R.id.nav_manage) {
//            fragment = new SettingFragment();
//        }
        else if (id == R.id.nav_exit) {
            showLogOutDialog();
        } else if (id == R.id.nav_home) {
            fragment = new HomeFragment();
        }

        if (fragment != null){
            fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(binding.appBarHome.contentHome.container.getId(), fragment).commit();
        }

//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        binding.drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void showLogOutDialog() {
        dialog.show();
    }

    private void prepareDialogLogOut() {
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        logOutBinding = PopUpLogOutBinding.inflate(LayoutInflater.from(this),null);
        dialog.setContentView(logOutBinding.getRoot());
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        Window window = dialog.getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.CENTER;
        int width = sharedPref.getInt(Constant.windowWidth);
        params.width = width;
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        logOutBinding.btnPopUpExit.setOnClickListener(v -> {
            finishAffinity();
        });
        logOutBinding.btnPopUpLogOut.setOnClickListener(v -> {
            vm.logout();
        });
    }


}
