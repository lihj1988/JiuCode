package com.jiuwang.buyer.net;


import com.jiuwang.buyer.bean.MoneyNumberBean;
import com.jiuwang.buyer.bean.SuccessBean;
import com.jiuwang.buyer.entity.AddressEntity;
import com.jiuwang.buyer.entity.AnnouncementEntity;
import com.jiuwang.buyer.entity.BalanceEntity;
import com.jiuwang.buyer.entity.BaseEntity;
import com.jiuwang.buyer.entity.BaseResultEntity;
import com.jiuwang.buyer.entity.GoodsDetailsEntity;
import com.jiuwang.buyer.entity.HomeResultEntity;
import com.jiuwang.buyer.entity.InviteEntity;
import com.jiuwang.buyer.entity.InviteUserEntity;
import com.jiuwang.buyer.entity.LoginEntity;
import com.jiuwang.buyer.entity.LotteryEntity;
import com.jiuwang.buyer.entity.MyCarEntity;
import com.jiuwang.buyer.entity.OrderEntity;
import com.jiuwang.buyer.entity.PoolEntity;
import com.jiuwang.buyer.entity.ProjectDetailsEntity;
import com.jiuwang.buyer.entity.ProjectEntity;
import com.jiuwang.buyer.entity.SelectGoodsEntity;
import com.jiuwang.buyer.entity.UserEntity;

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
	 * 注册
	 *
	 * @param map
	 * @return
	 */
	public static void register(HashMap<String, String> map, Consumer<BaseResultEntity> consumer, Consumer<Throwable> throwableConsumer) {

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
	public static void login(HashMap<String, String> map, Consumer<BaseEntity<LoginEntity>> consumer, Consumer<Throwable> throwableConsumer) {

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
	public static void goodsInfo(HashMap<String, String> map, Consumer<HomeResultEntity> consumer, Consumer<Throwable> throwableConsumer) {

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
	public static void selectGoodsDetails(HashMap<String, String> map, Consumer<GoodsDetailsEntity> consumer, Consumer<Throwable> throwableConsumer) {

		QClitent.getInstance()
				.selectGoodsDetails(map)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(consumer, throwableConsumer);

	}

	/**
	 * 获取抢购数据
	 *
	 * @param map
	 * @return
	 */
	public static void selectProjectList(HashMap<String, String> map, Consumer<ProjectEntity> consumer, Consumer<Throwable> throwableConsumer) {

		QClitent.getInstance()
				.selectProjectList(map)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(consumer, throwableConsumer);

	}

	/**
	 * 抢购-获取选择商品
	 *
	 * @param map
	 * @return
	 */
	public static void selectChooseGoods(HashMap<String, String> map, Consumer<SelectGoodsEntity> consumer, Consumer<Throwable> throwableConsumer) {

		QClitent.getInstance()
				.selectChooseGoods(map)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(consumer, throwableConsumer);

	}

	/**
	 * 抢购-报名
	 *
	 * @param map
	 * @return
	 */
	public static void enroll(HashMap<String, String> map, Consumer<BaseResultEntity> consumer, Consumer<Throwable> throwableConsumer) {

		QClitent.getInstance()
				.enroll(map)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(consumer, throwableConsumer);

	}

	/**
	 * 抢购-查询中奖
	 *
	 * @param map
	 * @return
	 */
	public static void isWin(HashMap<String, String> map, Consumer<ProjectDetailsEntity> consumer, Consumer<Throwable> throwableConsumer) {

		QClitent.getInstance()
				.isWin(map)
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
	public static void addCar(HashMap<String, String> map, Consumer<BaseResultEntity> consumer, Consumer<Throwable> throwableConsumer) {

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
	public static void selectCar(HashMap<String, String> map, Consumer<MyCarEntity> consumer, Consumer<Throwable> throwableConsumer) {

		QClitent.getInstance()
				.selectCar(map)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(consumer, throwableConsumer);

	}

	/**
	 * 购物车相关操作
	 *
	 * @param map
	 * @return
	 */
	public static void cartInfo(HashMap<String, String> map, Consumer<BaseResultEntity> consumer, Consumer<Throwable> throwableConsumer) {

		QClitent.getInstance()
				.cartInfo(map)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(consumer, throwableConsumer);

	}

	/**
	 * 购物车结算
	 *
	 * @param map
	 * @return
	 */
	public static void settlement(HashMap<String, String> map, Consumer<OrderEntity> consumer, Consumer<Throwable> throwableConsumer) {

		QClitent.getInstance()
				.settlement(map)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(consumer, throwableConsumer);

	}

	/**
	 * 获取收货地址
	 *
	 * @param map
	 * @return
	 */
	public static void selectAddressList(HashMap<String, String> map, Consumer<AddressEntity> consumer, Consumer<Throwable> throwableConsumer) {

		QClitent.getInstance()
				.selectAddressList(map)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(consumer, throwableConsumer);

	}

	/**
	 * 获取收货地址-相关操作
	 *
	 * @param map
	 * @return
	 */
	public static void addressInfo(HashMap<String, String> map, Consumer<BaseResultEntity> consumer, Consumer<Throwable> throwableConsumer) {

		QClitent.getInstance()
				.addressInfo(map)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(consumer, throwableConsumer);

	}

	/**
	 * 订单
	 *
	 * @param map
	 * @return
	 */
	public static void selectOrder(HashMap<String, String> map, Consumer<OrderEntity> consumer, Consumer<Throwable> throwableConsumer) {

		QClitent.getInstance()
				.selectOrder(map)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(consumer, throwableConsumer);

	}
	/**
	 * 订单操作
	 *
	 * @param map
	 * @param consumer
	 * @param throwableConsumer
	 */
	public static void orderInfo(HashMap<String, String> map, Consumer<BaseResultEntity> consumer, Consumer<Throwable> throwableConsumer) {
		QClitent.getInstance()
				.orderInfo(map)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(consumer, throwableConsumer);
	}


	/**
	 * 获得购物车和可用余额
	 *
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
	 * 修改密码
	 *
	 * @param throwableConsumer
	 */
	public static void getchagepass(String act, String newpass, String oldpass, Consumer<SuccessBean> consumer, Consumer<Throwable> throwableConsumer) {
		QClitent.getInstance()
				.getchagepass(act, newpass, oldpass)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(consumer, throwableConsumer);
	}

	/**
	 * 忘记密码-获取验证码
	 *
	 * @param throwableConsumer
	 */
	public static void getVerify(HashMap<String, String> map, Consumer<BaseResultEntity> consumer, Consumer<Throwable> throwableConsumer) {
		QClitent.getInstance()
				.getVerify(map)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(consumer, throwableConsumer);
	}

	/**
	 * 注册-获取验证码
	 *
	 * @param throwableConsumer
	 */
	public static void regVerify(HashMap<String, String> map, Consumer<BaseResultEntity> consumer, Consumer<Throwable> throwableConsumer) {
		QClitent.getInstance()
				.regVerify(map)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(consumer, throwableConsumer);
	}

	/**
	 * 忘记密码-重置密码
	 *
	 * @param throwableConsumer
	 */
	public static void onForget(HashMap<String, String> map, Consumer<BaseResultEntity> consumer, Consumer<Throwable> throwableConsumer) {
		QClitent.getInstance()
				.onForget(map)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(consumer, throwableConsumer);
	}

	/**
	 * 获取用户信息
	 *
	 * @param map
	 * @param consumer
	 * @param throwableConsumer
	 */
	public static void selectUserInfo(HashMap<String, String> map, Consumer<UserEntity> consumer, Consumer<Throwable> throwableConsumer) {
		QClitent.getInstance()
				.selectUserInfo(map)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(consumer, throwableConsumer);
	}

	/**
	 * 用户信息操作
	 *
	 * @param map
	 * @param consumer
	 * @param throwableConsumer
	 */
	public static void userInfo(HashMap<String, String> map, Consumer<BaseResultEntity> consumer, Consumer<Throwable> throwableConsumer) {
		QClitent.getInstance()
				.userInfo(map)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(consumer, throwableConsumer);
	}

	/**
	 * 获取用户信息
	 *
	 * @param map
	 * @param consumer
	 * @param throwableConsumer
	 */
	public static void recharge(HashMap<String, String> map, Consumer<BaseResultEntity> consumer, Consumer<Throwable> throwableConsumer) {
		QClitent.getInstance()
				.recharge(map)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(consumer, throwableConsumer);
	}

	/**
	 * 资金
	 *
	 * @param map
	 * @param consumer
	 * @param throwableConsumer
	 */
	public static void fund(HashMap<String, String> map, Consumer<BalanceEntity> consumer, Consumer<Throwable> throwableConsumer) {
		QClitent.getInstance()
				.fund(map)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(consumer, throwableConsumer);
	}

	/**
	 * 余额付款
	 *
	 * @param map
	 * @param consumer
	 * @param throwableConsumer
	 */
	public static void fundPay(HashMap<String, String> map, Consumer<BaseResultEntity> consumer, Consumer<Throwable> throwableConsumer) {
		QClitent.getInstance()
				.fundPay(map)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(consumer, throwableConsumer);
	}

	/**
	 * 被邀请人查询
	 *
	 * @param map
	 * @param consumer
	 * @param throwableConsumer
	 */
	public static void invite(HashMap<String, String> map, Consumer<InviteEntity> consumer, Consumer<Throwable> throwableConsumer) {
		QClitent.getInstance()
				.invite(map)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(consumer, throwableConsumer);
	}
	/**
	 * 被邀请人查询
	 *
	 * @param map
	 * @param consumer
	 * @param throwableConsumer
	 */
	public static void inviteUser(HashMap<String, String> map, Consumer<InviteUserEntity> consumer, Consumer<Throwable> throwableConsumer) {
		QClitent.getInstance()
				.inviteUser(map)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(consumer, throwableConsumer);
	}


	/**
	 * 提现
	 *
	 * @param map
	 * @param consumer
	 * @param throwableConsumer
	 */
	public static void cash(HashMap<String, String> map, Consumer<BaseResultEntity> consumer, Consumer<Throwable> throwableConsumer) {
		QClitent.getInstance()
				.cash(map)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(consumer, throwableConsumer);
	}

	/**
	 * 获取版本信息
	 *
	 * @param map
	 * @param consumer
	 * @param throwableConsumer
	 */
	public static void version(HashMap<String, String> map, Consumer<BaseResultEntity> consumer, Consumer<Throwable> throwableConsumer) {
		QClitent.getInstance()
				.version(map)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(consumer, throwableConsumer);
	}
	/**
	 * 获取首页滚动数据
	 *
	 * @param map
	 * @param consumer
	 * @param throwableConsumer
	 */
	public static void selectAnnouncement(HashMap<String, String> map, Consumer<AnnouncementEntity> consumer, Consumer<Throwable> throwableConsumer) {
		QClitent.getInstance()
				.selectAnnouncement(map)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(consumer, throwableConsumer);
	}
	/**
	 * 获取奖池数据
	 *
	 * @param map
	 * @param consumer
	 * @param throwableConsumer
	 */
	public static void poolInfo(HashMap<String, String> map, Consumer<PoolEntity> consumer, Consumer<Throwable> throwableConsumer) {
		QClitent.getInstance()
				.poolInfo(map)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(consumer, throwableConsumer);
	}
	/**
	 * 抽奖记录
	 *
	 * @param map
	 * @param consumer
	 * @param throwableConsumer
	 */
	public static void lotteryInfo(HashMap<String, String> map, Consumer<LotteryEntity> consumer, Consumer<Throwable> throwableConsumer) {
		QClitent.getInstance()
				.lotteryInfo(map)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(consumer, throwableConsumer);
	}

}
