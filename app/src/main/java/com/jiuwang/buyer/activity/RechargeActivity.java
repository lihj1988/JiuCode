package com.jiuwang.buyer.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jiuwang.buyer.R;
import com.jiuwang.buyer.base.BaseActivity;
import com.jiuwang.buyer.util.CommonUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.jiuwang.buyer.R.id.return_img;

/**
 * author：lihj
 * desc：充值页面
 * Created by lihj on  2019/7/4 17:02.
 */

public class RechargeActivity extends BaseActivity {
	@Bind(R.id.topView)
	LinearLayout topView;
	@Bind(R.id.actionbar_text)
	TextView actionbarText;
	@Bind(return_img)
	ImageView returnImg;
	@Bind(R.id.onclick_layout_left)
	RelativeLayout onclickLayoutLeft;
	@Bind(R.id.onclick_layout_right)
	Button onclickLayoutRight;
	@Bind(R.id.etMoney)
	EditText etMoney;
	@Bind(R.id.iv_find)
	ImageView ivFind;
	@Bind(R.id.btnNext)
	TextView btnNext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recharge);
		ButterKnife.bind(this);
		initView();
	}

	private void initView() {
		setTopView(topView);
		actionbarText.setText("充值");
		onclickLayoutRight.setVisibility(View.INVISIBLE);
	}

	@OnClick({R.id.onclick_layout_left, R.id.btnNext})
	public void onViewClicked(View view) {
		switch (view.getId()) {
			case R.id.onclick_layout_left:
				finish();
				break;
			case R.id.btnNext:
				String money = etMoney.getText().toString();
				if (!"".equals(money)) {
					if (CommonUtil.numberCheck(money)) {
						//弹出支付方式窗口

					}
				}

				break;
		}
	}
}
