package com.jiuwang.buyer.bean;

/**
 * Created by Administrator on 2019/1/24.
 * 钢厂配送地址  标准版  字段没有太多没有写完，用到了那个写了那个字段，详细字段见接口文档 4.4
 */

public class SteelAddressBean {
    private String destination;//详细地址
    private String area_cd;//省市县编码
    private String area_info;//省市县详细地址 全
    private String prov_code;//省code
    private String city_code;//市code
    private String area_code;//县code
    private String consignee_name;//收货人姓名
    private String consignee_telephone;//联系电话
    private String special_line;//专用线
    private String arrive_station;//到站
    private String notes;//备注
    private String user_cd;//

    public String getUser_cd() {
        return user_cd;
    }

    public void setUser_cd(String user_cd) {
        this.user_cd = user_cd;
    }

    public String getSpecial_line() {
        return special_line;
    }

    public void setSpecial_line(String special_line) {
        this.special_line = special_line;
    }

    public String getArrive_station() {
        return arrive_station;
    }

    public void setArrive_station(String arrive_station) {
        this.arrive_station = arrive_station;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getArea_cd() {
        return area_cd;
    }

    public void setArea_cd(String area_cd) {
        this.area_cd = area_cd;
    }

    public String getArea_info() {
        return area_info;
    }

    public void setArea_info(String area_info) {
        this.area_info = area_info;
    }

    public String getProv_code() {
        return prov_code;
    }

    public void setProv_code(String prov_code) {
        this.prov_code = prov_code;
    }

    public String getCity_code() {
        return city_code;
    }

    public void setCity_code(String city_code) {
        this.city_code = city_code;
    }

    public String getArea_code() {
        return area_code;
    }

    public void setArea_code(String area_code) {
        this.area_code = area_code;
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

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
