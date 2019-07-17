package com.jiuwang.buyer.popupwindow;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.jiuwang.buyer.R;
import com.jiuwang.buyer.util.MyToastView;

/**
 * author：lihj
 * desc：充值
 * Created by lihj on  2019/7/4 14:15.
 */

public class RechargePopupWindow extends PopupWindow {

	private WindowManager.LayoutParams params;
	private View mMenuView;
	private Activity context;
	private TextView aliPay, wxPay, cancle;


	public RechargePopupWindow(Activity context) {
		super(context);
		this.context = context;
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mMenuView = inflater.inflate(R.layout.popup_recharge, null);
		setContentView(mMenuView);
		this.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
		this.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
		setAnimationStyle(R.style.ActionSheetDialogAnimation);
		// 实例化一个ColorDrawable颜色为半透明
		ColorDrawable dw = new ColorDrawable(0xb0000000);
		// 设置SelectPicPopupWindow弹出窗体的背景
		setBackgroundDrawable(dw);
		setFocusable(true);
		setOutsideTouchable(true);
		params = this.context.getWindow().getAttributes();
		// 当弹出Popupwindow时，背景变半透明
		params.alpha = 0.7f;
		this.context.getWindow().setAttributes(params);
		setOnDismissListener(new PopupWindow.OnDismissListener() {
			@Override
			public void onDismiss() {
				params = RechargePopupWindow.this.context.getWindow()
						.getAttributes();
				params.alpha = 1f;
				RechargePopupWindow.this.context.getWindow().setAttributes(
						params);
			}
		});

		initView(mMenuView);
	}

	private void initView(View mMenuView) {
		aliPay = mMenuView.findViewById(R.id.aliPay);
		wxPay = mMenuView.findViewById(R.id.wxPay);
		cancle = mMenuView.findViewById(R.id.cancle);

		aliPay.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//支付宝支付
				MyToastView.showToast("支付宝支付" ,context);

			}
		});
		wxPay.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//微信支付
				MyToastView.showToast("微信支付" ,context);
			}
		});
		cancle.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dismiss();
			}
		});
	}
}
