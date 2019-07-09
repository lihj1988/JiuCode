package com.jiuwang.buyer.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jiuwang.buyer.R;
import com.jiuwang.buyer.activity.MainActivity;
import com.jiuwang.buyer.activity.SearchActivity;
import com.jiuwang.buyer.adapter.GoodsAdapter;
import com.jiuwang.buyer.bean.GoodsBean;
import com.jiuwang.buyer.constant.Constant;
import com.jiuwang.buyer.entity.HomeResultEntity;
import com.jiuwang.buyer.net.HttpUtils;
import com.jiuwang.buyer.util.CommonUtil;
import com.jiuwang.buyer.util.MyToastView;
import com.jiuwang.buyer.constant.NetURL;
import com.jiuwang.buyer.util.PreforenceUtils;
import com.jiuwang.buyer.view.ADInfo;
import com.jiuwang.buyer.view.ImageCycleView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;

/**
 * 首页
 */
public class HomeFragment extends Fragment implements XRecyclerView.LoadingListener {


	@Bind(R.id.shopping_car)
	ImageView imageShopping_car;
	@Bind(R.id.visible_dot)
	TextView visible_dot;
	@Bind(R.id.et_search)
	EditText et_search;
	@Bind(R.id.xRecyclerView)
	XRecyclerView xRecyclerView;

