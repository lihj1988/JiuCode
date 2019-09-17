package com.jiuwang.buyer.wxapi;


import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jiuwang.buyer.R;
import com.jiuwang.buyer.base.BaseActivity;
import com.jiuwang.buyer.base.MyApplication;
import com.jiuwang.buyer.constant.Constant;
import com.jiuwang.buyer.util.LogUtils;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.jiuwang.buyer.R.id.actionbar_text;

public class WXPayEntryActivity extends BaseActivity implements IWXAPIEventHandler {

	private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";

	private IWXAPI api;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pay_complete);
		ButterKnife.bind(this);
		initData();
		initView();

		api = WXAPIFactory.createWXAPI(this, Constant.WXPAY_APPID);
		api.handleIntent(getIntent(), this);
	}

	@Bind(R.id.topView)
	LinearLayout topView;
	@Bind(actionbar_text)
	TextView actionbarText;
	@Bind(R.id.return_img)
	ImageView returnImg;
	@Bind(R.id.onclick_layout_left)
	RelativeLayout onclickLayoutLeft;
	@Bind(R.id.onclick_layout_right)
	Button onclickLayoutRight;
	@Bind(R.id.iv_find)
	ImageView ivFind;
	@Bind(R.id.tvTotalAmount)
	TextView tvTotalAmount;
	private String totalAmount;
	private String type;


	private void initData() {

		String info = "订单金额 <font color='#FF5001'>" + Constant.wx_pay_amount_temp + " 元" + "</font>";
		tvTotalAmount.setText(Html.fromHtml(info));
	}

	private void initView() {
		setTopView(topView);
		actionbarText.setText("订单支付成功");
		onclickLayoutRight.setText("完成");
		onclickLayoutLeft.setVisibility(View.INVISIBLE);
	}

	@OnClick(R.id.onclick_layout_right)
	public void onViewClicked() {
		finish();
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
		api.handleIntent(intent, this);
	}

	@Override
	public void onReq(BaseReq req) {

	}

	@Override
	public void onResp(BaseResp resp) {

		Log.e(TAG, "onPayFinish, errCode = " + resp.errCode);
		LogUtils.writeLogToFile("request=" + resp.errCode + "-" + resp.errStr,
				MyApplication.getInstance().filePath);
		if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {

			switch (resp.errCode) {
				case Constant.WX_PAY_ERRCODE_SUCCESS:
					initData();
					Intent intent = new Intent();
					intent.setAction("minerefresh");
					sendBroadcast(intent);
					intent.setAction("rechargefinish");
					sendBroadcast(intent);
					intent.setAction("balancerefresh");
					sendBroadcast(intent);
					intent.setAction("buy2finish");
					sendBroadcast(intent);
					break;
				case Constant.WX_PAY_ERRCODE_FAIL:
				case Constant.WX_PAY_ERRCODE_FAIL_OTHTER:
					AlertDialog.Builder builder = new AlertDialog.Builder(this);
					builder.setTitle(R.string.app_tip);
					builder.setMessage(getString(R.string.pay_result_callback_msg, String.valueOf(resp.errCode)));
					builder.show();
					break;
			}

		}
	}
}