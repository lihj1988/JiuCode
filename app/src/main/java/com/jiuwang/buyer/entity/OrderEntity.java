package com.jiuwang.buyer.entity;

import com.jiuwang.buyer.bean.OrderBean;

import java.util.List;

/**
 * author：lihj
 * desc：
 * Created by lihj on  2019/7/15 14:06.
 */

public class OrderEntity extends BaseResultEntity {
	private List<OrderBean> data;

	public List<OrderBean> getData() {
		return data;
	}

	public void setData(List<OrderBean> date) {
		this.data = date;
	}
}
