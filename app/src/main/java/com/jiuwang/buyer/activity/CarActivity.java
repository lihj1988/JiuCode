package com.jiuwang.buyer.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jiuwang.buyer.R;
import com.jiuwang.buyer.adapter.MyCarAdapter;
import com.jiuwang.buyer.base.BaseActivity;
import com.jiuwang.buyer.bean.CarBean;
import com.jiuwang.buyer.bean.CarGoodsBean;
import com.jiuwang.buyer.constant.Constant;
import com.jiuwang.buyer.entity.BaseEntity;
import com.jiuwang.buyer.entity.LoginEntity;
import com.jiuwang.buyer.entity.MyCarEntity;
import com.jiuwang.buyer.net.HttpUtils;
import com.jiuwang.buyer.util.CommonUtil;
import com.jiuwang.buyer.util.MyToastView;
import com.jiuwang.buyer.util.PreforenceUtils;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.reactivex.functions.Consumer;

import static java.lang.Integer.parseInt;

/**
 * author：lihj
 * desc：
 * Created by lihj on  2019/6/20 14:43.
 */

public class CarActivity extends BaseActivity {
	@Bind(R.id.topView)
	LinearLayout topView;
	@Bind(R.id.actionbar_text)
	TextView actionbarText;
	@Bind(R.id.return_img)
	ImageView returnImg;
	@Bind(R.id.onclick_layout_left)
	RelativeLayout onclickLayoutLeft;
	@Bind(R.id.onclick_layout_right)
	Button onclickLayoutRight;
	@Bind(R.id.iv_find)
	ImageView ivFind;
	@Bind(R.id.carListView)
	ListView carListView;
	@Bind(R.id.stateTextView)
	TextView stateTextView;
	@Bind(R.id.tipsTextView)
	TextView tipsTextView;
	@Bind(R.id.check_box)
	CheckBox checkBox;
	@Bind(R.id.number)
	TextView number;
	@Bind(R.id.cheapPrice)
	TextView cheapPrice;
	@Bind(R.id.tv_cart_select_num)
	TextView tvCartSelectNum;
	@Bind(R.id.tv_cart_Allprice)
	TextView tvCartAllprice;
	@Bind(R.id.tv_cart_total)
	TextView tvCartTotal;
	@Bind(R.id.cart_rl_allprie_total)
	LinearLayout cartRlAllprieTotal;
	@Bind(R.id.tv_cart_buy_or_del)
	TextView tvCartBuyOrDel;
	@Bind(R.id.bottom_bar)
	LinearLayout bottomBar;
	private MyCarAdapter cartListAdapter;
	private List<CarBean> carBeanList;
	private MyBroadCastReceiver myBroadCastReceiver;
	private MyReceiver myReceiver;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shopcar);
		ButterKnife.bind(this);
		initView();
		initData();
		myBroadCastReceiver = new MyBroadCastReceiver();
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction("refreshCar");
		registerReceiver(myBroadCastReceiver, intentFilter);
		myReceiver = new MyReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction("finish");
		registerReceiver(myReceiver,filter);
	}

	private void initData() {
//		carListView.refresh();
		carBeanList = new ArrayList<>();
		HashMap<String, String> map = new HashMap<>();
		map.put("act", "");
		HttpUtils.selectCar(map, new Consumer<MyCarEntity>() {
			@Override
			public void accept(MyCarEntity myCarEntity) throws Exception {

				if (Constant.HTTP_SUCCESS_CODE.equals(myCarEntity.getCode())) {
					if (myCarEntity.getData() != null && myCarEntity.getData().size() > 0) {
						tipsTextView.setVisibility(View.GONE);
						tipsTextView.setOnClickListener(null);

						stateTextView.setVisibility(View.GONE);
					} else {
						number.setText("0");
						tvCartTotal.setText("￥0.00");
						tipsTextView.setVisibility(View.VISIBLE);
						tipsTextView.setText("暂无数据");
						tipsTextView.setOnClickListener(new View.OnClickListener() {
							@Override
							public void onClick(View view) {
								initData();
							}
						});
					}
					carBeanList.addAll(myCarEntity.getData());
//					if (cartListAdapter != null) {
//						cartListAdapter.notifyDataSetChanged();
//					} else {
					setAdapter();
//					}
				} else if (Constant.HTTP_LOGINOUTTIME_CODE.equals(myCarEntity.getCode())) {
					CommonUtil.reLogin(PreforenceUtils.getStringData("loginInfo", "userID"), PreforenceUtils.getStringData("loginInfo", "password"), new CommonUtil.LoginCallBack() {
						@Override
						public void callBack(BaseEntity<LoginEntity> loginEntity) {
							initData();
						}

						@Override
						public void failCallBack(Throwable throwable) {

						}
					});
				}
			}
		}, new Consumer<Throwable>() {
			@Override
			public void accept(Throwable throwable) throws Exception {
//				carListView.refreshComplete();
//				MyToastView.showToast("获取失败", getActivity());
				tipsTextView.setVisibility(View.VISIBLE);
				tipsTextView.setText("暂无数据");
				number.setText("0");
				tvCartTotal.setText("￥0.00");
				carBeanList.clear();

				setAdapter();

			}
		});
	}

	public void setAdapter() {
		cartListAdapter = new MyCarAdapter(CarActivity.this, carBeanList, new MyCarAdapter.ItemCheckStatusChangeListener() {
			@Override
			public void checkStatusChange() {
				setTotalData();
			}
		});
		carListView.setAdapter(cartListAdapter);
	}

	int totalNum;
	double totalAmount;

	/**
	 * 初始化控件
	 */
	private void initView() {
		setTopView(topView);
		actionbarText.setText("购物车");
		onclickLayoutLeft.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				finish();
			}
		});
		onclickLayoutRight.setVisibility(View.INVISIBLE);
		checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
				if (carBeanList != null) {
					for (int i = 0; i < carBeanList.size(); i++) {
						carBeanList.get(i).setIscheck(b);
						for (int j = 0; j < carBeanList.get(i).getGoods_detail().size(); j++) {
							carBeanList.get(i).getGoods_detail().get(j).setIscheck(b);
						}

					}
					cartListAdapter.notifyDataSetChanged();
					if (b) {
						setTotalData();
					} else {
						number.setText("0");
						tvCartTotal.setText("￥0.00");
					}
				}

			}

		});
		tvCartBuyOrDel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				settlement();
			}
		});
		tipsTextView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				initData();
			}
		});
	}

	//结算
	private void settlement() {

		List<CarGoodsBean> selectedList = new ArrayList<CarGoodsBean>();
		for (int i = 0; i < carBeanList.size(); i++) {

			for (int j = 0; j < carBeanList.get(i).getGoods_detail().size(); j++) {
				if (carBeanList.get(i).getGoods_detail().get(j).ischeck()) {
					selectedList.add(carBeanList.get(i).getGoods_detail().get(j));
				}
			}
		}

		if (selectedList.size() == 0) {
			MyToastView.showToast("请选择您要结算的商品", CarActivity.this);
			return;
		}

		Intent intent = new Intent(CarActivity.this, BuySetup1Activity.class);
		intent.putExtra("data", (Serializable) selectedList);
		intent.putExtra("totalAmount", totalAmount);
		startActivity(intent);


	}

	private void setTotalData() {
		totalNum = 0;
		totalAmount = 0.00;
		boolean isall = false;
		for (int i = 0; i < carBeanList.size(); i++) {
			if (carBeanList.get(i).ischeck()) {
				for (int j = 0; j < carBeanList.get(i).getGoods_detail().size(); j++) {
					if (carBeanList.get(i).getGoods_detail().get(j).ischeck()) {
						totalNum += parseInt(carBeanList.get(i).getGoods_detail().get(j).getQuantity());
						totalAmount += Double.parseDouble(carBeanList.get(i).getGoods_detail().get(j).getQuantity()) * Double.parseDouble(carBeanList.get(i).getGoods_detail().get(j).getSale_price());
					}
				}
			}

		}
		number.setText(totalNum + "");
		NumberFormat nf = NumberFormat.getInstance();
		nf.setGroupingUsed(false);
		tvCartTotal.setText(nf.format(CommonUtil.getTwoDecimal(2, totalAmount)));
		int all = 0;
		int allCheck = 0;
		for (int i = 0; i < carBeanList.size(); i++) {
			if (carBeanList.get(i).ischeck()) {
				for (int j = 0; j < carBeanList.get(i).getGoods_detail().size(); j++) {
					all++;
					if (carBeanList.get(i).getGoods_detail().get(j).ischeck()) {
						allCheck++;
						totalNum += parseInt(carBeanList.get(i).getGoods_detail().get(j).getQuantity());
						totalAmount += Double.parseDouble(carBeanList.get(i).getGoods_detail().get(j).getQuantity()) * Double.parseDouble(carBeanList.get(i).getGoods_detail().get(j).getSale_price());
					}
				}
			}

		}
		if (all == allCheck && all != 0) {
			isall = true;
		}
		checkBox.setChecked(isall);

	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		ButterKnife.unbind(this);
		unregisterReceiver(myBroadCastReceiver);
		unregisterReceiver(myReceiver);
	}




	class MyBroadCastReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			initData();
		}
	}
	class MyReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			finish();
		}
	}

}
