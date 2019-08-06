package com.jiuwang.buyer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
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
import com.jiuwang.buyer.constant.Constant;
import com.jiuwang.buyer.entity.BaseResultEntity;
import com.jiuwang.buyer.net.CommonHttpUtils;
import com.jiuwang.buyer.util.AppUtils;
import com.jiuwang.buyer.util.MyToastView;

import java.util.ArrayList;
import java.util.HashMap;
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
	@Bind(R.id.optionTextView)
	TextView optionTextView;
	@Bind(R.id.operaTextView)
	TextView operaTextView;
	@Bind(R.id.tvState)
	TextView tvState;
	@Bind(R.id.rlDeal)
	LinearLayout rlDeal;

	private MyApplication mApplication;
	private String address_id;
	private String destination_prov_cd;
	private String destination_city_cd;
	private String destination_area_cd;
	private String consignee_name;
	private String consignee_telephone;
	private String destination_address;
	private double amount;
	private OrderBean orderBean;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order_details);
		ButterKnife.bind(this);
//		mActivity = this;
		mApplication = (MyApplication) getApplication();

		Intent intent = getIntent();
		orderBean = (OrderBean) intent.getSerializableExtra("data");

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
			amount += Double.parseDouble(sale_price[i]) * Double.parseDouble(quantity[i]);
		}
		tvAmount.setText("￥" + AppUtils.decimalFormat(amount, "0") + "元");
		AppUtils.initListView(OrderDetailedActivity.this, mainListView, false, false);
		GoodsBuyListAdapter mAdapter = new GoodsBuyListAdapter(OrderDetailedActivity.this, detailsList);
		mainListView.setAdapter(mAdapter);
		initView();
	}

	private void initView() {
		setTopView(topView);
		titleTextView.setText("订单详情");
		tvState.setText(orderBean.getStatus_name());
		nameTextView.setText(orderBean.getConsignee_name());
		phoneTextView.setText(orderBean.getConsignee_telephone());
		addressTextView.setText(orderBean.getDestination());
		if (!"1".equals(orderBean.getOrder_type())) {
			addressRelativeLayout.setClickable(false);
			switch (orderBean.getStatus()) {
				case Constant.ORDER_STATUS_UNPAY://未付款
					rlDeal.setVisibility(View.VISIBLE);
					optionTextView.setText("取消订单");
					optionTextView.setVisibility(View.GONE);
					operaTextView.setText("去支付");
					optionTextView.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View view) {
//							HashMap<String, String> map = new HashMap<>();
//							map.put("consignee_name", consignee_name);
//							map.put("consignee_telephone", consignee_telephone);
//							map.put("id",destination_address );
//							map.put("id", orderBean.getId());
//							map.put("act","9");
//							//取消订单
//							CommonHttpUtils.orderInfo(map, new CommonHttpUtils.CallingBack() {
//								@Override
//								public void successBack(BaseResultEntity baseResultEntity) {
//
//								}
//
//								@Override
//								public void failBack() {
//
//								}
//							});

						}
					});
					operaTextView.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View view) {
							//去付款
							Intent intentBuy2 = new Intent();
							intentBuy2.setClass(OrderDetailedActivity.this, BuySetup2Activity.class);
							intentBuy2.putExtra("data", orderBean);
							intentBuy2.putExtra("pay_sn", "online");
							startActivity(intentBuy2);
						}
					});
					break;
				case Constant.ORDER_STATUS_PAYED://已付款
					rlDeal.setVisibility(View.VISIBLE);
					optionTextView.setVisibility(View.GONE);
					operaTextView.setVisibility(View.GONE);
					operaTextView.setText("退货/款");
					operaTextView.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View view) {
							//退货/款
							HashMap<String, String> map = new HashMap<>();
							map.put("id", orderBean.getId());
							map.put("act", "");
							CommonHttpUtils.orderInfo(map, new CommonHttpUtils.CallingBack() {
								@Override
								public void successBack(BaseResultEntity baseResultEntity) {
									if (Constant.HTTP_SUCCESS_CODE.equals(baseResultEntity.getCode())) {
										Intent intent = new Intent();
										intent.setAction("refreshOrder");
										sendBroadcast(intent);
										finish();
									} else if (Constant.HTTP_LOGINOUTTIME_CODE.equals(baseResultEntity.getCode())) {
										MyToastView.showToast(baseResultEntity.getMsg(), OrderDetailedActivity.this);
										startActivity(new Intent(OrderDetailedActivity.this, LoginActivity.class));
										finish();
									} else {
										MyToastView.showToast(baseResultEntity.getMsg(), OrderDetailedActivity.this);
									}
								}

								@Override
								public void failBack() {
									MyToastView.showToast(getResources().getString(R.string.msg_error_operation), OrderDetailedActivity.this);
								}
							});
						}
					});
					break;
				case Constant.ORDER_STATUS_CANCLE:
					rlDeal.setVisibility(View.VISIBLE);
					optionTextView.setVisibility(View.GONE);
					operaTextView.setVisibility(View.GONE);
					operaTextView.setText("删除订单");
					operaTextView.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View view) {
							//删除
							HashMap<String, String> map = new HashMap<>();
							map.put("id", orderBean.getId());
							map.put("act", Constant.ACTION_ACT_DELETE);
							//取消订单
							CommonHttpUtils.orderInfo(map, new CommonHttpUtils.CallingBack() {
								@Override
								public void successBack(BaseResultEntity baseResultEntity) {
									if (Constant.HTTP_SUCCESS_CODE.equals(baseResultEntity.getCode())) {
										Intent intent = new Intent();
										intent.setAction("refreshOrder");
										sendBroadcast(intent);
										finish();
									} else if (Constant.HTTP_LOGINOUTTIME_CODE.equals(baseResultEntity.getCode())) {
										MyToastView.showToast(baseResultEntity.getMsg(), OrderDetailedActivity.this);
										startActivity(new Intent(OrderDetailedActivity.this, LoginActivity.class));
										finish();
									} else {
										MyToastView.showToast(baseResultEntity.getMsg(), OrderDetailedActivity.this);
									}
								}

								@Override
								public void failBack() {
									MyToastView.showToast(getResources().getString(R.string.msg_error_operation), OrderDetailedActivity.this);
								}
							});
						}
					});
					break;
				case Constant.ORDER_STATUS_FINISH:
					rlDeal.setVisibility(View.GONE);
					break;
				default:
					break;
			}
		} else {
			if ("".equals(orderBean.getConsignee_name())) {
				addressRelativeLayout.setClickable(true);
			} else {
				addressRelativeLayout.setClickable(false);
			}

			switch (orderBean.getStatus()) {
				case Constant.ORDER_STATUS_SEND:
					rlDeal.setVisibility(View.VISIBLE);
					optionTextView.setVisibility(View.GONE);
					operaTextView.setVisibility(View.GONE);
					operaTextView.setText("确认收货");
					operaTextView.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View view) {
							//确认收货
							MyToastView.showToast("开发中", OrderDetailedActivity.this);

							HashMap<String, String> map = new HashMap<>();
							map.put("id", orderBean.getId());
							map.put("act", "");
							CommonHttpUtils.orderInfo(map, new CommonHttpUtils.CallingBack() {
								@Override
								public void successBack(BaseResultEntity baseResultEntity) {
									if (Constant.HTTP_SUCCESS_CODE.equals(baseResultEntity.getCode())) {
										Intent intent = new Intent();
										intent.setAction("refreshOrder");
										sendBroadcast(intent);
										finish();
									} else if (Constant.HTTP_LOGINOUTTIME_CODE.equals(baseResultEntity.getCode())) {
										MyToastView.showToast(baseResultEntity.getMsg(), OrderDetailedActivity.this);
										startActivity(new Intent(OrderDetailedActivity.this, LoginActivity.class));
										finish();
									} else {
										MyToastView.showToast(baseResultEntity.getMsg(), OrderDetailedActivity.this);
									}
								}

								@Override
								public void failBack() {
									MyToastView.showToast(getResources().getString(R.string.msg_error_operation), OrderDetailedActivity.this);
								}
							});
						}
					});
					break;
				case Constant.ORDER_STATUS_PAYED:
					if ("".equals(orderBean.getConsignee_name())) {
						addressRelativeLayout.setClickable(true);
						optionTextView.setVisibility(View.GONE);
						operaTextView.setText("保存地址");
						operaTextView.setOnClickListener(new View.OnClickListener() {
							@Override
							public void onClick(View view) {
								if(null == consignee_name){
									MyToastView.showToast("请填写收货地址",OrderDetailedActivity.this);
									return;
								}
								//添加收货地址
								HashMap<String, String> map = new HashMap<>();
								map.put("consignee_name", consignee_name);
								map.put("consignee_telephone", consignee_telephone);
								map.put("destination", destination_address);
								map.put("id", orderBean.getId());
								map.put("act", "9");
								CommonHttpUtils.orderInfo(map, new CommonHttpUtils.CallingBack() {
									@Override
									public void successBack(BaseResultEntity baseResultEntity) {
										if (Constant.HTTP_SUCCESS_CODE.equals(baseResultEntity.getCode())) {
											Intent intent = new Intent();
											intent.setAction("refreshOrder");
											sendBroadcast(intent);
											finish();
										} else if (Constant.HTTP_LOGINOUTTIME_CODE.equals(baseResultEntity.getCode())) {
											MyToastView.showToast(baseResultEntity.getMsg(), OrderDetailedActivity.this);
											startActivity(new Intent(OrderDetailedActivity.this, LoginActivity.class));
											finish();
										} else {
											MyToastView.showToast(baseResultEntity.getMsg(), OrderDetailedActivity.this);
										}
									}

									@Override
									public void failBack() {
										MyToastView.showToast(getResources().getString(R.string.msg_error_operation), OrderDetailedActivity.this);
									}
								});
							}
						});
					} else {
						addressRelativeLayout.setClickable(false);
					}
					break;
				case Constant.ORDER_STATUS_UNPAY:
				case Constant.ORDER_STATUS_FINISH:
					addressRelativeLayout.setClickable(false);
					rlDeal.setVisibility(View.GONE);
					break;
				default:
					break;
			}
		}

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
	}

	//设置地址
	private void setAddress(AddressBean addressBean) {
		address_id = addressBean.getId();
		destination_prov_cd = addressBean.getDestination_prov_cd();
		destination_city_cd = addressBean.getDestination_city_cd();
		destination_area_cd = addressBean.getDestination_area_cd();
		consignee_name = addressBean.getConsignee_name();
		consignee_telephone = addressBean.getConsignee_telephone();
		destination_address = addressBean.getDestination();
		nameTextView.setText(consignee_name);
		phoneTextView.setText(consignee_telephone);
		addressTextView.setText(destination_address);

	}

	@OnClick({R.id.backImageView, R.id.addressRelativeLayout})
	public void onViewClicked(View view) {
		switch (view.getId()) {
			case R.id.backImageView:
				finish();
				break;
			case R.id.addressRelativeLayout:
				//
				Intent intent = new Intent(OrderDetailedActivity.this, AddressActivity.class);
				intent.putExtra("model", "choose");
				mApplication.startActivity(OrderDetailedActivity.this, intent, MyApplication.CODE_CHOOSE_ADDRESS);
				break;
		}
	}
}
