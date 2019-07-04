package com.jiuwang.buyer.parser;


import com.jiuwang.buyer.entity.MyCarLengthEntity;
import com.jiuwang.buyer.entity.MyCarTypeEntity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class CommonMainParser {

	public static boolean IsForNet(String result) {
		String msgcode = null;
		try {
			JSONObject jsonOBject = new JSONObject(result);
			msgcode = jsonOBject.getString("msgcode");
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (msgcode == null || msgcode.equals("0")) {
			return false;
		}
		return true;
	}

	/**
	 * 登录失败结果
	 */
	public static String getServierInfosParser(String result) {
		String msg = null;
		String results = null;
		try {
			JSONObject jsonOBject = new JSONObject(result);
			results = jsonOBject.getString("msgcode");
			msg = jsonOBject.getString("msg");
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (results == null) {
			return "访问失败";
		}
		if (results.equals("0") || results.equals("0") || results.equals("0")) {
			if (msg == null) {
				return "访问失败";
			} else if (msg.equals("null")) {
				return "访问失败";
			}
			return msg;
		}
		return msg;

	}

	public static ArrayList<MyCarLengthEntity> getCarLength(JSONArray array)
			throws JSONException {
		ArrayList<MyCarLengthEntity> carTypes = new ArrayList<MyCarLengthEntity>();
		for (int i = 0; i < array.length(); i++) {
			JSONObject object = array.getJSONObject(i);
			String aaa = object.getString("carLength");
			String bbb = object.getString("carLengthCode");
			MyCarLengthEntity carType1 = new MyCarLengthEntity(aaa, bbb);
			carTypes.add(carType1);
		}
		return carTypes;
	}
	public static ArrayList<MyCarTypeEntity> getCarType(JSONArray array)
			throws JSONException {
		ArrayList<MyCarTypeEntity> carTypes = new ArrayList<MyCarTypeEntity>();
		for (int i = 0; i < array.length(); i++) {
			JSONObject object = array.getJSONObject(i);
			String aaa = object.getString("carKind");
			String bbb = object.getString("carKindCode");
			MyCarTypeEntity carType1 = new MyCarTypeEntity(aaa, bbb);
			carTypes.add(carType1);
		}
		return carTypes;
	}
}
