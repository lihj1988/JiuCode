package com.jiuwang.buyer.entity;

public class MyCarLengthEntity {
	private String carLength;
	private String carLengthCode;
	public MyCarLengthEntity(String carLength, String carLengthCode) {
		super();
		this.carLength = carLength;
		this.carLengthCode = carLengthCode;
	}
	public MyCarLengthEntity() {
		super();
	}
	public String getCarLength() {
		return carLength;
	}
	public void setCarLength(String carLength) {
		this.carLength = carLength;
	}
	public String getCarLengthCode() {
		return carLengthCode;
	}
	public void setCarLengthCode(String carLengthCode) {
		this.carLengthCode = carLengthCode;
	}
	

}
