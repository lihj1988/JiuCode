package com.jiuwang.buyer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jiuwang.buyer.R;
import com.jiuwang.buyer.adapter.GoodsBuyListAdapter;
import com.jiuwang.buyer.base.BaseActivity;
import com.jiuwang.buyer.base.MyApplication;
import com.jiuwang.buyer.bean.AddressBean;
import com.jiuwang.buyer.bean.CarGoodsBean;
import com.jiuwang.buyer.bean.OrderBean;
import com.jiuwang.buyer.util.AppUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OrderDetailedActivity extends BaseActivity {
	@Bind(R.id.topView)
	LinearLayout topView;
	@Bind(R.id.backImageView)
	ImageView backImageView;
	@Bind(R.id.titleTextView)
	TextView titleTextView;
	@Bind(R.id.moreImageView)
	ImageView moreImageView;
	@Bind(R.id.addressTitleTextView)
	TextView addressTitleTextView;
	@Bind(R.id.nameTextView)
	TextView nameTextView;
	@Bind(R.id.phoneTextView)
	TextView phoneTextView;
	@Bind(R.id.addressTextView)
	TextView addressTextView;
	@Bind(R.id.addressRelativeLayout)
	RelativeLayout addressRelativeLayout;
	@Bind(R.id.mainListView)
	XRecyclerView mainListView;
	@Bind(R.id.invoiceTitleTextView)
	TextView invoiceTitleTextView;
	@Bind(R.id.paymentTitleTextView)
	TextView paymentTitleTextView;
	@Bind(R.id.tvAmount)
	TextView tvAmount;
	@Bind(R.id.confirmTextView)
	Button confirmTextView;
	private MyApplication mApplication;
	private String address_id;
	private String destination_prov_cd;
	private String destination_city_cd;
	private String destination_area_cd;
	private String consignee_name;
	private String consignee_telephone;
	private String destination_address;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order_details);
		ButterKnife.bind(this);
//		mActivity = this;
		mApplication = (MyApplication) getApplication();
		initView();
		Intent intent = getIntent();
		OrderBean orderBean = (OrderBean) intent.getSerializableExtra("data");

		List<CarGoodsBean> detailsList = new ArrayList<CarGoodsBean>();
		String[] goods_name = orderBean.getGoods_name().split(",");
		String[] quantity = orderBean.getQuantity().split(",");
		String[] sale_price = orderBean.getSale_price().split(",");
		String[] pic_url = orderBean.getPic_url().split(",");
		for (int i = 0; i < goods_name.length; i++) {
			CarGoodsBean detailListBean = new CarGoodsBean();
			detailListBean.setGoods_name(goods_name[i]);
			detailListBean.setQuantity(quantity[i]);
			detailListBean.setSale_price(sale_price[i]);
			if (pic_url.length < goods_name.length) {
				detailListBean.setPic_url(pic_url[0]);
			} else {

				detailListBean.setPic_url(pic_url[i]);
			}
			detailsList.add(detailListBean);
		}

		AppUtils.initListView(OrderDetailedActivity.this, mainListView, false, false);
		GoodsBuyListAdapter mAdapter = new GoodsBuyListAdapter(OrderDetailedActivity.this, detailsList);
		mainListView.setAdapter(mAdapter);
	}

	private void initView() {
		setTopView(topView);
		titleTextView.setText("订单详情");
	}
	@Override
	protected void onActivityResult(int req, int res, Intent data) {
		super.onActivityResult(req, res, data);
		if (res == RESULT_OK) {
			switch (req) {
				case MyApplication.CODE_CHOOSE_ADDRESS:
					AddressBean addressBean = (AddressBean) data.getSerializableExtra("address");
					setAddress(addressBean);
					break;
				default:
					break;
			}

		}
//		else {
//			switch (req) {
//				case MyApplication.CODE_CHOOSE_ADDRESS:
//					DialogUtil.query(
//							mActivity,
//							"确认您的选择",
//							"添加收货地址？",
//							new View.OnClickListener() {
//								@Override
//								public void onClick(View v) {
//									DialogUtil.cancel();
//									Intent intent = new Intent(mActivity, AddressActivity.class);
//									intent.putExtra("model", "choose");
//									mApplication.startActivity(mActivity, intent, MyApplication.CODE_CHOOSE_ADDRESS);
//								}
//							},
//							new View.OnClickListener() {
//								@Override
//								public void onClick(View v) {
//									DialogUtil.cancel();
//									mApplication.finishActivity(mActivity);
//								}
//							}
//					);
//					break;
//				default:
//					break;
//			}
//		}
	}

	//设置地址
	private void setAddress(AddressBean addressBean) {
		address_id = addressBean.getId();
		destination_prov_cd = addressBean.getDestination_prov_cd();
		destination_city_cd = addressBean.getDestination_city_cd();
		destination_area_cd = addressBean.getDestination_area_cd();
		consignee_name = addressBean.getConsignee_name();
		consignee_telephone = addressBean.getConsignee_telephone();
		destination_address = addressBean.getDestination_address();
		nameTextView.setText(consignee_name);
		phoneTextView.setText(consignee_telephone);
		addressTextView.setText(destination_address);

	}
	@OnClick({R.id.backImageView, R.id.addressRelativeLayout, R.id.confirmTextView})
	public void onViewClicked(View view) {
		switch (view.getId()) {
			case R.id.backImageView:
				break;
			case R.id.addressRelativeLayout:
				//
				Intent intent = new Intent(OrderDetailedActivity.this, AddressActivity.class);
				intent.putExtra("model", "choose");
				mApplication.startActivity(OrderDetailedActivity.this, intent, MyApplication.CODE_CHOOSE_ADDRESS);
				break;
			case R.id.confirmTextView:
				break;
		}
	}
}
