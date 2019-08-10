package com.jiuwang.buyer.bean;

import java.io.Serializable;

/**
 * Created by lihj on 2019/8/10
 * desc:
 */

public class InviteUserBean implements Serializable {

	/**
	 * user_cd : 17303968099
	 * invite_user : 18500997554
	 * create_user :
	 * create_username :
	 * create_time : 2019-08-10 14:30:55
	 * r : 1
	 */

	private String user_cd;
	private String invite_user;
	private String create_user;
	private String create_username;
	private String create_time;
	private int r;

	public String getUser_cd() {
		return user_cd;
	}

	public void setUser_cd(String user_cd) {
		this.user_cd = user_cd;
	}

	public String getInvite_user() {
		return invite_user;
	}

	public void setInvite_user(String invite_user) {
		this.invite_user = invite_user;
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

	public int getR() {
		return r;
	}

	public void setR(int r) {
		this.r = r;
	}
}
