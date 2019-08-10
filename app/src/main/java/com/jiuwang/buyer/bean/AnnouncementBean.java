package com.jiuwang.buyer.bean;

import java.io.Serializable;

/**
 * Created by lihj on 2019/8/10
 * desc:
 */

public class AnnouncementBean implements Serializable {

	/**
	 * user_cd : 18500997554
	 * amount : 949
	 * project_name :
	 * goods_name :
	 * announce_type : 1
	 * r : 1
	 */

	private String user_cd;
	private int amount;
	private String project_name;
	private String goods_name;
	private String announce_type;
	private int r;

	public String getUser_cd() {
		return user_cd;
	}

	public void setUser_cd(String user_cd) {
		this.user_cd = user_cd;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public String getProject_name() {
		return project_name;
	}

	public void setProject_name(String project_name) {
		this.project_name = project_name;
	}

	public String getGoods_name() {
		return goods_name;
	}

	public void setGoods_name(String goods_name) {
		this.goods_name = goods_name;
	}

	public String getAnnounce_type() {
		return announce_type;
	}

	public void setAnnounce_type(String announce_type) {
		this.announce_type = announce_type;
	}

	public int getR() {
		return r;
	}

	public void setR(int r) {
		this.r = r;
	}
}
