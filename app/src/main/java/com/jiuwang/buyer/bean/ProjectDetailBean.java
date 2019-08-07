package com.jiuwang.buyer.bean;

import java.io.Serializable;

/**
 * author：lihj
 * desc：
 * Created by lihj on  2019/8/7 17:00.
 */

public class ProjectDetailBean implements Serializable {

	/**
	 * id : 12654
	 * is_win : 0
	 * aution_id : A2019080700002440
	 * create_user : 13845900321
	 * create_username : sys
	 * create_time : 2019-08-07 16:30:53
	 * goods_id :
	 * goods_name :
	 * status_name : 否
	 */

	private String id;
	private String is_win;
	private String aution_id;
	private String create_user;
	private String create_username;
	private String create_time;
	private String goods_id;
	private String goods_name;
	private String status_name;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIs_win() {
		return is_win;
	}

	public void setIs_win(String is_win) {
		this.is_win = is_win;
	}

	public String getAution_id() {
		return aution_id;
	}

	public void setAution_id(String aution_id) {
		this.aution_id = aution_id;
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

	public String getGoods_id() {
		return goods_id;
	}

	public void setGoods_id(String goods_id) {
		this.goods_id = goods_id;
	}

	public String getGoods_name() {
		return goods_name;
	}

	public void setGoods_name(String goods_name) {
		this.goods_name = goods_name;
	}

	public String getStatus_name() {
		return status_name;
	}

	public void setStatus_name(String status_name) {
		this.status_name = status_name;
	}
}
