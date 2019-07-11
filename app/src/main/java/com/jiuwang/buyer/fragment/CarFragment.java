package com.jiuwang.buyer.fragment;

import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jiuwang.buyer.R;
import com.jiuwang.buyer.activity.BuySetup1Activity;
import com.jiuwang.buyer.activity.LoginActivity;
import com.jiuwang.buyer.adapter.MyCarAdapter;
import com.jiuwang.buyer.bean.CarBean;
import com.jiuwang.buyer.bean.CarGoodsBean;
import com.jiuwang.buyer.constant.Constant;
import com.jiuwang.buyer.entity.BaseResultEntity;
import com.jiuwang.buyer.entity.MyCarEntity;
import com.jiuwang.buyer.net.HttpUtils;
import com.jiuwang.buyer.util.CommonUtil;
import com.jiuwang.buyer.util.DialogUtil;
import com.jiuwang.buyer.util.MyToastView;

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
 * desc：购物车页面
 * Created by lihj on  2019/6/24 10:19.
 */

public class CarFragment extends Fragment {

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

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
		View view = View.inflate(getActivity(), R.layout.fragment_cart, null);
		ButterKnife.bind(this, view);
		initView();
		initData();
		myBroadCastReceiver = new MyBroadCastReceiver();
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction("refreshCar");
		getActivity().registerReceiver(myBroadCastReceiver, intentFilter);
		return view;
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
					MyToastView.showToast(myCarEntity.getMsg(), getActivity());
					Intent intent = new Intent(getActivity(), LoginActivity.class);
					startActivity(intent);
					getActivity().finish();
				}
			}
		}, new Consumer<Throwable>() {
			@Override
			public void accept(Throwable throwable) throws Exception {
//				carListView.refreshComplete();
				MyToastView.showToast("获取失败", getActivity());
			}
		});
	}

	public void setAdapter() {
		cartListAdapter = new MyCarAdapter(getActivity(), carBeanList, new MyCarAdapter.ItemCheckStatusChangeListener() {
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
		actionbarText.setText("购物车");
		onclickLayoutLeft.setVisibility(View.INVISIBLE);
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
			MyToastView.showToast("请选择您要结算的商品", getActivity());
			return;
		}

		Intent intent = new Intent(getActivity(), BuySetup1Activity.class);
		intent.putExtra("data", (Serializable) selectedList);
		startActivity(intent);


	}

	private void setTotalData() {
		totalNum = 0;
		totalAmount = 0.00;
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
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		ButterKnife.unbind(this);
		getActivity().unregisterReceiver(myBroadCastReceiver);
	}

	class MyBroadCastReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			initData();
		}
	}
}