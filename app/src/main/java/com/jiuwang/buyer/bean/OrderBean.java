package com.jiuwang.buyer.bean;

import java.io.Serializable;
import java.util.List;

/**
 * author：lihj
 * desc：订单信息
 * Created by lihj on  2019/7/1 11:00.
 */

public class OrderBean implements Serializable {


//	{
//		"msg": "",
//			"code": "0",
//			"data": [
//		{
//			    "order_id": "订单号",
//				"order_amount": "订单金额",
//				"order_count": "订单件数",
//				"order_state": "订单状态",
//				"evaluation_state": "评价状态",
//				"store_list": [
//			{
//				"store_name": "店铺名称",
//					"store_desc": "店铺描述",
//					"detail_list": [
//				{
//					"goods_price": "价格",
//						"goods_num": "数量",
//						"pic_url": "图片地址"
//				}
//                    ]
//			}
//            ]
//		}
//    ]
//	}

	/**
	 * store_name : 店铺名称
	 * store_desc : 店铺描述
	 * order_amount : 总金额
	 * order_state : 状态
	 * order_count : 总件数
	 * order_id : 订单id
	 * detail_list : [{"goods_price":"价格","goods_num":"数量","pic_url":"图片地址"}]
	 */

	private String store_name;
	private String store_desc;
	private String order_id;
	private String order_amount;
	private String order_count;
	private String order_state;
	private List<DetailListBean> detail_list;

	public String getStore_name() {
		return store_name;
	}

	public void setStore_name(String store_name) {
		this.store_name = store_name;
	}

	public String getStore_desc() {
		return store_desc;
	}

	public void setStore_desc(String store_desc) {
		this.store_desc = store_desc;
	}

	public String getOrder_amount() {
		return order_amount;
	}

	public void setOrder_amount(String order_amount) {
		this.order_amount = order_amount;
	}

	public String getOrder_count() {
		return order_count;
	}

	public void setOrder_count(String order_count) {
		this.order_count = order_count;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getOrder_state() {
		return order_state;
	}

	public void setOrder_state(String order_state) {
		this.order_state = order_state;
	}

	public List<DetailListBean> getDetail_list() {
		return detail_list;
	}

	public void setDetail_list(List<DetailListBean> detail_list) {
		this.detail_list = detail_list;
	}

	public static class DetailListBean {
		/**
		 * goods_name : 名称
		 * goods_price : 价格
		 * goods_num : 数量
		 * pic_url : 图片地址
		 */

		private String goods_name;
		private String goods_price;
		private String goods_num;
		private String pic_url;

		public String getGoods_name() {
			return goods_name;
		}

		public void setGoods_name(String goods_name) {
			this.goods_name = goods_name;
		}

		public String getGoods_price() {
			return goods_price;
		}

		public void setGoods_price(String goods_price) {
			this.goods_price = goods_price;
		}

		public String getGoods_num() {
			return goods_num;
		}

		public void setGoods_num(String goods_num) {
			this.goods_num = goods_num;
		}

		public String getPic_url() {
			return pic_url;
		}

		public void setPic_url(String pic_url) {
			this.pic_url = pic_url;
		}
	}
}
