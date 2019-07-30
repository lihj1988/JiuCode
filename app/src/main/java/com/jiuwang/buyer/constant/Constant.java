package com.jiuwang.buyer.constant;

/**
 * Created by lihj on 2019/7/1
 * desc:
 */

public class Constant {
	public static  boolean IS_LOGIN = false;
	public static  boolean IS_DEBUG = false;//是否是调试
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
	public static final String PRIVATE_KEY = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQDY0dN4gMa3wHv41yARqWCT4IMIDa/S7k+OExY6BCtDnB3m69wvEKuhuxOteojOdGdNSLg2QQncas5UP6WCVzm8GYfBv4SthK5A4pmwgR+3Lv4t+AEKfebrrkkttEEec0dhtzyaNKRr6D42xWRQMPnWEFf5SxJ5CzzdU+wudOcLtc+fcJNqp4RhgewCilAz/AVOQN07MJhK6f9I2x6oelfikjdBj4/+6x93MzeaOFBeO5BAeE2CuQv7MK0/MpBd98mOg3MNvhTznzRNYVlZZpVaD8DbtImcpOnfHZw9+M/PWkjo0QqbbdiW0RgFuNT/V8U7FvV179bznpbZaxMcr4wPAgMBAAECggEAaB+0L82gIQSWRLWDGBhAYiPSqV5xTpnVqMQvy2LPKCPkFE62qf+WfQ/rbkdHReaO6YV/ucZYJvs/5SO/py2ec1/LW1dPL0llqZFo/2OzSHSP6qGMVm6KJCrJx+Il16rC4AFwYBnh0FRy9DitaPlsMcodhYHw+CJWc2/nH5RSlMM0DTS69BwWjhx8RmJPnig20GvGhRQ6gJv1hSjWF6vl27VXd9dqqx3vqm6/+tO2mM104hQ7r/ayXSUz/Zct+2FUEkIKjIztLxGTK5Wqgka8oFeL+akM74gzSVCWTkY+UWvUxBE1x5U5KCdzXziFq3v/eS5VGYs2l0JxvxSFWNeYcQKBgQDs3p/ZnP15gupYfuE6tiq5nqwGmTbJ5L/YAtTHWP2VnzLVrv8z5jb/Kp979TCLqvBcpOo68wZrCUG8Etf/y5ek1tQsnhgsWZKmAKuTVa85tXDPA7qqMZFRBEVdv5CHHf7QooaGUyxl20pcoP+tl+qGQXTgBdOhMVHj8eddJIZVqQKBgQDqVKju5R9JvDe4+OPzClVAx/ySPEdsghvzUiyLUJwipUeF1yA+yyyI7mxUKOaJpttDv8px0xnJ+23hJqqwC67PtoEbvosofngA47fhIjNHk3DIK/V951aAF0XcQtiXwfD0QuQrHILfJ7P17ZYISqS0Ipr/LSH1qNkhrSreRz929wKBgCtMltPkysEHmWcL6ogc/LppsvCR2V4WnqsR+WtnbROIwnYlQyltj+gdFoJGU9V0LfQBgX+9JRMz4fxLRYjQ4quTSb3OOIUC6rfFr5eDNkDKVhDBkcHCxdmVekik1/qbFRV2YDj1w0vrRjCgjZxiBND97it1ZTY2Gc4dTjnMj3HhAoGABzVTfQXPUJVZoXcGjBNlS/ja7CSujcfy1ClS9XYlV2t+J7/WXhgh5pRvAKfBb7qSRCNCNv4nbO8vEh3+Yiv51KnojtHttfEt/9kBbwt+SFVgwseXZagYIRiWLZBg2Uo9/0WPf0QBQpwWDRqh4t9o8aGe4vqgcM4g38hT6UKH0UECgYBHuNey4MPjT0ZpBc9V4jucQdhLkJBUEiHZD3MR2LmBB5CqFaJ3qU7weQjkdp5+f3yc/gsjvJgD07YLY6atfzLYgB8JY6JkeQn7q4hWkcZTtf0Vac5jCIxgPkJ3Jwr6YT7YS5HZTOzhkDAQEtgUbAZzthDf1Y1LZ/x+ZWu+k8ihlQ==";
	//提单状态
	public static final String ORDER_STATUS_UNPAY = "0";//未付款
	public static final String ORDER_STATUS_PAYED = "1";//已付款
	public static final String ORDER_STATUS_FINISH = "2";//已完成
	public static final String ORDER_STATUS_CANCLE = "3";//已取消
	public static final String ORDER_STATUS_SEND = "4";//已发货

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
	public static final int REQUEST_CALL_PERMISSION = 10111; //拨号请求码
	public static final int REQUEST_WRITE_READ_PERMISSION = 10112; //读写权限

	public static String localVersion;// 本地安装版本
	public static String serverVersion;// 服务器版本
}
