package com.jiuwang.buyer.activity;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.KeyEvent;
import android.widget.LinearLayout;
import android.widget.Toast;


import com.jiuwang.buyer.R;
import com.jiuwang.buyer.base.BaseActivity;
import com.jiuwang.buyer.base.MyApplication;
import com.jiuwang.buyer.constant.Constant;
import com.jiuwang.buyer.entity.BaseResultEntity;
import com.jiuwang.buyer.fragment.GoodsFragment;
import com.jiuwang.buyer.fragment.HomeFragment;
import com.jiuwang.buyer.fragment.MineFragment;
import com.jiuwang.buyer.fragment.ProjectFragment;
import com.jiuwang.buyer.net.CommonHttpUtils;
import com.jiuwang.buyer.receiver.NotificationReceiver;
import com.jiuwang.buyer.service.UpdateService;
import com.jiuwang.buyer.spinerwidget.MyTabWidget;
import com.jiuwang.buyer.util.AppUtils;
import com.jiuwang.buyer.util.CommonUtil;
import com.jiuwang.buyer.util.ConstantValues;
import com.jiuwang.buyer.util.LoadingDialog;
import com.jiuwang.buyer.util.MyToastView;
import com.jiuwang.buyer.util.PermissionsUtils;

import java.util.HashMap;


public class MainActivity extends BaseActivity implements MyTabWidget.OnTabSelectedListener {


	private static final String TAG = MainActivity.class.getName();
	private FragmentManager mFragmentManager;
	private MyTabWidget tbBottom;
	private GoodsFragment goodsFragment;
	private ProjectFragment projectFragment;
	//	private CarFragment carFragment;
	private MineFragment mineFragment;
	private int backIndex;
	private MyReceiver myReceiver;
	private String invite;
	private NotificationReceiver notificationReceiver;
	private HomeFragment homeFragment;
	private final int SDK_PERMISSION_REQUEST = 127;
	private String permissionInfo;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Intent intent = getIntent();
		invite = intent.getStringExtra("from");
		backIndex = this.getIntent().getIntExtra("index", 0);
		MyApplication.currentActivity = this;
		init();
		myReceiver = new MyReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction("first");
		registerReceiver(myReceiver, filter);
		notificationReceiver = new NotificationReceiver();
		IntentFilter filterNotification = new IntentFilter();
		filterNotification.addAction("com.jiuwang.buyer.receiver.NotificationReceiver");
		registerReceiver(notificationReceiver, filterNotification);
		if (Constant.IS_LOGIN) {
			if (invite != null && !invite.equals("")) {
				bindInvite();
			}
			AppUtils.getSystemVersion(MainActivity.this, permissionsResult, "1");
		}

//		getPersimmions();
//		initLocation();
	}

	@Override
	protected void onStart() {
		super.onStart();



	}

