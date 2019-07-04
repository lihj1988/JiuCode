package com.jiuwang.buyer.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2019/1/23.
 * 常用联系人单条数据  标准版
 */

public class CommonItemBean implements Serializable{
    private String linkman_cd;
    private String linkman_type;
    private String consignee_name;//联系人姓名
    private String consignee_telephone;//联系人电话
    private String destination_prov_cd;//省份code
    private String destination_city_cd;//市code
    private String destination_area_cd;//县code
    private String destination_address;//详细地址
    private String driver_name;//司机姓名
    private String driver_telephone;//司机电话
    private String driver_icard_no;//司机身份证号
    private String vehicle_plate_no;//车牌号
    private String record_user_cd;
    private String record_date;
    private String grouping_cd;
    private String destination;//省市县详细地址
    private String arrive_station;//到站
    private String special_line;//专线
    private String corp_name;//承运商
    private String car_weight;//车货总重
    private String car_model;//车型

    private String r;
    private boolean ischeck;


    public String getCorp_name() {
        return corp_name;
    }

    public void setCorp_name(String corp_name) {
        this.corp_name = corp_name;
    }

    public String getCar_weight() {
        return car_weight;
    }

    public void setCar_weight(String car_weight) {
        this.car_weight = car_weight;
    }

    public String getCar_model() {
        return car_model;
    }

    public void setCar_model(String car_model) {
        this.car_model = car_model;
    }

    public boolean ischeck() {
        return ischeck;
    }

    public void setIscheck(boolean ischeck) {
        this.ischeck = ischeck;
    }

    public String getR() {
        return r;
    }

    public void setR(String r) {
        this.r = r;
    }

    public String getLinkman_cd() {
        return linkman_cd;
    }

    public void setLinkman_cd(String linkman_cd) {
        this.linkman_cd = linkman_cd;
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

    public String getDriver_name() {
        return driver_name;
    }

    public void setDriver_name(String driver_name) {
        this.driver_name = driver_name;
    }

    public String getDriver_telephone() {
        return driver_telephone;
    }

    public void setDriver_telephone(String driver_telephone) {
        this.driver_telephone = driver_telephone;
    }

    public String getDriver_icard_no() {
        return driver_icard_no;
    }

    public void setDriver_icard_no(String driver_icard_no) {
        this.driver_icard_no = driver_icard_no;
    }

    public String getVehicle_plate_no() {
        return vehicle_plate_no;
    }

    public void setVehicle_plate_no(String vehicle_plate_no) {
        this.vehicle_plate_no = vehicle_plate_no;
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

    public String getGrouping_cd() {
        return grouping_cd;
    }

    public void setGrouping_cd(String grouping_cd) {
        this.grouping_cd = grouping_cd;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getArrive_station() {
        return arrive_station;
    }

    public void setArrive_station(String arrive_station) {
        this.arrive_station = arrive_station;
    }

    public String getSpecial_line() {
        return special_line;
    }

    public void setSpecial_line(String special_line) {
        this.special_line = special_line;
    }

}
