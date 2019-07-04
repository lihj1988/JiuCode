package com.jiuwang.buyer.entity;

import java.util.List;

/**
 * Created by Administrator on 2018/8/17.
 * 我的代付款
 */

public class MypayEntity {


    /**
     * result : {"waitPayList":[{"one_invoice":"3","amount":"2168.10","contract_amount":"2168.1","contract_no":"HT101120180815153901424","trans_amount":"0.00","contract_weight":"1.1","trans_type":"1","service_amount":"0.00"}],"account_balance":"17699950.16"}
     * msgcode : 1
     * msg : 查询成功
     */

    private ResultBean result;
    private String msgcode;
    private String msg;

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public String getMsgcode() {
        return msgcode;
    }

    public void setMsgcode(String msgcode) {
        this.msgcode = msgcode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class ResultBean {
        /**
         * waitPayList : [{"one_invoice":"3","amount":"2168.10","contract_amount":"2168.1","contract_no":"HT101120180815153901424","trans_amount":"0.00","contract_weight":"1.1","trans_type":"1","service_amount":"0.00"}]
         * account_balance : 17699950.16
         */
        private String account_balance;//预付款余额
        private List<WaitPayListBean> waitPayList;

        public String getAccount_balance() {
            return account_balance;
        }

        public void setAccount_balance(String account_balance) {
            this.account_balance = account_balance;
        }

        public List<WaitPayListBean> getWaitPayList() {
            return waitPayList;
        }

        public void setWaitPayList(List<WaitPayListBean> waitPayList) {
            this.waitPayList = waitPayList;
        }

        public static class WaitPayListBean {
            /**
             * one_invoice : 3
             * amount : 2168.10
             * contract_amount : 2168.1
             * contract_no : HT101120180815153901424
             * trans_amount : 0.00
             * contract_weight : 1.1
             * trans_type : 1
             * service_amount : 0.00
             */
            private String create_time;//合同创建时间
            private String one_invoice;
            private String amount;
            private String contract_amount;
            private String contract_no;
            private String trans_amount;
            private String contract_weight;
            private String trans_type;
            private String service_amount;
            private boolean ischeck;//是否选中

            public boolean ischeck() {
                return ischeck;
            }

            public void setIscheck(boolean ischeck) {
                this.ischeck = ischeck;
            }

            public String getCreate_time() {
                return create_time;
            }

            public void setCreate_time(String create_time) {
                this.create_time = create_time;
            }

            public String getOne_invoice() {
                return one_invoice;
            }

            public void setOne_invoice(String one_invoice) {
                this.one_invoice = one_invoice;
            }

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

            public String getContract_no() {
                return contract_no;
            }

            public void setContract_no(String contract_no) {
                this.contract_no = contract_no;
            }

            public String getTrans_amount() {
                return trans_amount;
            }

            public void setTrans_amount(String trans_amount) {
                this.trans_amount = trans_amount;
            }

            public String getContract_weight() {
                return contract_weight;
            }

            public void setContract_weight(String contract_weight) {
                this.contract_weight = contract_weight;
            }

            public String getTrans_type() {
                return trans_type;
            }

            public void setTrans_type(String trans_type) {
                this.trans_type = trans_type;
            }

            public String getService_amount() {
                return service_amount;
            }

            public void setService_amount(String service_amount) {
                this.service_amount = service_amount;
            }
        }
    }
}
