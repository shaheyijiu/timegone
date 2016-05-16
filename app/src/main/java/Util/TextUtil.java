package Util;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2016/5/16.
 */
public class TextUtil{
    //RegisterActiviter 保存了"hxid"和"password
    /*
     *获取默认的SharedPreferences
     */
    private static SharedPreferences getSharedPreference(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static void putIntValue(Context context,String key,int value){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putInt(key,value);
        editor.commit();
    }
    public static void putStringValue(Context context,String key,String value){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(key, value);
        editor.commit();
    }
    public static String getStringValue(Context context,String key){
        return getSharedPreference(context).getString(key, "");
    }
    public static int getIntValue(Context context,String key){
        return getSharedPreference(context).getInt(key, 0);
    }
    public static  void putBooleanValue(Context context,String key,Boolean state){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putBoolean(key, state);
        editor.commit();
    }
    public static Boolean getBooleanValue(Context context, String key) {
        return getSharedPreference(context).getBoolean(key, false);
    }

}