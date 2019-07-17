package com.jiuwang.buyer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jiuwang.buyer.R;
import com.jiuwang.buyer.base.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.jiuwang.buyer.R.id.actionbar_text;

/**
 * author：lihj
 * desc：
 * Created by lihj on  2019/7/17 09:43.
 */

public class OrderPayCompleteActivity extends BaseActivity {
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
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pay_complete);
		ButterKnife.bind(this);
		initData();
		initView();

	}

	private void initData() {
		Intent intent = getIntent();
		totalAmount = intent.getStringExtra("totalAmount");
		String info =  "订单金额 <font color='#FF5001'>" + totalAmount + " 元"+ "</font>";
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
}
