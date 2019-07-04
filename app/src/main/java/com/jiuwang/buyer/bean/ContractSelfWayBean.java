package com.jiuwang.buyer.bean;

/**
 * Created by Administrator on 2019/1/28.
 * 自提明细列表bean
 */

public class ContractSelfWayBean {
	private String listing_cd;//资源明细id
	private String item_name;//品名
	private String item_texture;//材质
	private String item_model;//规格
	private String item_length;//长度
	private String warehouse_name;//仓库
	private String item_producing_area;//产地
	private String item_weight;//明细重量
	private String item_metering;//计量方式
	private String sale_price;//明细单价
	private String item_quantity;//明细件数
	private String item_total_weight;//明细总重量
	private String bill_quantity;//明细已开件数
	private String bill_weight;//明细已开重量
	private String remain_quantity;//开单件数
	private String remain_weight;//开单重量
	private String item_info;//合同数量/重量
	private String bill_info;//已开数量/重量
	private String r;//资源序列号
	private String itemLeftQuantityBackup;//用户输入数量
	private String itemLeftWeightBackup;//用户输入重量
	private boolean ischeck;//是否选中
	private String overquantity;//未开单件数
	private String overweight;//未开单重量

	private String allnumber;//提交总件数  调自提接口时传的
	private String allweight;//提交总吨数  调自提接口时传的

	public String getAllnumber() {
		return allnumber;
	}

	public void setAllnumber(String allnumber) {
		this.allnumber = allnumber;
	}

	public String getAllweight() {
		return allweight;
	}

	public void setAllweight(String allweight) {
		this.allweight = allweight;
	}

	public String getOverquantity() {
		return overquantity;
	}

	public void setOverquantity(String overquantity) {
		this.overquantity = overquantity;
	}

	public String getOverweight() {
		return overweight;
	}

	public void setOverweight(String overweight) {
		this.overweight = overweight;
	}

	public boolean ischeck() {
		return ischeck;
	}

	public void setIscheck(boolean ischeck) {
		this.ischeck = ischeck;
	}

	public String getItemLeftQuantityBackup() {
		return itemLeftQuantityBackup;
	}

	public void setItemLeftQuantityBackup(String itemLeftQuantityBackup) {
		this.itemLeftQuantityBackup = itemLeftQuantityBackup;
	}

	public String getItemLeftWeightBackup() {
		return itemLeftWeightBackup;
	}

	public void setItemLeftWeightBackup(String itemLeftWeightBackup) {
		this.itemLeftWeightBackup = itemLeftWeightBackup;
	}

	public String getListing_cd() {
		return listing_cd;
	}

	public void setListing_cd(String listing_cd) {
		this.listing_cd = listing_cd;
	}

	public String getItem_name() {
		return item_name;
	}

	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}

	public String getItem_texture() {
		return item_texture;
	}

	public void setItem_texture(String item_texture) {
		this.item_texture = item_texture;
	}

	public String getItem_model() {
		return item_model;
	}

	public void setItem_model(String item_model) {
		this.item_model = item_model;
	}

	public String getItem_length() {
		return item_length;
	}

	public void setItem_length(String item_length) {
		this.item_length = item_length;
	}

	public String getWarehouse_name() {
		return warehouse_name;
	}

	public void setWarehouse_name(String warehouse_name) {
		this.warehouse_name = warehouse_name;
	}

	public String getItem_producing_area() {
		return item_producing_area;
	}

	public void setItem_producing_area(String item_producing_area) {
		this.item_producing_area = item_producing_area;
	}

	public String getItem_weight() {
		return item_weight;
	}

	public void setItem_weight(String item_weight) {
		this.item_weight = item_weight;
	}

	public String getItem_metering() {
		return item_metering;
	}

	public void setItem_metering(String item_metering) {
		this.item_metering = item_metering;
	}

	public String getSale_price() {
		return sale_price;
	}

	public void setSale_price(String sale_price) {
		this.sale_price = sale_price;
	}

	public String getItem_quantity() {
		return item_quantity;
	}

	public void setItem_quantity(String item_quantity) {
		this.item_quantity = item_quantity;
	}

	public String getItem_total_weight() {
		return item_total_weight;
	}

	public void setItem_total_weight(String item_total_weight) {
		this.item_total_weight = item_total_weight;
	}

	public String getBill_quantity() {
		return bill_quantity;
	}

	public void setBill_quantity(String bill_quantity) {
		this.bill_quantity = bill_quantity;
	}

	public String getBill_weight() {
		return bill_weight;
	}

	public void setBill_weight(String bill_weight) {
		this.bill_weight = bill_weight;
	}

	public String getRemain_quantity() {
		return remain_quantity;
	}

	public void setRemain_quantity(String remain_quantity) {
		this.remain_quantity = remain_quantity;
	}

	public String getRemain_weight() {
		return remain_weight;
	}

	public void setRemain_weight(String remain_weight) {
		this.remain_weight = remain_weight;
	}

	public String getItem_info() {
		return item_info;
	}

	public void setItem_info(String item_info) {
		this.item_info = item_info;
	}

	public String getBill_info() {
		return bill_info;
	}

	public void setBill_info(String bill_info) {
		this.bill_info = bill_info;
	}

	public String getR() {
		return r;
	}

	public void setR(String r) {
		this.r = r;
	}
}
