package com.jiuwang.buyer.entity;

import com.jiuwang.buyer.bean.ProjectDetailBean;

import java.util.List;

/**
 * author：lihj
 * desc：
 * Created by lihj on  2019/8/7 16:59.
 */

public class ProjectDetailsEntity extends BaseResultEntity {
	List<ProjectDetailBean> data;

	public List<ProjectDetailBean> getData() {
		return data;
	}

	public void setData(List<ProjectDetailBean> data) {
		this.data = data;
	}
}
