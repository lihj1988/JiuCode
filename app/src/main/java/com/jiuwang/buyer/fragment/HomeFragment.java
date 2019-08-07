package com.jiuwang.buyer.fragment;

import android.Manifest;
import android.app.Activity;
import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jiuwang.buyer.R;
import com.jiuwang.buyer.activity.CarActivity;
import com.jiuwang.buyer.activity.LoginActivity;
import com.jiuwang.buyer.activity.MainActivity;
import com.jiuwang.buyer.activity.RegisterActivity;
import com.jiuwang.buyer.activity.SearchActivity;
import com.jiuwang.buyer.adapter.GoodsAdapter;
import com.jiuwang.buyer.appinterface.DialogClickInterface;
import com.jiuwang.buyer.bean.GoodsBean;
import com.jiuwang.buyer.camera.zxing.activity.CaptureActivity;
import com.jiuwang.buyer.constant.Constant;
import com.jiuwang.buyer.constant.NetURL;
import com.jiuwang.buyer.entity.BaseResultEntity;
import com.jiuwang.buyer.entity.HomeResultEntity;
import com.jiuwang.buyer.entity.MyCarEntity;
import com.jiuwang.buyer.net.CommonHttpUtils;
import com.jiuwang.buyer.net.HttpUtils;
import com.jiuwang.buyer.util.AppUtils;
import com.jiuwang.buyer.util.CommonUtil;
import com.jiuwang.buyer.util.LoadingDialog;
import com.jiuwang.buyer.util.LogUtils;
import com.jiuwang.buyer.util.MyToastView;
import com.jiuwang.buyer.util.PreforenceUtils;
import com.jiuwang.buyer.view.ADInfo;
import com.jiuwang.buyer.view.ImageCycleView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;

import static android.app.Activity.RESULT_OK;
import static android.support.design.widget.TabLayout.MODE_FIXED;

/**
 * 首页
 */
public class HomeFragment extends Fragment implements XRecyclerView.LoadingListener {


	private static final String TAG = HomeFragment.class.getName();
	@Bind(R.id.shopping_car)
	ImageView imageShopping_car;
	@Bind(R.id.visible_dot)
	TextView visible_dot;
	@Bind(R.id.et_search)
	EditText et_search;
	@Bind(R.id.xRecyclerView)
	XRecyclerView xRecyclerView;
	@Bind(R.id.clear_keyword_iv)
	ImageView clear_keyword_iv;
	@Bind(R.id.ivScan)
	ImageView ivSacn;
	@Bind(R.id.mainTabLayout)
	TabLayout mainTabLayout;
	@Bind(R.id.relativeLayout)
	RelativeLayout relativeLayout;

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
	private HashMap<String, String> map;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = View.inflate(getActivity(), R.layout.home_layout, null);
		ButterKnife.bind(this, view);
		userCode = PreforenceUtils.getStringData("loginInfo", "userName");
		initView();
		map = new HashMap<>();
		map.put("field", "sale_price");
		map.put("order", "asc");
		List<String> mTitleList = new ArrayList<>();
		mTitleList.add("价格升序");
		mTitleList.add("价格降序");
		mTitleList.add("销量");
		for (int i = 0; i < mTitleList.size(); i++) {
			//添加tab
			mainTabLayout.addTab(mainTabLayout.newTab().setText(mTitleList.get(i)));
		}
		mainTabLayout.setTabMode(MODE_FIXED);
		mainTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
			@Override
			public void onTabSelected(TabLayout.Tab tab) {
//				tag = tab.getPosition() + "";
//				Message message = new Message();
//				Bundle bundle = new Bundle();
//				bundle.putInt("position", tab.getPosition());
//				message.setData(bundle);
//				message.what = 0;
//				handler.sendMessage(message);
//				map.put();
				switch (tab.getPosition()) {
					case 0:
						map.put("field", "sale_price");
						map.put("order", "asc");
						break;
					case 1:
						map.put("field", "sale_price");
						map.put("order", "desc");
						break;
					case 2:
						map.put("field", "sale_count");
						map.put("order", "desc");
						break;
				}
//				map.put("sort_type", tab.getPosition() + "");

				intDatas();
			}

			@Override
			public void onTabUnselected(TabLayout.Tab tab) {

			}

