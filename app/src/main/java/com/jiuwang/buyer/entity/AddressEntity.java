package com.jiuwang.buyer.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/8/14.
 * 常用地址bean
 */

public class AddressEntity implements Serializable{
    private String linkman_cd;//联系人id
    private String linkman_type;//联系人类型
    private String driver_icard_no;//司机身份证
    private String destination_address;//目的地-详细地址
    private String destination_city_cd;//目的地-市Code
    private String consignee_name;//收货人
    private String driver_name;//司机姓名
    private String special_line;//专线
    private String destination_prov_cd;//目的地-省Code
    private String vehicle_plate_no;//车号
    private String arrive_station;//到站
    private String destination_area_cd;//目的地-县Code
    private String consignee_telephone;//收货人电话
    private  String city_name;//城市名字
    private  String prov_name;//省份名字
    private  String area_name;//县名
    private  String price;//运费单价
    private  String notes;//备注

    private boolean ischeck;//是否被选中

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public String getProv_name() {
        return prov_name;
    }

    public void setProv_name(String prov_name) {
        this.prov_name = prov_name;
    }

    public String getArea_name() {
        return area_name;
    }

    public void setArea_name(String area_name) {
        this.area_name = area_name;
    }



    public String getLinkman_type() {
        return linkman_type;
    }

    public void setLinkman_type(String linkman_type) {
        this.linkman_type = linkman_type;
    }

    public String getLinkman_cd() {
        return linkman_cd;
    }

    public void setLinkman_cd(String linkman_cd) {
        this.linkman_cd = linkman_cd;
    }

    public boolean ischeck() {
        return ischeck;
    }

    public void setIscheck(boolean ischeck) {
        this.ischeck = ischeck;
    }

    public String getDriver_icard_no() {
        return driver_icard_no;
    }

    public void setDriver_icard_no(String driver_icard_no) {
        this.driver_icard_no = driver_icard_no;
    }

    public String getDestination_address() {
        return destination_address;
    }

    public void setDestination_address(String destination_address) {
        this.destination_address = destination_address;
    }

    public String getDestination_city_cd() {
        return destination_city_cd;
    }

    public void setDestination_city_cd(String destination_city_cd) {
        this.destination_city_cd = destination_city_cd;
    }

    public String getConsignee_name() {
        return consignee_name;
    }

    public void setConsignee_name(String consignee_name) {
        this.consignee_name = consignee_name;
    }

    public String getDriver_name() {
        return driver_name;
    }

    public void setDriver_name(String driver_name) {
        this.driver_name = driver_name;
    }

    public String getSpecial_line() {
        return special_line;
    }

    public void setSpecial_line(String special_line) {
        this.special_line = special_line;
    }

    public String getDestination_prov_cd() {
        return destination_prov_cd;
    }

    public void setDestination_prov_cd(String destination_prov_cd) {
        this.destination_prov_cd = destination_prov_cd;
    }

    public String getVehicle_plate_no() {
        return vehicle_plate_no;
    }

    public void setVehicle_plate_no(String vehicle_plate_no) {
        this.vehicle_plate_no = vehicle_plate_no;
    }

    public String getArrive_station() {
        return arrive_station;
    }

    public void setArrive_station(String arrive_station) {
        this.arrive_station = arrive_station;
    }

    public String getDestination_area_cd() {
        return destination_area_cd;
    }

    public void setDestination_area_cd(String destination_area_cd) {
        this.destination_area_cd = destination_area_cd;
    }

    public String getConsignee_telephone() {
        return consignee_telephone;
    }

    public void setConsignee_telephone(String consignee_telephone) {
        this.consignee_telephone = consignee_telephone;
    }
}
