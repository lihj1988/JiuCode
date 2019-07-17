package com.jiuwang.buyer.util;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiuwang.buyer.R;




public class LoadingDialog extends Dialog {

	private TextView mTextView;
	private AnimationDrawable mAnimationDrawable;
	private ImageView mImageView;
	private Animation mAnimation;
	private Context mContext;
	public boolean isShow = false;
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (isShow) {
				dismiss();
				// ToastUtil.show(mContext, "服务器响应超时");
			}
		}
	};

	public LoadingDialog(Context context) {
		super(context, R.style.WinDialog);
		setContentView(R.layout.dialog_loading);
		this.mContext = context.getApplicationContext();
		mTextView = (TextView) findViewById(android.R.id.message);
		mImageView = (ImageView) findViewById(R.id.loading_image);
		mAnimation = AnimationUtils.loadAnimation(context, R.anim.loading);
		mAnimation.setInterpolator(new LinearInterpolator());
	}

	@Override
	public void show() {
		super.show();
		isShow = true;
//		new Thread() {
//			public void run() {
//				try {
//					Thread.sleep(1000 * 15);
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
//				if (isShow) {
//					handler.sendEmptyMessage(1);
//				}
//			};
//		}.start();
		mTextView.post(new Runnable() {
			@Override
			public void run() {
				mImageView.startAnimation(mAnimation);
			}
		});

	}

	@Override
	public void dismiss() {
		super.dismiss();
		isShow = false;
		if (mAnimationDrawable != null)
			mAnimationDrawable.stop();
	}

	public void setText(String s) {
		if (mTextView != null) {
			mTextView.setText(s);
			mTextView.setVisibility(View.VISIBLE);
		}
	}



	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			return false;
		}
		return super.onTouchEvent(event);
	}

	public void setCancelEnable(boolean enable) {
		setCanceledOnTouchOutside(enable);
		setCancelable(enable);
	}

}
