package com.nur_hidayat_agung.bkmmobile.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.nur_hidayat_agung.bkmmobile.model.login.UserDetailRes;

public class SharedPref {

    private SharedPreferences sp;
    private SharedPreferences.Editor spe;

    public SharedPref(Context context) {
        sp = context.getSharedPreferences(Constant.appName,Context.MODE_PRIVATE);
        spe = sp.edit();
    }

    public boolean setUserID(String user_id)
    {
        spe.putString("mUser_id", user_id);
        return spe.commit();
    }
    public String getUserId()
    {
        return sp.getString("mUser_id", Constant.user_id);
    }

    public boolean setAuth(String auth)
    {
        spe.putString("mAuth", auth);
        return spe.commit();
    }
    public String getAuth()
    {
        return sp.getString("mAuth", Constant.Token);
    }

    public boolean getBool(String key)
    {
        return sp.getBoolean(key,false);
    }

    public boolean setBool(String key, boolean value)
    {
        spe.putBoolean(key, value);
        return spe.commit();
    }

    public String getString(String key)
    {
        return sp.getString(key,"");
    }

    public boolean setString(String key, String value)
    {
        spe.putString(key,value);
        return spe.commit();
    }

    public Integer getInt(String key)
    {
        return sp.getInt(key,0);
    }

    public boolean setInt(String key, int value)
    {
        spe.putInt(key,value);
        return spe.commit();
    }

    public float getDouble(String key)
    {
        return sp.getFloat(key,0);
    }

    public boolean setFloat(String key, float value)
    {
        spe.putFloat(key,value);
        return spe.commit();
    }

    public boolean setUserDetail(UserDetailRes userDetailRes)
    {
        Gson gson = new Gson();
        String json = gson.toJson(userDetailRes);
        Log.i("sharedPrefLogBKM", "toJson : " + json);
        spe.putString(Constant.userDetail,json);
        return spe.commit();
    }

    public UserDetailRes getUserDetail()
    {
        String res = sp.getString(Constant.userDetail,"");
        Log.i("sharedPrefLogBKM", "fromJson : " + res);
        if (!UtilFunc.isStringNotNull(res))
        {
            Log.i("sharedPrefLogBKM", "fromJson dari dlm null");
            return null;
        }
        Gson gson = new Gson();
        UserDetailRes userDetailRes = gson.fromJson(res, UserDetailRes.class);
        if (userDetailRes == null) Log.i("sharedPrefLogBKM", "toObj : Convert result is null");
        Log.i("sharedPrefLogBKM", "toObj : " + userDetailRes.getUser_id());
        return userDetailRes;
    }
}
