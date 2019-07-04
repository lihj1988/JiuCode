package com.jiuwang.buyer.net;


import com.jiuwang.buyer.bean.MoneyNumberBean;
import com.jiuwang.buyer.bean.SuccessBean;
import com.jiuwang.buyer.entity.BaseEntity;
import com.jiuwang.buyer.entity.BaseResultEntity;
import com.jiuwang.buyer.entity.GoodsDetailsEntity;
import com.jiuwang.buyer.entity.HomeResultEntity;
import com.jiuwang.buyer.entity.LoginEntity;
import com.jiuwang.buyer.entity.MyCarEntity;

import java.util.HashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * author：lihj
 * desc：
 * Created by lihj on  2019/1/25 10:33.
 */

public class HttpUtils {
	/**
	 * 登录
	 *
	 * @param map
	 * @return
	 */
	public static void register(HashMap<String,String> map, Consumer<BaseResultEntity> consumer, Consumer<Throwable> throwableConsumer) {

		QClitent.getInstance()
				.register(map)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(consumer, throwableConsumer);

	}
	/**
	 * 登录
	 *
	 * @param map
	 * @return
	 */
	public static void login(HashMap<String,String> map, Consumer<BaseEntity<LoginEntity>> consumer, Consumer<Throwable> throwableConsumer) {

		QClitent.getInstance()
				.getLogining(map)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(consumer, throwableConsumer);

	}
	/**
	 * 获取首页数据
	 *
	 * @param map
	 * @return
	 */
	public static void goodsInfo(HashMap<String,String> map, Consumer<HomeResultEntity> consumer, Consumer<Throwable> throwableConsumer) {

		QClitent.getInstance()
				.goodsInfo(map)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(consumer, throwableConsumer);

	}

	/**
	 * 获取商品详情
	 *
	 * @param map
	 * @return
	 */
	public static void selectGoodsDetails(HashMap<String,String> map, Consumer<GoodsDetailsEntity> consumer, Consumer<Throwable> throwableConsumer) {

		QClitent.getInstance()
				.selectGoodsDetails(map)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(consumer, throwableConsumer);

	}
	/**
	 * 添加购物车
	 *
	 * @param map
	 * @return
	 */
	public static void addCar(HashMap<String,String> map, Consumer<BaseResultEntity> consumer, Consumer<Throwable> throwableConsumer) {

		QClitent.getInstance()
				.addCar(map)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(consumer, throwableConsumer);

	}

	/**
	 * 获取购物车
	 *
	 * @param map
	 * @return
	 */
	public static void selectCar(HashMap<String,String> map, Consumer<MyCarEntity> consumer, Consumer<Throwable> throwableConsumer) {

		QClitent.getInstance()
				.selectCar(map)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(consumer, throwableConsumer);

	}

	/**
	 * 获取购物车
	 *
	 * @param map
	 * @return
	 */
	public static void cartInfo(HashMap<String,String> map, Consumer<BaseResultEntity> consumer, Consumer<Throwable> throwableConsumer) {

		QClitent.getInstance()
				.cartInfo(map)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(consumer, throwableConsumer);

	}


	/**
	 *获得购物车和可用余额
	 * @param consumer
	 * @param throwableConsumer
	 */
	public static void getNumberdata(String act, Consumer<MoneyNumberBean> consumer, Consumer<Throwable> throwableConsumer) {
		QClitent.getInstance()
				.getNumberdata(act)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(consumer, throwableConsumer);
	}
	/**
	 *修改密码
	 * @param throwableConsumer
	 */
	public static void getchagepass(String act, String newpass, String oldpass , Consumer<SuccessBean> consumer, Consumer<Throwable> throwableConsumer) {
		QClitent.getInstance()
				.getchagepass(act,newpass,oldpass)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(consumer, throwableConsumer);
	}

}
