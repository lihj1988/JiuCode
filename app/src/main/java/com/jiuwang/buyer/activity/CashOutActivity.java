package com.jiuwang.buyer.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jiuwang.buyer.R;
import com.jiuwang.buyer.base.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * author：lihj
 * desc：
 * Created by lihj on  2019/7/23 16:28.
 */

public class CashOutActivity extends BaseActivity {
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cash_out);
		ButterKnife.bind(this);
		initView();
	}

	private void initView() {
		setTopView(topView);
		actionbarText.setText("支付宝提现");
		onclickLayoutRight.setVisibility(View.INVISIBLE);
	}

	@OnClick({R.id.onclick_layout_left, R.id.btnNext})
	public void onViewClicked(View view) {
		switch (view.getId()) {
			case R.id.onclick_layout_left:
				finish();
				break;
			case R.id.btnNext:
				//执行提现
				break;
		}
	}
}
