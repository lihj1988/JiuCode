package com.jiuwang.buyer.bean;

import java.util.List;

/**
 * Created by Administrator on 2019/1/23.
 * 常用联系人  列表bean 标准版
 */

public class CommonListBean {
    private String count;
    private String code;
    private String msg;
    private List<CommonItemBean> data;

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

    public List<CommonItemBean> getData() {
        return data;
    }

    public void setData(List<CommonItemBean> data) {
        this.data = data;
    }
}
