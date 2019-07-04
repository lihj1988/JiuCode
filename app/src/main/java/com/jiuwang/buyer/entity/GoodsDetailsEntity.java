package com.jiuwang.buyer.entity;

import com.jiuwang.buyer.bean.GoodsDetailsBean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lihj on 2019/7/1
 * desc:
 */

public class GoodsDetailsEntity extends BaseResultEntity implements Serializable {
	private List<GoodsDetailsBean> data;

	public List<GoodsDetailsBean> getData() {
		return data;
	}

	public void setData(List<GoodsDetailsBean> data) {
		this.data = data;
	}
}
