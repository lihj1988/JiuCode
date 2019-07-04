package com.jiuwang.buyer.util;

import android.os.Environment;


public class NetURL {
    public static final String directory = Environment.getExternalStorageDirectory()
            + "/jbbuy/";

    //public static final String URL_BASE = "http://www.lange360.com/";

//    public static final String BASEURL = "http://192.168.0.4";//标准版内网
    public static final String BASEURL = "http://47.93.82.141:9999/";//标准版外网
    public static final String PIC_BASEURL = BASEURL+"upload/pic/";//标准版外网

    public static final String URL_NEWSURL = "http://newapp.lgmi.com/";

    public static final String APP_ID_WEIXIN = "wx40c80eb06a1f029d";
    public static final String APP_ID_QQ = "1107763141";
    public static final int HANDLER_GET_NEWS = 1;//获得新闻资讯
    // 分享底部文字5个
    public static String[] SHARE_BELOW_TEXT_FIVE = new String[]{"微信好友", "微信朋友圈",
            "QQ好友", "QQ空间"};
    // 分享底部文字4个
    public static String[] SHARE_BELOW_TEXT_FOUR = new String[]{"微信好友", "微信朋友圈",
            "QQ好友"};

}
