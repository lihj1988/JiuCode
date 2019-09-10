package com.jiuwang.buyer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jiuwang.buyer.R;
import com.jiuwang.buyer.appinterface.DialogClickInterface;
import com.jiuwang.buyer.base.BaseActivity;
import com.jiuwang.buyer.base.MyApplication;
import com.jiuwang.buyer.bean.UserBean;
import com.jiuwang.buyer.constant.Constant;
import com.jiuwang.buyer.constant.NetURL;
import com.jiuwang.buyer.entity.BaseEntity;
import com.jiuwang.buyer.entity.BaseResultEntity;
import com.jiuwang.buyer.entity.LoginEntity;
import com.jiuwang.buyer.net.CommonHttpUtils;
import com.jiuwang.buyer.net.HttpUtils;
import com.jiuwang.buyer.util.AppUtils;
import com.jiuwang.buyer.util.CommonUtil;
import com.jiuwang.buyer.util.LoadingDialog;
import com.jiuwang.buyer.util.MyToastView;
import com.jiuwang.buyer.util.PreforenceUtils;

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
	@Bind(R.id.tvAccountTextName)
	TextView tvAccountTextName;
	@Bind(R.id.tvAll)
	TextView tvAll;
	@Bind(R.id.rbAli)
	RadioButton rbAli;
	@Bind(R.id.rbWX)
	RadioButton rbWX;
	@Bind(R.id.tvNotice)
	TextView tvNotice;
	@Bind(R.id.ivWebChat)
	ImageView ivWebChat;
	private String avail_amount;
	private String account_name;
	private String account_name_wx;
	private String account_no_wx;
	private String account_no;
	private String payMode;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cash_out);
		ButterKnife.bind(this);
		selectUser();
	}

	private void selectUser() {
//		Intent intent = getIntent();
//		 account_name = intent.getStringExtra("account_name");
//		 account_no = intent.getStringExtra("account_no");
//		 avail_amount = intent.getStringExtra("avail_amount");
//		initView();
		final LoadingDialog loadingDialog = AppUtils.setDialog_wait(this, "1");
		HashMap<String, String> map = new HashMap<>();
		CommonHttpUtils.selectUserInfo(map, new CommonHttpUtils.UserCallBack() {
			@Override
			public void successBack(UserBean userBean) {
				avail_amount = userBean.getAvail_amount();
				etMoney.setHint("可提现金额" + avail_amount + "元");
				account_name = userBean.getAccount_name();
				account_no = userBean.getAccount_no();
				MyApplication.getInstance().status = userBean.getStatus();
				account_no_wx = userBean.getAccount_no_wx();
				account_name_wx = userBean.getAccount_name_wx();
				initView();
				loadingDialog.dismiss();
			}

			@Override
			public void failBack() {
				loadingDialog.dismiss();
			}
		});
	}

	private void initView() {
		setTopView(topView);
		actionbarText.setText("提现");
		onclickLayoutRight.setVisibility(View.INVISIBLE);
		etMoney.setHint("可提现金额" + avail_amount + "元");


//		if ((account_name != null && !"".equals(account_name)) && (account_name_wx != null && !"".equals(account_name_wx))) {
//			payMode = Constant.PAY_MODE_ALI;
//			rbAli.setChecked(true);
//			etName.setEnabled(false);
//			etName.setText(account_name);
//			etAccount.setEnabled(false);
//			etAccount.setText(account_no);
//		} else {
		if (account_name == null || "".equals(account_name)) {
			rbAli.setVisibility(View.GONE);
			etName.setEnabled(false);
		} else {
			payMode = Constant.PAY_MODE_ALI;
			rbAli.setChecked(true);
			etName.setEnabled(false);
			etName.setText(account_name);
		}

		if (account_no == null || "".equals(account_no)) {
			etAccount.setEnabled(false);
		} else {
			etAccount.setEnabled(false);
			etAccount.setText(account_no);
		}
		CommonUtil.loadImageWithOutCache(CashOutActivity.this, NetURL.BASEURL+MyApplication.getInstance().webchat,ivWebChat);
		tvNotice.setText(MyApplication.getInstance().notes);
//			if (account_name_wx == null || "".equals(account_name_wx)) {
//				rbWX.setVisibility(View.GONE);
//				etName.setEnabled(false);
//			} else {
//				payMode = Constant.PAY_MODE_WX;
//				rbWX.setChecked(true);
//				etName.setEnabled(false);
//				etName.setText(account_name_wx);
//			}
//
//			if (account_no_wx == null || "".equals(account_no_wx)) {
//				etAccount.setEnabled(false);
//			} else {
//				etAccount.setEnabled(false);
//				etAccount.setText(account_no_wx);
//			}
//		}

		rbAli.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
				if (b) {
					rbWX.setChecked(false);
					payMode = Constant.PAY_MODE_ALI;
					tvAccountTextName.setText("支付宝账号：");
					etAccount.setHint("请输入真实的支付宝账号");
					etName.setText(account_name);
					etAccount.setText(account_no);
				}
			}
		});
		rbWX.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
				if (b) {
					rbAli.setChecked(false);
					payMode = Constant.PAY_MODE_WX;
					tvAccountTextName.setText("微信账号：");
					etAccount.setHint("请输入真实的微信账号");
					etName.setText(account_name_wx);
					etAccount.setText(account_no_wx);
				}
			}
		});


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
				if (!(Double.parseDouble(money) > 0)) {
					MyToastView.showToast("提现金额必须大于零", CashOutActivity.this);
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
			map.put("pay_mode", payMode);
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
						CommonUtil.reLogin(PreforenceUtils.getStringData("loginInfo", "userID"), PreforenceUtils.getStringData("loginInfo", "password"), new CommonUtil.LoginCallBack() {
							@Override
							public void callBack(BaseEntity<LoginEntity> loginEntity) {
								cashOut();
							}

							@Override
							public void failCallBack(Throwable throwable) {

							}
						});
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
