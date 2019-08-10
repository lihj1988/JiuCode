package com.jiuwang.buyer.view;

import android.support.annotation.IdRes;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;

/**
 * Created by lihj on 2019/8/10
 * desc:
 */

public class MyBaseViewHolder extends BaseViewHolder {
	public MyBaseViewHolder(View view) {
		super(view);
	}

	public BaseViewHolder setText(@IdRes int viewId, String value) {
		TextView view = getView(viewId);
		view.setText(Html.fromHtml(value));
		return this;
	}

}
