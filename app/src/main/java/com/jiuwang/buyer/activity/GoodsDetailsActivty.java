package com.jiuwang.buyer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gxz.PagerSlidingTabStrip;
import com.jiuwang.buyer.R;
import com.jiuwang.buyer.base.BaseActivity;
import com.jiuwang.buyer.bean.GoodsBean;
import com.jiuwang.buyer.bean.GoodsDetailsBean;
import com.jiuwang.buyer.constant.Constant;
import com.jiuwang.buyer.entity.BaseResultEntity;
import com.jiuwang.buyer.entity.GoodsDetailsEntity;
import com.jiuwang.buyer.goods.adaper.ItemTitlePagerAdapter;
import com.jiuwang.buyer.goods.fragment.GoodsCommentFragment;
import com.jiuwang.buyer.goods.fragment.GoodsDetailFragment;
import com.jiuwang.buyer.goods.fragment.GoodsInfoFragment;
import com.jiuwang.buyer.net.HttpUtils;
import com.jiuwang.buyer.util.DialogUtil;
import com.jiuwang.buyer.util.MyToastView;
import com.jiuwang.buyer.widget.NoScrollViewPager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;

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
	private List<Fragment> fragmentList = new ArrayList<>();
	private GoodsInfoFragment goodsInfoFragment;
	private GoodsDetailFragment goodsDetailFragment;
	private GoodsCommentFragment goodsCommentFragment;
	private String goods_id;
	private GoodsDetailsBean goodsDetailsBean;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_goods_details);
		ButterKnife.bind(this);
		setTopView(topView);
		Intent intent = getIntent();
		GoodsBean goods = (GoodsBean) intent.getSerializableExtra("goods");
		goods_id = goods.getId();
		psts_tabs = (PagerSlidingTabStrip) findViewById(R.id.psts_tabs);
		vp_content = (NoScrollViewPager) findViewById(R.id.vp_content);
		tv_title = (TextView) findViewById(R.id.tv_title);
		selectDetail();


	}

	private void initFragment(Bundle bundle) {
		goodsInfoFragment = new GoodsInfoFragment();
		goodsInfoFragment.setArguments(bundle);
		fragmentList.add(goodsInfoFragment);
		goodsDetailFragment = new GoodsDetailFragment();
		goodsDetailFragment.setArguments(bundle);
		fragmentList.add(goodsDetailFragment );
		goodsCommentFragment = new GoodsCommentFragment();
		goodsCommentFragment.setArguments(bundle);
		fragmentList.add(goodsCommentFragment);
		vp_content.setAdapter(new ItemTitlePagerAdapter(getSupportFragmentManager(),
				fragmentList, new String[]{"商品", "详情", "评价"}));
		vp_content.setOffscreenPageLimit(3);
		psts_tabs.setViewPager(vp_content);
	}

	public void selectDetail(){
		DialogUtil.progress(GoodsDetailsActivty.this);
		HashMap<String, String> hashMap = new HashMap<>();
		hashMap.put("goods_id", goods_id);
		hashMap.put("act", Constant.GOODS_ACTION_DETAILS_ACT);
		HttpUtils.selectGoodsDetails(hashMap, new Consumer<GoodsDetailsEntity>() {
			@Override
			public void accept(GoodsDetailsEntity goodsDetailsEntity) throws Exception {
				goodsDetailsBean = goodsDetailsEntity.getData().get(0);
				Bundle bundle = new Bundle();bundle.putSerializable("goods",goodsDetailsBean);
				initFragment(bundle);
				DialogUtil.cancel();
			}
		}, new Consumer<Throwable>() {
			@Override
			public void accept(Throwable throwable) throws Exception {
				DialogUtil.cancel();
				MyToastView.showToast("获取失败",GoodsDetailsActivty.this);
			}
		});


	}


	@OnClick({R.id.ll_back, R.id.tvKefu, R.id.tvShop, R.id.llCollect, R.id.tvAddCar, R.id.tvBuyNow})
	public void onViewClicked(View view) {
		switch (view.getId()) {
			case R.id.ll_back:
				finish();
				break;
			case R.id.tvKefu:
				MyToastView.showToast("开发中", GoodsDetailsActivty.this);
				break;
			case R.id.tvShop:
				MyToastView.showToast("开发中", GoodsDetailsActivty.this);
				break;
			case R.id.llCollect:
				MyToastView.showToast("开发中", GoodsDetailsActivty.this);
				break;
			case R.id.tvAddCar:
				addCar();
				break;
			case R.id.tvBuyNow:
				break;
		}
	}

	public void addCar(){

		HashMap<String, String> map = new HashMap<>();
		map.put("act", Constant.ACTION_ACT_ADD);
		map.put("goods_id",goods_id);
		map.put("quantity",1+"");
		DialogUtil.progress(GoodsDetailsActivty.this);
		HttpUtils.addCar(map, new Consumer<BaseResultEntity>() {
			@Override
			public void accept(BaseResultEntity baseResultEntity) throws Exception {
				DialogUtil.cancel();
				if(Constant.HTTP_SUCCESS_CODE.equals(baseResultEntity.getCode())){
					Intent intent = new Intent();
					intent.setAction("refreshCar");
					sendBroadcast(intent);

				}else {

				}
				MyToastView.showToast(baseResultEntity.getMsg(),GoodsDetailsActivty.this);
			}
		}, new Consumer<Throwable>() {
			@Override
			public void accept(Throwable throwable) throws Exception {
				DialogUtil.cancel();
				MyToastView.showToast("请求失败",GoodsDetailsActivty.this);
			}
		});

	}
}
