package com.jiuwang.buyer.bean;

import java.io.Serializable;

/**
 * Created by lihj on 2019/7/17
 * desc:用户信息
 */

public class UserBean implements Serializable {

	/**
	 * id : coder
	 * user_cd : coder
	 * account_flag : 1
	 * user_name : 超级管理员
	 * user_name_alias :
	 * user_name_eng :
	 * telephone_number : 超级管理员
	 * mobile_number : 13910583709
	 * fax_number :
	 * extension_number :
	 * extension_fax_number :
	 * country_cd :
	 * zip_code :
	 * address :
	 * email_address1 :
	 * email_address2 :
	 * mobile_email_address :
	 * url :
	 * notes :
	 * sort_key :
	 * record_user_cd : coder
	 * record_date : 2016/09/10|12:20:39
	 * password : MWJiZDg4NjQ2MDgyNzAxNWU1ZDYwNWVkNDQyNTIyNTE=
	 * dept_cd :
	 * grouping_cd : 1999
	 * warehouse_cds :
	 * hide_flag : 0
	 * valid_time :
	 * invite_code :
	 * trial_amount :
	 * account_balance :
	 * avail_trial_amount : 体验金余额
	 * r : 1
	 */

	private String id;
	private String user_cd;
	private String account_flag;
	private String user_name;
	private String user_name_alias;
	private String user_name_eng;
	private String telephone_number;
	private String mobile_number;
	private String fax_number;
	private String extension_number;
	private String extension_fax_number;
	private String country_cd;
	private String zip_code;
	private String address;
	private String email_address1;
	private String email_address2;
	private String mobile_email_address;
	private String url;
	private String notes;
	private String sort_key;
	private String record_user_cd;
	private String record_date;
	private String password;
	private String dept_cd;
	private String grouping_cd;
	private String warehouse_cds;
	private String hide_flag;
	private String valid_time;
	private String invite_code;
	private String trial_amount;
	private String account_balance;
	private String avail_amount;
	private String account_no;
	private String account_name;
	private String is_invited;//是否被邀请
	private String is_trial;//是否是体验金账户
	private String account_no_wx;//微信账号
	private String account_name_wx;//微信账号名称
	private String status;//1 代表体验金可用
	private String avail_trial_amount;//体验金余额
	private int r;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUser_cd() {
		return user_cd;
	}

	public void setUser_cd(String user_cd) {
		this.user_cd = user_cd;
	}

	public String getAccount_flag() {
		return account_flag;
	}

	public void setAccount_flag(String account_flag) {
		this.account_flag = account_flag;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getUser_name_alias() {
		return user_name_alias;
	}

	public void setUser_name_alias(String user_name_alias) {
		this.user_name_alias = user_name_alias;
	}

	public String getUser_name_eng() {
		return user_name_eng;
	}

	public void setUser_name_eng(String user_name_eng) {
		this.user_name_eng = user_name_eng;
	}

	public String getTelephone_number() {
		return telephone_number;
	}

	public void setTelephone_number(String telephone_number) {
		this.telephone_number = telephone_number;
	}

	public String getMobile_number() {
		return mobile_number;
	}

	public void setMobile_number(String mobile_number) {
		this.mobile_number = mobile_number;
	}

	public String getFax_number() {
		return fax_number;
	}

	public void setFax_number(String fax_number) {
		this.fax_number = fax_number;
	}

	public String getExtension_number() {
		return extension_number;
	}

	public void setExtension_number(String extension_number) {
		this.extension_number = extension_number;
	}

	public String getExtension_fax_number() {
		return extension_fax_number;
	}

	public void setExtension_fax_number(String extension_fax_number) {
		this.extension_fax_number = extension_fax_number;
	}

	public String getCountry_cd() {
		return country_cd;
	}

	public void setCountry_cd(String country_cd) {
		this.country_cd = country_cd;
	}

	public String getZip_code() {
		return zip_code;
	}

	public void setZip_code(String zip_code) {
		this.zip_code = zip_code;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail_address1() {
		return email_address1;
	}

	public void setEmail_address1(String email_address1) {
		this.email_address1 = email_address1;
	}

	public String getEmail_address2() {
		return email_address2;
	}

	public void setEmail_address2(String email_address2) {
		this.email_address2 = email_address2;
	}

	public String getMobile_email_address() {
		return mobile_email_address;
	}

	public void setMobile_email_address(String mobile_email_address) {
		this.mobile_email_address = mobile_email_address;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getSort_key() {
		return sort_key;
	}

	public void setSort_key(String sort_key) {
		this.sort_key = sort_key;
	}

	public String getRecord_user_cd() {
		return record_user_cd;
	}

	public void setRecord_user_cd(String record_user_cd) {
		this.record_user_cd = record_user_cd;
	}

	public String getRecord_date() {
		return record_date;
	}

	public void setRecord_date(String record_date) {
		this.record_date = record_date;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDept_cd() {
		return dept_cd;
	}

	public void setDept_cd(String dept_cd) {
		this.dept_cd = dept_cd;
	}

	public String getGrouping_cd() {
		return grouping_cd;
	}

	public void setGrouping_cd(String grouping_cd) {
		this.grouping_cd = grouping_cd;
	}

	public String getWarehouse_cds() {
		return warehouse_cds;
	}

	public void setWarehouse_cds(String warehouse_cds) {
		this.warehouse_cds = warehouse_cds;
	}

	public String getHide_flag() {
		return hide_flag;
	}

	public void setHide_flag(String hide_flag) {
		this.hide_flag = hide_flag;
	}

	public String getValid_time() {
		return valid_time;
	}

	public void setValid_time(String valid_time) {
		this.valid_time = valid_time;
	}

	public String getInvite_code() {
		return invite_code;
	}

	public void setInvite_code(String invite_code) {
		this.invite_code = invite_code;
	}

	public String getTrial_amount() {
		return trial_amount;
	}

	public void setTrial_amount(String trial_amount) {
		this.trial_amount = trial_amount;
	}

	public String getAccount_balance() {
		return account_balance;
	}

	public void setAccount_balance(String account_balance) {
		this.account_balance = account_balance;
	}

	public String getAvail_amount() {
		return avail_amount;
	}

	public void setAvail_amount(String avail_amount) {
		this.avail_amount = avail_amount;
	}

	public String getAccount_no() {
		return account_no;
	}

	public void setAccount_no(String account_no) {
		this.account_no = account_no;
	}

	public String getAccount_name() {
		return account_name;
	}

	public void setAccount_name(String account_name) {
		this.account_name = account_name;
	}

	public String getIs_trial() {
		return is_trial;
	}

	public void setIs_trial(String is_trial) {
		this.is_trial = is_trial;
	}

	public String getIs_invited() {
		return is_invited;
	}

	public void setIs_invited(String is_invited) {
		this.is_invited = is_invited;
	}

	public int getR() {
		return r;
	}

	public void setR(int r) {
		this.r = r;
	}

	public String getAccount_no_wx() {
		return account_no_wx;
	}

	public void setAccount_no_wx(String account_no_wx) {
		this.account_no_wx = account_no_wx;
	}

	public String getAccount_name_wx() {
		return account_name_wx;
	}

	public void setAccount_name_wx(String account_name_wx) {
		this.account_name_wx = account_name_wx;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAvail_trial_amount() {
		return avail_trial_amount;
	}

	public void setAvail_trial_amount(String avail_trial_amount) {
		this.avail_trial_amount = avail_trial_amount;
	}
}
