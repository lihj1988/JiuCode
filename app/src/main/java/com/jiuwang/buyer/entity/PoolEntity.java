package com.jiuwang.buyer.entity;

import com.jiuwang.buyer.bean.PoolBean;

import java.util.List;

/**
 * author：lihj
 * desc：
 * Created by lihj on  2019/8/19 15:38.
 */

public class PoolEntity extends BaseResultEntity {
	List<PoolBean> data;

	public List<PoolBean> getData() {
		return data;
	}

	public void setData(List<PoolBean> data) {
		this.data = data;
	}
}
