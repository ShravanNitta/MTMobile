package com.matching.tech.bio.common;

import android.content.Context;
import android.content.SharedPreferences;
import com.matching.tech.bio.utilities.Constants;

public class UserSession {

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Context context;

    public UserSession(Context context){
        this.context = context;
        pref = context.getSharedPreferences(Constants.preferenceName, Constants.privateMode);
        editor = pref.edit();
    }
    public void set(String type,String value){
        editor.putString(type, value);
        editor.commit();
    }
    public String get(String type){
        return pref.getString(type, null);
    }

    public void clearSession(){
        editor.clear();
        editor.commit();
    }
}
