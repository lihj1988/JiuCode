package com.jiuwang.buyer.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jiuwang.buyer.R;
import com.jiuwang.buyer.adapter.BuyListAdapter;
import com.jiuwang.buyer.adapter.GoodsBuyListAdapter;
import com.jiuwang.buyer.base.BaseActivity;
import com.jiuwang.buyer.base.MyApplication;
import com.jiuwang.buyer.bean.AddressBean;
import com.jiuwang.buyer.bean.CarGoodsBean;
import com.jiuwang.buyer.constant.Constant;
import com.jiuwang.buyer.entity.OrderEntity;
import com.jiuwang.buyer.net.HttpUtils;
import com.jiuwang.buyer.util.AppUtils;
import com.jiuwang.buyer.util.CommonUtil;
import com.jiuwang.buyer.util.DialogUtil;
import com.jiuwang.buyer.util.MyToastView;
import com.jiuwang.buyer.util.TextUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import io.reactivex.functions.Consumer;

/*
*
* 作者：lihj
* 作用：购买第一步，确认收货地址，选择支付方式等
*/


public class BuySetup1Activity extends BaseActivity {

	private Activity mActivity;
	private MyApplication mApplication;

	private String ifcart;
	private String cart_id;
	private HashMap<String, String> mHashMap;
	private HashMap<String, String> messageHashMap;

	private String address_id;
	private String destination_prov_cd;
	private String destination_city_cd;
	private String destination_area_cd;
	private String destination_address;
	private String consignee_name;
	private String consignee_telephone;

	private ImageView backImageView;
	private TextView titleTextView;

	private XRecyclerView mListView;
	private BuyListAdapter mAdapter;
	private ArrayList<HashMap<String, String>> mArrayList;

	private TextView nameTextView;
	private TextView phoneTextView;
	private TextView tvAmount;
	private TextView addressTextView;
	private TextView addressTitleTextView;
	private RelativeLayout addressRelativeLayout;
	private LinearLayout topView;

	private Button confirmTextView;
	private List<CarGoodsBean> carGoodsList;

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
		destination_area_cd = addressBean.getDestination_area_cd();
		consignee_name = addressBean.getConsignee_name();
		consignee_telephone = addressBean.getConsignee_telephone();
		destination_address = addressBean.getDestination_address();
		nameTextView.setText(consignee_name);
		phoneTextView.setText(consignee_telephone);
		addressTextView.setText(destination_address);

	}

	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
			returnActivity();
			return true;
		}
		return super.dispatchKeyEvent(event);
	}

	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.activity_buy_setup1);
		initView();
		initData();
		initEven();
