package com.jiuwang.buyer.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
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
import com.jiuwang.buyer.service.ServiceInterface;
import com.jiuwang.buyer.service.ServiceLocation;
import com.jiuwang.buyer.service.UpdateService;
import com.jiuwang.buyer.spinerwidget.MyTabWidget;
import com.jiuwang.buyer.util.AppUtils;
import com.jiuwang.buyer.util.CommonUtil;
import com.jiuwang.buyer.util.ConstantValues;
import com.jiuwang.buyer.util.LoadingDialog;
import com.jiuwang.buyer.util.MyToastView;
import com.jiuwang.buyer.util.PermissionsUtils;

import java.util.ArrayList;
import java.util.HashMap;

import static com.jiuwang.buyer.util.CommonUtil.isServiceWorked;


public class MainActivity extends BaseActivity implements MyTabWidget.OnTabSelectedListener {
	//定义一个变量，来标识是否退出
	private static boolean isExit = false;

	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			isExit = false;
		}
	};


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
	private Intent serviceLocation;
	ServiceInterface sLocationSubmit;
	private boolean mIsBound = false;

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
			getPersimmions();
			if (invite != null && !invite.equals("")) {
				bindInvite();
			}

			AppUtils.getSystemVersion(MainActivity.this, permissionsResult, "1");
		}
		if (!isServiceWorked(MainActivity.this,
				"com.jiuwang.buyer.service.ServiceLocation")) {
			serviceLocation = new Intent();
			serviceLocation.setClass(MainActivity.this, ServiceLocation.class);
//			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//				startForegroundService(serviceLocation);
//			} else {
//				startService(serviceLocation);
//			}


			startService(serviceLocation);
			mIsBound = bindService(serviceLocation, connLocation, BIND_AUTO_CREATE);
		}

	}

	@Override
	protected void onStart() {
		super.onStart();

	}


	@Override
	protected void onStop() {
		super.onStop();
		// TODO Auto-generated method stub

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(myReceiver);
		unregisterReceiver(notificationReceiver);
		if (mIsBound) {

			unbindService(connLocation);
			stopService(serviceLocation);
			mIsBound = false;
		}
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

	private ServiceConnection connLocation = new ServiceConnection() {
		@Override
		public void onServiceDisconnected(ComponentName name) {
			System.out.println("--Service Disconnected--");
			sLocationSubmit = null;

		}

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			System.out.println("--Service Connected--");
			sLocationSubmit = (ServiceInterface) service;
			sLocationSubmit.startMyService();
		}
	};

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			exit();

			return false;
		}

		return super.onKeyDown(keyCode, event);
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

	@TargetApi(23)
	private void getPersimmions() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
			ArrayList<String> permissions = new ArrayList<String>();
			/***
			 * 定位权限为必须权限，用户如果禁止，则每次进入都会申请
			 */
			// 定位精确位置
			if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
				permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
			}
			if (checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
				permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
			}
			/*
			 * 读写权限和电话状态权限非必要权限(建议授予)只会申请一次，用户同意或者禁止，只会弹一次
			 */
			// 读写权限
			if (addPermission(permissions, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
				permissionInfo += "Manifest.permission.WRITE_EXTERNAL_STORAGE Deny \n";
			}
			// 读取电话状态权限
			if (addPermission(permissions, Manifest.permission.READ_PHONE_STATE)) {
				permissionInfo += "Manifest.permission.READ_PHONE_STATE Deny \n";
			}

			if (permissions.size() > 0) {
				requestPermissions(permissions.toArray(new String[permissions.size()]), SDK_PERMISSION_REQUEST);
			}
		}
	}

	@TargetApi(23)
	private boolean addPermission(ArrayList<String> permissionsList, String permission) {
		if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) { // 如果应用没有获得对应权限,则添加到列表中,准备批量申请
			if (shouldShowRequestPermissionRationale(permission)) {
				return true;
			} else {
				permissionsList.add(permission);
				return false;
			}

		} else {
			return true;
		}
	}


	private void exit() {
		if (!isExit) {
			isExit = true;
			Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
			//利用handler延迟发送更改状态信息
			handler.sendEmptyMessageDelayed(0, 2000);
		} else {
			finish();
			System.exit(0);
		}
	}
}