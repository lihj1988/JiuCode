package com.jiuwang.buyer.activity;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gxz.PagerSlidingTabStrip;
import com.jiuwang.buyer.R;
import com.jiuwang.buyer.appinterface.DialogClickInterface;
import com.jiuwang.buyer.base.BaseActivity;
import com.jiuwang.buyer.bean.GoodsBean;
import com.jiuwang.buyer.bean.GoodsDetailsBean;
import com.jiuwang.buyer.constant.Constant;
import com.jiuwang.buyer.entity.BaseResultEntity;
import com.jiuwang.buyer.entity.GoodsDetailsEntity;
import com.jiuwang.buyer.entity.MyCarEntity;
import com.jiuwang.buyer.goods.adaper.ItemTitlePagerAdapter;
import com.jiuwang.buyer.goods.fragment.GoodsCommentFragment;
import com.jiuwang.buyer.goods.fragment.GoodsDetailFragment;
import com.jiuwang.buyer.goods.fragment.GoodsInfoFragment;
import com.jiuwang.buyer.net.HttpUtils;
import com.jiuwang.buyer.util.AppUtils;
import com.jiuwang.buyer.util.CommonUtil;
import com.jiuwang.buyer.util.DialogUtil;
import com.jiuwang.buyer.util.LoadingDialog;
import com.jiuwang.buyer.util.MyToastView;
import com.jiuwang.buyer.widget.NoScrollViewPager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;

import static com.jiuwang.buyer.constant.Constant.REQUEST_CALL_PERMISSION;

/**
 * author：lihj
 * desc：商品详情
 * Created by lihj on  2019/6/20 10:27.
 */

public class GoodsDetailsActivty extends BaseActivity {
	private static final String TAG = "GoodsDetailsActivty";
	public PagerSlidingTabStrip psts_tabs;
	public NoScrollViewPager vp_content;
	public TextView tv_title;

	@Bind(R.id.topView)
	LinearLayout topView;
	@Bind(R.id.ll_back)
	LinearLayout ll_back;
	@Bind(R.id.tvKefu)
	TextView tvKefu;
	@Bind(R.id.tvShop)
	TextView tvShop;
	@Bind(R.id.llCollect)
	LinearLayout llCollect;
	@Bind(R.id.tvAddCar)
	TextView tvAddCar;
	@Bind(R.id.tvBuyNow)
	TextView tvBuyNow;
	@Bind(R.id.visible_dot)
	TextView visible_dot;
	@Bind(R.id.shopcar)
	ImageView shopcar;
	private List<Fragment> fragmentList = new ArrayList<>();
	private GoodsInfoFragment goodsInfoFragment;
	private GoodsDetailFragment goodsDetailFragment;
	private GoodsCommentFragment goodsCommentFragment;
	private String goods_id;
	private GoodsDetailsBean goodsDetailsBean;
	private GoodsBean goods;
	private LoadingDialog loadingDialog;
	private MyReceiver myReceiver;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_goods_details);
		ButterKnife.bind(this);
		EventBus.getDefault().register(this);
		setTopView(topView);
		Intent intent = getIntent();
		goods = (GoodsBean) intent.getSerializableExtra("goods");
		goods_id = goods.getId();
		psts_tabs = (PagerSlidingTabStrip) findViewById(R.id.psts_tabs);
		vp_content = (NoScrollViewPager) findViewById(R.id.vp_content);
		tv_title = (TextView) findViewById(R.id.tv_title);
		shopcarCount();
