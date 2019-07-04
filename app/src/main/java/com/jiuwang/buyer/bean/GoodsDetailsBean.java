package com.jiuwang.buyer.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lihj on 2019/6/19
 * desc: 商品
 */

public class GoodsDetailsBean implements Serializable {


	/**
	 * config_info : [{"spec_name":"规格名称","spec_value":"规格描述"},{"spec_name":"酒精度","spec_value":"52%"}]
	 * pic_list : ["https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1561610861276&di=2d8c04127b8351e7519accce7adf373d&imgtype=0&src=http%3A%2F%2Fwww.baimg.com%2Fuploadfile%2F2015%2F0112%2FBTW_2015011248166.jpg","https://image.baidu.com/search/detail?ct=503316480&z=0&ipn=d&word=%E8%BD%AE%E6%92%AD%E5%9B%BE&step_word=&hs=0&pn=79&spn=0&di=12540&pi=0&rn=1&tn=baiduimagedetail&is=0%2C0&istype=2&ie=utf-8&oe=utf-8&in=&cl=2&lm=-1&st=-1&cs=2658912767%2C4006246253&os=313934023%2C2973580904&simid=4202445716%2C769453605&adpicid=0&lpn=0&ln=1548&fr=&fmq=1561600769951_R&fm=result&ic=&s=undefined&hd=&latest=&copyright=&se=&sme=&tab=0&width=&height=&face=undefined&ist=&jit=&cg=&bdtype=0&oriquery=&objurl=http%3A%2F%2Fpic2.cxtuku.com%2F00%2F09%2F01%2Fb14515268df1.jpg&fromurl=ippr_z2C%24qAzdH3FAzdH3Fooo_z%26e3Bvxp7h7_z%26e3Bv54AzdH3Frtv_la889c_z%26e3Bip4s&gsm=5a&rpstart=0&rpnum=0&islist=&querylist=&force=undefined"]
	 * detail_list : ["http://img4.hqbcdn.com/product/79/f3/79f3ef1b0b2283def1f01e12f21606d4.jpg","http://img14.hqbcdn.com/product/77/6c/776c63e6098f05fdc5639adc96d8d6ea.jpg"]
	 * praise : 100%
	 * other_picurl : 见备注
	 * praise_count : 102
	 */

	private String praise;
	private String other_picurl;
	private String praise_count;
	private List<ConfigInfoBean> config_info;
	private List<String> pic_list;
	private List<String> detail_list;

	public String getPraise() {
		return praise;
	}

	public void setPraise(String praise) {
		this.praise = praise;
	}

	public String getOther_picurl() {
		return other_picurl;
	}

	public void setOther_picurl(String other_picurl) {
		this.other_picurl = other_picurl;
	}

	public String getPraise_count() {
		return praise_count;
	}

	public void setPraise_count(String praise_count) {
		this.praise_count = praise_count;
	}

	public List<ConfigInfoBean> getConfig_info() {
		return config_info;
	}

	public void setConfig_info(List<ConfigInfoBean> config_info) {
		this.config_info = config_info;
	}

	public List<String> getPic_list() {
		return pic_list;
	}

	public void setPic_list(List<String> pic_list) {
		this.pic_list = pic_list;
	}

	public List<String> getDetail_list() {
		return detail_list;
	}

	public void setDetail_list(List<String> detail_list) {
		this.detail_list = detail_list;
	}

	public static class ConfigInfoBean {
		/**
		 * spec_name : 规格名称
		 * spec_value : 规格描述
		 */

		private String spec_name;
		private String spec_value;

		public String getSpec_name() {
			return spec_name;
		}

		public void setSpec_name(String spec_name) {
			this.spec_name = spec_name;
		}

		public String getSpec_value() {
			return spec_value;
		}

		public void setSpec_value(String spec_value) {
			this.spec_value = spec_value;
		}
	}
}
