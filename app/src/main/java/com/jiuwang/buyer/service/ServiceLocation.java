package com.jiuwang.buyer.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClientOption;
import com.jiuwang.buyer.util.LogUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;


/**
 * author：lihj desc： Created by lihj on 2017/12/20 16:49.
 */

public class ServiceLocation extends Service {


	public final static String TAG = "com.jiuwang.buyer.service.ServiceLocation";
	private Timer timer;
	private TimerTask task;
	public boolean flagLogin;

	public LocationService locationService;
	// 当前时间;// 当前时间
	WakeLock wakeLock = null;

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		init();
	}

	private void init() {
		// TODO Auto-generated method stub
		PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
		wakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
				"com.jiuwang.buyer.ServiceLocation");
		wakeLock.acquire();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		flags = START_STICKY;
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction("reStartGPS");
		startMyService();
		return flags;

	}

	int i = 0;



	@Override
	public IBinder onBind(Intent arg0) {
		// mPlayer.start();
		return new Binder();
	}

	@Override
	public void onDestroy() {


		wakeLock.release();// 需要在合适的地方释放
		// super.onDestroy();
		LogUtils.e(TAG, "ServiceLocation stop: " + System.currentTimeMillis());


	}


	@Override
	public boolean onUnbind(Intent intent) {
		// mPlayer.stop();
		locationService.stop();
		locationService.unregisterListener(mListener);



		return super.onUnbind(intent);
		// return true;
	}

	// 提交定位
	private void submitDingWei() {

	}

	/**
	 * 设置定位参数
	 */
	@SuppressWarnings("deprecation")
	private void setLocationOption() {
		// TODO Auto-generated method stub
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true); // 打开GPS
		option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);// 设置定位模式
		option.setCoorType("bd09ll"); // 返回的定位结果是百度经纬度,默认值gcj02
		option.setWifiCacheTimeOut(15000);
		option.setIsNeedAddress(true);// 可选，设置是否需要地址信息，默认不需要
		option.setIsNeedLocationDescribe(true); // 返回的定位结果包含地址信息
		option.setLocationNotify(true);
		option.setNeedDeviceDirect(true); // 返回的定位结果包含手机机头的方向
		option.setIsNeedAltitude(true);
		option.setOpenGps(true);
		option.disableCache(true);// 地图缓存
		option.setIgnoreKillProcess(true);
		option.setEnableSimulateGps(true);

		locationService.setLocationOption(option);
	}

	/*****
	 *
	 * 定位结果回调，重写onReceiveLocation方法，可以直接拷贝如下代码到自己工程中修改
	 *
	 */
	private BDAbstractLocationListener mListener = new BDAbstractLocationListener() {
		@SuppressWarnings("unused")
		@Override
		public void onReceiveLocation(BDLocation location) {
			// TODO Auto-generated method stub、
			SimpleDateFormat formatter = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
			String currentTime = formatter.format(curDate);
			LogUtils.e(TAG, location.getLongitude()+"-"+location.getLatitude()+"-"+location.getProvince()+"-"+location.getCity());
			try {

				if (null != location) {

				} else {
									}

				if (location != null) {

				} else {

				}
			} catch (Exception e) {
				// TODO: handle exception

			}
		}

		@SuppressWarnings("unused")
		@Override
		public void onLocDiagnosticMessage(int locType, int diagnosticType,
		                                   String diagnosticMessage) {

			LogUtils.e(TAG, diagnosticMessage);
		}

	};


	/**
	 * 执行服务中具体方法
	 */
	public void startMyService() {



		locationService = new LocationService(ServiceLocation.this, true);
		// 会将定位SDK的SERVICE设置成为前台服务，适配ANDROID 8后台无法定位问题，其他版本下也会提高定位进程存活率
		setLocationOption(); // 设置定位参数
		locationService.registerListener(mListener);
		locationService.start();

		new Handler(){
			@Override
			public void handleMessage(Message msg) {

			}
		}.sendEmptyMessageDelayed(0,30*1000);
		// Toast.makeText(MyApplication.getInstance(),
		// "开机自动服务自动启动-onStartCommand--One", Toast.LENGTH_SHORT).show();
		LogUtils.e(TAG, "onStartCommand--One");
	}

	public class Binder extends android.os.Binder implements ServiceInterface {

		public void startMyService() {

		}

		@Override
		public void setLocation() {
			// TODO Auto-generated method stub

		}
	}


}
