package com.jiuwang.buyer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jiuwang.buyer.R;
import com.jiuwang.buyer.base.BaseActivity;
import com.jiuwang.buyer.base.MyApplication;
import com.jiuwang.buyer.constant.Constant;
import com.jiuwang.buyer.entity.BaseEntity;
import com.jiuwang.buyer.entity.LoginEntity;
import com.jiuwang.buyer.util.CommonUtil;
import com.jiuwang.buyer.util.LoadingDialog;
import com.jiuwang.buyer.util.LogUtils;
import com.jiuwang.buyer.util.MyToastView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 登录界面
 */
public class LoginActivity extends BaseActivity {
	private static final String TAG = LoginActivity.class.getName();
	@Bind(R.id.topView)
	LinearLayout topView;
	@Bind(R.id.etNumber)
	EditText etNumber;
	@Bind(R.id.linear1)
	LinearLayout linear1;
	@Bind(R.id.etPwd)
	EditText etPwd;
	@Bind(R.id.linear2)
	LinearLayout linear2;
	@Bind(R.id.subBtn)
	TextView subBtn;
	@Bind(R.id.registerTextView)
	TextView registerTextView;
	@Bind(R.id.findTextView)
	TextView findTextView;
	@Bind(R.id.screen)
	LinearLayout screen;
	private String index = "0";
	private LoadingDialog loadingDialog;
	private String invite;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		ButterKnife.bind(this);
		MyApplication.currentActivity = this;
		Intent intent = getIntent();
		invite = intent.getStringExtra("from");
		initView();
		index = this.getIntent().getStringExtra("index");

	}

	private void initView() {
//		actionbar_text.setText("登录");
//		onclick_layout_right.setVisibility(View.GONE);
//		setTopView(topView);
	}


	public void getToken() {
		String loginName = etNumber.getText().toString().trim();
		final String password = etPwd.getText().toString().trim();
		if (CommonUtil.isNull(loginName)) {
			MyToastView.showToast("用户名不能为空", LoginActivity.this);
			return;
		}
		if (CommonUtil.isNull(password)) {
			MyToastView.showToast("密码不能为空", LoginActivity.this);
			return;
		}
		loadingDialog = new LoadingDialog(LoginActivity.this);
		loadingDialog.show();
		CommonUtil.login(loginName, password, new CommonUtil.LoginCallBack() {
			@Override
			public void callBack(BaseEntity<LoginEntity> loginEntity) {
				loadingDialog.dismiss();
				if ("0".equals(loginEntity.getCode())) {
					Constant.IS_LOGIN = true;//记录登录状态
					Intent intent = new Intent();
					intent.setClass(LoginActivity.this, MainActivity.class);
					if (invite != null) {
						intent.putExtra("from", invite);
					}
					startActivity(intent);
					finish();
				} else {
					MyToastView.showToast(loginEntity.getMsg(), LoginActivity.this);
				}
			}

			@Override
			public void failCallBack(Throwable throwable) {
				loadingDialog.dismiss();
				MyToastView.showToast("登录失败", LoginActivity.this);
				LogUtils.e(TAG, throwable.getMessage());
			}
		});
	}

	@Override
	public void onBackPressed() {
//		Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//		intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
//		intent.putExtra("isConfirm", false);
//		LoginActivity.this.startActivity(intent);
//		finish();
		super.onBackPressed();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		ButterKnife.unbind(this);
	}

	@OnClick({R.id.subBtn, R.id.registerTextView, R.id.findTextView})
	public void onViewClicked(View view) {
		switch (view.getId()) {
			case R.id.subBtn:
				if (CommonUtil.getNetworkRequest(LoginActivity.this)) {
					getToken();
				}
				break;
			case R.id.registerTextView:
				startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
				break;
			case R.id.findTextView:
				startActivity(new Intent(LoginActivity.this, ForgetActivity.class));
				break;

		}
	}
}
