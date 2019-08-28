package com.jiuwang.buyer.util.wxpay;

import android.util.Log;

import com.jiuwang.buyer.constant.Constant;
import com.jiuwang.buyer.constant.NetURL;
import com.jiuwang.buyer.util.SystemUtil;
import com.tencent.mm.opensdk.modelpay.PayReq;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;


import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Created by lihj on 2019/8/26
 * desc:
 */

public class WXPayUtils {

	private String out_trade_no;
	private PayReq req;
	private String body;
	private String total_fee;
	private String attach;


	public WXPayUtils(String out_trade_no, String body, String total_fee, String attach) {
		this.out_trade_no = out_trade_no;

		this.body = body;
		this.total_fee = total_fee;
		this.attach = attach;

	}

	private final String TAG = WXPayUtils.class.getName();

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
			packageParams.add(new BasicNameValuePair("total_fee", String.valueOf(Integer.parseInt(total_fee) * 100)));
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

	//统一下单请求
	public SortedMap<Object, Object> requestProductArgs() {

		SortedMap<Object, Object> parameters = new TreeMap<Object, Object>();
		try {

			parameters.put("appid", Constant.WXPAY_APPID);
			parameters.put("body", "weixin");
			parameters.put("mch_id", Constant.MCH_ID);
			parameters.put("nonce_str", genNonceStr());
			parameters.put("notify_url", NetURL.notify_url_wx);
			parameters.put("out_trade_no", out_trade_no);
			parameters.put("spbill_create_ip", SystemUtil.getIpAddressString());
			parameters.put("total_fee", String.valueOf(Integer.parseInt(total_fee) * 100));
			parameters.put("trade_type", "APP");
			parameters.put("attach", attach);
			parameters.put("attach", attach);
			List<NameValuePair> packageParams = new LinkedList<NameValuePair>();
			packageParams.add(new BasicNameValuePair("appid", parameters.get("appid").toString()));
			packageParams.add(new BasicNameValuePair("body", parameters.get("body").toString()));
			packageParams.add(new BasicNameValuePair("mch_id", parameters.get("body").toString()));
			packageParams.add(new BasicNameValuePair("nonce_str", parameters.get("nonce_str").toString()));
			packageParams.add(new BasicNameValuePair("notify_url", parameters.get("notify_url").toString()));
			packageParams.add(new BasicNameValuePair("out_trade_no", parameters.get("out_trade_no").toString()));
			packageParams.add(new BasicNameValuePair("spbill_create_ip", parameters.get("spbill_create_ip").toString()));
			packageParams.add(new BasicNameValuePair("total_fee", parameters.get("total_fee").toString()));
			packageParams.add(new BasicNameValuePair("trade_type", parameters.get("trade_type").toString()));
			packageParams.add(new BasicNameValuePair("attach", parameters.get("attach").toString()));
			String sign = genAppSign(packageParams);
			parameters.put("sign", sign);
			return parameters;

		} catch (Exception e) {
			Log.e(TAG, "genProductArgs fail, ex = " + e.getMessage());
			return null;
		}

	}


	public String genNonceStr() {
		Random random = new Random();
		return MD5.getMessageDigest(String.valueOf(random.nextInt(10000))
				.getBytes());
	}

	public long genTimeStamp() {
		return System.currentTimeMillis() / 1000;
	}


	public String genAppSign(List<NameValuePair> params) {
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < params.size(); i++) {
			sb.append(params.get(i).getName());
			sb.append('=');
			sb.append(params.get(i).getValue());
			sb.append('&');
		}
		sb.append("key=");
		sb.append(Constant.WXAPI_KEY);

//		sb.append("sign str\n" + sb.toString() + "\n\n");
		String appSign = MD5.getMessageDigest(sb.toString().getBytes())
				.toUpperCase();
		Log.e("orion", appSign);
		return appSign;
	}


	//	将map数组拼装成xml
	public String getRequestXml(SortedMap<Object, Object> parameters) {
		StringBuffer sb = new StringBuffer();
		sb.append("<xml>");
		Set es = parameters.entrySet();
		Iterator it = es.iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String k = (String) entry.getKey();
			String v = (String) entry.getValue();
			if ("attach".equalsIgnoreCase(k) || "body".equalsIgnoreCase(k)) {
				sb.append("<" + k + ">" + "<![CDATA[" + v + "]]></" + k + ">");
			} else {
				sb.append("<" + k + ">" + v + "</" + k + ">");
			}
		}
		sb.append("</xml>");
		return sb.toString();
	}

	/** * @description 将xml字符串转换成map * @param xml * @return Map */
	public static Map<String, String> readStringXmlOut(String xml) {
		Map<String, String> map = new HashMap<String, String>();
		Document doc = null;
		try {
			doc = DocumentHelper.parseText(xml); // 将字符串转为XML
			Element rootElt = doc.getRootElement(); // 获取根节点
			@SuppressWarnings("unchecked")
			List<Element> list = rootElt.elements();// 获取根节点下所有节点
			for (Element element : list) { // 遍历节点
				map.put(element.getName(), element.getText()); // 节点的name为map的key，text为map的value
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

}
