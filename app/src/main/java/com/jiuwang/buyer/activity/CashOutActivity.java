package com.jiuwang.buyer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jiuwang.buyer.R;
import com.jiuwang.buyer.appinterface.DialogClickInterface;
import com.jiuwang.buyer.base.BaseActivity;
import com.jiuwang.buyer.bean.UserBean;
import com.jiuwang.buyer.constant.Constant;
import com.jiuwang.buyer.entity.BaseResultEntity;
import com.jiuwang.buyer.net.CommonHttpUtils;
import com.jiuwang.buyer.net.HttpUtils;
import com.jiuwang.buyer.util.AppUtils;
import com.jiuwang.buyer.util.CommonUtil;
import com.jiuwang.buyer.util.LoadingDialog;
import com.jiuwang.buyer.util.MyToastView;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;

/**
 * author：lihj
 * desc：
 * Created by lihj on  2019/7/23 16:28.
 */

public class CashOutActivity extends BaseActivity {
	private static final String TAG = CashOutActivity.class.getName();
	@Bind(R.id.topView)
	LinearLayout topView;
	@Bind(R.id.actionbar_text)
	TextView actionbarText;
	@Bind(R.id.onclick_layout_left)
	RelativeLayout onclickLayoutLeft;
	@Bind(R.id.onclick_layout_right)
	Button onclickLayoutRight;
	@Bind(R.id.etName)
	EditText etName;
	@Bind(R.id.etAccount)
	EditText etAccount;
	@Bind(R.id.etMoney)
	EditText etMoney;
	@Bind(R.id.btnNext)
	TextView btnNext;
	@Bind(R.id.tvAll)
	TextView tvAll;
	private String avail_amount;
	private String account_name;
	private String account_no;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cash_out);
		ButterKnife.bind(this);
		selectUser();
	}

	private void selectUser() {
		Intent intent = getIntent();
		 account_name = intent.getStringExtra("account_name");
		 account_no = intent.getStringExtra("account_no");
		 avail_amount = intent.getStringExtra("avail_amount");
		initView();
//		final LoadingDialog loadingDialog = AppUtils.setDialog_wait(this, "1");
//		HashMap<String, String> map = new HashMap<>();
//		CommonHttpUtils.selectUserInfo(map, new CommonHttpUtils.UserCallBack() {
//			@Override
//			public void successBack(UserBean userBean) {
//				avail_amount = userBean.getAvail_amount();
//				etMoney.setHint("可提现金额" + avail_amount + "元");
//				account_name = userBean.getAccount_name();
//				account_no = userBean.getAccount_no();
//				initView();
//				loadingDialog.dismiss();
//			}
//
//			@Override
//			public void failBack() {
//				loadingDialog.dismiss();
//			}
//		});
	}

	private void initView() {
		setTopView(topView);
		actionbarText.setText("支付宝提现");
		onclickLayoutRight.setVisibility(View.INVISIBLE);
		etMoney.setHint("可提现金额" + avail_amount + "元");
		if (account_name==null||"".equals(account_name)) {
			etName.setEnabled(true);
		} else {
			etName.setEnabled(false);
			etName.setText(account_name);
		}
		if (account_no==null||"".equals(account_no)) {
			etAccount.setEnabled(true);
		} else {
			etAccount.setEnabled(false);
			etAccount.setText(account_no);
		}

	}

	@OnClick({R.id.onclick_layout_left, R.id.btnNext, R.id.tvAll})
	public void onViewClicked(View view) {
		switch (view.getId()) {
			case R.id.onclick_layout_left:
				finish();
				break;
			case R.id.btnNext:

				//执行提现
				if (etName.getText().toString().trim().equals("")) {
					MyToastView.showToast("请填写姓名", CashOutActivity.this);
					return;
				}
				if (etAccount.getText().toString().trim().equals("")) {
					MyToastView.showToast("请填写提现账号", CashOutActivity.this);
					return;
				}
				if (etMoney.getText().toString().trim().equals("")) {
					MyToastView.showToast("请填写提现金额", CashOutActivity.this);
					return;
				}
				String money = etMoney.getText().toString().trim();
				if (Double.parseDouble(money) > Double.parseDouble(avail_amount)) {
					MyToastView.showToast("提现金额不能大于可提现金额", CashOutActivity.this);
					return;
				}
				if (!CommonUtil.checkMoney(money, CashOutActivity.this)) return;
				AppUtils.showDialog(CashOutActivity.this, "提示", getResources().getString(R.string.cash_out_content), new DialogClickInterface() {
					@Override
					public void onClick() {
						cashOut();

					}

					@Override
					public void nagtiveOnClick() {

					}
				});

				break;
			case R.id.tvAll:
				//执行提现
				etMoney.setText(avail_amount + "");
				break;
		}
	}

	private void cashOut() {
		if (CommonUtil.getNetworkRequest(CashOutActivity.this)) {
			final LoadingDialog loadingDialog = AppUtils.setDialog_wait(this, "1");
			HashMap<String, String> map = new HashMap<>();
			map.put("act", Constant.ACTION_ACT_ADD);
			map.put("amount", etMoney.getText().toString().trim());
			map.put("pay_mode", Constant.PAY_MODE_ALI);
			HttpUtils.cash(map, new Consumer<BaseResultEntity>() {
				@Override
				public void accept(BaseResultEntity baseResultEntity) throws Exception {
					loadingDialog.dismiss();
					if (Constant.HTTP_SUCCESS_CODE.equals(baseResultEntity.getCode())) {
						Intent intent = new Intent();
						intent.setAction("minerefresh");
						sendBroadcast(intent);
						intent.setAction("balancerefresh");
						sendBroadcast(intent);
						intent.setAction("balancefinish");
						sendBroadcast(intent);
						finish();
					} else if (Constant.HTTP_LOGINOUTTIME_CODE.equals(baseResultEntity.getCode())) {
						startActivity(new Intent(CashOutActivity.this, LoginActivity.class));
						finish();
					}
					MyToastView.showToast(baseResultEntity.getMsg(), CashOutActivity.this);
				}
			}, new Consumer<Throwable>() {
				@Override
				public void accept(Throwable throwable) throws Exception {
					loadingDialog.dismiss();
					MyToastView.showToast(getResources().getString(R.string.msg_error_operation), CashOutActivity.this);
				}
			});
		}
	}
}
