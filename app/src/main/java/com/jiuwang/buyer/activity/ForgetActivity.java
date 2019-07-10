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
	private Button btRegist;
	private String phone, password, verifyCode, confirm;
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
		btRegist.setOnClickListener(new OnClickListener() {

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
		btRegist = (Button) findViewById(R.id.bt_regist);
	}

	public void getVerify() {

		phone = etPhone.getText().toString();
		if (phone.equals("") || phone == null) {
			MyToastView.showToast("手机号不能为空", this);
			return;
		}
		if (!CommonUtil.isMobileNO(phone)) {
			MyToastView.showToast("手机号格式不正确", getApplicationContext());
			return;
		}
		if (CommonUtil.getNetworkRequest(this)) {
//			manager = new VerifyManager(this, false, "");
//			if (!boo1) {
//				boo1 = true;
//				manager.getUidCode(new AsyncHttpResponseHandler(this) {
//					@Override
//					public void onSuccess(int statusCode, Header[] headers,
//							byte[] responseBody) {
//						// TODO Auto-generated method stub
//						String str = new String(responseBody);
//						if (CommonMainParser.IsForNet(str)) {
//							JSONObject obj;
//							try {
//								obj = new JSONObject(str);
//								result = obj.getString("result");
//								getVerify_real();
//							} catch (JSONException e) {
//								// TODO Auto-generated catch block
//								e.printStackTrace();
//							}
//						} else {
//							MyToastView.showToast(
//									CommonMainParser.getServierInfosParser(str),
//									ForgetActivity.this);
//						}
//					}
//
//				});
//			}

		}
	}

	protected void getVerify_real() {
		// TODO Auto-generated method stub
		HashMap<String, String> map = new HashMap<>();
		map.put("user_cd","lihj");
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


//		manager.getVerify(etPhone.getText().toString(), result,
//				new AsyncHttpResponseHandler(this) {
//					@Override
//					public void onSuccess(int statusCode, Header[] headers,
//							byte[] responseBody) {
//						String he = new String(responseBody);
//						Log.i("headers", he);
//						String str = new String(responseBody);
//						if (CommonMainParser.IsForNet(str)) {
//							/**
//							 * 倒计时
//							 */
//							Log.i("Str", str);
//							tvDaoJiShi.setVisibility(View.VISIBLE);
//							tvDaoJiShi.setText("60秒后重新发送");
//							tvVerify.setVisibility(View.INVISIBLE);
//							handler.post(runnable);
//						} else {
//							MyToastView.showToast(
//									CommonMainParser.getServierInfosParser(str),
//									ForgetActivity.this);
//							boo1 = false;
//						}
//
//					}
//
//					@Override
//					public void onFailure(int statusCode, Header[] headers,
//							byte[] responseBody, Throwable error) {
//						super.onFailure(statusCode, headers, responseBody,
//								error);
//						boo1 = false;
//					}
//
//					@Override
//					public void onFinish() {
//						super.onFinish();
//					}
//				});

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
		phone = etPhone.getText().toString();
		password = etPassword.getText().toString();
		confirm = etConfirm.getText().toString();
		verifyCode = etMessage.getText().toString();
		getVerify_real();
//		if (phone.equals("") || phone == null) {
//			MyToastView.showToast("手机号不能为空", getApplicationContext());
//			return;
//		}
//		if (!CommonUtil.isMobileNO(phone)) {
//			MyToastView.showToast("手机号格式不正确", getApplicationContext());
//			return;
//		}
//		if (verifyCode.equals("") || verifyCode == null) {
//			MyToastView.showToast("验证码不能为空", getApplicationContext());
//			return;
//		}
//		if (password.equals("") || password == null) {
//			MyToastView.showToast("密码不能为空", getApplicationContext());
//			return;
//		} else if (password.length() > 18 || password.length() < 8) {
//			MyToastView.showToast("密码长度格式不正确,请输入8—20位密码",
//					getApplicationContext());
//			return;
//		}
//		if (!(password.equals(confirm))) {
//			MyToastView.showToast("确认密码与新密码不一致", getApplicationContext());
//			return;
//		}

//		if (CommonUtil.getNetworkRequest(ForgetActivity.this)) {
//			LoginManager manager = new LoginManager(ForgetActivity.this, true,
//					"正在找回密码...");
//			manager.forget(password, phone, verifyCode, confirm,
//					new AsyncHttpResponseHandler(ForgetActivity.this) {
//						@Override
//						public void onSuccess(int statusCode, Header[] headers,
//								byte[] responseBody) {
//							String str = new String(responseBody);
//							if (CommonMainParser.IsForNet(str)) {
//								MyToastView.showToast("修改密码成功",
//										ForgetActivity.this);
//								/**
//								 * 自动登陆调用 登陆接口
//								 */
//								// toLoginNetConnection();
//								finish();
//
//							} else {
//								MyToastView.showToast(CommonMainParser
//										.getServierInfosParser(str),
//										ForgetActivity.this);
//
//							}
//
//						};
//
//						@Override
//						public void onFailure(int statusCode, Header[] headers,
//								byte[] responseBody, Throwable error) {
//							MyToastView.showToast("访问网络失败，请稍后再试 ",
//									ForgetActivity.this);
//
//						};
//
//						@Override
//						public void onFinish() {
//							super.onFinish();
//						}
//					});
//		}
	}

}
