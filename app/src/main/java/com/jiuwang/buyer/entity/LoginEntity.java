package com.jiuwang.buyer.entity;

/**
 * Created by Administrator on 2019/1/14.
 * 登录
 */

public class LoginEntity {
    private String  userCode;//用户code
    private String  userName;//用户姓名
    private String  corpCode;//用户公司code
    private String  corpName;//用户公司名字
    private String  appId;
    private String  token;
    private String  check_status;//是否审核通过  1审核通过 9 审核不通过

    public String getCheck_status() {
        return check_status;
    }

    public void setCheck_status(String check_status) {
        this.check_status = check_status;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCorpCode() {
        return corpCode;
    }

    public void setCorpCode(String corpCode) {
        this.corpCode = corpCode;
    }

    public String getCorpName() {
        return corpName;
    }

    public void setCorpName(String corpName) {
        this.corpName = corpName;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
