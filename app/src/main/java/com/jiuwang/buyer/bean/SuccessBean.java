package com.jiuwang.buyer.bean;

/**
 * Created by Administrator on 2019/1/24.
 */

public class SuccessBean {
    private String code;
    private String msg;
    private String contractNo;//合同号

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
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
}
