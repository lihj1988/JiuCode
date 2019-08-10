package com.jiuwang.buyer.entity;

import com.jiuwang.buyer.bean.InviteUserBean;

import java.util.List;

/**
 * Created by lihj on 2019/8/10
 * desc:
 */

public class InviteUserEntity extends BaseResultEntity {
	List<InviteUserBean> data ;

	public List<InviteUserBean> getData() {
		return data;
	}

	public void setData(List<InviteUserBean> data) {
		this.data = data;
	}
}
