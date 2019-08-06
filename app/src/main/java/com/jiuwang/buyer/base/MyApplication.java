package com.jiuwang.buyer.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.webkit.WebView;

import com.jiuwang.buyer.activity.LoginActivity;
import com.jiuwang.buyer.constant.Constant;
import com.jiuwang.buyer.util.ToastUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.log.LoggerInterceptor;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;

public class MyApplication extends Application {

	private static MyApplication myApplication;
	public WebView webView;
	public String number;
	private Context context;

	public List<Activity> activitys = null;
	public Activity currActivity = null;
	public String url_langde = "apk/shop.apk";//下载地址
	public String serverVersion;//版本号

	public static final int CODE_REGISTER = 5;

	public static final int CODE_CHOOSE_AREA = 10;
	public static final int CODE_CHOOSE_PHOTO = 11;
	public static final int CODE_CHOOSE_CAMERA = 12;
	public static final int CODE_CHOOSE_ADDRESS = 13;

	public static final int CODE_ADDRESS_ADD = 20;
	public static final int CODE_ADDRESS_EDIT = 21;

	public static Handler handler;
	public static Activity currentActivity;//当前屏幕上正在显示的Activity
	private boolean userLoginBoolean;
	private boolean userLoginSuccessBoolean;

	//公用变量
	public ArrayList<HashMap<String, String>>[] orderArrayList; //订单数组
	public MyApplication() {
		activitys = new LinkedList();
	}

	@Override
	public void onCreate() {
		super.onCreate();
		myApplication = this;
		handler = new Handler();
		//公用变量
		orderArrayList = new ArrayList[6];
		for (int i = 0; i < orderArrayList.length; i++) {
			orderArrayList[i] = new ArrayList<>();
		}
		getLocalVersion();
		OkHttpClient okHttpClient = new OkHttpClient.Builder()
				.addInterceptor(new LoggerInterceptor("jiuwangLog"))
				.cookieJar(new CookieJar() {
					private final HashMap<String, List<Cookie>> cookieStore = new HashMap<>();

					@Override
					public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
						cookieStore.put(url.host(), cookies);
					}

					@Override
					public List<Cookie> loadForRequest(HttpUrl url) {
						List<Cookie> cookies = cookieStore.get(url.host());
						return cookies != null ? cookies : new ArrayList<Cookie>();
					}
				})
				.connectTimeout(10000L, TimeUnit.MILLISECONDS)
				.readTimeout(10000L, TimeUnit.MILLISECONDS).build();
		OkHttpUtils.initClient(okHttpClient);
//		regToQq();
//		regToWx();
//		JPushInterface.setDebugMode(true);
//		JPushInterface.init(this);
	}

	/**
	 * 单例模式中获取唯一的MyApplication实例
	 *
	 * @return
	 */
	public synchronized static MyApplication getInstance() {
		return myApplication;
	}


	// 遍历所有Activity并finish
	public void exit() {
		if (activitys != null && activitys.size() > 0) {
			for (Activity activity : activitys) {
				activity.finish();
				activitys.size();
			}
		}
	}
	public void getLocalVersion() {
		try {
			PackageInfo packageInfo = getApplicationContext()
					.getPackageManager().getPackageInfo(getPackageName(), 0);
			Constant.localVersion = packageInfo.versionName;
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 返回当前程序版本号
	 */
	public static String getAppVersionNCode(Context context) {
		String versionName = "";
		int versioncode = 0;
		try {
			PackageManager pm = context.getPackageManager();
			PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
			versionName = pi.versionName;//获得软件版本名字
//            versioncode=pi.versionCode;//获得软件版本号
			if (versionName == null || versionName.length() <= 0) {
				return "";
			}
		} catch (Exception e) {

		}
		return versionName + "";
	}

	//微信分享 暂时没有用到
	private void regToWx() {
//		api = WXAPIFactory.createWXAPI(this, NetURL.APP_ID_WEIXIN, true);
//		api.registerApp(NetURL.APP_ID_WEIXIN);
	}

	//QQ分享  暂时没有用到
	private void regToQq() {
//		mTencent = Tencent.createInstance(NetURL.APP_ID_QQ,
//				this.getApplicationContext());
	}

	/*
    * 作用：开始一个 Activity
    * 更新：2019.07.01
    */
	public void startActivity(Activity activity, Intent intent) {

		activity.startActivity(intent);

	}
	/*
    * 作用：开始一个 Activity 并带返回参数
    * 更新：2019.07.01
    */
	public void startActivity(Activity activity, Intent intent, int i) {

		activity.startActivityForResult(intent, i);

	}
	/*
   * 作用：结束一个 Activity
   * 更新：2019.07.01
   */
	public void finishActivity(Activity activity) {

		activity.finish();

	}
	/*
   * 作用：开始一个 Activity 并检测是否已登录成功
   * 更新：2019.07.01
   */
	public void startActivityLoginSuccess(Activity activity, Intent intent) {

		if (userLoginBoolean) {
			if (userLoginSuccessBoolean) {
				activity.startActivity(intent);
			} else {
				ToastUtil.show(activity, "请等待登录成功");
			}
		} else {
			activity.startActivity(new Intent(activity, LoginActivity.class));
		}

	}

	/*
    * 作用：跳到拍照
    * 更新：2019.07.01
    */
	public void startCamera(Activity activity, File file) {

		try {
			Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
			activity.startActivityForResult(intent, CODE_CHOOSE_CAMERA);
		} catch (Exception e) {
			ToastUtil.show(activity, "未检测到相机");
		}

	}

	/*
   * 作用：跳到相册
   * 更新：2019.07.01
   */
	public void startPhoto(Activity activity) {

		Intent intent = new Intent();
		intent.setType("image/*");
		intent.setAction("android.intent.action.GET_CONTENT");
		activity.startActivityForResult(intent, CODE_CHOOSE_PHOTO);

	}



}
