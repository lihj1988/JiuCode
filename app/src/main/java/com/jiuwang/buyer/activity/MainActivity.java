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
import com.jiuwang.buyer.fragment.HomeFragment;
import com.jiuwang.buyer.fragment.MineFragment;
import com.jiuwang.buyer.fragment.ProjectFragment;
import com.jiuwang.buyer.net.CommonHttpUtils;
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


	private FragmentManager mFragmentManager;
	private MyTabWidget tbBottom;
	private HomeFragment homeFragment;
	private ProjectFragment projectFragment;
	//	private CarFragment carFragment;
	private MineFragment mineFragment;
	private int backIndex;
	private MyReceiver myReceiver;
	private String invite;

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
		if (Constant.IS_LOGIN) {
			if (invite != null && !invite.equals("")) {
				bindInvite();
			}

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
						AppUtils.getSystemVersion(MainActivity.this, permissionsResult, "1");
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
		} else if (backIndex == ConstantValues.PROJECT_FRAGMENT_INDEX) {//抢购进入
			myClick(backIndex);
		}
//		else if (backIndex == ConstantValues.CAR_FRAGMENT_INDEX) {//购物车进入
//			myClick(backIndex);
//		}
		else if (backIndex == ConstantValues.MINE_FRAGMENT_INDEX) {//我的
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
				if (null == homeFragment) {
					homeFragment = new HomeFragment();
					transaction.add(R.id.center_layout, homeFragment);
				} else {
					transaction.show(homeFragment);
					homeFragment.intDatas();
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
//			case ConstantValues.CAR_FRAGMENT_INDEX://购物车
//				if (null == carFragment) {
//					carFragment = new CarFragment();
//					transaction.add(R.id.center_layout, carFragment);
//				} else {
//					transaction.show(carFragment);
////					carFragment.trackRefresh(1);
//				}
//				break;
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

}