//		selectDetail();
		Bundle bundle = new Bundle();
		bundle.putSerializable("goods", goodsDetailsBean);
		initFragment(bundle);
		myReceiver = new MyReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction("finish");
		registerReceiver(myReceiver, filter);

	}

	private void initFragment(Bundle bundle) {
		bundle.putSerializable("good", goods);
		goodsInfoFragment = new GoodsInfoFragment();
		goodsInfoFragment.setArguments(bundle);
		fragmentList.add(goodsInfoFragment);
		goodsDetailFragment = new GoodsDetailFragment();
		goodsDetailFragment.setArguments(bundle);
		fragmentList.add(goodsDetailFragment);
		goodsCommentFragment = new GoodsCommentFragment();
		goodsCommentFragment.setArguments(bundle);
		fragmentList.add(goodsCommentFragment);
		vp_content.setAdapter(new ItemTitlePagerAdapter(getSupportFragmentManager(),fragmentList, new String[]{"商品", "详情", "评价"}));
		vp_content.setOffscreenPageLimit(3);
		psts_tabs.setViewPager(vp_content);
	}

	public void selectDetail() {
		DialogUtil.progress(GoodsDetailsActivty.this);
		HashMap<String, String> hashMap = new HashMap<>();
		hashMap.put("goods_id", goods_id);
		hashMap.put("act", Constant.GOODS_ACTION_DETAILS_ACT);
		HttpUtils.selectGoodsDetails(hashMap, new Consumer<GoodsDetailsEntity>() {
			@Override
			public void accept(GoodsDetailsEntity goodsDetailsEntity) throws Exception {
				goodsDetailsBean = goodsDetailsEntity.getData().get(0);
				Bundle bundle = new Bundle();
				bundle.putSerializable("goods", goodsDetailsBean);
				initFragment(bundle);
				DialogUtil.cancel();
			}
		}, new Consumer<Throwable>() {
			@Override
			public void accept(Throwable throwable) throws Exception {
				DialogUtil.cancel();
				MyToastView.showToast("获取失败", GoodsDetailsActivty.this);
			}
		});


	}


	@OnClick({R.id.ll_back, R.id.tvKefu, R.id.tvShop, R.id.llCollect, R.id.tvAddCar, R.id.tvBuyNow, R.id.shopcar})
	public void onViewClicked(View view) {
		switch (view.getId()) {
			case R.id.ll_back:
				finish();
				break;
			case R.id.tvKefu:
				//打电话
				AppUtils.showDialog(GoodsDetailsActivty.this, "拨打电话", "客服电话：" + getResources().getString(R.string.phone), "1", new DialogClickInterface() {
					@Override
					public void nagtiveOnClick() {

					}

					@Override
					public void onClick() {
						call(getResources().getString(R.string.phone));
					}
				});

				break;
			case R.id.tvShop:
				MyToastView.showToast("开发中", GoodsDetailsActivty.this);
				break;
			case R.id.llCollect:
				MyToastView.showToast("开发中", GoodsDetailsActivty.this);
				break;
			case R.id.shopcar:
				startActivity(new Intent(GoodsDetailsActivty.this,CarActivity.class));
				break;
			case R.id.tvAddCar:
				addCar();
				break;
			case R.id.tvBuyNow:
				//立即购买
				MyToastView.showToast("开发中", GoodsDetailsActivty.this);
//				List<CarGoodsBean> selectedList = new ArrayList<CarGoodsBean>();
//				CarGoodsBean carGoodsBean = new CarGoodsBean();
//				carGoodsBean.setQuantity(goods.getId());
//				carGoodsBean.setPic_url(goods.getPic_url());
//				carGoodsBean.setSale_price("".equals(goods.getSale_price()) ? goods.getPrice() : goods.getSale_price());
//				carGoodsBean.setGoods_name(goods.getGoods_name());
//				selectedList.add(carGoodsBean);
//				Intent intent = new Intent(GoodsDetailsActivty.this, BuySetup1Activity.class);
//				intent.putExtra("data", (Serializable) selectedList);
//				startActivity(intent);
				break;
		}
	}

	public void addCar() {

		HashMap<String, String> map = new HashMap<>();
		map.put("act", Constant.ACTION_ACT_ADD);
		map.put("goods_id", goods_id);
		map.put("quantity", 1 + "");
		loadingDialog = new LoadingDialog(GoodsDetailsActivty.this);
		loadingDialog.show();
//		DialogUtil.progress(GoodsDetailsActivty.this);
		HttpUtils.addCar(map, new Consumer<BaseResultEntity>() {
			@Override
			public void accept(BaseResultEntity baseResultEntity) throws Exception {
				loadingDialog.dismiss();
				if (Constant.HTTP_SUCCESS_CODE.equals(baseResultEntity.getCode())) {
					visible_dot.setText(baseResultEntity.getCount());
					Intent intent = new Intent();
					intent.setAction("refreshCar");
					sendBroadcast(intent);
					intent.setAction("refresh_home");
					sendBroadcast(intent);
					shopcarCount();
				} else if (Constant.HTTP_LOGINOUTTIME_CODE.equals(baseResultEntity.getCode())) {
					MyToastView.showToast(baseResultEntity.getMsg(), GoodsDetailsActivty.this);
					startActivity(new Intent(GoodsDetailsActivty.this, LoginActivity.class));
					finish();

				} else {
					MyToastView.showToast(baseResultEntity.getMsg(), GoodsDetailsActivty.this);
				}
				MyToastView.showToast(baseResultEntity.getMsg(), GoodsDetailsActivty.this);

			}
		}, new Consumer<Throwable>() {
			@Override
			public void accept(Throwable throwable) throws Exception {
				loadingDialog.dismiss();
				MyToastView.showToast("请求失败", GoodsDetailsActivty.this);
			}
		});

	}

	/**
	 * 查询购物车数量
	 */
	public void shopcarCount() {
		if (CommonUtil.getNetworkRequest(GoodsDetailsActivty.this)) {
			HashMap<String, String> map = new HashMap<>();
			map.put("act", "");
			HttpUtils.selectCar(map, new Consumer<MyCarEntity>() {
				@Override
				public void accept(MyCarEntity myCarEntity) throws Exception {
					int count = 0;
					if (Constant.HTTP_SUCCESS_CODE.equals(myCarEntity.getCode())) {
						if (myCarEntity.getData() != null && myCarEntity.getData().size() > 0) {
							for (int i = 0; i < myCarEntity.getData().size(); i++) {
//								count += Integer.parseInt(myCarEntity.getData().get(i).getCount());
								for (int j = 0; j < myCarEntity.getData().get(i).getGoods_detail().size(); j++) {
									count+=Integer.parseInt(myCarEntity.getData().get(i).getGoods_detail().get(j).getQuantity());
								}
							}
						}
						visible_dot.setText(count + "");

					} else if (Constant.HTTP_LOGINOUTTIME_CODE.equals(myCarEntity.getCode())) {
						MyToastView.showToast(myCarEntity.getMsg(), GoodsDetailsActivty.this);
						Intent intent = new Intent(GoodsDetailsActivty.this, LoginActivity.class);
						startActivity(intent);
						finish();
					}
				}
			}, new Consumer<Throwable>() {
				@Override
				public void accept(Throwable throwable) throws Exception {
					visible_dot.setText("0");
				}
			});
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(myReceiver);
		EventBus.getDefault().unregister(this);
	}
	/**
	 * 检查权限后的回调
	 * @param requestCode 请求码
	 * @param permissions  权限
	 * @param grantResults 结果
	 */
	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		switch (requestCode) {
			case REQUEST_CALL_PERMISSION: //拨打电话
				if (permissions.length != 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED) {//失败
					Toast.makeText(this,"请允许拨号权限后再试",Toast.LENGTH_SHORT).show();
				} else {//成功
					call(getResources().getString(R.string.phone));
				}
				break;
		}
	}

	public void call(String telPhone){
		if(checkReadPermission(Manifest.permission.CALL_PHONE,REQUEST_CALL_PERMISSION)){
			Intent dialIntent =  new Intent(Intent.ACTION_DIAL,Uri.parse("tel:" + telPhone));//跳转到拨号界面，同时传递电话号码
			startActivity(dialIntent);
		}
	}

	/**
	 * 判断是否有某项权限
	 * @param string_permission 权限
	 * @param request_code 请求码
	 * @return
	 */
	public boolean checkReadPermission(String string_permission,int request_code) {
		boolean flag = false;
		if (ContextCompat.checkSelfPermission(this, string_permission) == PackageManager.PERMISSION_GRANTED) {//已有权限
			flag = true;
		} else {//申请权限
			ActivityCompat.requestPermissions(this, new String[]{string_permission}, request_code);
		}
		return flag;
	}
	class MyReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			finish();
		}
	}
	@Subscribe(threadMode = ThreadMode.MAIN)
	public void handleData(String mMessageEvent) {
		if(mMessageEvent.equals("resfreshCarCount")){
			shopcarCount();
		}
	}
}
