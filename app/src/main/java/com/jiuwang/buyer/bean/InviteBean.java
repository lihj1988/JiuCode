package com.jiuwang.buyer.bean;

import java.io.Serializable;

/**
 * Created by lihj on 2019/7/23
 * desc:
 */

public class InviteBean implements Serializable {


	/**
	 * user_cd : 18500997554
	 * total_count : 1
	 * project_count :
	 */

	private String user_cd;
	private String total_count;
	private String project_count;

	public String getUser_cd() {
		return user_cd;
	}

	public void setUser_cd(String user_cd) {
		this.user_cd = user_cd;
	}

	public String getTotal_count() {
		return total_count;
	}

	public void setTotal_count(String total_count) {
		this.total_count = total_count;
	}

	public String getProject_count() {
		return project_count;
	}

	public void setProject_count(String project_count) {
		this.project_count = project_count;
	}
}
