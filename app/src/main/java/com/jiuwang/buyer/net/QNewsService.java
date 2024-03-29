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

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

public interface QNewsService {
	//注册
	@POST("/reg_action.jsp")
	Observable<BaseResultEntity> register(
			@QueryMap HashMap<String, String> map

	);

	//登录
	@POST("/login_action.jsp")
	Observable<BaseEntity<LoginEntity>> getLogining(
			@QueryMap HashMap<String, String> map

	);

	//首页数据
	@POST("admin/goods/goods_action.jsp")
	Observable<HomeResultEntity> goodsInfo(
			@QueryMap HashMap<String, String> map
	);

	//商品详情
	@POST("admin/goods/goods_action.jsp")
	Observable<GoodsDetailsEntity> selectGoodsDetails(
			@QueryMap HashMap<String, String> map
	);

	//抢购数据
	@POST("admin/project/aution_action.jsp")
	Observable<ProjectEntity> selectProjectList(
			@QueryMap HashMap<String, String> map
	);

	//抢购-获取可选择的商品
	@POST("admin/project/aution_user_action.jsp")
	Observable<SelectGoodsEntity> selectChooseGoods(
			@QueryMap HashMap<String, String> map
	);

	//抢购-报名
	@POST("admin/project/aution_user_action.jsp")
	Observable<BaseResultEntity> enroll(
			@QueryMap HashMap<String, String> map
	);

	//抢购-是否中奖
	@POST("/admin/project/aution_action.jsp")
	Observable<ProjectDetailsEntity> isWin(
			@QueryMap HashMap<String, String> map
	);

	//添加购物车
	@POST("car_action.jsp")
	Observable<BaseResultEntity> addCar(
			@QueryMap HashMap<String, String> map
	);

	//查询购物车
	@POST("car_action.jsp")
	Observable<MyCarEntity> selectCar(
			@QueryMap HashMap<String, String> map
	);

	//购物车相关操作
	@POST("car_action.jsp")
	Observable<BaseResultEntity> cartInfo(
			@QueryMap HashMap<String, String> map
	);

	//购物车结算
	@POST("admin/order/order_action.jsp")
	Observable<OrderEntity> settlement(
			@QueryMap HashMap<String, String> map
	);

	//收货地址-查询
	@POST("admin/linkman/linkman_action.jsp")
	Observable<AddressEntity> selectAddressList(
			@QueryMap HashMap<String, String> map
	);

	//收货地址-添加
	@POST("admin/linkman/linkman_action.jsp")
	Observable<BaseResultEntity> addressInfo(
			@QueryMap HashMap<String, String> map
	);

	//订单查询
	@POST("admin/order/order_action.jsp")
	Observable<OrderEntity> selectOrder(
			@QueryMap HashMap<String, String> map
	);

	//订单相关操作
	@POST("admin/order/order_action.jsp")
	Observable<OrderEntity> orderInfo(
			@QueryMap HashMap<String, String> map
	);


	//获得购物车数量和可用余额
	@POST("/public/select_shopcar_count.jsp")
	@FormUrlEncoded
	Observable<MoneyNumberBean> getNumberdata(
			@Field("act") String act//写死getShopCarCount
	);


	//修改密码
	@POST("/sys/group/user_pwd_action.jsp")
	@FormUrlEncoded
	Observable<SuccessBean> getchagepass(
			@Field("act") String act,//modifySelfPwd
			@Field("new_password") String new_password,//新密码
			@Field("password") String password//旧密码
	);

	//注册-获取验证码
	@POST("reg_action.jsp")
	Observable<BaseResultEntity> regVerify(
			@QueryMap HashMap<String, String> map
	);//忘记密码-获取验证码

	@POST("forget_password_action.jsp")
	Observable<BaseResultEntity> getVerify(
			@QueryMap HashMap<String, String> map
	);

	//忘记密码-修改密码
	@POST("set_new_password_action.jsp")
	Observable<BaseResultEntity> onForget(
			@QueryMap HashMap<String, String> map
	);

	//用户信息查询
	@POST("/reg_action.jsp")
	Observable<UserEntity> selectUserInfo(
			@QueryMap HashMap<String, String> map
	);

	//用户信息操作
	@POST("/reg_action.jsp")
	Observable<BaseResultEntity> userInfo(
			@QueryMap HashMap<String, String> map
	);

	//充值
	@POST("admin/fund/receive_action.jsp")
	Observable<BaseResultEntity> recharge(
			@QueryMap HashMap<String, String> map
	);

	//资金查询
	@POST("admin/fund/fund_action.jsp")
	Observable<BalanceEntity> fund(
			@QueryMap HashMap<String, String> map
	);

	//余额付款
	@POST("admin/fund/fund_action.jsp")
	Observable<BaseResultEntity> fundPay(
			@QueryMap HashMap<String, String> map
	);

	//被邀请人查询
	@POST("admin/invite/invite_action.jsp")
	Observable<InviteEntity> invite(
			@QueryMap HashMap<String, String> map
	);//被邀请人查询

	@POST("admin/invite/invite_action.jsp")
	Observable<InviteUserEntity> inviteUser(
			@QueryMap HashMap<String, String> map
	);


	//提现
	@POST("admin/fund/cash_action.jsp")
	Observable<BaseResultEntity> cash(
			@QueryMap HashMap<String, String> map
	);

	//获取版本
	@POST("sys/sys_action.jsp")
	Observable<BaseResultEntity> version(
			@QueryMap HashMap<String, String> map
	);

	//获取首页滚动数据
	@POST("admin/announce/announce_action.jsp")
	Observable<AnnouncementEntity> selectAnnouncement(
			@QueryMap HashMap<String, String> map
	);
	//奖池
	@POST("admin/pool/pool_action.jsp")
	Observable<PoolEntity> poolInfo(
			@QueryMap HashMap<String, String> map
	);
	//奖池 相关
	@POST("admin/pool/lottery_action.jsp")
	Observable<LotteryEntity> lotteryInfo(
			@QueryMap HashMap<String, String> map
	);
	//抽奖-开红包
	@POST("admin/pool/lottery_action.jsp")
	Observable<BaseResultEntity> lottery(
			@QueryMap HashMap<String, String> map
	);

}
