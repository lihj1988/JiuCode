package com.jiuwang.buyer.bean;

/**
 * Created by Administrator on 2019/1/22.
 * 县级bean 标准版
 */

public class AreaBean {
    private String code;
    private String name;

    public AreaBean(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }


    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
