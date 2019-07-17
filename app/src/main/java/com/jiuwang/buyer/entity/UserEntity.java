package com.jiuwang.buyer.entity;

import com.jiuwang.buyer.bean.UserBean;

import java.util.List;

/**
 * Created by lihj on 2019/7/17
 * desc:
 */

public class UserEntity extends BaseResultEntity{
	private List<UserBean> data;

	public List<UserBean> getData() {
		return data;
	}

	public void setData(List<UserBean> data) {
		this.data = data;
	}
}
