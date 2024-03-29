package com.jiuwang.buyer.bean;

import com.jiuwang.buyer.constant.Constant;

import java.io.Serializable;
import java.util.List;

/**
 * author：lihj
 * desc：订单信息
 * Created by lihj on  2019/7/1 11:00.
 */

public class OrderBean implements Serializable {



	private String timeout_express;//有效时间
	private String product_code;//有效时间
	private String total_amount;//总金额
	private String subject;//商品的标题/交易标题/订单标题/订单关键字等
	private String out_trade_no;//商户网站唯一订单号
	private String body;//对一笔交易的具体描述信息。如果是多种商品，请将商品描述字符串累加传给body
	private List<DetailListBean> detail_list;
	private String id;
	private String create_time;
	private String goods_name;
	private String quantity;
	private String sale_price;
	private String producer;
	private String grouping_name;
	private String grouping_name_seller;
	private String total_quantity;
	private String pic_url;
	private String notes;
	private String status;
	private String business_type;
	private String status_name;
	private String pay_mode;
	private String order_type;//订单类型
	private String order_type_name;
	private String consignee_name;
	private String consignee_telephone;
	private String destination;
	private String attach;

	public String getAttach() {
		return attach;
	}

	public void setAttach(String attach) {
		this.attach = attach;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public String getGoods_name() {
		return goods_name;
	}

	public void setGoods_name(String goods_name) {
		this.goods_name = goods_name;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getSale_price() {
		return sale_price;
	}

	public void setSale_price(String sale_price) {
		this.sale_price = sale_price;
	}

	public String getProducer() {
		return producer;
	}

	public void setProducer(String producer) {
		this.producer = producer;
	}

	public String getGrouping_name() {
		return grouping_name;
	}

	public void setGrouping_name(String grouping_name) {
		this.grouping_name = grouping_name;
	}

	public String getGrouping_name_seller() {
		return grouping_name_seller;
	}

	public void setGrouping_name_seller(String grouping_name_seller) {
		this.grouping_name_seller = grouping_name_seller;
	}

	public String getTotal_quantity() {
		return total_quantity;
	}

	public void setTotal_quantity(String total_quantity) {
		this.total_quantity = total_quantity;
	}

	public String getPic_url() {
		return pic_url;
	}

	public void setPic_url(String pic_url) {
		this.pic_url = pic_url;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public List<DetailListBean> getDetail_list() {
		return detail_list;
	}

	public void setDetail_list(List<DetailListBean> detail_list) {
		this.detail_list = detail_list;
	}

	public String getTimeout_express() {
		return timeout_express;
	}

	public void setTimeout_express(String timeout_express) {
		this.timeout_express = timeout_express;
	}

	public String getTotal_amount() {
		return total_amount;
	}

	public void setTotal_amount(String total_amount) {
		this.total_amount = total_amount;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getOut_trade_no() {
		return out_trade_no;
	}

	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}

	public String getProduct_code() {
		return Constant.ALIPAY_PRODUCT_CODE;
	}

	public void setProduct_code(String product_code) {
		this.product_code = product_code;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatus_name() {
		return status_name;
	}

	public void setStatus_name(String status_name) {
		this.status_name = status_name;
	}

	public String getBusiness_type() {
		return business_type;
	}

	public void setBusiness_type(String business_type) {
		this.business_type = business_type;
	}

	public String getPay_mode() {
		return pay_mode;
	}

	public void setPay_mode(String pay_mode) {
		this.pay_mode = pay_mode;
	}

	public String getOrder_type() {
		return order_type;
	}

	public void setOrder_type(String order_type) {
		this.order_type = order_type;
	}

	public String getOrder_type_name() {
		return order_type_name;
	}

	public void setOrder_type_name(String order_type_name) {
		this.order_type_name = order_type_name;
	}

	public String getConsignee_name() {
		return consignee_name;
	}

	public void setConsignee_name(String consignee_name) {
		this.consignee_name = consignee_name;
	}

	public String getConsignee_telephone() {
		return consignee_telephone;
	}

	public void setConsignee_telephone(String consignee_telephone) {
		this.consignee_telephone = consignee_telephone;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public static class DetailListBean implements Serializable{
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
