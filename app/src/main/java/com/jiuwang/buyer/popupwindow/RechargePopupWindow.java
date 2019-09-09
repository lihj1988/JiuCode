package com.jiuwang.buyer.popupwindow;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.alipay.sdk.app.EnvUtils;
import com.alipay.sdk.app.PayTask;
import com.jiuwang.buyer.R;
import com.jiuwang.buyer.activity.OrderPayCompleteActivity;
import com.jiuwang.buyer.base.MyApplication;
import com.jiuwang.buyer.bean.AuthResult;
import com.jiuwang.buyer.bean.OrderBean;
import com.jiuwang.buyer.bean.PayResult;
import com.jiuwang.buyer.constant.Constant;
import com.jiuwang.buyer.constant.NetURL;
import com.jiuwang.buyer.entity.BaseEntity;
import com.jiuwang.buyer.entity.BaseResultEntity;
import com.jiuwang.buyer.entity.LoginEntity;
import com.jiuwang.buyer.net.HttpUtils;
import com.jiuwang.buyer.util.CommonUtil;
import com.jiuwang.buyer.util.LoadingDialog;
import com.jiuwang.buyer.util.LogUtils;
import com.jiuwang.buyer.util.MyToastView;
import com.jiuwang.buyer.util.PreforenceUtils;
import com.jiuwang.buyer.util.alipay.OrderInfoUtil2_0;
import com.jiuwang.buyer.util.wxpay.HttpKit;
import com.jiuwang.buyer.util.wxpay.WXPayUtils;
import com.tencent.mm.opensdk.modelpay.PayReq;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import io.reactivex.functions.Consumer;

/**
 * author：lihj
 * desc：充值
 * Created by lihj on  2019/7/4 14:15.
 */

public class RechargePopupWindow extends PopupWindow {

	private static final String TAG = RechargePopupWindow.class.getName();
	private WindowManager.LayoutParams params;
	private View mMenuView;
	private String type;
	private Activity context;
	private OrderBean orderBean;
	private TextView aliPay, wxPay, cancle;
	private static final int SDK_PAY_FLAG = 1;
	private static final int SDK_AUTH_FLAG = 2;
	private LoadingDialog loadingDialog ;
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
						RechargePopupWindow.this.dismiss();
						Intent intent = new Intent();
						intent.setAction("minerefresh");
						context.sendBroadcast(intent);
						intent.setAction("rechargefinish");
						context.sendBroadcast(intent);
						intent.setAction("balancerefresh");
						context.sendBroadcast(intent);
						Intent intent1 = new Intent(context,OrderPayCompleteActivity.class);
						intent1.putExtra("totalAmount",orderBean.getTotal_amount());
						intent1.putExtra("type",type);
						context.startActivity(intent1);

					} else {
						// 该笔订单真实的支付结果，需要依赖服务端的异步通知。
//						showAlert(BuySetup2Activity.this, getString(R.string.pay_failed) + payResult);
						MyToastView.showToast(context.getResources().getString(R.string.pay_failed),context);
					}
					break;
				}
				case SDK_AUTH_FLAG: {
					@SuppressWarnings("unchecked")
					AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);
					String resultStatus = authResult.getResultStatus();

					// 判断resultStatus 为“9000”且result_code
					// 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
					if (TextUtils.equals(resultStatus,  Constant.ALIPAY_RESULTSTATUS) && TextUtils.equals(authResult.getResultCode(), "200")) {
						// 获取alipay_open_id，调支付时作为参数extern_token 的value
						// 传入，则支付账户为该授权账户
//						showAlert(BuySetup2Activity.this, getString(R.string.auth_success) + authResult);
					} else {
						// 其他状态值则为授权失败
//						showAlert(BuySetup2Activity.this, getString(R.string.auth_failed) + authResult);
					}
					break;
				}
				default:
					break;
			}
		};
	};

	public RechargePopupWindow(Activity context,OrderBean orderBean, String type) {
		super(context);
		this.context = context;
		this.orderBean = orderBean;
		this.type = type;
		if(Constant.IS_DEBUG){
			EnvUtils.setEnv(EnvUtils.EnvEnum.SANDBOX);
		}
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mMenuView = inflater.inflate(R.layout.popup_recharge, null);
		setContentView(mMenuView);
		this.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
		this.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
		setAnimationStyle(R.style.ActionSheetDialogAnimation);
		// 实例化一个ColorDrawable颜色为半透明
		ColorDrawable dw = new ColorDrawable(0xb0000000);
		// 设置SelectPicPopupWindow弹出窗体的背景
		setBackgroundDrawable(dw);
		setFocusable(true);
		setOutsideTouchable(true);
		params = this.context.getWindow().getAttributes();
		// 当弹出Popupwindow时，背景变半透明
		params.alpha = 0.7f;
		this.context.getWindow().setAttributes(params);
		setOnDismissListener(new PopupWindow.OnDismissListener() {
			@Override
			public void onDismiss() {
				params = RechargePopupWindow.this.context.getWindow()
						.getAttributes();
				params.alpha = 1f;
				RechargePopupWindow.this.context.getWindow().setAttributes(
						params);
			}
		});

		initView(mMenuView);
	}

	private void initView(View mMenuView) {
		aliPay = mMenuView.findViewById(R.id.aliPay);
		wxPay = mMenuView.findViewById(R.id.wxPay);
		cancle = mMenuView.findViewById(R.id.cancle);

		aliPay.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//支付宝支付
				loadingDialog = new LoadingDialog(context);
//				MyToastView.showToast("支付宝支付" ,context);
				loadingDialog.show();
				if(orderBean!=null){
					if(CommonUtil.getNetworkRequest(context)){
						recharge(Constant.PAY_MODE_ALI);
					}
				}

			}
		});
		wxPay.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//微信支付
