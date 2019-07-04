package com.jiuwang.buyer.bean;

/**
 * Created by Administrator on 2019/2/23.
 * 购物车 和可用余额bean
 */

public class MoneyNumberBean {
    /**
     * count : 0
     * code : 0
     * msg : 成功
     * account_balance : 96233592.36
     */

    private String count;//购物车数量
    private String code;
    private String msg;
    private String account_balance;//可用资金余额

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getAccount_balance() {
        return account_balance;
    }

    public void setAccount_balance(String account_balance) {
        this.account_balance = account_balance;
    }
}
