package com.jiuwang.buyer.view;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.util.Log;

import com.jcodecraeer.xrecyclerview.XRecyclerView;

/**
 * author：lihj
 * desc：
 * Created by lihj on  2019/7/3 14:59.
 */

public class TestXRecyclerView extends XRecyclerView {
	public TestXRecyclerView(Context context) {
		super(context);
	}

	public TestXRecyclerView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public TestXRecyclerView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	@Override
	public void onScrollStateChanged(int state) {
        super.onScrollStateChanged(state);
		if (state == RecyclerView.SCROLL_STATE_IDLE) {
			Log.i("log" , "==是否调用加载更多方法1==");
			LayoutManager layoutManager = getLayoutManager();
			int lastVisibleItemPosition;
			if (layoutManager instanceof GridLayoutManager) {
				lastVisibleItemPosition = ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
			} else if (layoutManager instanceof StaggeredGridLayoutManager) {
				int[] into = new int[((StaggeredGridLayoutManager) layoutManager).getSpanCount()];
				((StaggeredGridLayoutManager) layoutManager).findLastVisibleItemPositions(into);
				lastVisibleItemPosition = 20;
			} else {
				lastVisibleItemPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
			}
			Log.i("log" , "==是否调用加载更多方法2layoutManager.getItemCount()=="+layoutManager.getItemCount() );
			Log.i("log" , "==是否调用加载更多方法2layoutManager.getChildCount()=="+layoutManager.getChildCount() );
			if (layoutManager.getChildCount() > 0
					&& lastVisibleItemPosition >= layoutManager.getItemCount() - 1 && layoutManager.getItemCount() > layoutManager.getChildCount()) {

				Log.i("log" , "==是否调用加载更多方法2==");
			}
		}
	}

}
