package com.jiuwang.buyer.entity;

public class CityEntity {
	private String cityCode;
	private String cityName;

	public CityEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	public CityEntity(String cityName, String cityCode) {
		super();
		this.cityName = cityName;
		this.cityCode = cityCode;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getCityCode() {
		return cityCode;
	}
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	
}
