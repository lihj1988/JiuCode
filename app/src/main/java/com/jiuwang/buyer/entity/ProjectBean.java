package com.jiuwang.buyer.entity;

import java.io.Serializable;

/**
 * Created by lihj on 2019/6/25
 * desc:抢购项目
 */

public class ProjectBean implements Serializable {

	/**
	 * pic_url : pic_2965703_b.jpg
	 * goods_code : 1999c0qsw26yehh16
	 * goods_name : 1999c0jxl2oanmu01
	 * notes : 描述
	 * start_time : 2019-01-01 11:00:00
	 * end_time : 2019-01-01 12:00:00
	 * price : 抢购价(积分)
	 */

	private String pic_url;
	private String goods_code;
	private String goods_name;
	private String notes;
	private String start_time;
	private String end_time;
	private String price;

	public String getPic_url() {
		return pic_url;
	}

	public void setPic_url(String pic_url) {
		this.pic_url = pic_url;
	}

	public String getGoods_code() {
		return goods_code;
	}

	public void setGoods_code(String goods_code) {
		this.goods_code = goods_code;
	}

	public String getGoods_name() {
		return goods_name;
	}

	public void setGoods_name(String goods_name) {
		this.goods_name = goods_name;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getStart_time() {
		return start_time;
	}

	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}

	public String getEnd_time() {
		return end_time;
	}

	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}
}
