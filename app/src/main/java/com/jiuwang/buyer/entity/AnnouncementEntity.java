package com.jiuwang.buyer.entity;

import com.jiuwang.buyer.bean.AnnouncementBean;

import java.util.List;

/**
 * Created by lihj on 2019/8/10
 * desc:
 */

public class AnnouncementEntity extends BaseResultEntity {
	List<AnnouncementBean> data;

	public List<AnnouncementBean> getData() {
		return data;
	}

	public void setData(List<AnnouncementBean> data) {
		this.data = data;
	}
}
