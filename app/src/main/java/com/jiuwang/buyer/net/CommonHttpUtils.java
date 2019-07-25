package com.jiuwang.buyer.net;

import com.jiuwang.buyer.bean.UserBean;
import com.jiuwang.buyer.entity.BaseResultEntity;
import com.jiuwang.buyer.entity.UserEntity;

import java.util.HashMap;

import io.reactivex.functions.Consumer;

/**
 * author：lihj
 * desc：
 * Created by lihj on  2019/7/23 14:15.
 */

public class CommonHttpUtils {

	/**
	 * 绑定邀请码
	 * @param map
	 * @param callingBack
	 */
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
	//查询用户信息
	public static void selectUserInfo(HashMap<String, String> map, final UserCallBack callingBack){
		HttpUtils.selectUserInfo(map, new Consumer<UserEntity>() {
			@Override
			public void accept(UserEntity userEntity) throws Exception {
				callingBack.successBack(userEntity.getData().get(0));
			}
		}, new Consumer<Throwable>() {
			@Override
			public void accept(Throwable throwable) throws Exception {
				callingBack.failBack();
			}
		});
	}

	public interface UserCallBack{
		void successBack(UserBean userBean);
		void failBack();

	}
}
