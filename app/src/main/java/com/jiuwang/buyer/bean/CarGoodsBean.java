package com.jiuwang.buyer.bean;

import java.io.Serializable;

/**
 * Created by lihj on 2019/6/19
 * desc: 商品
 */

public class CarGoodsBean implements Serializable {

	/**
	 * id : 1999c0r8u1o3whf03
	 * goods_id : 1
	 * goods_code : 1
	 * goods_name : 1
	 * price : 1
	 * sale_price : 1
	 * quantity : 1
	 * deliver_place : 1
	 * producer : 1
	 * notes : 1
	 * grouping_cd : 1999
	 * grouping_cd_seller : 1999
	 * create_user : coder
	 * create_username : 超级管理员
	 * create_time : 2019-07-02 10:10:03
	 * pic_url :
	 * grouping_name : 酒网电商
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
	private String grouping_cd;
	private String grouping_cd_seller;
	private String create_user;
	private String create_username;
	private String create_time;
	private String pic_url;
	private String grouping_name;
	private boolean ischeck;//是否单个选中
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

	public String getGrouping_cd() {
		return grouping_cd;
	}

	public void setGrouping_cd(String grouping_cd) {
		this.grouping_cd = grouping_cd;
	}

	public String getGrouping_cd_seller() {
		return grouping_cd_seller;
	}

	public void setGrouping_cd_seller(String grouping_cd_seller) {
		this.grouping_cd_seller = grouping_cd_seller;
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

	public String getPic_url() {
		return pic_url;
	}

	public void setPic_url(String pic_url) {
		this.pic_url = pic_url;
	}

	public String getGrouping_name() {
		return grouping_name;
	}

	public void setGrouping_name(String grouping_name) {
		this.grouping_name = grouping_name;
	}

	public boolean ischeck() {
		return ischeck;
	}

	public void setIscheck(boolean ischeck) {
		this.ischeck = ischeck;
	}
}
