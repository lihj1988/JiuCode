package com.jiuwang.buyer.entity;

import com.jiuwang.buyer.bean.LotteryRecordBean;

import java.util.List;

/**
 * author：lihj
 * desc：
 * Created by lihj on  2019/8/20 14:41.
 */

public class LotteryEntity extends BaseResultEntity {
	List<LotteryRecordBean> data;

	public List<LotteryRecordBean> getData() {
		return data;
	}

	public void setData(List<LotteryRecordBean> data) {
		this.data = data;
	}
}
