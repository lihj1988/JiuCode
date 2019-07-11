package com.jiuwang.buyer.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jiuwang.buyer.R;
import com.jiuwang.buyer.adapter.BuyListAdapter;
import com.jiuwang.buyer.adapter.GoodsBuyListAdapter;
import com.jiuwang.buyer.base.MyApplication;
import com.jiuwang.buyer.bean.CarGoodsBean;
import com.jiuwang.buyer.constant.Constant;
import com.jiuwang.buyer.entity.BaseResultEntity;
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


public class BuySetup1Activity extends AppCompatActivity {

	private Activity mActivity;
	private MyApplication mApplication;

	private String ifcart;
	private String cart_id;
	private HashMap<String, String> mHashMap;
	private HashMap<String, String> messageHashMap;

	private String address_id;
	private String vat_hash;
	private String offpay_hash;
	private String offpay_hash_batch;
	private String pay_name;
	private String invoice_id;
	private String voucher;
	private String pd_pay;
	private String password;
	private String fcode;
	private String rcb_pay;
	private String rpt;
	private String pay_message;

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

	private Button confirmTextView;
	private List<CarGoodsBean> carGoodsList;

	@Override
	protected void onActivityResult(int req, int res, Intent data) {
		super.onActivityResult(req, res, data);
		if (res == RESULT_OK) {
			switch (req) {
				case MyApplication.CODE_CHOOSE_ADDRESS:
					address_id = data.getStringExtra("address_id");
					nameTextView.setText(data.getStringExtra("true_name"));
					phoneTextView.setText(data.getStringExtra("tel_phone"));
					addressTextView.setText(data.getStringExtra("area_info"));
					addressTextView.append(" ");
					addressTextView.append(data.getStringExtra("address"));
					changeAddress(data);
					break;
			}
		} else {
			switch (req) {
				case MyApplication.CODE_CHOOSE_ADDRESS:
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
					break;
				default:
					break;
			}
		}
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

		nameTextView = (TextView) findViewById(R.id.nameTextView);
		phoneTextView = (TextView) findViewById(R.id.phoneTextView);
		addressTextView = (TextView) findViewById(R.id.addressTextView);
		addressTitleTextView = (TextView) findViewById(R.id.addressTitleTextView);
		addressRelativeLayout = (RelativeLayout) findViewById(R.id.addressRelativeLayout);
		confirmTextView = (Button) findViewById(R.id.confirmTextView);
		tvAmount = (TextView) findViewById(R.id.tvAmount);

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
		tvAmount.setText("￥"+AppUtils.decimalFormat(amount,"0")+"元");
		goodsIds = carBuilder.toString();

//        ifcart = mActivity.getIntent().getStringExtra("ifcart");
//        cart_id = mActivity.getIntent().getStringExtra("cart_id");
		address_id = "";
		vat_hash = "";
		offpay_hash = "";
		offpay_hash_batch = "";
		pay_name = "";
		invoice_id = "";
		voucher = "";
		pd_pay = "";
		password = "";
		fcode = "";
		rcb_pay = "";
		rpt = "";
		pay_message = "";

//        if (TextUtil.isEmpty(ifcart) || TextUtil.isEmpty(cart_id) || TextUtil.isEmpty(cart_id)) {
//            MyToastView.showToast("数据错误", mActivity);
//            mApplication.finishActivity(mActivity);
//        }

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

//        mAdapter.setOnTextWatcherListener(new BuyListAdapter.onTextWatcherListener() {
//            @Override
//            public void onTextWatcher(String id, String content) {
//                messageHashMap.put(id, content);
//            }
//        });

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


	//解析信息
	private void parseInfo() {

		vat_hash = mHashMap.get("vat_hash");

		try {
			JSONObject jsonObject = new JSONObject(mHashMap.get("address_api"));
			offpay_hash = jsonObject.getString("offpay_hash");
			offpay_hash_batch = jsonObject.getString("offpay_hash_batch");
		} catch (JSONException e) {
			e.printStackTrace();
		}

		String temp = "订单支付：" + mHashMap.get("order_amount") + " 元（含运费）";
		confirmTextView.setText(temp);
		pay_name = "online";
		invoice_id = "0";
		voucher = "";
		pd_pay = "0";
		password = "";
		fcode = "";
		rcb_pay = "";
		rpt = "";

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

	//更换地址
	private void changeAddress(final Intent intent) {

		DialogUtil.progress(mActivity);
//        KeyAjaxParams ajaxParams = new KeyAjaxParams(mApplication);
//        ajaxParams.putAct("member_buy");
//        ajaxParams.putOp("change_address");
//        ajaxParams.put("freight_hash", mHashMap.get("freight_hash"));
//        ajaxParams.put("city_id", intent.getStringExtra("city_id"));
//        ajaxParams.put("area_id", intent.getStringExtra("area_id"));
//        mApplication.mFinalHttp.post(mApplication.apiUrlString, ajaxParams, new AjaxCallBack<Object>() {
//            @Override
//            public void onSuccess(Object o) {
//                super.onSuccess(o);
//                DialogUtil.cancel();
//                if (TextUtil.isJson(o.toString())) {
//                    String error = mApplication.getJsonError(o.toString());
//                    if (TextUtil.isEmpty(error)) {
//                        String data = mApplication.getJsonData(o.toString());
//                        try {
//                            JSONObject jsonObject = new JSONObject(data);
//                            offpay_hash = jsonObject.getString("offpay_hash");
//                            offpay_hash_batch = jsonObject.getString("offpay_hash_batch");
//                        } catch (JSONException e) {
//                            changeAddressFailure(intent);
//                            e.printStackTrace();
//                        }
//                    } else {
//                        changeAddressFailure(intent);
//                    }
//                } else {
//                    changeAddressFailure(intent);
//                }
//            }
//
//            @Override
//            public void onFailure(Throwable t, int errorNo, String strMsg) {
//                super.onFailure(t, errorNo, strMsg);
//                changeAddressFailure(intent);
//                DialogUtil.cancel();
//            }
//        });

	}

	//更换地址失败
	private void changeAddressFailure(final Intent intent) {

		DialogUtil.query(
				mActivity,
				"确认您的选择",
				"数据加载失败，是否重试？",
				new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						DialogUtil.cancel();
						changeAddress(intent);
					}
				},
				new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						mApplication.finishActivity(mActivity);
						DialogUtil.cancel();
					}
				}
		);

	}

	//确认订单信息
	private void confirmOrderInfo() {

				DialogUtil.progress(BuySetup1Activity.this);
		if (CommonUtil.getNetworkRequest(BuySetup1Activity.this)) {
			HashMap<String, String> map = new HashMap<>();
			map.put("act", Constant.ACTION_ACT_ADD);
			map.put("id", goodsIds);
			HttpUtils.settlement(map, new Consumer<BaseResultEntity>() {
				@Override
				public void accept(BaseResultEntity baseResultEntity) throws Exception {
					DialogUtil.cancel();
					if (Constant.HTTP_SUCCESS_CODE.equals(baseResultEntity)) {
						//跳转付款页面 拿到订单号到付款页面
					}
					MyToastView.showToast(baseResultEntity.getMsg(), BuySetup1Activity.this);
				}
			}, new Consumer<Throwable>() {
				@Override
				public void accept(Throwable throwable) throws Exception {
					DialogUtil.cancel();
					MyToastView.showToast(getString(R.string.msg_error_operation),BuySetup1Activity.this);
				}
			});
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
