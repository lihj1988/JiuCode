package com.jiuwang.buyer.bean;

import java.io.Serializable;

/**
 * Created by lihj on 2019/7/9
 * desc:
 */

public class SelectGoodsBean implements Serializable {

	/**
	 * id : 1
	 * goods_id : 1
	 * goods_code : 1
	 * goods_name : 1
	 * price :
	 * sale_price :
	 * quantity :
	 * deliver_place :
	 * producer : 1
	 * notes :
	 * project_id : 1
	 * create_user :
	 * create_username :
	 * create_time : 2019-07-09 17:37:10
	 * filename : 长页.jpg
	 * fileid : 1
	 * pic_url : 1/长页.jpg
	 * r : 1
	 */

	private String id;
	private String goods_id;
	private String goods_code;
	private String goods_name;
	private String price;
	private String sale_price;
	private String quantity;
	private String deliver_place;
	private String producer;
	private String notes;
	private String project_id;
	private String create_user;
	private String create_username;
	private String create_time;
	private String filename;
	private String fileid;
	private String pic_url;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getGoods_id() {
		return goods_id;
	}

	public void setGoods_id(String goods_id) {
		this.goods_id = goods_id;
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

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getSale_price() {
		return sale_price;
	}

	public void setSale_price(String sale_price) {
		this.sale_price = sale_price;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getDeliver_place() {
		return deliver_place;
	}

	public void setDeliver_place(String deliver_place) {
		this.deliver_place = deliver_place;
	}

	public String getProducer() {
		return producer;
	}

	public void setProducer(String producer) {
		this.producer = producer;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getProject_id() {
		return project_id;
	}

	public void setProject_id(String project_id) {
		this.project_id = project_id;
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

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getFileid() {
		return fileid;
	}

	public void setFileid(String fileid) {
		this.fileid = fileid;
	}

	public String getPic_url() {
		return pic_url;
	}

	public void setPic_url(String pic_url) {
		this.pic_url = pic_url;
	}

}
