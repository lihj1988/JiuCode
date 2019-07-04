package com.jiuwang.buyer.bean;

import java.util.List;

/**
 * Created by Administrator on 2019/1/22.
 * 市级bean  标准版
 */

public class CityBean {
    private String  name;//城市名字
    private String  code;//城市code
    private List<AreaBean> area;//县级集合

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<AreaBean> getArea() {
        return area;
    }

    public void setArea(List<AreaBean> area) {
        this.area = area;
    }
}
