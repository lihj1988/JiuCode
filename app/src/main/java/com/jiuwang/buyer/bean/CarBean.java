package com.jiuwang.buyer.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lihj on 2019/7/2
 * desc:
 */

public class CarBean implements Serializable {

	/**
	 * grouping_cd : 1999
	 * count : 4
	 * grouping_name : 酒网电商
	 * goods_detail : [{"id":"1999c0r8u1o3whf03","goods_id":"1","goods_code":"1","goods_name":"1","price":1,"sale_price":1,"quantity":1,"deliver_place":"1","producer":"1","notes":"1","grouping_cd":"1999","grouping_cd_seller":"1999","create_user":"coder","create_username":"超级管理员","create_time":"2019-07-02 10:10:03","pic_url":"","grouping_name":"酒网电商"}]
	 */

	private String grouping_cd;
	private String count;
	private String grouping_name;
	private boolean ischeck;//是否被选中
	private List<CarGoodsBean> goods_detail;

	public String getGrouping_cd() {
		return grouping_cd;
	}

	public void setGrouping_cd(String grouping_cd) {
		this.grouping_cd = grouping_cd;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public String getGrouping_name() {
		return grouping_name;
	}

	public void setGrouping_name(String grouping_name) {
		this.grouping_name = grouping_name;
	}

	public boolean ischeck() {
		return ischeck;
	}

	public void setIscheck(boolean ischeck) {
		this.ischeck = ischeck;
	}

	public List<CarGoodsBean> getGoods_detail() {
		return goods_detail;
	}

	public void setGoods_detail(List<CarGoodsBean> goods_detail) {
		this.goods_detail = goods_detail;
	}


}
