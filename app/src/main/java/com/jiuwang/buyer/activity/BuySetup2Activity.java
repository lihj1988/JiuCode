package com.jiuwang.buyer.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.EnvUtils;
import com.alipay.sdk.app.PayTask;
import com.jiuwang.buyer.R;
import com.jiuwang.buyer.appinterface.DialogClickInterface;
import com.jiuwang.buyer.base.BaseActivity;
import com.jiuwang.buyer.base.MyApplication;
import com.jiuwang.buyer.bean.AuthResult;
import com.jiuwang.buyer.bean.OrderBean;
import com.jiuwang.buyer.bean.PayResult;
import com.jiuwang.buyer.bean.UserBean;
import com.jiuwang.buyer.constant.Constant;
import com.jiuwang.buyer.entity.BaseResultEntity;
import com.jiuwang.buyer.entity.UserEntity;
import com.jiuwang.buyer.net.HttpUtils;
import com.jiuwang.buyer.util.AppUtils;
import com.jiuwang.buyer.util.CommonUtil;
import com.jiuwang.buyer.util.MyToastView;
import com.jiuwang.buyer.util.alipay.OrderInfoUtil2_0;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;

/*
*
* 作者：lihj
* 作用：购买第二步
*/
public class BuySetup2Activity extends BaseActivity {

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
	@Bind(R.id.snTextView)
	TextView snTextView;
	@Bind(R.id.payListTextView)
	TextView payListTextView;
	@Bind(R.id.aliPayTextView)
	RadioButton aliPayRadioButton;
	@Bind(R.id.wxPayTextView)
	RadioButton wxPayRadioButton;
	@Bind(R.id.balancePay)
	RadioButton balancePay;
	@Bind(R.id.listRadioGroup)
	RadioGroup listRadioGroup;
	@Bind(R.id.payTextView)
	TextView payTextView;
	private Activity mActivity;
	private MyApplication mApplication;
	private UserBean userBean;
	private String pay_sn;
	private static final int SDK_PAY_FLAG = 1;
	private static final int SDK_AUTH_FLAG = 2;
	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler() {
		@SuppressWarnings("unused")
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case SDK_PAY_FLAG: {
					@SuppressWarnings("unchecked")
					PayResult payResult = new PayResult((Map<String, String>) msg.obj);
					/**
					 * 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
					 */
					String resultInfo = payResult.getResult();// 同步返回需要验证的信息
					String resultStatus = payResult.getResultStatus();
					// 判断resultStatus 为9000则代表支付成功
					if (TextUtils.equals(resultStatus, Constant.ALIPAY_RESULTSTATUS)) {
						// 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
//						showAlert(BuySetup2Activity.this, getString(R.string.pay_success) + payResult);
						payComplete();
					} else {
						// 该笔订单真实的支付结果，需要依赖服务端的异步通知。
//						showAlert(BuySetup2Activity.this, getString(R.string.pay_failed) + payResult);
						MyToastView.showToast(getString(R.string.pay_failed), BuySetup2Activity.this);
					}
					break;
				}
				case SDK_AUTH_FLAG: {
					@SuppressWarnings("unchecked")
					AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);
					String resultStatus = authResult.getResultStatus();

					// 判断resultStatus 为“9000”且result_code
					// 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
					if (TextUtils.equals(resultStatus, Constant.ALIPAY_RESULTSTATUS) && TextUtils.equals(authResult.getResultCode(), "200")) {
						// 获取alipay_open_id，调支付时作为参数extern_token 的value
						// 传入，则支付账户为该授权账户
						showAlert(BuySetup2Activity.this, getString(R.string.auth_success) + authResult);
					} else {
						// 其他状态值则为授权失败
						showAlert(BuySetup2Activity.this, getString(R.string.auth_failed) + authResult);
					}
					break;
				}
				default:
					break;
			}
		}

		;
	};

	private void payComplete() {
		Intent intent1 = new Intent();
		intent1.setAction("finish");
		sendBroadcast(intent1);
		intent1.setAction("first");
		sendBroadcast(intent1);
		Intent intent = new Intent(BuySetup2Activity.this, OrderPayCompleteActivity.class);
		intent.putExtra("totalAmount", orderBean.getTotal_amount());
		startActivity(intent);
		finish();
	}

	private OrderBean orderBean;
	private String balance;

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
		setContentView(R.layout.activity_buy_setup2);
		if (Constant.IS_DEBUG) {
			EnvUtils.setEnv(EnvUtils.EnvEnum.SANDBOX);
		}
		ButterKnife.bind(this);
		requestPermission();
		initView();
		initData();
	}

	//初始化控件
	private void initView() {
		onclickLayoutRight.setVisibility(View.INVISIBLE);

		setTopView(topView);

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
					showToast(this, getString(R.string.permission_rejected));
					return;
				}

				// 用户拒绝了某些权限
				for (int x : grantResults) {
					if (x == PackageManager.PERMISSION_DENIED) {
						showToast(this, getString(R.string.permission_rejected));
						return;
					}
				}

				// 所需的权限均正常获取
				showToast(this, getString(R.string.permission_granted));
			}
		}
	}

	//初始化数据
	private void initData() {

		mActivity = this;
		mApplication = (MyApplication) getApplication();

//		pay_sn = mActivity.getIntent().getStringExtra("pay_sn");
//
//		if (!mActivity.getIntent().getStringExtra("payment_code").equals("online")) {
//			MyToastView.showToast("不支持的支付方式", mActivity);
//			mApplication.finishActivity(mActivity);
//		}

		actionbarText.setText("订单支付");

		aliPayRadioButton.setVisibility(View.VISIBLE);
		wxPayRadioButton.setVisibility(View.VISIBLE);
		aliPayRadioButton.setChecked(true);
		Intent intent = getIntent();
		if (intent != null) {
			orderBean = (OrderBean) intent.getSerializableExtra("data");
			snTextView.append("：");
//		snTextView.append(pay_sn);
			snTextView.append(orderBean.getId());
		}

		selectUser();

	}


	//返回&销毁Activity
	private void returnActivity() {
		AppUtils.showNormalDialog(BuySetup2Activity.this, "确认您的选择", "取消支付", "取消", "确定", new DialogClickInterface() {
			@Override
			public void nagtiveOnClick() {


			}

			@Override
			public void onClick() {
				startActivity(new Intent(BuySetup2Activity.this, OrderActivity.class));
				mApplication.finishActivity(mActivity);
			}
		});

	}

	@OnClick({R.id.onclick_layout_left, R.id.payTextView})
	public void onViewClicked(View view) {
		switch (view.getId()) {
			case R.id.onclick_layout_left:
				returnActivity();
				break;
			case R.id.payTextView://支付
				if (aliPayRadioButton.isChecked()) {
					payAli();

				}
				if (wxPayRadioButton.isChecked()) {
					MyToastView.showToast("不支付的支付方式", mActivity);

				}
				if (balancePay.isChecked()) {
					//余额支付
//					if(Double.parseDouble(balance)<Double.parseDouble(orderBean.getTotal_amount())){
//
//					}
					payBalance();
				}
				break;
		}
	}

	private void payAli() {
		if (orderBean != null) {
//						orderBean.setTimeout_express(baseResultEntity.getDate().get(0).getTimeout_express());
			orderBean.setProduct_code(Constant.ALIPAY_PRODUCT_CODE);
			orderBean.setTotal_amount(orderBean.getTotal_amount());
			orderBean.setOut_trade_no(orderBean.getId());
			orderBean.setSubject(orderBean.getGoods_name());
			JSONObject object = new JSONObject();
			try {
				object.put("product_code", orderBean.getProduct_code());
				object.put("total_amount", orderBean.getTotal_amount());
				object.put("subject", orderBean.getGoods_name());
				object.put("out_trade_no", orderBean.getOut_trade_no());
				object.put("passback_params", Constant.BUSINESSTYPE_PAYMWENT);
			} catch (JSONException e) {
				e.printStackTrace();
			}

			Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(Constant.ALIPAY_APPID, object.toString(), Constant.RSA2);
			String orderParam = OrderInfoUtil2_0.buildOrderParam(params);
			String sign = OrderInfoUtil2_0.getSign(params, Constant.PRIVATE_KEY, Constant.RSA2);
			final String orderInfo = orderParam + "&" + sign;

			final Runnable payRunnable = new Runnable() {

				@Override
				public void run() {
					PayTask alipay = new PayTask(BuySetup2Activity.this);
					Map<String, String> result = alipay.payV2(orderInfo, true);
					Log.i("msp", result.toString());

					Message msg = new Message();
					msg.what = SDK_PAY_FLAG;
					msg.obj = result;
					mHandler.sendMessage(msg);
				}
			};

			// 必须异步调用
			Thread payThread = new Thread(payRunnable);
			payThread.start();
		}
	}

	private void payBalance() {
		if (CommonUtil.getNetworkRequest(BuySetup2Activity.this)) {
			HashMap<String, String> map = new HashMap<>();
			map.put("act", "pay");
			map.put("order_id", orderBean.getId());
			HttpUtils.fundPay(map, new Consumer<BaseResultEntity>() {
				@Override
				public void accept(BaseResultEntity baseResultEntity) throws Exception {
					if (Constant.HTTP_SUCCESS_CODE.equals(baseResultEntity.getCode())) {

						payComplete();
					} else if (Constant.HTTP_LOGINOUTTIME_CODE.equals(baseResultEntity.getCode())) {
						MyToastView.showToast(baseResultEntity.getMsg(), BuySetup2Activity.this);
						Intent intent = new Intent(BuySetup2Activity.this, LoginActivity.class);
						startActivity(intent);
						finish();
					} else {
						MyToastView.showToast(baseResultEntity.getMsg(), BuySetup2Activity.this);
					}

				}
			}, new Consumer<Throwable>() {
				@Override
				public void accept(Throwable throwable) throws Exception {
					MyToastView.showToast(getResources().getString(R.string.msg_error_operation), BuySetup2Activity.this);
				}
			});
		}
	}

	//获取用户数据
	public void selectUser() {

		if (CommonUtil.getNetworkRequest(BuySetup2Activity.this)) {
			HashMap<String, String> map = new HashMap<>();
			map.put("", "");
			HttpUtils.selectUserInfo(map, new Consumer<UserEntity>() {

				@Override
				public void accept(UserEntity userEntity) throws Exception {

					if (Constant.HTTP_SUCCESS_CODE.equals(userEntity.getCode())) {
						userBean = userEntity.getData().get(0);

						if ("".equals(userBean.getAccount_balance())) {
							balance = "0.00";
						} else {
							balance = userBean.getAccount_balance();
						}

						String total = "余额支付（ <font color='#FF5001'>" + CommonUtil.decimalFormat(Double.parseDouble(balance), "0") + "元）" + "</font>";
						balancePay.setText(Html.fromHtml(total));

					} else if (Constant.HTTP_LOGINOUTTIME_CODE.equals(userEntity.getCode())) {
						startActivity(new Intent(BuySetup2Activity.this, LoginActivity.class));
						BuySetup2Activity.this.finish();
					} else {

					}
				}
			}, new Consumer<Throwable>() {
				@Override
				public void accept(Throwable throwable) throws Exception {

				}
			});
		}

	}

	private static void showAlert(Context ctx, String info) {
		showAlert(ctx, info, null);
	}

	private static void showAlert(Context ctx, String info, DialogInterface.OnDismissListener onDismiss) {
		new AlertDialog.Builder(ctx)
				.setMessage(info)
				.setPositiveButton(R.string.confirm, null)
				.setOnDismissListener(onDismiss)
				.show();
	}

	private static void showToast(Context ctx, String msg) {
		Toast.makeText(ctx, msg, Toast.LENGTH_LONG).show();
	}
}
