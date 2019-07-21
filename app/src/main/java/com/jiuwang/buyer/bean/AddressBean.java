package com.jiuwang.buyer.bean;

import java.io.Serializable;

/**
 * author：lihj
 * desc：
 * Created by lihj on  2019/7/11 13:51.
 */

public class AddressBean implements Serializable {

	/**
	 * id : 1999c0r931qnuy505
	 * linkman_type :
	 * consignee_name : 1
	 * consignee_telephone : 13800000000
	 * destination_prov_cd : 110000
	 * destination_city_cd : 110101
	 * destination_area_cd :
	 * destination_address : 1
	 * grouping_cd : 1999
	 * create_user : coder
	 * create_username :  超级管理员
	 * create_time : 2019-07-11 10:53:03
	 * r : 1
	 */

	private String id;
	private String linkman_type;
	private String consignee_name;
	private String consignee_telephone;
	private String destination_prov_cd;
	private String destination_city_cd;
	private String destination_area_cd;
	private String destination_address;
	private String grouping_cd;
	private String create_user;
	private String create_username;
	private String create_time;
	private String is_default;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLinkman_type() {
		return linkman_type;
	}

	public void setLinkman_type(String linkman_type) {
		this.linkman_type = linkman_type;
	}

	public String getConsignee_name() {
		return consignee_name;
	}

	public void setConsignee_name(String consignee_name) {
		this.consignee_name = consignee_name;
	}

	public String getConsignee_telephone() {
		return consignee_telephone;
	}

	public void setConsignee_telephone(String consignee_telephone) {
		this.consignee_telephone = consignee_telephone;
	}

	public String getDestination_prov_cd() {
		return destination_prov_cd;
	}

	public void setDestination_prov_cd(String destination_prov_cd) {
		this.destination_prov_cd = destination_prov_cd;
	}

	public String getDestination_city_cd() {
		return destination_city_cd;
	}

	public void setDestination_city_cd(String destination_city_cd) {
		this.destination_city_cd = destination_city_cd;
	}

	public String getDestination_area_cd() {
		return destination_area_cd;
	}

	public void setDestination_area_cd(String destination_area_cd) {
		this.destination_area_cd = destination_area_cd;
	}

	public String getDestination_address() {
		return destination_address;
	}

	public void setDestination_address(String destination_address) {
		this.destination_address = destination_address;
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

	public String getIs_default() {
		return is_default;
	}

	public void setIs_default(String is_default) {
		this.is_default = is_default;
	}
}
