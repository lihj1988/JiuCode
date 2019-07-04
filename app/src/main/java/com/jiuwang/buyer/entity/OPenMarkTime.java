package com.jiuwang.buyer.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2019/2/12.
 * 资源开市时间
 */

public class OPenMarkTime implements Serializable{
	private String openMarketTimeStart;//开市时间
	private String openMarketTimeStop;//闭市时间
	private String openMarketFlagName;//当前开市状态

	public String getOpenMarketTimeStart() {
		return openMarketTimeStart;
	}

	public void setOpenMarketTimeStart(String openMarketTimeStart) {
		this.openMarketTimeStart = openMarketTimeStart;
	}

	public String getOpenMarketTimeStop() {
		return openMarketTimeStop;
	}

	public void setOpenMarketTimeStop(String openMarketTimeStop) {
		this.openMarketTimeStop = openMarketTimeStop;
	}

	public String getOpenMarketFlagName() {
		return openMarketFlagName;
	}

	public void setOpenMarketFlagName(String openMarketFlagName) {
		this.openMarketFlagName = openMarketFlagName;
	}
}
