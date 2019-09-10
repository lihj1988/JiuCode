package com.jiuwang.buyer.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jiuwang.buyer.R;
import com.jiuwang.buyer.appinterface.DialogClickInterface;
import com.jiuwang.buyer.base.BaseActivity;
import com.jiuwang.buyer.bean.UserBean;
import com.jiuwang.buyer.constant.Constant;
import com.jiuwang.buyer.entity.BaseEntity;
import com.jiuwang.buyer.entity.LoginEntity;
import com.jiuwang.buyer.entity.UserEntity;
import com.jiuwang.buyer.net.HttpUtils;
import com.jiuwang.buyer.util.AppUtils;
import com.jiuwang.buyer.util.CommonUtil;
import com.jiuwang.buyer.util.PreforenceUtils;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;

/**
 * author：lihj
 * desc：
 * Created by lihj on  2019/7/18 16:11.
 */

public class BalanceActivity extends BaseActivity {
	@Bind(R.id.topView)
	LinearLayout topView;
	@Bind(R.id.actionbar_text)
	TextView actionbarText;
	@Bind(R.id.onclick_layout_left)
	RelativeLayout onclickLayoutLeft;
	@Bind(R.id.onclick_layout_right)
	Button onclickLayoutRight;
	@Bind(R.id.iv_find)
	ImageView ivFind;
	@Bind(R.id.llRecharge)
	LinearLayout llRecharge;
	@Bind(R.id.llCashout)
	LinearLayout llCashout;
	@Bind(R.id.tvBalance)
	TextView tvBalance;
	private MyReceiver myReceiver;
	private MyFinishReceiver myFinishReceiver;
	private UserBean userBean;
	private String account_name;
	private String account_no;
	private String avail_amount;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_balance);
		ButterKnife.bind(this);
		initView();
		myReceiver = new MyReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction("balancerefresh");
		registerReceiver(myReceiver, filter);
		myFinishReceiver = new MyFinishReceiver();
		IntentFilter filterFinish = new IntentFilter();
		filterFinish.addAction("balancefinish");
		registerReceiver(myFinishReceiver, filterFinish);
		initData();
	}

	private void initView() {
		setTopView(topView);
		actionbarText.setText("余额");
		onclickLayoutRight.setText("明细");
	}

	@OnClick({R.id.onclick_layout_left, R.id.onclick_layout_right, R.id.llRecharge, R.id.llCashout})
	public void onViewClicked(View view) {
		switch (view.getId()) {
			case R.id.onclick_layout_left:
				finish();
				break;
			case R.id.onclick_layout_right:
				startActivity(new Intent(BalanceActivity.this, BalanceListActivity.class));
				break;
			case R.id.llRecharge:
				Intent intent = new Intent(BalanceActivity.this, RechargeActivity.class);
				intent.putExtra("type", "balance");
				startActivityForResult(intent, 0);
				break;
			case R.id.llCashout:
				//提现
//				if ("".equals(userBean.getAccount_no())&&"".equals(userBean.getAccount_no_wx())) {
				if ("".equals(userBean.getAccount_no())) {
					AppUtils.showDialog(BalanceActivity.this, "提示", getResources().getString(R.string.bind_account_content), new DialogClickInterface() {
						@Override
						public void onClick() {

							Intent intentAccount = new Intent(BalanceActivity.this, BindAccountActivity.class);
							intentAccount.putExtra("data", userBean);
							startActivity(intentAccount);

						}

						@Override
						public void nagtiveOnClick() {

						}
					});
				} else {
					Intent intentCashout = new Intent(BalanceActivity.this, CashOutActivity.class);
					intentCashout.putExtra("account_name", userBean.getAccount_name());
					intentCashout.putExtra("account_no", userBean.getAccount_no());
					intentCashout.putExtra("avail_amount", userBean.getAvail_amount());
					startActivity(intentCashout);
				}

				break;
		}
	}

	//获取用户数据
	private void initData() {

		if (CommonUtil.getNetworkRequest(BalanceActivity.this)) {
			HashMap<String, String> map = new HashMap<>();
			map.put("", "");
			HttpUtils.selectUserInfo(map, new Consumer<UserEntity>() {
				@Override
				public void accept(UserEntity userEntity) throws Exception {

					if (Constant.HTTP_SUCCESS_CODE.equals(userEntity.getCode())) {
						userBean = userEntity.getData().get(0);
						new Handler() {

							@Override
							public void handleMessage(Message msg) {
								account_name = userBean.getAccount_name();
								account_no = userBean.getAccount_no();
								avail_amount = userBean.getAvail_amount();
								tvBalance.setText(CommonUtil.decimalFormat(Double.parseDouble(userBean.getAccount_balance()), "0"));

							}
						}.sendEmptyMessage(0);
					} else if (Constant.HTTP_LOGINOUTTIME_CODE.equals(userEntity.getCode())) {
						CommonUtil.reLogin(PreforenceUtils.getStringData("loginInfo", "userID"), PreforenceUtils.getStringData("loginInfo", "password"), new CommonUtil.LoginCallBack() {
							@Override
							public void callBack(BaseEntity<LoginEntity> loginEntity) {
								initData();
							}

							@Override
							public void failCallBack(Throwable throwable) {

							}
						});
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

	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(myReceiver);
		unregisterReceiver(myFinishReceiver);
	}

	class MyReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			new Handler() {
				@Override
				public void handleMessage(Message msg) {
					initData();
				}
			}.sendEmptyMessageDelayed(0, 1500);
		}
	}

	class MyFinishReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			intent = new Intent();
			intent.setAction("minerefresh");
			sendBroadcast(intent);
			finish();
		}
	}

}
