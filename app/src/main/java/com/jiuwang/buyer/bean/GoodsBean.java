package com.jiuwang.buyer.bean;

import java.io.Serializable;

/**
 * Created by lihj on 2019/6/19
 * desc: 商品
 */

public class GoodsBean implements Serializable {


	/**
	 * pic_url : https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1560955703390&di=1284d9beb3b9749c3e6862e562c85ecf&imgtype=0&src=http%3A%2F%2Fpic3.16pic.com%2F00%2F29%2F65%2F16pic_2965703_b.jpg
	 * goods_code : 1999c0qsw26yehh16
	 * goods_name : 1999c0jxl2oanmu01
	 * producter : 江苏无锡
	 * sale_price : 3176 电商价
	 * price : 3000 原售价
	 * notes : 3000
	 * deliver_place : 发货地
	 * order_count : 100
	 */

	private String pic_url;
	private String filename;
	private String id;
	private String goods_code;
	private String goods_name;
	private String producer;
	private String sale_price;
	private String price;
	private String notes;
	private String deliver_place;
	private String order_count;
	private String project_id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

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

	public String getProducer() {
		return producer;
	}

	public void setProducer(String producer) {
		this.producer = producer;
	}

	public String getSale_price() {
		return sale_price;
	}

	public void setSale_price(String sale_price) {
		this.sale_price = sale_price;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getDeliver_place() {
		return deliver_place;
	}

	public void setDeliver_place(String deliver_place) {
		this.deliver_place = deliver_place;
	}

	public String getOrder_count() {
		return order_count;
	}

	public void setOrder_count(String order_count) {
		this.order_count = order_count;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getProject_id() {
		return project_id;
	}

	public void setProject_id(String project_id) {
		this.project_id = project_id;
	}
}
