package com.jiuwang.buyer.entity;

/**
 * Created by lihj on 2019/7/1
 * desc:
 */

public class BaseResultEntity {

	/**
	 * msg : 返回提示信息
	 * code : 返回状态码
	 */

	private String msg;
	private String code;

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


}
