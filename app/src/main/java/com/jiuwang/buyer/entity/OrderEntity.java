package com.jiuwang.buyer.entity;

import com.jiuwang.buyer.bean.OrderBean;

import java.io.Serializable;
import java.util.List;

/**
 * author：lihj
 * desc：
 * Created by lihj on  2019/7/15 14:06.
 */

public class OrderEntity extends BaseResultEntity implements Serializable {
	private List<OrderBean> data;

	public List<OrderBean> getDate() {
		return data;
	}

	public void setDate(List<OrderBean> date) {
		this.data = date;
	}
}
