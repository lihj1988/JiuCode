package com.jiuwang.buyer.entity;

import com.jiuwang.buyer.bean.CarBean;

import java.util.List;

/**
 * Created by lihj on 2019/7/2
 * desc:
 */

public class MyCarEntity extends BaseResultEntity {
	private List<CarBean> data;

	public List<CarBean> getData() {
		return data;
	}

	public void setData(List<CarBean> data) {
		this.data = data;
	}
}