	private View view;
	private String searchName = "";// 搜索
	private String token;
	private String userCode;
	private String shoppingCount;
	private String search_Flag = "0";// 1.代表进入搜索页面
	int[] imageUrls_local = {R.drawable.play, R.drawable.play, R.drawable.play};
	private ArrayList<ADInfo> infos = new ArrayList();
	private List<String> urls = new ArrayList();
	private MainActivity mActivity;
	private int refreshTime = 0;
	private int times = 0;
	private GoodsAdapter mAdapter;
	private int page = 1;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = View.inflate(getActivity(), R.layout.home_layout, null);
		ButterKnife.bind(this, view);
		userCode = PreforenceUtils.getStringData("loginInfo", "userName");
		initView();
		intDatas();
//		refreshMefragment();
		if (receiver == null) {
			receiver = new RefreshReceiver();
			IntentFilter filter = new IntentFilter();
			filter.addAction("refresh_home");
			getActivity().registerReceiver(receiver, filter);
		}
		return view;
	}

	private List<GoodsBean> listData;

	private void initView() {
		StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2,
				StaggeredGridLayoutManager.VERTICAL);
		layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
		xRecyclerView.setLayoutManager(layoutManager);
		xRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
		xRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
		xRecyclerView.setArrowImageView(R.drawable.iconfont_downgrey);
		xRecyclerView.setLoadingMoreEnabled(true);
		xRecyclerView.setPullRefreshEnabled(true);
		xRecyclerView.setLoadingListener(this);
		listData = new ArrayList<GoodsBean>();


	}

	private void intDatas() {
		HashMap<String, String> map = new HashMap<>();
		map.put("currPage", String.valueOf(page));
		map.put("pageSize", Constant.PAGESIZE);
		map.put("searchWord", "");
		map.put("status", "1");
		HttpUtils.goodsInfo(map, new Consumer<HomeResultEntity>() {
			@Override
			public void accept(HomeResultEntity homeResultEntity) throws Exception {

				if(Constant.HTTP_SUCCESS_CODE.equals(homeResultEntity.getCode())){
					if (page == 1) {
						listData.clear();
						urls = homeResultEntity.getPic_list();
						setTopImage();
					}

					listData.addAll(homeResultEntity.getData());
//					listData.addAll(homeResultEntity.getData());

					if (mAdapter != null) {
						mAdapter.notifyDataSetChanged();

					} else {
						setAdapter();
					}
				}else {
					MyToastView.showToast(homeResultEntity.getMsg(),getActivity());
				}

				if(page==1){
					xRecyclerView.refreshComplete();
				}else {
					xRecyclerView.loadMoreComplete();
				}

			}
		}, new Consumer<Throwable>() {
			@Override
			public void accept(Throwable throwable) throws Exception {
				MyToastView.showToast("请求失败",getActivity());
			}
		});

	}

	private void setTopImage(){
		if(urls!=null){
			for (int i = 0; i < urls.size(); i++) {
				ADInfo adInfo = new ADInfo();
				adInfo.setUrl(NetURL.BASEURL+urls.get(i));
				infos.add(adInfo);
			}
			View header = LayoutInflater.from(getActivity()).inflate(R.layout.recyclerview_home_header, (ViewGroup) getActivity().findViewById(android.R.id.content), false);
			ImageCycleView imageCycleView = header.findViewById(R.id.icView);
			imageCycleView.setImageResources(infos, mAdCycleViewListener);
			xRecyclerView.addHeaderView(header);
		}



	}

	private void setAdapter() {
		mAdapter = new GoodsAdapter(getActivity(), listData);
		xRecyclerView.setAdapter(mAdapter);
	}

	;
	private ImageCycleView.ImageCycleViewListener mAdCycleViewListener = new ImageCycleView.ImageCycleViewListener() {
		@Override
		public void onImageClick(ADInfo info, int position, View imageView) {
			// 去掉图片点击
			// Toast.makeText(HomeFragment.this.getActivity(),
			// "content->"+info.getContent(), Toast.LENGTH_SHORT).show();
		}

		@Override
		public void displayImage(String imageURL, ImageView imageView) {

		}

		@Override
		public void displayImage_local(int imageURL, ImageView imageView) {
			//ImageLoader.getInstance().displayImage("drawable://" + imageURL, imageView);
			imageView.setImageDrawable(getResources().getDrawable(imageURL));
		}
	};

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == 2) {
			searchName = data.getStringExtra("serchName");
			search_Flag = data.getStringExtra("search_Flag");// 1.代表进入搜索页面
		}
	}

	private RefreshReceiver receiver;

	@Override
	public void onRefresh() {
		page = 1;
		refreshTime++;
		times = 0;
		intDatas();
	}

	@Override
	public void onLoadMore() {
		page++;
		times++;
		intDatas();
	}

	@OnClick({R.id.et_search, R.id.shopping_car})
	public void onViewClicked(View view) {
		switch (view.getId()) {
			case R.id.et_search:
				Intent intent = new Intent(mActivity, SearchActivity.class);
				intent.putExtra("type", "1");
				getActivity().startActivity(intent);
				break;
			case R.id.shopping_car:
//				if (!CommonUtil.isNull(userCode)) {
//					Intent intentCar = new Intent(mActivity, CarActivity.class);
//					intentCar.putExtra("shoppingCount", shoppingCount);
//					mActivity.startActivity(intentCar);
//				} else {
//					MyToastView.showToast("请您先登录", mActivity);
//					Intent intentLogin = new Intent(mActivity, LoginActivity.class);
//					intentLogin.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
//					intentLogin.putExtra("index", "0");
//					startActivity(intentLogin);
//				}
				break;
		}
	}


	class RefreshReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			String count = intent.getStringExtra("shoppingCount");
			visible_dot.setText(count);
		}
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.mActivity = (MainActivity) activity;
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		refreshMefragment();
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		if (receiver != null) {
			getActivity().unregisterReceiver(receiver);
			receiver = null;
		}
		ButterKnife.unbind(this);
	}

	/**
	 * 查询购物车数量
	 */
	public void refreshMefragment() {
		if (CommonUtil.getNetworkRequest(getActivity())) {
//			HttpUtils.getNumberdata("getShopCarCount", new Consumer<MoneyNumberBean>() {
//				@Override
//				public void accept(MoneyNumberBean entity) throws Exception {
//					if ("0".equals(entity.getCode())) {//0成功  1失败 2登录超时 重新登录
//						visible_dot.setText(entity.getCount());//购物车数量
//						shoppingCount = entity.getCount();
//					} else if ("2".equals(entity.getCode())) {
//						Intent intent = new Intent(getActivity(), LoginActivity.class);
//						startActivity(intent);
//					} else {
//						MyToastView.showToast(entity.getMsg(), getActivity());
//					}
//				}
//			}, new Consumer<Throwable>() {
//				@Override
//				public void accept(Throwable throwable) throws Exception {
//				}
//			});
		}
	}


}
