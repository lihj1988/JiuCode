package com.jiuwang.buyer.entity;

/**
 * Created by lihj on 2019/7/1
 * desc:
 */

public class BaseResultEntity {

	/**
	 * msg : 返回提示信息
	 * code : 返回状态码
	 * count:
	 */

	private String msg;
	private String code;
	private String count;

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}
}
