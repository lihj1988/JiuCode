package com.jiuwang.buyer.bean;

import java.io.Serializable;

/**
 * Created by lihj on 2019/6/25
 * desc:抢购项目
 */

public class ProjectBean implements Serializable {


	/**
	 * id : 1999c0r8z212jpa01
	 * project_name : 2
	 * start_time : 2019-07-07 00:27:22
	 * stop_time : 2019-07-07 00:27:22
	 * step : 2
	 * max_count : 2
	 * min_count : 2
	 * win_count : 2
	 * profit : 2
	 * notes :
	 * status : 2
	 * grouping_cd : 1999
	 * create_user : coder
	 * create_username :  超级管理员
	 * create_time : 2019-07-07 12:27:30
	 * sale_price : 2
	 * status_name : 进行中
	 */

	private String id; //项目id
	private String project_name;//项目名称
	private String start_time;//开始时间
	private String stop_time;//结束时间
	private String step;
	private String max_count;
	private String min_count;
	private String win_count;
	private String profit;
	private String notes;
	private String status;//状态code 1 进行中 2 完成
	private String grouping_cd;
	private String create_user;
	private String create_username;
	private String create_time;
	private String sale_price;//价格
	private String status_name;//状态名称

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProject_name() {
		return project_name;
	}

	public void setProject_name(String project_name) {
		this.project_name = project_name;
	}

	public String getStart_time() {
		return start_time;
	}

	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}

	public String getStop_time() {
		return stop_time;
	}

	public void setStop_time(String stop_time) {
		this.stop_time = stop_time;
	}


	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
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


	public String getStatus_name() {
		return status_name;
	}

	public void setStatus_name(String status_name) {
		this.status_name = status_name;
	}

	public String getStep() {
		return step;
	}

	public void setStep(String step) {
		this.step = step;
	}

	public String getMax_count() {
		return max_count;
	}

	public void setMax_count(String max_count) {
		this.max_count = max_count;
	}

	public String getMin_count() {
		return min_count;
	}

	public void setMin_count(String min_count) {
		this.min_count = min_count;
	}

	public String getWin_count() {
		return win_count;
	}

	public void setWin_count(String win_count) {
		this.win_count = win_count;
	}

	public String getProfit() {
		return profit;
	}

	public void setProfit(String profit) {
		this.profit = profit;
	}

	public String getSale_price() {
		return sale_price;
	}

	public void setSale_price(String sale_price) {
		this.sale_price = sale_price;
	}
}
