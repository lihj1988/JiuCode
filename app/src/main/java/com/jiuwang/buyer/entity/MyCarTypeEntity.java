package com.jiuwang.buyer.entity;

public class MyCarTypeEntity {
	private String carKind;
	private String carKindCode;
	public MyCarTypeEntity(String carKind, String carKindCode) {
		super();
		this.carKind = carKind;
		this.carKindCode = carKindCode;
	}
	public MyCarTypeEntity() {
		super();
	}
	public String getCarKind() {
		return carKind;
	}
	public void setCarKind(String carKind) {
		this.carKind = carKind;
	}
	public String getCarKindCode() {
		return carKindCode;
	}
	public void setCarKindCode(String carKindCode) {
		this.carKindCode = carKindCode;
	}


}
