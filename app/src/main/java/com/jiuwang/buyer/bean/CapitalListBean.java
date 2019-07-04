package com.jiuwang.buyer.bean;

/**
 * Created by Administrator on 2019/2/1.
 * 我的资金列表bean 标准版
 */

public class CapitalListBean {

	private String receive_date;//日期
	private String expenditure;//收款金额
	private String receivables;//支出金额
	private String record_date;
	private String grouping_bankname;//银行名称
	private String proceeds_type;
	private String grouping_bankaccount;//银行账号
	private String receive_amount;//金额
	private String receive_balance;//余额
	private String receive_type;//资金类型1：收款2：出金3：合同4：结算5：运费收款6：运费退费7：月结算8：红冲9：退款10：服务费收款11：服务费退款12：运费补收款13：月结算撤销14：退货退款15：预定
	private String receive_type_name;//资金类型说明
	private String contract_no;//合同号
	private String notes;//备注
	private String r;//资源编号

	public String getReceive_amount() {
		return receive_amount;
	}

	public void setReceive_amount(String receive_amount) {
		this.receive_amount = receive_amount;
	}

	public String getReceive_balance() {
		return receive_balance;
	}

	public void setReceive_balance(String receive_balance) {
		this.receive_balance = receive_balance;
	}

	public String getReceive_type() {
		return receive_type;
	}

	public void setReceive_type(String receive_type) {
		this.receive_type = receive_type;
	}

	public String getR() {
		return r;
	}

	public void setR(String r) {
		this.r = r;
	}

	public String getReceive_date() {
		return receive_date;
	}

	public void setReceive_date(String receive_date) {
		this.receive_date = receive_date;
	}

	public String getExpenditure() {
		return expenditure;
	}

	public void setExpenditure(String expenditure) {
		this.expenditure = expenditure;
	}

	public String getReceivables() {
		return receivables;
	}

	public void setReceivables(String receivables) {
		this.receivables = receivables;
	}

	public String getRecord_date() {
		return record_date;
	}

	public void setRecord_date(String record_date) {
		this.record_date = record_date;
	}

	public String getGrouping_bankname() {
		return grouping_bankname;
	}

	public void setGrouping_bankname(String grouping_bankname) {
		this.grouping_bankname = grouping_bankname;
	}

	public String getProceeds_type() {
		return proceeds_type;
	}

	public void setProceeds_type(String proceeds_type) {
		this.proceeds_type = proceeds_type;
	}

	public String getGrouping_bankaccount() {
		return grouping_bankaccount;
	}

	public void setGrouping_bankaccount(String grouping_bankaccount) {
		this.grouping_bankaccount = grouping_bankaccount;
	}


	public String getReceive_type_name() {
		return receive_type_name;
	}

	public void setReceive_type_name(String receive_type_name) {
		this.receive_type_name = receive_type_name;
	}

	public String getContract_no() {
		return contract_no;
	}

	public void setContract_no(String contract_no) {
		this.contract_no = contract_no;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}


}