//				MyToastView.showToast("暂未启用" ,context);
				loadingDialog = new LoadingDialog(context);
				loadingDialog.show();
				recharge(Constant.PAY_MODE_WX);
			}
		});
		cancle.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dismiss();
			}
		});
	}

	private void recharge(final String payMode) {
		HashMap<String, String> map = new HashMap<>();
		map.put("act","1");
		map.put("pay_mode",payMode);
		map.put("amount",orderBean.getTotal_amount());
		HttpUtils.recharge(map, new Consumer<BaseResultEntity>() {
			@Override
			public void accept(BaseResultEntity baseResultEntity) throws Exception {
				loadingDialog.dismiss();
				if(Constant.HTTP_SUCCESS_CODE.equals(baseResultEntity.getCode())){
					if(payMode.equals(Constant.PAY_MODE_ALI)){
						JSONObject object = new JSONObject();
						try {
							object.put("product_code",orderBean.getProduct_code());
							object.put("total_amount",orderBean.getTotal_amount());
							object.put("subject",orderBean.getSubject());
							object.put("out_trade_no",baseResultEntity.getMsg());
							object.put("passback_params",Constant.BUSINESSTYPE_RECHARGE);
						} catch (JSONException e) {
							e.printStackTrace();
						}

						Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(Constant.ALIPAY_APPID,object.toString(), Constant.RSA2);
						String orderParam = OrderInfoUtil2_0.buildOrderParam(params);
						String sign = OrderInfoUtil2_0.getSign(params, Constant.PRIVATE_KEY, Constant.RSA2);
						final String orderInfo = orderParam + "&" + sign;

						final Runnable payRunnable = new Runnable() {

							@Override
							public void run() {
								PayTask alipay = new PayTask(context);
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
					}else if(payMode.equals(Constant.PAY_MODE_WX)){
						//微信支付
						OrderBean order = new OrderBean();
						order.setBody(orderBean.getSubject());
						order.setOut_trade_no(baseResultEntity.getMsg());
						order.setTotal_amount(orderBean.getTotal_amount());
						order.setAttach("1");//附加参数
						final WXPayUtils wxPayUtils = new WXPayUtils(order.getOut_trade_no(), order.getBody(), order.getTotal_amount(), order.getAttach());
						final String request = wxPayUtils.getRequestXml(wxPayUtils.requestProductArgs());

						new Thread(new Runnable() {
							@Override
							public void run() {
								String post = HttpKit.post(NetURL.WX_UNIFIEDORDER, request);
								LogUtils.e(TAG,post);
								LogUtils.writeLogToFile("post=" + post,
										MyApplication.getInstance().filePath);
								Map<String, String> stringXmlOut = wxPayUtils.readStringXmlOut(post);
								if(stringXmlOut!=null){
									if(stringXmlOut.get("return_code").equals("FAIL")){
										MyToastView.showToast(stringXmlOut.get("return_msg").toString(),context);
//										RechargePopupWindow.this.dismiss();
										return;
									}
								}
//								LogUtils.e(TAG,post);
								new Handler(){
									@Override
									public void handleMessage(Message msg) {
										PayReq req = new PayReq();
										req.appId = Constant.WXPAY_APPID;
										req.partnerId = Constant.MCH_ID;
										req.prepayId = "prepay_id";
										req.packageValue = "Sign=WXPay";
										req.nonceStr = wxPayUtils.genNonceStr();
										req.timeStamp = String.valueOf(wxPayUtils.genTimeStamp());
										List<NameValuePair> signParams = new LinkedList<NameValuePair>();
										signParams.add(new BasicNameValuePair("appid", req.appId));
										signParams.add(new BasicNameValuePair("noncestr", req.nonceStr));
										signParams.add(new BasicNameValuePair("package", req.packageValue));
										signParams.add(new BasicNameValuePair("partnerid", req.partnerId));
										signParams.add(new BasicNameValuePair("prepayid", req.prepayId));
										signParams.add(new BasicNameValuePair("timestamp", req.timeStamp));
										req.sign = wxPayUtils.genAppSign(signParams);
										MyApplication.getInstance().api.sendReq(req);
									}
								}.sendEmptyMessage(0);
							}
						}).start();

					}

				}else if(Constant.HTTP_LOGINOUTTIME_CODE.equals(baseResultEntity.getCode())){
					CommonUtil.reLogin(PreforenceUtils.getStringData("loginInfo", "userID"), PreforenceUtils.getStringData("loginInfo", "password"), new CommonUtil.LoginCallBack() {
						@Override
						public void callBack(BaseEntity<LoginEntity> loginEntity) {
							recharge(payMode);
						}

						@Override
						public void failCallBack(Throwable throwable) {

						}
					});
				}else {
					MyToastView.showToast(baseResultEntity.getMsg(),context);
				}
			}
		}, new Consumer<Throwable>() {
			@Override
			public void accept(Throwable throwable) throws Exception {
				MyToastView.showToast("充值失败",context);
				loadingDialog.dismiss();
			}
		});
	}

}
