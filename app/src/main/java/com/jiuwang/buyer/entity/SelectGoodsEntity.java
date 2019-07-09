package com.jiuwang.buyer.entity;

import android.widget.LinearLayout;

import com.jiuwang.buyer.bean.SelectGoodsBean;

import java.util.List;

/**
 * Created by lihj on 2019/7/9
 * desc:
 */

public class SelectGoodsEntity extends BaseResultEntity {
	private List<SelectGoodsBean> data;

	public List<SelectGoodsBean> getData() {
		return data;
	}

	public void setData(List<SelectGoodsBean> data) {
		this.data = data;
	}
}
