package com.jiuwang.buyer.entity;

/**
 * Created by Administrator on 2019/2/1.
 * 我要付款 列表bean 标准版
 */

public class MyPayMentEntity {
	private String contract_no;//合同号
	private String create_time;//生成时间
	private String contract_weight;//合同重量
	private String contract_amount;//合同金额
	private String grouping_cd;//买家cd
	private String trans_type;
	private String trans_price;
	private String train_price;
	private String one_invoice;
	private String service_price;
	private String trans_amount;//运费
	private String service_amount;//服务费
	private String amount;//应付总金额
	private String r;
	private boolean ischeck;//是否被选中

	public boolean ischeck() {
		return ischeck;
	}

	public void setIscheck(boolean ischeck) {
		this.ischeck = ischeck;
	}

	public String getContract_weight() {
		return contract_weight;
	}

	public void setContract_weight(String contract_weight) {
		this.contract_weight = contract_weight;
	}

	public String getContract_amount() {
		return contract_amount;
	}

	public void setContract_amount(String contract_amount) {
		this.contract_amount = contract_amount;
	}

	public String getTrans_amount() {
		return trans_amount;
	}

	public void setTrans_amount(String trans_amount) {
		this.trans_amount = trans_amount;
	}

	public String getService_amount() {
		return service_amount;
	}

	public void setService_amount(String service_amount) {
		this.service_amount = service_amount;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getR() {
		return r;
	}

	public void setR(String r) {
		this.r = r;
	}

	public String getContract_no() {
		return contract_no;
	}

	public void setContract_no(String contract_no) {
		this.contract_no = contract_no;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}



	public String getGrouping_cd() {
		return grouping_cd;
	}

	public void setGrouping_cd(String grouping_cd) {
		this.grouping_cd = grouping_cd;
	}

	public String getTrans_type() {
		return trans_type;
	}

	public void setTrans_type(String trans_type) {
		this.trans_type = trans_type;
	}

	public String getTrans_price() {
		return trans_price;
	}

	public void setTrans_price(String trans_price) {
		this.trans_price = trans_price;
	}

	public String getTrain_price() {
		return train_price;
	}

	public void setTrain_price(String train_price) {
		this.train_price = train_price;
	}

	public String getOne_invoice() {
		return one_invoice;
	}

	public void setOne_invoice(String one_invoice) {
		this.one_invoice = one_invoice;
	}

	public String getService_price() {
		return service_price;
	}

	public void setService_price(String service_price) {
		this.service_price = service_price;
	}


}
