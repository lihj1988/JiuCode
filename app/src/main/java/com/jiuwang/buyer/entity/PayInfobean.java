package com.jiuwang.buyer.entity;

/**
 * Created by Administrator on 2019/1/24.
 * 运费信息
 */

public class PayInfobean {
    private String amount;//总金额
    private String contract_amount;//合同总金额
    private String trans_amount;//运费
    private String code;
    private String service_amount;//服务费
    private String success;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getContract_amount() {
        return contract_amount;
    }

    public void setContract_amount(String contract_amount) {
        this.contract_amount = contract_amount;
    }

    public String getTrans_amount() {
        return trans_amount;
    }

    public void setTrans_amount(String trans_amount) {
        this.trans_amount = trans_amount;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getService_amount() {
        return service_amount;
    }

    public void setService_amount(String service_amount) {
        this.service_amount = service_amount;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }
}
