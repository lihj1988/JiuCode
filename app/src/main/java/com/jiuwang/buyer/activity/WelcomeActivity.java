package com.jiuwang.buyer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

import com.jiuwang.buyer.R;
import com.jiuwang.buyer.base.BaseActivity;
import com.jiuwang.buyer.base.MyApplication;
import com.jiuwang.buyer.constant.Constant;
import com.jiuwang.buyer.entity.BaseEntity;
import com.jiuwang.buyer.entity.LoginEntity;
import com.jiuwang.buyer.util.CommonUtil;
import com.jiuwang.buyer.util.PreforenceUtils;


public class WelcomeActivity extends BaseActivity implements Animation.AnimationListener {
	private String  password, userId;

	private ImageView iv_start;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
		MyApplication.currentActivity = this;

		iv_start = (ImageView) findViewById(R.id.iv_start);
		startAnim();
		startNextActivity();
	}

	private void startNextActivity() {
		userId = PreforenceUtils.getStringData("loginInfo", "userID");
		password = PreforenceUtils.getStringData("loginInfo", "password");

		MyApplication.handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				/*Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
				startActivity(intent);*/
				boolean isLogin = PreforenceUtils.getBooleanData("loginInfo","isLogin");
				if (isLogin) {
					if (!TextUtils.isEmpty(userId) && !userId.equals("")) {
						if (!TextUtils.isEmpty(password) && !password.equals("")) {
							if (CommonUtil.getNetworkRequest(WelcomeActivity.this)) {
									CommonUtil.login(userId,password,new CommonUtil.LoginCallBack() {

										@Override
										public void callBack(BaseEntity<LoginEntity> loginEntity) {
											if("0".equals(loginEntity.getCode())){
												Constant.IS_LOGIN = true;//记录登录状态
												Intent intent = new Intent();
												intent.setClass(WelcomeActivity.this,MainActivity.class);
												startActivity(intent);
												finish();
											}else {
												CommonUtil.launchActivity(WelcomeActivity.this, LoginActivity.class);
												finish();
											}
										}

										@Override
										public void failCallBack(Throwable throwable) {
											CommonUtil.launchActivity(WelcomeActivity.this, LoginActivity.class);
											finish();
										}
									});
//								LoginManager.getLoginManager().loginSubmit(userId, password,WelcomeActivity.this, "0");
							}
						} else {
							CommonUtil.launchActivity(WelcomeActivity.this, LoginActivity.class);
							finish();
						}
					} else {
						CommonUtil.launchActivity(WelcomeActivity.this, LoginActivity.class);
						finish();
					}
				} else {
					CommonUtil.launchActivity(WelcomeActivity.this, MainActivity.class);
					finish();
				}
			}
		}, 2500);

	}

	//动画
	private void startAnim() {
		AlphaAnimation anim = new AlphaAnimation(0, 1);
		anim.setDuration(2000);
		anim.setFillAfter(true);
		anim.setAnimationListener(this);
		iv_start.startAnimation(anim);
	}


	@Override
	public void onAnimationStart(Animation animation) {

	}

	@Override
	public void onAnimationEnd(Animation animation) {

	}

	@Override
	public void onAnimationRepeat(Animation animation) {

	}
}