package com.jiuwang.buyer.bean;
import java.util.List;

/**
 * Created by Administrator on 2019/1/24.
 */

public class BaseBean<T> {
    private String count;
    private String code;
    private String msg;
    private List<T> data;

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

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