//	private void initLocation() {
//		locationService = MyApplication.getInstance().locationService;
//		//获取locationservice实例，建议应用中只初始化1个location实例，然后使用，可以参考其他示例的activity，都是通过此种方式获取locationservice实例的
//		locationService.registerListener(mListener);
//		//注册监听
//		int type = getIntent().getIntExtra("from", 0);
//		if (type == 0) {
//			locationService.setLocationOption(locationService.getDefaultLocationClientOption());
//		} else if (type == 1) {
//			locationService.setLocationOption(locationService.getOption());
//		}
//
//		locationService.start();// 定位SDK
//		// start之后会默认发起一次定位请求，开发者无须判断isstart并主动调用request
////					startLocation.setText(getString(R.string.stoplocation));
//
////		locationService.stop();
//	}

	@Override
	protected void onStop() {
		super.onStop();
		// TODO Auto-generated method stub
//		locationService.unregisterListener(mListener); //注销掉监听
//		locationService.stop(); //停止定位服务
	}

	PermissionsUtils.IPermissionsResult permissionsResult = new PermissionsUtils.IPermissionsResult() {
		@Override
		public void passPermissons() {
			Intent updateIntent = new Intent(MainActivity.
					this, UpdateService.class);
			startService(updateIntent);
		}

		@Override
		public void forbitPermissons() {
			Toast.makeText(MainActivity.this, "权限不通过!", Toast.LENGTH_SHORT).show();
		}
	};

	private void bindInvite() {
		if (invite != null) {
			if (CommonUtil.getNetworkRequest(MainActivity.this)) {
				final LoadingDialog loadingDialog = new LoadingDialog(MainActivity.this);
				loadingDialog.show();
				HashMap<String, String> map = new HashMap<>();
				map.put("act", "invite");
				map.put("from", invite);
				CommonHttpUtils.ref_action(map, new CommonHttpUtils.CallingBack() {
					@Override
					public void successBack(BaseResultEntity baseResultEntity) {
						loadingDialog.dismiss();
						MyToastView.showToast(baseResultEntity.getMsg(), MainActivity.this);
						if (Constant.HTTP_LOGINOUTTIME_CODE.equals(baseResultEntity.getCode())) {
							startActivity(new Intent(MainActivity.this, LoginActivity.class));
							finish();
						}

					}

					@Override
					public void failBack() {
						loadingDialog.dismiss();
						MyToastView.showToast(getResources().getString(R.string.msg_error_operation), MainActivity.this);
					}
				});
			}
		}

	}

	private void init() {
		mFragmentManager = getFragmentManager();
		tbBottom = (MyTabWidget) findViewById(R.id.tbBottom);
		LinearLayout topView = findViewById(R.id.topView);
		setTopView(topView);
		if (backIndex == ConstantValues.HOME_FRAGMENT_INDEX) {//首页
			myClick(backIndex);
		} else if (backIndex == ConstantValues.GOODS_FRAGMENT_INDEX) {//商品进入
			myClick(backIndex);
		} else if (backIndex == ConstantValues.PROJECT_FRAGMENT_INDEX) {//抢购进入
			myClick(backIndex);
		} else if (backIndex == ConstantValues.MINE_FRAGMENT_INDEX) {//我的
			myClick(backIndex);
		}
		tbBottom.setIndicateDisplay(this, 2, false);
		tbBottom.setOnTabSelectedListener(this);

	}

	private void myClick(int backIndex) {
		onTabSelected(backIndex);
		tbBottom.setTabsDisplay(this, backIndex);
	}

	@Override
	public void onTabSelected(final int index) {
		FragmentTransaction transaction = mFragmentManager.beginTransaction();
		hideFragments(transaction);

		switch (index) {
			case ConstantValues.HOME_FRAGMENT_INDEX://首页
				if (null == goodsFragment) {
					homeFragment = new HomeFragment();
					transaction.add(R.id.center_layout, homeFragment);
				} else {
					transaction.show(homeFragment);
					homeFragment.intDatas();
				}
				break;
			case ConstantValues.GOODS_FRAGMENT_INDEX://商品
				if (null == goodsFragment) {
					goodsFragment = new GoodsFragment();
					transaction.add(R.id.center_layout, goodsFragment);
				} else {
					transaction.show(goodsFragment);
					goodsFragment.intDatas();
				}
				break;
			case ConstantValues.PROJECT_FRAGMENT_INDEX://抢购

				if (null == projectFragment) {
					projectFragment = new ProjectFragment();
					transaction.add(R.id.center_layout, projectFragment);
				} else {
					transaction.show(projectFragment);
					projectFragment.initData();
//					carFragment.trackRefresh(1);
				}


				break;

			case ConstantValues.MINE_FRAGMENT_INDEX://我的

				if (null == mineFragment) {
					mineFragment = new MineFragment();

					transaction.add(R.id.center_layout, mineFragment);
				} else {
					transaction.show(mineFragment);
					mineFragment.initData();
//					resouceFragment.trackRefresh();
				}


				break;


			default:
				break;
		}
//		hIndex = index;
		transaction.commitAllowingStateLoss();
	}


	private void hideFragments(FragmentTransaction transaction) {
		if (null != homeFragment) {
			transaction.hide(homeFragment);
		}
		if (null != goodsFragment) {
			transaction.hide(goodsFragment);
		}
		if (null != projectFragment) {
			transaction.hide(projectFragment);
		}
//		if (null != carFragment) {
//			transaction.hide(carFragment);
//		}
		if (null != mineFragment) {
			transaction.hide(mineFragment);
		}

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
			new AlertDialog.Builder(this).setTitle("确认退到桌面吗？")
					.setIcon(android.R.drawable.ic_dialog_info)
					.setPositiveButton("确定", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							MyApplication.getInstance().exit();
							System.exit(0);
//                            stopService(new Intent(MyApplication.getInstance(), UpdateVersionService.class));
						}
					})
					.setNegativeButton("取消", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// 点击“返回”后的操作,这里不设置没有任何操作
						}
					}).show();
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(myReceiver);
		unregisterReceiver(notificationReceiver);
	}

	class MyReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			myClick(0);
		}
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		PermissionsUtils.getInstance().onRequestPermissionsResult(MainActivity.this, requestCode, permissions, grantResults);
	}

