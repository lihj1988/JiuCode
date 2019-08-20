package com.jiuwang.buyer.bean;

import java.io.Serializable;

/**
 * author：lihj
 * desc：
 * Created by lihj on  2019/8/20 14:38.
 */

public class LotteryRecordBean implements Serializable {

	/**
	 * id : 1999c0rc32hve3b02
	 * period : 2
	 * pool_no : 2
	 * user_cd : coder
	 * amount : 40
	 * create_user : coder
	 * create_username : 超级管理员
	 * create_time : 2019-08-19 15:09:49
	 */

	private String id;
	private String period;
	private String pool_no;
	private String user_cd;
	private String amount;
	private String create_user;
	private String create_username;
	private String create_time;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public String getPool_no() {
		return pool_no;
	}

	public void setPool_no(String pool_no) {
		this.pool_no = pool_no;
	}

	public String getUser_cd() {
		return user_cd;
	}

	public void setUser_cd(String user_cd) {
		this.user_cd = user_cd;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getCreate_user() {
		return create_user;
	}

	public void setCreate_user(String create_user) {
		this.create_user = create_user;
	}

	public String getCreate_username() {
		return create_username;
	}

	public void setCreate_username(String create_username) {
		this.create_username = create_username;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
}
