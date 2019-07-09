package com.jiuwang.buyer.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * author：lihj
 * desc：字符串处理
 * Created by lihj on  2019/7/4 17:05.
 */

public class StringUtils {

	public static int getTimeCompareSize(String startTime, String endTime){
		int i=0;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//年-月-日 时-分
		try {
			Date date1 = dateFormat.parse(startTime);//开始时间
			Date date2 = dateFormat.parse(endTime);//结束时间
			// 1 结束时间小于开始时间 2 开始时间与结束时间相同 3 结束时间大于开始时间
			if (date2.getTime()<date1.getTime()){
				i= 1;
			}else if (date2.getTime()==date1.getTime()){
				i= 2;
			}else if (date2.getTime()>date1.getTime()){
				//正常情况下的逻辑操作.
				i= 3;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return  i;
	}
}
