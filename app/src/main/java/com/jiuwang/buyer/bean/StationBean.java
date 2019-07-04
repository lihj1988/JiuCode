package com.jiuwang.buyer.bean;

/**
 * Created by Administrator on 2019/2/23.
 * 到站 bean 标准版
 */

public class StationBean {
    /**
     * id : 10
     * station_name : 莱钢
     * r : 1
     */
    private String id;
    private String station_name;//站名
    private int r;

    public StationBean(String id, String station_name) {
        this.id = id;
        this.station_name = station_name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStation_name() {
        return station_name;
    }

    public void setStation_name(String station_name) {
        this.station_name = station_name;
    }

    public int getR() {
        return r;
    }

    public void setR(int r) {
        this.r = r;
    }
}
