package com.jiuwang.buyer.activity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jiuwang.buyer.R;
import com.jiuwang.buyer.base.BaseActivity;
import com.jiuwang.buyer.popupwindow.RechargePopupWindow;
import com.jiuwang.buyer.util.CommonUtil;
import com.jiuwang.buyer.util.MyToastView;

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
	private View rootView;
	private RechargePopupWindow rechargePopupWindow;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recharge);
		rootView = View.inflate(RechargeActivity.this,R.layout.activity_recharge,null);
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
						String[] split = money.split(".");
						if(split.length==2){
							if(split[1].length()>2){
								MyToastView.showToast("充值金额最小单位为分" ,RechargeActivity.this);
								return;
							}
						}
						//弹出支付方式窗口
						rechargePopupWindow = new RechargePopupWindow(RechargeActivity.this);
						// 显示窗口
						rechargePopupWindow.showAtLocation(rootView, Gravity.BOTTOM
								| Gravity.CENTER_HORIZONTAL, 0, 0); // 设置layout在PopupWindow中显示的位置
					}
				}else {
					MyToastView.showToast("请输入充值金额" ,RechargeActivity.this);
				}

				break;
		}
	}
}
