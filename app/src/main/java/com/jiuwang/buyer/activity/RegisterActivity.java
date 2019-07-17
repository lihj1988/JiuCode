package com.jiuwang.buyer.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jiuwang.buyer.R;
import com.jiuwang.buyer.base.BaseActivity;
import com.jiuwang.buyer.base.MyApplication;
import com.jiuwang.buyer.constant.Constant;
import com.jiuwang.buyer.entity.BaseEntity;
import com.jiuwang.buyer.entity.BaseResultEntity;
import com.jiuwang.buyer.entity.LoginEntity;
import com.jiuwang.buyer.net.HttpUtils;
import com.jiuwang.buyer.util.AppUtils;
import com.jiuwang.buyer.util.CommonUtil;
import com.jiuwang.buyer.util.DialogUtil;
import com.jiuwang.buyer.util.LoadingDialog;
import com.jiuwang.buyer.util.MyToastView;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;

import static com.jiuwang.buyer.R.id.tv_gainmessage;



/*
*
* 作者：Yokey软件工作室
*
* 企鹅：1002285057
*
* 网址：www.yokey.top
*
* 作用：注册
*
* 更新：2016-04-17
*
*/

public class RegisterActivity extends BaseActivity {
	@Bind(R.id.topView)
	LinearLayout topView;
	@Bind(R.id.actionbar_text)
	TextView actionbarText;
	@Bind(R.id.return_img)
	ImageView returnImg;
	@Bind(R.id.onclick_layout_left)
	RelativeLayout onclickLayoutLeft;
	@Bind(R.id.onclick_layout_right)
	Button onclickLayoutRight;
	@Bind(R.id.iv_find)
	ImageView ivFind;
	@Bind(R.id.et_username)
	EditText etUserName;
	@Bind(R.id.et_password)
	EditText etPassword;
	@Bind(R.id.et_surepass)
	EditText etSurepass;

