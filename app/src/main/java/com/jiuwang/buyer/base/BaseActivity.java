package com.jiuwang.buyer.base;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.jiuwang.buyer.R;

public class BaseActivity extends FragmentActivity{
	public MyApplication application;


	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		MyApplication.getInstance().addActivity(this);
		//写在了ResourceActivity类里边，由于与键盘冲突不能写在共用类里边
//		ImmersionBar.with(this)
//				.statusBarColor(R.color.app_main_color)
//				.fitsSystemWindows(true)
//				.keyboardEnable(false)  //解决软键盘与底部输入框冲突问题，默认为false
//				.init(); //初始化，默认透明状态栏和黑色导航栏

		application = MyApplication.getInstance();
		fullScreen(this);
		requestPermission();
	}

	protected void onSaveInstanceState(Bundle outState) {
		// super.onSaveInstanceState(outState);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
//		ImmersionBar.with(this).destroy();
	}

	//点击任意地方键盘消失
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		if (ev.getAction() == MotionEvent.ACTION_DOWN) {
			View v = getCurrentFocus();
			if (isShouldHideInput(v, ev)) {

				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				if (imm != null) {
					imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
				}
			}
			return super.dispatchTouchEvent(ev);
		}
		// 必不可少，否则所有的组件都不会有TouchEvent了
		if (getWindow().superDispatchTouchEvent(ev)) {
			return true;
		}
		return onTouchEvent(ev);
	}

	public boolean isShouldHideInput(View v, MotionEvent event) {
		if (v != null && (v instanceof EditText)) {
			int[] leftTop = {0, 0};
			// 获取输入框当前的location位置
			v.getLocationInWindow(leftTop);
			int left = leftTop[0];
			int top = leftTop[1];
			int bottom = top + v.getHeight();
			int right = left + v.getWidth();
			if (event.getX() > left && event.getX() < right
					&& event.getY() > top && event.getY() < bottom) {
				// 点击的是输入框区域，保留点击EditText的事件
				return false;
			} else {
				return true;
			}
		}
		return false;
	}
	public void fullScreen(Activity activity) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
				Window window = activity.getWindow();
				View decorView = window.getDecorView();
				//两个 flag 要结合使用，表示让应用的主体内容占用系统状态栏的空间
				int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
						| View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
				decorView.setSystemUiVisibility(option);
				window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
				//设置状态栏为透明，否则在部分手机上会呈现系统默认的浅灰色
				window.setStatusBarColor(Color.TRANSPARENT);
				//导航栏颜色也可以考虑设置为透明色
				//window.setNavigationBarColor(Color.TRANSPARENT);
			} else {
				Window window = activity.getWindow();
				WindowManager.LayoutParams attributes = window.getAttributes();
				int flagTranslucentStatus = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
				int flagTranslucentNavigation = WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION;
				attributes.flags |= flagTranslucentStatus;
//                attributes.flags |= flagTranslucentNavigation;
				window.setAttributes(attributes);
			}
		}
	}

	public  int getStatusBarHeight(Context context) {
		int result = 0;
		int resId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
		if (resId > 0) {
			result = context.getResources().getDimensionPixelOffset(resId);
		}
		return result;
	}

	public void setTopView(View topView){
		LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) topView.getLayoutParams();
		layoutParams.height = getStatusBarHeight(this);
		topView.setLayoutParams(layoutParams);
		topView.setLayoutParams(layoutParams);
	}

	/**
	 * 获取权限使用的 RequestCode
	 */
	private static final int PERMISSIONS_REQUEST_CODE = 1002;

	/**
	 * 检查支付宝 SDK 所需的权限，并在必要的时候动态获取。
	 * 在 targetSDK = 23 以上，READ_PHONE_STATE 和 WRITE_EXTERNAL_STORAGE 权限需要应用在运行时获取。
	 * 如果接入支付宝 SDK 的应用 targetSdk 在 23 以下，可以省略这个步骤。
	 */
	private void requestPermission() {
		// Here, thisActivity is the current activity
		if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
				!= PackageManager.PERMISSION_GRANTED
				|| ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
				!= PackageManager.PERMISSION_GRANTED) {

			ActivityCompat.requestPermissions(this,
					new String[]{
							Manifest.permission.READ_PHONE_STATE,
							Manifest.permission.WRITE_EXTERNAL_STORAGE
					}, PERMISSIONS_REQUEST_CODE);

		} else {
//			showToast(this, getString(R.string.permission_already_granted));
		}
	}

	/**
	 * 权限获取回调
	 */
	@Override
	public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
		switch (requestCode) {
			case PERMISSIONS_REQUEST_CODE: {

				// 用户取消了权限弹窗
				if (grantResults.length == 0) {
//					showToast(this, getString(R.string.permission_rejected));
					return;
				}

				// 用户拒绝了某些权限
				for (int x : grantResults) {
					if (x == PackageManager.PERMISSION_DENIED) {
//						showToast(this, getString(R.string.permission_rejected));
						return;
					}
				}

				// 所需的权限均正常获取
//				showToast(this, getString(R.string.permission_granted));
			}
		}
	}

}
