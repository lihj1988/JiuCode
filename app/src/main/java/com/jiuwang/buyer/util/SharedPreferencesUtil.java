package com.jiuwang.buyer.util;

import android.content.Context;
import android.content.SharedPreferences;


public class SharedPreferencesUtil {
	private static SharedPreferencesUtil sharedPredUtils;
	private SharedPreferences mySharedPreferences;
	private SharedPreferences.Editor editor;
	public static SharedPreferencesUtil getInstanceSharedUtils() {
		if (sharedPredUtils == null) {
			sharedPredUtils = new SharedPreferencesUtil();
		}
		return sharedPredUtils;
	}
	public void saveLoginUser(String token, String userCode, String userId,String userName, String  password,
			Context context) {
		getSharedPreferences(context);
		editor = mySharedPreferences.edit();
		editor.putString("token", token);
		editor.putString("userCode", userCode);
		editor.putString("userId", userId);
		editor.putString("userName", userName);
		editor.putString("password", password);
		editor.commit();
	}
	public void getSharedPreferences(Context cons) {
		mySharedPreferences =cons.getSharedPreferences("shangang",
				cons.MODE_PRIVATE);
	}



	
	
}
