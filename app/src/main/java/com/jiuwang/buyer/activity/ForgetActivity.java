package com.jiuwang.buyer.activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jiuwang.buyer.R;
import com.jiuwang.buyer.base.BaseActivity;
import com.jiuwang.buyer.constant.Constant;
import com.jiuwang.buyer.entity.BaseResultEntity;
import com.jiuwang.buyer.net.HttpUtils;
import com.jiuwang.buyer.util.CommonUtil;
import com.jiuwang.buyer.util.MyToastView;

import java.util.HashMap;

import io.reactivex.functions.Consumer;


public class ForgetActivity extends BaseActivity {

	private RelativeLayout rg_left;
	private EditText etPhone, etMessage, etPassword, etConfirm;
	private TextView tvVerify, tvDaoJiShi, tv;
	private Button bt_submit;
	private String user_cd, password, verifyCode, confirm;
	private String result;
	// 防止用户连续点击，获取多个验证码
	// 获取验证码标记，控制一分只能点击一次
	public boolean boo1 = false;
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
		View views = findViewById(R.id.forget);
		tv = (TextView) views.findViewById(R.id.actionbar_text);
		tv.setText("找回密码");
		rg_left = (RelativeLayout) findViewById(R.id.onclick_layout_left);
		etPhone = (EditText) findViewById(R.id.et_phone);
		etMessage = (EditText) findViewById(R.id.et_messRegist);
		etPassword = (EditText) findViewById(R.id.et_password);
		etConfirm = (EditText) findViewById(R.id.et_confirm);
		tvVerify = (TextView) findViewById(R.id.tv_verify);
		tvDaoJiShi = (TextView) findViewById(R.id.tv_daojishi);
		bt_submit = (Button) findViewById(R.id.bt_submit);
	}

	public void getVerify() {

		user_cd = etPhone.getText().toString();
		if (user_cd.equals("") || user_cd == null) {
			MyToastView.showToast("用户名不能为空", this);
			return;
		}

		if (CommonUtil.getNetworkRequest(this)) {
			getVerify_real();

		}
	}

	protected void getVerify_real() {
		// TODO Auto-generated method stub
		HashMap<String, String> map = new HashMap<>();
		map.put("user_cd",user_cd);
		HttpUtils.getVerify(map, new Consumer<BaseResultEntity>() {
			@Override
			public void accept(BaseResultEntity baseResultEntity) throws Exception {
//				Log.i("Str", str);
				if(Constant.HTTP_SUCCESS_CODE.equals(baseResultEntity.getCode())){
					tvDaoJiShi.setVisibility(View.VISIBLE);
					tvDaoJiShi.setText("60秒后重新发送");
					tvVerify.setVisibility(View.INVISIBLE);
					handler.post(runnable);
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
				tvVerify.setVisibility(View.INVISIBLE);
				tvDaoJiShi.setText(recLen + "秒后重新发送");
				handler.postDelayed(this, 1000);
			} else {
				tvDaoJiShi.setVisibility(View.INVISIBLE);
				tvVerify.setVisibility(View.VISIBLE);
				recLen = 60;
				boo1 = false;
			}
		}
	};

	public void onForget() {
		// TODO Auto-generated method stub
		user_cd = etPhone.getText().toString();
		password = etPassword.getText().toString();
		confirm = etConfirm.getText().toString();
		verifyCode = etMessage.getText().toString();
		getVerify_real();
		if (user_cd.equals("") || user_cd == null) {
			MyToastView.showToast("手机号不能为空", getApplicationContext());
			return;
		}
		if (!CommonUtil.isMobileNO(user_cd)) {
			MyToastView.showToast("手机号格式不正确", getApplicationContext());
			return;
		}
		if (verifyCode.equals("") || verifyCode == null) {
			MyToastView.showToast("验证码不能为空", getApplicationContext());
			return;
		}
		if (password.equals("") || password == null) {
			MyToastView.showToast("密码不能为空", getApplicationContext());
			return;
		} else if (password.length() > 18 || password.length() < 8) {
			MyToastView.showToast("密码长度格式不正确,请输入8—20位密码",
					getApplicationContext());
			return;
		}
		if (!(password.equals(confirm))) {
			MyToastView.showToast("确认密码与新密码不一致", getApplicationContext());
			return;
		}

		if (CommonUtil.getNetworkRequest(ForgetActivity.this)) {

			//密码重置

		}
	}

}
