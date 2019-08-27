package com.jiuwang.buyer.util.wxpay;

import android.util.Log;

import com.jiuwang.buyer.constant.Constant;
import com.jiuwang.buyer.constant.NetURL;
import com.jiuwang.buyer.util.SystemUtil;
import com.tencent.mm.opensdk.modelpay.PayReq;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Created by lihj on 2019/8/26
 * desc:
 */

public class WXPayUtils {

	private String out_trade_no;
	private PayReq req ;
	private String body ;
	private String total_fee ;
	private String attach ;


	public WXPayUtils(String out_trade_no,String body,String total_fee,String attach) {
		this.out_trade_no = out_trade_no;

		this.body = body;
		this.total_fee = total_fee;
		this.attach = attach;

	}

	private final String TAG =WXPayUtils.class.getName();
	//统一下单请求
	public String genProductArgs() {
		StringBuffer xml = new StringBuffer();

		try {
			String nonceStr = genNonceStr();

			xml.append("</xml>");
			List<NameValuePair> packageParams = new LinkedList<NameValuePair>();
			packageParams
					.add(new BasicNameValuePair("appid", Constant.WXPAY_APPID));
			packageParams.add(new BasicNameValuePair("body", "weixin"));
			packageParams
					.add(new BasicNameValuePair("mch_id", Constant.MCH_ID));
			packageParams.add(new BasicNameValuePair("nonce_str", nonceStr));
			packageParams.add(new BasicNameValuePair("notify_url",
					NetURL.notify_url_wx));
			packageParams.add(new BasicNameValuePair("out_trade_no",
					out_trade_no));
			packageParams.add(new BasicNameValuePair("spbill_create_ip",
					SystemUtil.getIpAddressString()));
			packageParams.add(new BasicNameValuePair("total_fee", String.valueOf(Integer.parseInt(total_fee)*100)));
			packageParams.add(new BasicNameValuePair("trade_type", "APP"));
			packageParams.add(new BasicNameValuePair("attach", attach));

			String sign = genAppSign(packageParams);
			packageParams.add(new BasicNameValuePair("sign", sign));

//			String xmlstring = toXml(packageParams);
			String xmlstring = "";

			return xmlstring;

		} catch (Exception e) {
			Log.e(TAG, "genProductArgs fail, ex = " + e.getMessage());
			return null;
		}

	}


	private void genPayReq(String prepay_id) {

		req.appId = Constant.WXPAY_APPID;
		req.partnerId = Constant.MCH_ID;
		req.prepayId = prepay_id;
		req.packageValue = "Sign=WXPay";
		req.nonceStr = genNonceStr();
		req.timeStamp = String.valueOf(genTimeStamp());
		req.timeStamp = String.valueOf(genTimeStamp());

		List<NameValuePair> signParams = new LinkedList<NameValuePair>();
		signParams.add(new BasicNameValuePair("appid", req.appId));
		signParams.add(new BasicNameValuePair("noncestr", req.nonceStr));
		signParams.add(new BasicNameValuePair("package", req.packageValue));
		signParams.add(new BasicNameValuePair("partnerid", req.partnerId));
		signParams.add(new BasicNameValuePair("prepayid", req.prepayId));
		signParams.add(new BasicNameValuePair("timestamp", req.timeStamp));

		req.sign = genAppSign(signParams);

//		sb.append("sign\n" + req.sign + "\n\n");

//		show.setText(sb.toString());

		Log.e("orion", signParams.toString());

	}

	private String genNonceStr() {
		Random random = new Random();
		return MD5.getMessageDigest(String.valueOf(random.nextInt(10000))
				.getBytes());
	}

	private long genTimeStamp() {
		return System.currentTimeMillis() / 1000;
	}

	private String genAppSign(List<NameValuePair> params) {
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < params.size(); i++) {
			sb.append(params.get(i).getName());
			sb.append('=');
			sb.append(params.get(i).getValue());
			sb.append('&');
		}
		sb.append("key=");
		sb.append(Constant.WXPAY_APPID);

		sb.append("sign str\n" + sb.toString() + "\n\n");
		String appSign = MD5.getMessageDigest(sb.toString().getBytes())
				.toUpperCase();
		Log.e("orion", appSign);
		return appSign;
	}
}
