package com.jiuwang.buyer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jiuwang.buyer.R;
import com.jiuwang.buyer.base.BaseActivity;
import com.jiuwang.buyer.constant.Constant;
import com.jiuwang.buyer.entity.BaseResultEntity;
import com.jiuwang.buyer.net.HttpUtils;
import com.jiuwang.buyer.util.CommonUtil;
import com.jiuwang.buyer.util.DialogUtil;
import com.jiuwang.buyer.util.MyToastView;

import java.util.HashMap;

import io.reactivex.functions.Consumer;


public class ForgetActivity extends BaseActivity {

	private RelativeLayout rg_left;
	private EditText etPhone, etMessage, etPassword, etConfirm;
	private TextView tvVerify, tvDaoJiShi, tv;
	private Button bt_submit;
	private String phone, password, verifyCode, confirm;
	private String result;
	// 防止用户连续点击，获取多个验证码
	// 获取验证码标记，控制一分只能点击一次
	public boolean boo1 = false;
	private Button onclick_layout_right;
	//	private VerifyManager manager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_forget);
		intView();
		addListener();
		// MyApplication.getInstance().addActivity(this);
	}

	private void addListener() {
		rg_left.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				finish();

			}
		});
		tvVerify.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				tvVerify.setEnabled(false);
				getVerify();
			}
		});
		rg_left.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		bt_submit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				onForget();
			}
		});
	}

	private void intView() {
		LinearLayout topView = findViewById(R.id.topView);
		setTopView(topView);
		tv = findViewById(R.id.actionbar_text);
		tv.setText("找回密码");
		rg_left = findViewById(R.id.onclick_layout_left);
		etPhone = findViewById(R.id.et_phone);
		etMessage = findViewById(R.id.et_messRegist);
		etPassword = findViewById(R.id.et_password);
		etConfirm = findViewById(R.id.et_confirm);
		tvVerify = findViewById(R.id.tv_verify);
		onclick_layout_right = findViewById(R.id.onclick_layout_right);
		bt_submit = (Button) findViewById(R.id.bt_submit);
		onclick_layout_right.setVisibility(View.INVISIBLE);
	}

	public void getVerify() {

		phone = etPhone.getText().toString();
		if (phone.equals("") || phone == null) {
			MyToastView.showToast("电话号码不能为空", this);
			return;
		}

		if (CommonUtil.getNetworkRequest(this)) {
			tvVerify.setVisibility(View.VISIBLE);
			tvVerify.setText("60秒后重新发送");
			handler.post(runnable);
			getVerify_real();

		}
	}

	protected void getVerify_real() {
		// TODO Auto-generated method stub
		HashMap<String, String> map = new HashMap<>();
		map.put("act", "2");
		map.put("mobile_number", phone);
		HttpUtils.getVerify(map, new Consumer<BaseResultEntity>() {
			@Override
			public void accept(BaseResultEntity baseResultEntity) throws Exception {
//				Log.i("Str", str);
				if (Constant.HTTP_SUCCESS_CODE.equals(baseResultEntity.getCode())) {
					MyToastView.showToast("验证码已发送", ForgetActivity.this);
				} else {
					MyToastView.showToast(baseResultEntity.getMsg(), ForgetActivity.this);
					runnable = null;
				}

			}
		}, new Consumer<Throwable>() {
			@Override
			public void accept(Throwable throwable) throws Exception {

			}
		});
	}

	private int recLen = 60;
	Handler handler = new Handler();
	Runnable runnable = new Runnable() {
		@Override
		public void run() {
			recLen--;
			if (recLen >= 1) {
				tvVerify.setVisibility(View.VISIBLE);
				tvVerify.setText(recLen + "秒后重新发送");
				handler.postDelayed(this, 1000);
			} else {
				tvVerify.setText("获取验证码");
				tvVerify.setVisibility(View.VISIBLE);
				recLen = 60;
				tvVerify.setEnabled(true);
				boo1 = false;
			}
		}
	};

	public void onForget() {
		// TODO Auto-generated method stub
		bt_submit.setEnabled(false);
		phone = etPhone.getText().toString();
		password = etPassword.getText().toString();
		confirm = etConfirm.getText().toString();
		verifyCode = etMessage.getText().toString();
//		getVerify_real();
		if (phone.equals("") || phone == null) {
			MyToastView.showToast("手机号不能为空", getApplicationContext());
			bt_submit.setEnabled(true);

			return;
		}
		if (!CommonUtil.isMobileNO(phone)) {
			MyToastView.showToast("手机号格式不正确", getApplicationContext());
			bt_submit.setEnabled(true);

			return;
		}
		if (verifyCode.equals("") || verifyCode == null) {
			MyToastView.showToast("验证码不能为空", getApplicationContext());
			bt_submit.setEnabled(true);

			return;
		}
		if (password.equals("") || password == null) {
			MyToastView.showToast("密码不能为空", getApplicationContext());
			bt_submit.setEnabled(true);

			return;
		} else if (password.length() > 18 || password.length() < 8) {
			MyToastView.showToast("密码长度格式不正确,请输入8—20位密码",
					getApplicationContext());
			bt_submit.setEnabled(true);

			return;
		}
		if (!(password.equals(confirm))) {
			MyToastView.showToast("确认密码与新密码不一致", getApplicationContext());
			bt_submit.setEnabled(true);

			return;
		}

		if (CommonUtil.getNetworkRequest(ForgetActivity.this)) {

			//密码重置
			DialogUtil.progress(ForgetActivity.this);
			HashMap<String, String> map = new HashMap<>();
			map.put("mobile_number", phone);
			map.put("newpwd", confirm);
			map.put("mobile_yzm", verifyCode);
			HttpUtils.onForget(map, new Consumer<BaseResultEntity>() {
				@Override
				public void accept(BaseResultEntity baseResultEntity) throws Exception {
					if (Constant.HTTP_SUCCESS_CODE.equals(baseResultEntity.getCode())) {
						finish();
					} else if (Constant.HTTP_LOGINOUTTIME_CODE.equals(baseResultEntity.getCode())) {

						Intent intent = new Intent(ForgetActivity.this, LoginActivity.class);
						startActivity(intent);
						finish();
					}
					bt_submit.setEnabled(true);
					MyToastView.showToast(baseResultEntity.getMsg(), ForgetActivity.this);
					DialogUtil.cancel();
				}
			}, new Consumer<Throwable>() {
				@Override
				public void accept(Throwable throwable) throws Exception {
					bt_submit.setEnabled(true);
					DialogUtil.cancel();
					MyToastView.showToast(getString(R.string.msg_error_operation), ForgetActivity.this);
					bt_submit.setEnabled(true);

				}
			});
		}
	}

}
