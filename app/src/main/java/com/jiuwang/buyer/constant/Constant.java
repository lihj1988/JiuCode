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
	public static final String ALIPAY_APPID = "2016081900289879";
//	public static final String ALIPAY_APPID = "2019032063599620";//生产环境
	public static final String ALIPAY_METHOD ="alipay.trade.app.pay";
	public static final String ALIPAY_RESULTSTATUS = "9000";
	public static final String CHARSET = "UTF-8";
	public static final String VERSION = "1.0";
	public static final String SIGN_TYPE = "RSA2";
	public static final String ALIPAY_PRODUCT_CODE = "QUICK_MSECURITY_PAY";
	public static final boolean RSA2 = true;
	public static final String NOTIFY_URL = "http://47.93.82.141:9999/notify.jsp";
	public static final String PRIVATE_KEY = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCRURe3ChBgQL2WAdeGxs8LLMy1GaPmJtUu1jCZBn3fTXwwmB2AbcOlA9yjKKo1YVNLovBD2bylkwSpf7LYirmACQIOv0qkqPrmwQfCpvaR/mBYob+ZumANdMZZJv9Q86s37HL/KXsCQsvtEGiEG2jkHrW+N18LltflVGtTa3y8i+NGnVfv7gFK/aDfewmJUAYjHJ2p2lTWYC5YI0bkbDpMpI+uMF200+D7gHHy9Sdrq8utD2ACgKUA8gx/c3Dudd05lHXhJ0rT8tR+MsYDwzGApITwdnZOhV/bAW2NvF8O5/cHNCX16LjJIKT4pwYgRyheP6EgZ+RGT1FoY9PZAH8FAgMBAAECggEACrP5Vi70k9JQ5GqQUEn2V/Fdxnzz+HClxQRbofm7baYMXpvlmF79SPsm1hGZr8LSWsDOh4DTBzLz5fQU3SQik46WSzXFrqkPic1v3+NiR9B/kiefsBftGTCfp1Na/MBlvQG6WpbYi4sV8AkEWa6XJC4q3q6/XmAm2KpGGWfSUSSzuXN2yJpy05BcwDD4r8pn+KIgwWjKDG6wjBhLdXwO+oLsKxjTV6HAuScCMrUfN5hlyN0kzZ6b7gHGv7MIwcfidiY2Dw6bT9xXCbc/pSBkMdb3Vz1FURBameextXx3erLTJdClju2WTnPe8BJnsl7ILiTlJIPC1AKCLWplMe8CQQKBgQDinTrNnOiiqIKrMB6qDTrqh3noo/kan3+R0cmG3cEohgh9X7UHHIVy4zLDVXASYuE9dpm1S645szlY8skAdhToo6jSbQiU/9dMVGIF7Y9YZ1hYYHHPIWFzb4ErbQsQRxNDB1tpcmd+vqLP6iR3+HpIRLUcoNSHyQ0ZKw/mEgTP8QKBgQCkKRVPFKO1nIfHCbG2eYgnRggkeVx02/1AXIOr4mr6hCl61mktTzgVX1TgUv0auV1+0ZdBc8OKS+F/jMiJ/WjYQ1zASlia3Kol/hlxNCpWdhms1+a2OHRKEObLo077j1Ub6gHRl1UJPyVZYc9PKWtDA5ShzhKS9+kp+ie34FK0VQKBgGMHMG4vtl12Y/upAI1xUKCyaSynkzD2KEoxse5Us5cg007uZu5foVVuQX2otVvjh+hTDqJyGwWt806SchHYiBSNiDXEXe62yn/ZlChhArYwGyQrqTBJtws52+l9Mkt/s8Qjz+eJjf6OEhnniN5AYDd6huamxZk825N3BPCWSTIRAoGAdjBd9yGU/BxGZTbFi+rT6/KvyhLWTgxUuRHies3414RvmNDlyIlQh5KrORaGw8bRWYzsqWL/VYJsR7jEaRTZd1qIXKkqQdS9a70V9DrqgHVm5tpKcMcUcn7Yq6RYKSV40TAZPx0FTDL6YO/aodPgNc9OI3CKTuXOKTrBfdEzukECgYB+EGu+WcKT186b2WAXRHLLkihqiT8iuLRyAtiRqIbknhMUHJQ2cG7tu0e2dMIfffuYnmBYX0VVtJ0fE2CJ3qxKd0cMdI7gGpxkrrbHqB6ypjWYPbCbwy4XEEn4542tTwbSFiuvcn39mn4S2J0ZxDd6xWzKFZ/jM0ottFegpIegkw==";
	//提单状态
	public static final String ORDER_STATUS_UNPAY = "0";
	public static final String ORDER_STATUS_PAYED = "1";
	public static final String ORDER_STATUS_FINISH = "2";

	//业务类型
	public static final String BUSINESSTYPE_RECHARGE = "3";//充值
	public static final String BUSINESSTYPE_PAYMWENT = "1";//订单付款
//	public static final String BUSINESSTYPE_PAYMWENT = "2";//返利
//	public static final String BUSINESSTYPE_PAYMWENT = "4";//底线


}
