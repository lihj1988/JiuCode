package com.jiuwang.buyer.bean;

import java.io.Serializable;

/**
 * Created by lihj on 2019/7/18
 * desc: 资金
 */

public class BalanceBean implements Serializable {

	/**
	 * id : 20190718220206911
	 * amount : 50
	 * order_id : 20190718220154192
	 * grouping_cd : 1999c0r991hqi2h01
	 * pay_mode : 1
	 * create_user : sys
	 * create_username : sys
	 * create_time : 2019-07-18 22:02:06
	 * grouping_cd_seller :
	 * fund_type : 3
	 * r : 3
	 */

	private String id;
	private String amount;
	private String order_id;
	private String grouping_cd;
	private String pay_mode;
	private String create_user;
	private String create_username;
	private String create_time;
	private String grouping_cd_seller;
	private String fund_type;
	private int r;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getGrouping_cd() {
		return grouping_cd;
	}

	public void setGrouping_cd(String grouping_cd) {
		this.grouping_cd = grouping_cd;
	}

	public String getPay_mode() {
		return pay_mode;
	}

	public void setPay_mode(String pay_mode) {
		this.pay_mode = pay_mode;
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

	public String getGrouping_cd_seller() {
		return grouping_cd_seller;
	}

	public void setGrouping_cd_seller(String grouping_cd_seller) {
		this.grouping_cd_seller = grouping_cd_seller;
	}

	public String getFund_type() {
		return fund_type;
	}

	public void setFund_type(String fund_type) {
		this.fund_type = fund_type;
	}

	public int getR() {
		return r;
	}

	public void setR(int r) {
		this.r = r;
	}
}
