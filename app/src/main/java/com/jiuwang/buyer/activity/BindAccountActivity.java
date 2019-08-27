package com.jiuwang.buyer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jiuwang.buyer.R;
import com.jiuwang.buyer.base.BaseActivity;
import com.jiuwang.buyer.bean.UserBean;
import com.jiuwang.buyer.constant.Constant;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by lihj on 2019/7/22
 * desc:
 */

public class BindAccountActivity extends BaseActivity {
	@Bind(R.id.topView)
	LinearLayout topView;
	@Bind(R.id.actionbar_text)
	TextView actionbarText;
	@Bind(R.id.onclick_layout_left)
	RelativeLayout onclickLayoutLeft;
	@Bind(R.id.onclick_layout_right)
	Button onclickLayoutRight;
	@Bind(R.id.tvAliName)
	TextView tvAliName;
	@Bind(R.id.tvAliAccount)
	TextView tvAliAccount;
	@Bind(R.id.tvEditALi)
	TextView tvEditALi;
	@Bind(R.id.tvWXName)
	TextView tvWXName;
	@Bind(R.id.tvWXAccount)
	TextView tvWXAccount;
	@Bind(R.id.tvEditWx)
	TextView tvEditWx;
	private UserBean userBean;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bind_account);
		ButterKnife.bind(this);
		intent.setClass(BindAccountActivity.this, EditAccountActivity.class);
		initData();
		initView();

	}

	private void initData() {
		Intent intent = getIntent();
		userBean = (UserBean) intent.getSerializableExtra("data");
	}

	private void initView() {
		setTopView(topView);
		actionbarText.setText("我的账户");
		onclickLayoutRight.setVisibility(View.INVISIBLE);
		tvAliAccount.setText(userBean.getAccount_no());
		tvAliName.setText(userBean.getAccount_name());

		tvWXAccount.setText(userBean.getAccount_no_wx());
		tvWXName.setText(userBean.getAccount_name_wx());

		tvEditWx.setText("");
		if (tvAliAccount.getText().toString().equals("")) {
			tvEditALi.setText("去绑定");
		} else {
			tvEditALi.setVisibility(View.INVISIBLE);
		}
		if (tvWXAccount.getText().toString().equals("")) {
			tvEditWx.setText("去绑定");
		} else {
			tvEditWx.setVisibility(View.INVISIBLE);
		}
	}

	private Intent intent = new Intent();

	@OnClick({R.id.onclick_layout_left, R.id.tvEditALi, R.id.tvEditWx})
	public void onViewClicked(View view) {
		switch (view.getId()) {
			case R.id.onclick_layout_left:
				finish();
				break;
			case R.id.tvEditALi:
				intent.putExtra("accountType", Constant.PAY_MODE_ALI);//账户类型 0 支付宝
				if ("去绑定".equals(tvEditALi.getText().toString().trim())) {
					intent.putExtra("editType", "0");//编辑类型 0 绑定
				} else {
					intent.putExtra("editType", "1");//编辑类型 1 修改
					intent.putExtra("account", tvAliAccount.getText().toString().trim());//账号
				}
				startActivityForResult(intent, Constant.CODE_ACCOUNT_ALI);
				break;
			case R.id.tvEditWx:
				intent.putExtra("accountType", Constant.PAY_MODE_WX);//账户类型 1 微信
				if ("去绑定".equals(tvEditWx.getText().toString().trim())) {
					intent.putExtra("editType", "0");
				} else {
					intent.putExtra("editType", "1");
					intent.putExtra("account", tvWXAccount.getText().toString().trim());//账号
				}
				startActivityForResult(intent, Constant.CODE_ACCOUNT_WX);
				break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Intent intent = new Intent();
		intent.setAction("minerefresh");
		sendBroadcast(intent);
		intent.setAction("balancerefresh");
		sendBroadcast(intent);
		if (resultCode == RESULT_OK) {
			switch (requestCode) {
				case Constant.CODE_ACCOUNT_ALI:
					tvAliAccount.setText(data.getStringExtra("account"));
					tvAliName.setText(data.getStringExtra("account_name"));
					tvEditALi.setVisibility(View.INVISIBLE);

					break;
				case Constant.CODE_ACCOUNT_WX:
					tvWXAccount.setText(data.getStringExtra("account"));
					tvWXName.setText(data.getStringExtra("account_name"));
					tvEditWx.setVisibility(View.INVISIBLE);
//					Intent intent1 = new Intent();
//					intent1.setAction("minerefresh");
//					sendBroadcast(intent1);
//					intent1.setAction("balancerefresh");
//					sendBroadcast(intent1);
					break;
				default:

			}
		}
	}
}
