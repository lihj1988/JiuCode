package com.jiuwang.buyer.redpakge;

/**
 * @author ChayChan
 * @description: 红包
 * @date 2017/11/28  14:23
 */

public class RedPacketEntity{
    public String name;
    public String avatar;
    public String remark;
    public String count;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}