	@Bind(R.id.et_phone)
	EditText etPhone;
	@Bind(R.id.et_messagecode)
	EditText etMessagecode;
	@Bind(tv_gainmessage)
	TextView tvGainmessage;
	@Bind(R.id.subBtn)
	Button subBtn;
	@Bind(R.id.tv_login)
	TextView tvLogin;
	private Activity mActivity;
	private String userName;
	private String password;
	private String surepass;
	private String phone;
	private String messagecode;
	private int recLen = 60;
	Handler handler = new Handler();
	private boolean boo1;
	private LoadingDialog loadingDialog;

	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
			returnActivity();
			return true;
		}
		return super.dispatchKeyEvent(event);
	}

	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.activity_register);
		ButterKnife.bind(this);
		mActivity = this;
		initView();
	}

	private void initView() {

		onclickLayoutRight.setVisibility(View.GONE);
		actionbarText.setText("用户注册");
		String str1 = "已有注册账号？<font color='#1897d6'>立即登录</font>";
		tvLogin.setText(Html.fromHtml(str1));
		setTopView(topView);

	}


	public void dataChenkAndRegister() {
		//用户姓名  登录账号
		userName = etUserName.getText().toString().trim();
		//密码
		password = etPassword.getText().toString().trim();
		//确认密码
		surepass = etSurepass.getText().toString().trim();
		//手机号
		phone = etPhone.getText().toString().trim();
		//短信验证码
		messagecode = etMessagecode.getText().toString().trim();

		if (TextUtils.isEmpty(userName)) {
			MyToastView.showToast("请输入登录账号", this);
			return;
		}

		if (TextUtils.isEmpty(password)) {
			MyToastView.showToast("请输入密码", this);
			return;
		}
		if (password.length() < 8) {
			MyToastView.showToast("请输入8-20位的密码", this);
			return;
		}
		if (TextUtils.isEmpty(surepass)) {
			MyToastView.showToast("请输入确认密码", this);
			return;
		}
		if (!surepass.equals(password)) {
			MyToastView.showToast("两次输入的密码不一致", this);
			return;
		}

		if (TextUtils.isEmpty(phone)) {
			MyToastView.showToast("请输入手机号码", this);
			return;
		}
		if (TextUtils.isEmpty(messagecode)) {
			MyToastView.showToast("请输入验证码", this);
			return;
		}
		register();
	}

	//返回&销毁Activity
	private void returnActivity() {

		DialogUtil.query(
				mActivity,
				"确认您的选择",
				"取消注册？",
				new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						MyApplication.getInstance().finishActivity(mActivity);
					}
				}
		);

	}

	@OnClick({R.id.subBtn, R.id.tv_login, tv_gainmessage, R.id.onclick_layout_left})
	public void onViewClicked(View view) {
		switch (view.getId()) {
			case R.id.subBtn:
				//提交
				dataChenkAndRegister();
				break;
			case R.id.tv_login:
				finish();
				break;
			case tv_gainmessage:
				tvGainmessage.setEnabled(false);
				//获取验证码
				tvGainmessage.setVisibility(View.VISIBLE);
				tvGainmessage.setText("60秒后重新发送");
//							tvVerify.setVisibility(View.INVISIBLE);
				handler.post(runnable);

				String phone = etPhone.getText().toString().trim();
				if (!TextUtils.isEmpty(phone)) {
					if (!AppUtils.isPhoneNumberValid(phone)) {
						MyToastView.showToast("请输入正确的手机号", RegisterActivity.this);
						return;
					}
				} else {
					MyToastView.showToast("请输入手机号", RegisterActivity.this);
					return;
				}
				//获取短信验证网络请求
				HashMap<String, String> map = new HashMap<>();
				map.put("linkman_mobile",phone);
				map.put("act","9");
				HttpUtils.regVerify(map, new Consumer<BaseResultEntity>() {
					@Override
					public void accept(BaseResultEntity baseResultEntity) throws Exception {
//				Log.i("Str", str);
						if(Constant.HTTP_SUCCESS_CODE.equals(baseResultEntity.getCode())){
							MyToastView.showToast("验证码已发送",RegisterActivity.this);
						}

					}
				}, new Consumer<Throwable>() {
					@Override
					public void accept(Throwable throwable) throws Exception {

					}
				});
				break;
			case R.id.onclick_layout_left:
				finish();
				break;
		}
	}

	//注册
	private void register() {


		loadingDialog = new LoadingDialog(RegisterActivity.this);
		loadingDialog.show();
//		DialogUtil.progress(RegisterActivity.this);
		HashMap<String, String> hashMap = new HashMap<>();
		hashMap.put("act", Constant.ACTION_ACT_ADD);
		hashMap.put("user_cd", userName);
		hashMap.put("user_name", userName);
		hashMap.put("mobile_number", phone);
		hashMap.put("mobile_code", messagecode);
		hashMap.put("new_passwd", password);
		HttpUtils.register(hashMap, new Consumer<BaseResultEntity>() {
			@Override
			public void accept(BaseResultEntity baseResultEntity) throws Exception {
				loadingDialog.dismiss();
				if (Constant.HTTP_SUCCESS_CODE.equals(baseResultEntity.getCode())) {
					MyToastView.showToast(baseResultEntity.getMsg(), RegisterActivity.this);
					//注册成功后登录
					CommonUtil.login(userName, password, new CommonUtil.LoginCallBack() {
						@Override
						public void callBack(BaseEntity<LoginEntity> loginEntity) {
							if ("0".equals(loginEntity.getCode())) {
								runnable = null;
								Intent intent = new Intent();
								intent.setClass(RegisterActivity.this, MainActivity.class);
								startActivity(intent);
							} else {
								MyToastView.showToast(loginEntity.getMsg(), RegisterActivity.this);
							}
						}

						@Override
						public void failCallBack(Throwable throwable) {
							DialogUtil.cancel();
						}
					});
				}
			}
		}, new Consumer<Throwable>() {
			@Override
			public void accept(Throwable throwable) throws Exception {
				loadingDialog.dismiss();
				MyToastView.showToast("注册失败", RegisterActivity.this);
			}
		});
	}



	Runnable runnable = new Runnable() {
		@Override
		public void run() {
			recLen--;
			if (recLen >= 1) {
				tvGainmessage.setText(recLen + "秒后重新发送");
				handler.postDelayed(this, 1000);
			} else {
				tvGainmessage.setVisibility(View.VISIBLE);
				tvGainmessage.setText("获取验证码");
				recLen = 60;
				boo1 = false;
				tvGainmessage.setEnabled(true);
			}
		}
	};
}

