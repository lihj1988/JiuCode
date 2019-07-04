package com.jiuwang.buyer.entity;

import com.jiuwang.buyer.bean.GoodsBean;

import java.util.List;

/**
 * Created by lihj on 2019/7/1
 * desc:
 */

public class HomeResultEntity extends BaseResultEntity {
	private List<String> pic_list;
	private List<GoodsBean> data;

	public List<String> getPic_list() {
		return pic_list;
	}

	public void setPic_list(List<String> pic_list) {
		this.pic_list = pic_list;
	}

	public List<GoodsBean> getData() {
		return data;
	}

	public void setData(List<GoodsBean> data) {
		this.data = data;
	}
}
