package com.jiuwang.buyer.constant;

/**
 * Created by lihj on 2019/7/1
 * desc:
 */

public class Constant {
	public static final String PAGESIZE = "10";
	public static final String HTTP_SUCCESS_CODE = "0";
	public static final String HTTP_FAIL_CODE = "1";
	public static final String HTTP_LOGINOUTTIME_CODE = "2";
	//查询商品详情 act赋值
	public static final String GOODS_ACTION_DETAILS_ACT = "details";

	//查询商品详情 act赋值
	public static final String ACTION_ACT_ADD = "1";
	public static final String ACTION_ACT_UPDATA = "2";
	public static final String ACTION_ACT_DELETE = "3";
	//支付宝AppID
//	public static final String ALIPAY_APPID = "2016101000655785";
	public static final String ALIPAY_APPID = "2019072165954132";//生产环境
	public static final String ALIPAY_METHOD ="alipay.trade.app.pay";
	public static final String ALIPAY_RESULTSTATUS = "9000";
	public static final String CHARSET = "UTF-8";
	public static final String VERSION = "1.0";
	public static final String SIGN_TYPE = "RSA2";
	public static final String ALIPAY_PRODUCT_CODE = "QUICK_MSECURITY_PAY";
	public static final boolean RSA2 = true;
	public static final String NOTIFY_URL = "http://47.93.82.141:9999/notify.jsp";
	public static final String PRIVATE_KEY = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCP3DB/ub0ZkasXwOCHVagS7AbypgGARIAxt+tlrFnli9oBols1baVuQvbSRdIXVnjoNVs64/uvXC89Yyu3CrTwjUpz1/UITGbQVqCKgrYBso3JnzQJFfSiWgQpFc72PnsvOcmfLqV1hqyZ6mQus/9cPLZl9W2TZBAutbWcNpRGWSOPKfMhL9RRBjIp6j/qWQtF+eoHvQLEtsty7zATFXCzZCixfmOj1wttK+UNNfxQgIdUOn2ZEdQaV8zLNSm11XIFsRHIrB+vd4IpaCfTk7Ynav9nE3HPuP6HMObqdPXD/o/yl6NntjzRoQfhDD2EnKMXZR2pXqxbPal5Ope56U0RAgMBAAECggEAVZY8TGDmfeXKEnGvoxR0z/0YDRhyYCtg0TOImo61VPmlE7/R37GB8F1qlqLvYO8TM6M6yYiFXkjL2gu8IEQAbfTBTLY4k11Q9bX0ATPOsKfX7xIGVTl4t3Yyb3cIEDwP8YLBzzBjQ86BtXhbbeWskOWV5objUAMCFagJigx2nnrXu0HeI9SlRq7upSDTaQzoqzvShJWVSV1UR8BKX2C85GqCP0/LuesZb01C0IE+/QEyQ8Ew/oJnPLvCYUPyeII/jbztB3/q+QQgqzkN5CCEHvD7tVAZ5p5jlcwVZFzlNMVH+qxfi/dlZRfUFcf2Vtm4nlX9iWXKTE4T+NOm8iZO+QKBgQDY6mwfODhbC5/Od/xX4P88Km7s4MKs8+0WBExeasx9Z8Fned3wTAKpgLjWnCTfeuP5YpVS7ajCjvLVNaglFz4+3oy0VezRpIpRu8fmj2oPraC7BwfbaCGMBOGDxgdnLCK2WChcms+dJBVTwTIPm7GE+5iXf7LioTmLDuVL9n/5rwKBgQCpx/aIqwh1bY4Qd3vyc19/MBqO2ClY2xr0wjb384IpsSWRLuTb9tWbpbs6DZugtpE6CkHfusqcjEEpxC3NgNfHzWkuvIcBt0IPgvrQ6vvHs8I9S5nEqZbGuHeFzoTyeWCQ3ibheZaGfQQ7BxKa226RcVw5RK/mcdsKm2Usa2iVPwKBgFl0W4CIe904LwBDxSIsnLWgPjyb+EzBD44b2a66VniFGGJnBJr7jcF/XzrKC/7JvEh1WmVns0gBEa3RwfUB396ngtk/8jkGpTBNw4OWs0Oh8/4NSTU3fVY0ERh5y0XGEs6mDocaJPKGFfGDY/TB3XSdGXGo7Qm/OW7ahr9L+bzrAoGAdGTK5dzjGqX1q+JODeP8hxRyUJVumyTysPNofcnp5S01xPdyHD53KJKQ7Xagx6gBndmkWX4UIwigL8L3Z5g51hdDUbAbmhiJaFryGkHORJU+x4jJrSJaZHfYMNppGFYP1qEF5LPXdvHD8beikJl4jqs5f2VQddp+QSJRVQd5lj8CgYEAsyWWKesklDdj1acBV7V3/zXVLsM5F9wxWMBH4vbYgJp6gC6JXs3D0l4/ikdoBaH7hRilnV8wRu/yGwakS6cbxynpnH8g1TrKxwQDQkqbQWzSyLxShjNSG1hTgYaQ1RiahK2XXVfBXkKL6s3fp/sn5a+ZxeH6HiSPxHpIdmOUwoU=";
	//提单状态
	public static final String ORDER_STATUS_UNPAY = "0";
	public static final String ORDER_STATUS_PAYED = "1";
	public static final String ORDER_STATUS_FINISH = "2";

	//业务类型 资金类型1直接付款(不扣余额) 2扣款(扣余额) 3 充值   4提现  5返利
	public static final String BUSINESSTYPE_PAYMWENT = "1";//直接付款
	public static final String BUSINESSTYPE_BUCKLE= "2";//扣款
	public static final String BUSINESSTYPE_RECHARGE = "3";//充值
	public static final String BUSINESSTYPE_CASHOUT= "4";//提现
	public static final String BUSINESSTYPE_RETURN = "5";//返利
	//支付方式
	public static final String PAY_MODE_ALI = "1";//支付宝
	public static final String PAY_MODE_WX = "2";//微信
	public static final String PAY_MODE_BALANCE = "3";//余额
	//排序方式
	public static final String SORT_TYPE_PRICE_UP = "0";//价格升序
	public static final String SORT_TYPE_PRICE_DWON = "1";//价格降序
	public static final String SORT_TYPE_SALES = "2";//销量排序

	// request参数
	public static final int REQ_QR_CODE = 11002; // // 打开扫描界面请求码
	public static final int REQ_PERM_CAMERA = 11003; // 打开摄像头
	public static final String INTENT_EXTRA_KEY_QR_SCAN = "qr_scan_result";
	//抢购项目是否报名
	public static final String PROJECT_IS_PART = "1";
	public static final String PROJECTIS_NOT_PART = "0";//未报名

	public static final String IS_DEFAULT = "1";//是否是默认地址

	public static final int CODE_ACCOUNT_ALI = 30;//账户类型code 支付宝
	public static final int CODE_ACCOUNT_WX = 31;//账户类型code 微信

}
