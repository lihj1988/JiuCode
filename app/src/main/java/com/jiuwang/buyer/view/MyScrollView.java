package com.jiuwang.buyer.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * Created by lihj on 2019/7/25
 * desc:
 */

public class MyScrollView extends ScrollView {
	public MyScrollView(Context context) {
		this(context,null);
	}
	public MyScrollView(Context context, AttributeSet attrs) {
		this(context, attrs,0);
	}
	public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		//关键点在这
		getParent().requestDisallowInterceptTouchEvent(true);
		return super.onInterceptTouchEvent(ev);
	}
}
