package com.jiuwang.buyer.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.jiuwang.buyer.base.MyApplication;


/**
 * Created on 2017/9/26.
 * 本地数据存储的工具类 采用单例模式
 */
public class PreforenceUtils {

    public static SharedPreferences sharedPreferences;

    private PreforenceUtils() {
    }

    public static void getSharedPreferences(String fileName) {

        sharedPreferences = MyApplication.getInstance().getSharedPreferences(fileName, Context.MODE_PRIVATE);

    }

    //存储字符串
    public static boolean setData(String key, String value) {

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        return editor.commit();
    }

    //存储整数
    public static boolean setData(String key, int value) {

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        return editor.commit();

    }

    //存储布尔值
    public static boolean setData(String key, boolean value) {

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        return editor.commit();

    }


    //获取登录状态
    public static boolean getchecklogin() {
        return getBoolean(MyApplication.getInstance(), "islogin", false);
    }

    public static void setchecklogin(boolean id) {
        putBoolean(MyApplication.getInstance(), "islogin", id);
    }


    //获取字符串数据
    public static String getStringData(String fileName, String key) {
        sharedPreferences = MyApplication.getInstance().getSharedPreferences(fileName, Context.MODE_PRIVATE);
        String value = sharedPreferences.getString(key, "");
        return value;
    }

    //获取整数数据
    public static int getIntData(String fileName, String key) {

        sharedPreferences = MyApplication.getInstance().getSharedPreferences(fileName, Context.MODE_PRIVATE);
        int value = sharedPreferences.getInt(key, 0);
        return value;
    }

    //获取布尔数据
    public static boolean getBooleanData(String fileName, String key) {

        sharedPreferences = MyApplication.getInstance().getSharedPreferences(fileName, Context.MODE_PRIVATE);
        boolean value = sharedPreferences.getBoolean(key, false);
        return value;

    }
    public static String PREFERENCE_NAME = "SpUtil";
    //获取消息状态
    public static boolean getcheckOff() {
        return getBoolean(MyApplication.getInstance(), "checkOff", true);
    }

    public static void setcheckOff(boolean id) {
        putBoolean(MyApplication.getInstance(), "checkOff", id);
    }
    public static boolean getBoolean(Context context, String key, boolean defaultValue) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        return settings.getBoolean(key, defaultValue);
    }
    public static boolean putBoolean(Context context, String key, boolean value) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(key, value);
        return editor.commit();
    }

}
