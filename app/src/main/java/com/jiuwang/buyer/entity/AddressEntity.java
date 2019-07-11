package com.jiuwang.buyer.entity;

import com.jiuwang.buyer.bean.AddressBean;

import java.util.List;

/**
 * Created by Administrator on 2018/8/14.
 * 常用地址bean
 */

public class AddressEntity extends BaseResultEntity{
    List<AddressBean> data;

    public List<AddressBean> getData() {
        return data;
    }

    public void setData(List<AddressBean> data) {
        this.data = data;
    }
}