//	@TargetApi(23)
//	private void getPersimmions() {
//		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//			ArrayList<String> permissions = new ArrayList<String>();
//			/***
//			 * 定位权限为必须权限，用户如果禁止，则每次进入都会申请
//			 */
//			// 定位精确位置
//			if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//				permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
//			}
//			if (checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//				permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
//			}
//			/*
//			 * 读写权限和电话状态权限非必要权限(建议授予)只会申请一次，用户同意或者禁止，只会弹一次
//			 */
//			// 读写权限
//			if (addPermission(permissions, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
//				permissionInfo += "Manifest.permission.WRITE_EXTERNAL_STORAGE Deny \n";
//			}
//			// 读取电话状态权限
//			if (addPermission(permissions, Manifest.permission.READ_PHONE_STATE)) {
//				permissionInfo += "Manifest.permission.READ_PHONE_STATE Deny \n";
//			}
//
//			if (permissions.size() > 0) {
//				requestPermissions(permissions.toArray(new String[permissions.size()]), SDK_PERMISSION_REQUEST);
//			}
//		}
//	}
//
//	@TargetApi(23)
//	private boolean addPermission(ArrayList<String> permissionsList, String permission) {
//		if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) { // 如果应用没有获得对应权限,则添加到列表中,准备批量申请
//			if (shouldShowRequestPermissionRationale(permission)) {
//				return true;
//			} else {
//				permissionsList.add(permission);
//				return false;
//			}
//
//		} else {
//			return true;
//		}
//	}
//
//	/*****
//	 *
//	 * 定位结果回调，重写onReceiveLocation方法，可以直接拷贝如下代码到自己工程中修改
//	 *
//	 */
//	private BDAbstractLocationListener mListener = new BDAbstractLocationListener() {
//
//		@Override
//		public void onReceiveLocation(BDLocation location) {
//			// TODO Auto-generated method stub
//			if (null != location && location.getLocType() != BDLocation.TypeServerError) {
//				StringBuffer sb = new StringBuffer(256);
//				sb.append("time : ");
//				/**
//				 * 时间也可以使用systemClock.elapsedRealtime()方法 获取的是自从开机以来，每次回调的时间；
//				 * location.getTime() 是指服务端出本次结果的时间，如果位置不发生变化，则时间不变
//				 */
//				sb.append(location.getTime());
//				sb.append("\nlocType : ");// 定位类型
//				sb.append(location.getLocType());
//				sb.append("\nlocType description : ");// *****对应的定位类型说明*****
//				sb.append(location.getLocTypeDescription());
//				sb.append("\nlatitude : ");// 纬度
//				sb.append(location.getLatitude());
//				sb.append("\nlontitude : ");// 经度
//				sb.append(location.getLongitude());
//				sb.append("\nradius : ");// 半径
//				sb.append(location.getRadius());
//				sb.append("\nCountryCode : ");// 国家码
//				sb.append(location.getCountryCode());
//				sb.append("\nCountry : ");// 国家名称
//				sb.append(location.getCountry());
//				sb.append("\ncitycode : ");// 城市编码
//				sb.append(location.getCityCode());
//				sb.append("\ncity : ");// 城市
//				sb.append(location.getCity());
//				sb.append("\nDistrict : ");// 区
//				sb.append(location.getDistrict());
//				sb.append("\nStreet : ");// 街道
//				sb.append(location.getStreet());
//				sb.append("\naddr : ");// 地址信息
//				sb.append(location.getAddrStr());
//				sb.append("\nUserIndoorState: ");// *****返回用户室内外判断结果*****
//				sb.append(location.getUserIndoorState());
//				sb.append("\nDirection(not all devices have value): ");
//				sb.append(location.getDirection());// 方向
//				sb.append("\nlocationdescribe: ");
//				sb.append(location.getLocationDescribe());// 位置语义化信息
//
//				if (location.getLocType() == BDLocation.TypeGpsLocation) {// GPS定位结果
//					sb.append("\nspeed : ");
//					sb.append(location.getSpeed());// 速度 单位：km/h
//					sb.append("\nsatellite : ");
//					sb.append(location.getSatelliteNumber());// 卫星数目
//					sb.append("\nheight : ");
//					sb.append(location.getAltitude());// 海拔高度 单位：米
//					sb.append("\ngps status : ");
//					sb.append(location.getGpsAccuracyStatus());// *****gps质量判断*****
//					sb.append("\ndescribe : ");
//					sb.append("gps定位成功");
//				} else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果
//					// 运营商信息
//					if (location.hasAltitude()) {// *****如果有海拔高度*****
//						sb.append("\nheight : ");
//						sb.append(location.getAltitude());// 单位：米
//					}
//					sb.append("\noperationers : ");// 运营商信息
//					sb.append(location.getOperators());
//					sb.append("\ndescribe : ");
//					sb.append("网络定位成功");
//				} else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
//					sb.append("\ndescribe : ");
//					sb.append("离线定位成功，离线定位结果也是有效的");
//				} else if (location.getLocType() == BDLocation.TypeServerError) {
//					sb.append("\ndescribe : ");
//					sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
//				} else if (location.getLocType() == BDLocation.TypeNetWorkException) {
//					sb.append("\ndescribe : ");
//					sb.append("网络不同导致定位失败，请检查网络是否通畅");
//				} else if (location.getLocType() == BDLocation.TypeCriteriaException) {
//					sb.append("\ndescribe : ");
//					sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
//				}
//				LogUtils.e(TAG, sb.toString());
//
//			}
//		}
//
//	};
}