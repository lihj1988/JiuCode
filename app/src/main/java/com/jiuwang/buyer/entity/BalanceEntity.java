package com.jiuwang.buyer.entity;

import com.jiuwang.buyer.bean.BalanceBean;

import java.util.List;

/**
 * Created by lihj on 2019/7/18
 * desc:
 */

public class BalanceEntity extends BaseResultEntity {
	private List<BalanceBean> data;

	public List<BalanceBean> getData() {
		return data;
	}

	public void setData(List<BalanceBean> data) {
		this.data = data;
	}
}
