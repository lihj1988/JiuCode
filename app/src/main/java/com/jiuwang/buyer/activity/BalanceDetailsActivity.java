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
import com.jiuwang.buyer.bean.BalanceBean;
import com.jiuwang.buyer.util.CommonUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by lihj on 2019/7/18
 * desc:
 */

public class BalanceDetailsActivity extends BaseActivity {
	private static final String TAG = BalanceDetailsActivity.class.getName();
	@Bind(R.id.topView)
	LinearLayout topView;
	@Bind(R.id.actionbar_text)
	TextView actionbarText;
	@Bind(R.id.onclick_layout_left)
	RelativeLayout onclickLayoutLeft;
	@Bind(R.id.onclick_layout_right)
	Button onclickLayoutRight;
	@Bind(R.id.tvNo)
	TextView tvNo;
	@Bind(R.id.tvFundType)
	TextView tvFundType;
	@Bind(R.id.tvAmount)
	TextView tvAmount;
	@Bind(R.id.tvPayMode)
	TextView tvPayMode;
	@Bind(R.id.tvTime)
	TextView tvTime;
	@Bind(R.id.tvNotes)
	TextView tvNotes;
	private List<BalanceBean> balanceList;
	private BalanceBean data;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_balance_details);
		ButterKnife.bind(this);
		balanceList = new ArrayList<>();
		initView();
		initData();

	}

	private void initData() {
		Intent intent = getIntent();
		data = (BalanceBean) intent.getSerializableExtra("data");
		tvNo.setText(data.getOrder_id());
		tvFundType.setText(data.getOrder_id());
		tvAmount.setText(CommonUtil.decimalFormat(Double.parseDouble(data.getAmount()),"0"));
		tvPayMode.setText(data.getOrder_id());
		tvTime.setText(data.getCreate_time());
	}


	private void initView() {
		setTopView(topView);
		actionbarText.setText("详情");
		onclickLayoutRight.setVisibility(View.INVISIBLE);

	}

	@OnClick(R.id.onclick_layout_left)
	public void onViewClicked() {
		finish();
	}


}