//        getJson();
	}

	//初始化控件
	private void initView() {

		backImageView = (ImageView) findViewById(R.id.backImageView);
		titleTextView = (TextView) findViewById(R.id.titleTextView);

		mListView = (XRecyclerView) findViewById(R.id.mainListView);
		topView = (LinearLayout) findViewById(R.id.topView);

		nameTextView = (TextView) findViewById(R.id.nameTextView);
		phoneTextView = (TextView) findViewById(R.id.phoneTextView);
		addressTextView = (TextView) findViewById(R.id.addressTextView);
		addressTitleTextView = (TextView) findViewById(R.id.addressTitleTextView);
		addressRelativeLayout = (RelativeLayout) findViewById(R.id.addressRelativeLayout);
		confirmTextView = (Button) findViewById(R.id.confirmTextView);
		tvAmount = (TextView) findViewById(R.id.tvAmount);
		setTopView(topView);

	}

	private String goodsIds;
	private double amount;

	//初始化数据
	private void initData() {
		StringBuffer carBuilder = new StringBuffer();
		mActivity = this;
		mApplication = (MyApplication) getApplication();
		Intent intent = getIntent();
		carGoodsList = (List<CarGoodsBean>) intent.getSerializableExtra("data");
		for (int i = 0; i < carGoodsList.size(); i++) {
			carBuilder.append(carGoodsList.get(i).getId());
			amount += Double.parseDouble(carGoodsList.get(i).getSale_price()) * Double.parseDouble(carGoodsList.get(i).getQuantity());
			if (i < carGoodsList.size() - 1) {
				carBuilder.append(",");
			}
		}
		tvAmount.setText("￥" + AppUtils.decimalFormat(amount, "0") + "元");
		goodsIds = carBuilder.toString();


		titleTextView.setText("确认订单信息");

		mArrayList = new ArrayList<>();
		AppUtils.initListView(BuySetup1Activity.this, mListView, false, false);
		GoodsBuyListAdapter mAdapter = new GoodsBuyListAdapter(mActivity, carGoodsList);
		mListView.setAdapter(mAdapter);

	}

	//初始化事件
	private void initEven() {

		backImageView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				returnActivity();
			}
		});

		addressTitleTextView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(mActivity, AddressActivity.class);
				intent.putExtra("model", "choose");
				mApplication.startActivity(mActivity, intent, MyApplication.CODE_CHOOSE_ADDRESS);
			}
		});

		addressRelativeLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(mActivity, AddressActivity.class);
				intent.putExtra("model", "choose");
				mApplication.startActivity(mActivity, intent, MyApplication.CODE_CHOOSE_ADDRESS);
			}
		});

		confirmTextView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				confirmOrderInfo();
			}
		});

	}

	//获取购买数据
	private void getJson() {

		DialogUtil.progress(mActivity);


	}




	//解析 cartList
	private void parseCartList() {

		try {

			mArrayList.clear();
			JSONObject jsonObject = new JSONObject(mHashMap.get("store_cart_list"));

			Iterator iterator = jsonObject.keys();
			while (iterator.hasNext()) {
				String key = iterator.next().toString();
				mArrayList.add(new HashMap<>(TextUtil.jsonObjectToHashMap(jsonObject.getString(key))));
			}

			mAdapter.notifyDataSetChanged();

		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	//解析地址
	private void parseAddress() {

		try {
			JSONObject jsonObject = new JSONObject(mHashMap.get("address_info"));
			address_id = jsonObject.getString("address_id");
			nameTextView.setText(jsonObject.getString("true_name"));
			if (TextUtil.isEmpty(jsonObject.getString("tel_phone"))) {
				phoneTextView.setText(jsonObject.getString("mob_phone"));
			} else {
				phoneTextView.setText(jsonObject.getString("tel_phone"));
			}
			addressTextView.setText(jsonObject.getString("area_info"));
			addressTextView.append(" ");
			addressTextView.append(jsonObject.getString("address"));
		} catch (JSONException e) {
			DialogUtil.query(
					mActivity,
					"确认您的选择",
					"添加收货地址？",
					new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							DialogUtil.cancel();
							Intent intent = new Intent(mActivity, AddressActivity.class);
							intent.putExtra("model", "choose");
							mApplication.startActivity(mActivity, intent, MyApplication.CODE_CHOOSE_ADDRESS);
						}
					},
					new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							DialogUtil.cancel();
							mApplication.finishActivity(mActivity);
						}
					}
			);
		}

	}

	//确认订单信息
	private void confirmOrderInfo() {
		if(address_id!=null&&!address_id.equals("")){
			DialogUtil.progress(BuySetup1Activity.this);
			if (CommonUtil.getNetworkRequest(BuySetup1Activity.this)) {
				HashMap<String, String> map = new HashMap<>();
				map.put("act", Constant.ACTION_ACT_ADD);
				map.put("id", goodsIds);
				HttpUtils.settlement(map, new Consumer<OrderEntity>() {
					@Override
					public void accept(OrderEntity baseResultEntity) throws Exception {
						DialogUtil.cancel();
						if (Constant.HTTP_SUCCESS_CODE.equals(baseResultEntity.getCode())) {
							//跳转付款页面 拿到订单号到付款页面
							Intent intent = new Intent();
							intent.setAction("refreshCar");
							sendBroadcast(intent);
							Intent intentBuy2 = new Intent();
							intentBuy2.setClass(BuySetup1Activity.this,BuySetup2Activity.class);
							intentBuy2.putExtra("data",baseResultEntity.getDate().get(0));
							intentBuy2.putExtra("pay_sn","online");
							startActivity(intentBuy2);
						}
						MyToastView.showToast(baseResultEntity.getMsg(), BuySetup1Activity.this);
					}
				}, new Consumer<Throwable>() {
					@Override
					public void accept(Throwable throwable) throws Exception {
						DialogUtil.cancel();
						MyToastView.showToast(getString(R.string.msg_error_operation), BuySetup1Activity.this);
					}
				});
			}else {
				MyToastView.showToast("请选择收货地址", BuySetup1Activity.this);
			}
		}


	}

	//返回&销毁Activity
	private void returnActivity() {

		DialogUtil.query(
				mActivity,
				"确认您的选择",
				"取消订单？",
				new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						DialogUtil.cancel();
						mApplication.finishActivity(mActivity);
					}
				}
		);

	}

}
