package com.jiuwang.buyer.entity;

/**
 * Created by Administrator on 2018/8/13.
 */

public class PayTypeEntity {
    private String pay_mode;//结算类型
    private String name;//结算类型名称

    public String getPay_mode() {
        return pay_mode;
    }

    public PayTypeEntity() {

    }

    public void setPay_mode(String pay_mode) {
        this.pay_mode = pay_mode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
