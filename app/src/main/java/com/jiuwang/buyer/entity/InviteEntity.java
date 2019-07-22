package com.jiuwang.buyer.entity;

import com.jiuwang.buyer.bean.InviteBean;

import java.util.List;

/**
 * Created by lihj on 2019/7/23
 * desc:
 */

public class InviteEntity extends BaseResultEntity {
	List<InviteBean> data;

	public List<InviteBean> getData() {
		return data;
	}

	public void setData(List<InviteBean> data) {
		this.data = data;
	}
}
