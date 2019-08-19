package com.jiuwang.buyer.bean;

import java.io.Serializable;

/**
 * author：lihj
 * desc：奖池
 * Created by lihj on  2019/8/19 15:38.
 */

public class PoolBean implements Serializable {

	/**
	 * id : 1
	 * period : 2
	 * total_amount : 81
	 * amount : 80
	 * start_time : 2019-08-17 00:00:00
	 * duration : 2
	 * status : 1
	 * grouping_cd : 1999
	 * create_user : sys
	 * create_username : sys
	 * create_time : 2019-08-17 00:00:00
	 * stop_time :
	 */

	private String id;
	private String period;
	private String total_amount;
	private String amount;
	private String start_time;
	private String duration;
	private String status;
	private String grouping_cd;
	private String create_user;
	private String create_username;
	private String create_time;
	private String stop_time;

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

	public String getTotal_amount() {
		return total_amount;
	}

	public void setTotal_amount(String total_amount) {
		this.total_amount = total_amount;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getStart_time() {
		return start_time;
	}

	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getGrouping_cd() {
		return grouping_cd;
	}

	public void setGrouping_cd(String grouping_cd) {
		this.grouping_cd = grouping_cd;
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

	public String getStop_time() {
		return stop_time;
	}

	public void setStop_time(String stop_time) {
		this.stop_time = stop_time;
	}
}
