package com.jiuwang.buyer.entity;

/**
 * Created by Administrator on 2019/1/16.
 * 我的合同  result 内的数据 标准版
 */

public class MycontractResultBean {
    private String total_count;//总资源数
    private String contract_weight;//总重量
    private String contract_amount;//总金额
    private String bill_weight;//提单总重量
    private String bill_weight_actual;//实提总重量

    public String getTotal_count() {
        return total_count;
    }

    public void setTotal_count(String total_count) {
        this.total_count = total_count;
    }

    public String getContract_weight() {
        return contract_weight;
    }

    public void setContract_weight(String contract_weight) {
        this.contract_weight = contract_weight;
    }

    public String getContract_amount() {
        return contract_amount;
    }

    public void setContract_amount(String contract_amount) {
        this.contract_amount = contract_amount;
    }

    public String getBill_weight() {
        return bill_weight;
    }

    public void setBill_weight(String bill_weight) {
        this.bill_weight = bill_weight;
    }

    public String getBill_weight_actual() {
        return bill_weight_actual;
    }

    public void setBill_weight_actual(String bill_weight_actual) {
        this.bill_weight_actual = bill_weight_actual;
    }
}
