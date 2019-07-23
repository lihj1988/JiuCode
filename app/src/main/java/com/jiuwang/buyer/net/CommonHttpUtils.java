package com.jiuwang.buyer.net;

import com.jiuwang.buyer.entity.BaseResultEntity;

import java.util.HashMap;

import io.reactivex.functions.Consumer;

/**
 * author：lihj
 * desc：
 * Created by lihj on  2019/7/23 14:15.
 */

public class CommonHttpUtils {


	public static void ref_action(HashMap<String, String> map, final CallingBack callingBack){
		HttpUtils.regVerify(map, new Consumer<BaseResultEntity>() {
			@Override
			public void accept(BaseResultEntity baseResultEntity) throws Exception {
				callingBack.successBack(baseResultEntity);
			}
		}, new Consumer<Throwable>() {
			@Override
			public void accept(Throwable throwable) throws Exception {
				callingBack.failBack();
			}
		});
	}
	public interface CallingBack{
		void successBack(BaseResultEntity baseResultEntity);
		void failBack();

	}
}
