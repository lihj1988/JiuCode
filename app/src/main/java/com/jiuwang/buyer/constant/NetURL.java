package com.jiuwang.buyer.constant;

import android.os.Environment;


public class NetURL {
    public static final String directory = Environment.getExternalStorageDirectory()
            + "/jbbuy/";

    //public static final String URL_BASE = "http://www.lange360.com/";

//    public static final String BASEURL = "http://192.168.0.213:8080/";//内网
    public static final String BASEURL = "http://47.93.163.66:80/";//正式
//    public static final String BASEURL = "http://47.93.82.141:9999/";//外网
//    public static final String BASEURL = "http://192.168.0.4:8080/";//外网
    public static final String PIC_BASEURL = BASEURL+"upload/pic/";//标准版外网

    public static final String URL_NEWSURL = "http://newapp.lgmi.com/";

    public static final String APP_ID_WEIXIN = "wx40c80eb06a1f029d";
    public static final String APP_ID_QQ = "1107763141";
    //微信统一下单接口
    public static final String WX_UNIFIEDORDER =BASEURL+ "notify_wx.jsp";
    public static final String notify_url_wx = "https://api.mch.weixin.qq.com/pay/unifiedorder";

    public static final int HANDLER_GET_NEWS = 1;//获得新闻资讯
    // 分享底部文字5个
    public static String[] SHARE_BELOW_TEXT_FIVE = new String[]{"微信好友", "微信朋友圈",
            "QQ好友", "QQ空间"};
    // 分享底部文字4个
    public static String[] SHARE_BELOW_TEXT_FOUR = new String[]{"微信好友", "微信朋友圈",
            "QQ好友"};

}