			@Override
			public void onTabReselected(TabLayout.Tab tab) {

			}
		});

		intDatas();
		if (Constant.IS_LOGIN) {

			shopcarCount();
		}
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
		et_search.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

			}

			@Override
			public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

			}

			@Override
			public void afterTextChanged(Editable editable) {
				if (!"".equals(editable.toString())) {
					clear_keyword_iv.setVisibility(View.VISIBLE);
					map.put("goods_name", editable.toString());
					intDatas();
				} else {
					map.put("goods_name", "");
					intDatas();
					clear_keyword_iv.setVisibility(View.GONE);
				}

			}
		});
	}

	public void intDatas() {

		map.put("currPage", String.valueOf(page));
		map.put("pageSize", Constant.PAGESIZE);
//		map.put("searchWord", "");
		HttpUtils.goodsInfo(map, new Consumer<HomeResultEntity>() {
			@Override
			public void accept(HomeResultEntity homeResultEntity) throws Exception {

				if (Constant.HTTP_SUCCESS_CODE.equals(homeResultEntity.getCode())) {
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
				} else if (Constant.HTTP_LOGINOUTTIME_CODE.equals(homeResultEntity.getCode())) {
					MyToastView.showToast(homeResultEntity.getMsg(), getActivity());
					Intent intent = new Intent(getActivity(), LoginActivity.class);
					startActivity(intent);
					getActivity().finish();
				} else {
					MyToastView.showToast(homeResultEntity.getMsg(), getActivity());
				}

				if (page == 1) {
					xRecyclerView.refreshComplete();
				} else {
					xRecyclerView.loadMoreComplete();
				}

			}
		}, new Consumer<Throwable>() {
			@Override
			public void accept(Throwable throwable) throws Exception {
				if (page == 1) {
					xRecyclerView.refreshComplete();
				} else {
					xRecyclerView.loadMoreComplete();
				}
				MyToastView.showToast("请求失败", getActivity());
			}
		});

	}

	private void setTopImage() {
		if (urls != null) {
			for (int i = 0; i < urls.size(); i++) {
				ADInfo adInfo = new ADInfo();
				adInfo.setUrl(NetURL.BASEURL + urls.get(i));
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
		mAdapter.setHasStableIds(true);
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
		//扫描结果回调
		if (requestCode == Constant.REQ_QR_CODE && resultCode == RESULT_OK) {
			Bundle bundle = data.getExtras();
			final String scanResult = bundle.getString(Constant.INTENT_EXTRA_KEY_QR_SCAN);
			//将扫描出的信息显示出来
			LogUtils.e(TAG, scanResult);
			String inviteCode = "";
			String type = "";

			try {
				JSONObject object = new JSONObject(scanResult);
				inviteCode = object.getString("text");
				type = object.getString("type");
			} catch (JSONException e) {
				e.printStackTrace();
				MyToastView.showToast("当前二维码无法识别", getActivity());
				return;
			}
			//检验账号是否绑定过邀请码 如果绑定了 提示已经绑定过了  否则去绑定
//			MyToastView.showToast(scanResult, getActivity());

			if (Constant.IS_LOGIN) {
				final LoadingDialog loadingDialog = new LoadingDialog(getActivity());
				loadingDialog.show();
				if ("inviteCode".equals(type)) {
					HashMap<String, String> map = new HashMap<>();
					map.put("act", "invite");
					map.put("from", inviteCode);
					CommonHttpUtils.ref_action(map, new CommonHttpUtils.CallingBack() {
						@Override
						public void successBack(BaseResultEntity baseResultEntity) {
							loadingDialog.dismiss();
							MyToastView.showToast(baseResultEntity.getMsg(), getActivity());
							if (Constant.HTTP_LOGINOUTTIME_CODE.equals(baseResultEntity.getCode())) {
								startActivity(new Intent(getActivity(), LoginActivity.class));
								getActivity().finish();
							}
						}

						@Override
						public void failBack() {
							loadingDialog.dismiss();
							MyToastView.showToast(getActivity().getResources().getString(R.string.msg_error_operation), getActivity());
						}

					});
				}else {

				}
			} else {

				AppUtils.showNormalDialog(getActivity(), "提示", "你当前处于未登录状态，请选择登录或注册", "去注册", "去登陆", new DialogClickInterface() {
					@Override
					public void onClick() {

						//去登陆

						Intent intent = new Intent(getActivity(), LoginActivity.class);
						intent.putExtra("from", scanResult);
						startActivity(intent);
//						getActivity().finish();
					}

					@Override
					public void nagtiveOnClick() {
						//去注册
						Intent intent = new Intent(getActivity(), RegisterActivity.class);
						intent.putExtra("from", scanResult);
						startActivity(intent);
//						getActivity().finish();
					}
				});

			}

		}
	}

	private RefreshReceiver receiver;

	@Override
	public void onRefresh() {
		page = 1;
		refreshTime++;
		times = 0;
		if (Constant.IS_LOGIN) {
			shopcarCount();
		}
		intDatas();
	}

	@Override
	public void onLoadMore() {
		page++;
		times++;
		intDatas();
	}


	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		if (requestCode == Constant.REQ_QR_CODE) {
			if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
				// Permission Granted
				//跳转扫描
				Intent intentScan = new Intent(getActivity(), CaptureActivity.class);
				startActivityForResult(intentScan, Constant.REQ_QR_CODE);
			} else {
				// Permission Denied
				Toast.makeText(getActivity(), "访问被拒绝！", Toast.LENGTH_SHORT).show();
			}
		}
	}

	@OnClick({R.id.relativeLayout, R.id.shopping_car, R.id.clear_keyword_iv, R.id.ivScan})
	public void onViewClicked(View view) {
		switch (view.getId()) {
			case R.id.relativeLayout:
				if (Constant.IS_LOGIN) {
					Intent intent = new Intent(mActivity, SearchActivity.class);
					intent.putExtra("type", "1");
					getActivity().startActivity(intent);
				} else {
					Intent intentExit = new Intent(getActivity(), LoginActivity.class);
					getActivity().startActivity(intentExit);
				}

				break;
			case R.id.clear_keyword_iv:
				et_search.setText("");
				break;
			case R.id.shopping_car:
//				if (!CommonUtil.isNull(userCode)) {
				if (Constant.IS_LOGIN) {
					Intent intentCar = new Intent(mActivity, CarActivity.class);
					intentCar.putExtra("shoppingCount", shoppingCount);
					mActivity.startActivity(intentCar);
				} else {
					Intent intentExit = new Intent(getActivity(), LoginActivity.class);
					getActivity().startActivity(intentExit);
				}

//				} else {
//					MyToastView.showToast("请您先登录", mActivity);
//					Intent intentLogin = new Intent(mActivity, LoginActivity.class);
//					intentLogin.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
//					intentLogin.putExtra("index", "0");
//					startActivity(intentLogin);
//				}
				break;
			case R.id.ivScan:
//				if (Constant.IS_LOGIN) {
				runPermission();
//				}else {
//					Intent intentExit = new Intent(getActivity(), LoginActivity.class);
//					getActivity().startActivity(intentExit);
//				}
				break;
		}
	}


	class RefreshReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {

			String serchName = intent.getStringExtra("searchName");
			intent.putExtra("search_flag", "1");//1.代表进入搜索页面
			if (serchName != null) {
				map.put("goods_name", serchName);
				et_search.setText(serchName);
			}
			intDatas();
			shopcarCount();

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

//		refreshMefragment();
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
	public void shopcarCount() {
		if (CommonUtil.getNetworkRequest(getActivity())) {
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
						MyToastView.showToast(myCarEntity.getMsg(), getActivity());
						Intent intent = new Intent(getActivity(), LoginActivity.class);
						startActivity(intent);
						getActivity().finish();
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

	private void runPermission() {
		if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)
				!= PackageManager.PERMISSION_GRANTED) {
			//申请WRITE_EXTERNAL_STORAGE权限
			//ActivityCompat.requestPermissions(getActivity(),
			//        new String[]{Manifest.permission.CAMERA}, CAMERA_REQUEST_CODE);
			//以下是直接使用Fragment的requestPermissions方法
			requestPermissions(new String[]{Manifest.permission.CAMERA}, Constant.REQ_QR_CODE);
		} else {
			//跳转扫描
			Intent intentScan = new Intent(getActivity(), CaptureActivity.class);
			startActivityForResult(intentScan, Constant.REQ_QR_CODE);
		}
	}
}
