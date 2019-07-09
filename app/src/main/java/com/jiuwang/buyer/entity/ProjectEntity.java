package com.jiuwang.buyer.entity;

import com.jiuwang.buyer.bean.ProjectBean;

import java.util.List;

/**
 * author：lihj
 * desc：
 * Created by lihj on  2019/7/9 14:49.
 */

public class ProjectEntity extends BaseResultEntity{
	List<ProjectBean> data;

	public List<ProjectBean> getData() {
		return data;
	}

	public void setData(List<ProjectBean> data) {
		this.data = data;
	}
}
