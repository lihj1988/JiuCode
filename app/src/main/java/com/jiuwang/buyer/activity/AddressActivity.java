package com.jiuwang.buyer.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiuwang.buyer.R;
import com.jiuwang.buyer.adapter.AddressListAdapter;
import com.jiuwang.buyer.base.MyApplication;
import com.jiuwang.buyer.util.DialogUtil;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * 作者:lihj
 * 作用：我的收货地址
 */

public class AddressActivity extends AppCompatActivity {

	private Activity mActivity;
	private MyApplication mApplication;

	private String modelString;

	private ImageView backImageView;
	private TextView titleTextView;
	private ImageView addImageView;

	private TextView tipsTextView;
	private RecyclerView mListView;
	private AddressListAdapter mAdapter;
	private SwipeRefreshLayout mSwipeRefreshLayout;
	private ArrayList<HashMap<String, String>> mArrayList;

	@Override
	protected void onActivityResult(int req, int res, Intent data) {
		super.onActivityResult(req, res, data);
		if (res == RESULT_OK) {
			switch (req) {
				case MyApplication.CODE_ADDRESS_ADD:
					getJson();
					break;
				case MyApplication.CODE_ADDRESS_EDIT:
					getJson();
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
		setContentView(R.layout.activity_recycler);
		initView();
		initData();
		initEven();
		getJson();
	}

	//初始化控件
	private void initView() {

		backImageView = (ImageView) findViewById(R.id.backImageView);
		titleTextView = (TextView) findViewById(R.id.titleTextView);
		addImageView = (ImageView) findViewById(R.id.moreImageView);

		tipsTextView = (TextView) findViewById(R.id.tipsTextView);
		mListView = (RecyclerView) findViewById(R.id.mainListView);
		mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.mainSwipeRefreshLayout);

	}

	//初始化数据
	private void initData() {

		mActivity = this;
		mApplication = (MyApplication) getApplication();

		modelString = mActivity.getIntent().getStringExtra("model");

		titleTextView.setText("收货地址");
		addImageView.setImageResource(R.drawable.ic_action_add);

		mArrayList = new ArrayList<>();
		mAdapter = new AddressListAdapter(mArrayList);
		mListView.setLayoutManager(new LinearLayoutManager(this));
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

		addImageView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mApplication.startActivity(mActivity, new Intent(mActivity, AddressAddActivity.class), MyApplication.CODE_ADDRESS_ADD);
			}
		});

		tipsTextView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (tipsTextView.getText().toString().equals("数据加载失败\n\n点击重试")) {
					getJson();
				}
				if (tipsTextView.getText().toString().equals("没有收货地址\n\n点击添加")) {
					mApplication.startActivity(mActivity, new Intent(mActivity, AddressAddActivity.class), MyApplication.CODE_ADDRESS_ADD);
				}
			}
		});

		mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
			@Override
			public void onRefresh() {
				new Handler().postDelayed(new Runnable() {
					@Override
					public void run() {
						getJson();
					}
				}, 1000);
			}
		});

		mAdapter.setOnItemClickListener(new AddressListAdapter.onItemClickListener() {
			@Override
			public void onItemClick(int position) {
				if (modelString.equals("choose")) {
					Intent intent = new Intent();
					intent.putExtra("address_id", mArrayList.get(position).get("address_id"));
					intent.putExtra("true_name", mArrayList.get(position).get("true_name"));
					intent.putExtra("area_id", mArrayList.get(position).get("area_id"));
					intent.putExtra("city_id", mArrayList.get(position).get("city_id"));
					intent.putExtra("area_info", mArrayList.get(position).get("area_info"));
					intent.putExtra("address", mArrayList.get(position).get("address"));
					intent.putExtra("tel_phone", mArrayList.get(position).get("tel_phone"));
					intent.putExtra("is_default", mArrayList.get(position).get("is_default"));
					mActivity.setResult(RESULT_OK, intent);
					mApplication.finishActivity(mActivity);
				} else {
					Intent intent = new Intent(mActivity, AddressEditActivity.class);
					intent.putExtra("address_id", mArrayList.get(position).get("address_id"));
					intent.putExtra("true_name", mArrayList.get(position).get("true_name"));
					intent.putExtra("area_id", mArrayList.get(position).get("area_id"));
					intent.putExtra("city_id", mArrayList.get(position).get("city_id"));
					intent.putExtra("area_info", mArrayList.get(position).get("area_info"));
					intent.putExtra("address", mArrayList.get(position).get("address"));
					intent.putExtra("tel_phone", mArrayList.get(position).get("tel_phone"));
					intent.putExtra("is_default", mArrayList.get(position).get("is_default"));
					mApplication.startActivity(mActivity, intent, MyApplication.CODE_ADDRESS_EDIT);
				}
			}
		});

		mAdapter.setOnItemLongClickListener(new AddressListAdapter.onItemLongClickListener() {
			@Override
			public void onItemLongClick(final int position) {
				DialogUtil.query(
						mActivity,
						"确认您的选择",
						"删除这个收货地址",
						new View.OnClickListener() {
							@Override
							public void onClick(View v) {
								DialogUtil.cancel();
//                                KeyAjaxParams ajaxParams = new KeyAjaxParams(mApplication);
//                                ajaxParams.putAct("member_address");
//                                ajaxParams.putOp("address_del");
//                                ajaxParams.put("address_id", mArrayList.get(position).get("address_id"));
//                                mApplication.mFinalHttp.post(mApplication.apiUrlString, ajaxParams, new AjaxCallBack<Object>() {
//                                    @Override
//                                    public void onSuccess(Object o) {
//                                        super.onSuccess(o);
//                                        if (TextUtil.isJson(o.toString())) {
//                                            String error = mApplication.getJsonError(o.toString());
//                                            if (TextUtil.isEmpty(error)) {
//                                                String data = mApplication.getJsonData(o.toString());
//                                                if (data.equals("1")) {
//                                                    mArrayList.remove(position);
//                                                    ToastUtil.showSuccess(mActivity);
//                                                    mAdapter.notifyDataSetChanged();
//                                                    if (mArrayList.size() == 0) {
//                                                        tipsTextView.setVisibility(View.VISIBLE);
//                                                        tipsTextView.setText("没有收货地址\n\n点击添加");
//                                                    }
//                                                } else {
//                                                    ToastUtil.showFailure(mActivity);
//                                                }
//                                            } else {
//                                                ToastUtil.show(mActivity, error);
//                                            }
//                                        } else {
//                                            ToastUtil.showFailure(mActivity);
//                                        }
//                                    }
//
//                                    @Override
//                                    public void onFailure(Throwable t, int errorNo, String strMsg) {
//                                        super.onFailure(t, errorNo, strMsg);
//                                        ToastUtil.showFailure(mActivity);
//                                    }
//                                });
							}
						}
				);
			}
		});

	}

	//读取数据
	private void getJson() {

		DialogUtil.progress(mActivity);
//        KeyAjaxParams ajaxParams = new KeyAjaxParams(mApplication);
//        ajaxParams.putAct("member_address");
//        ajaxParams.putOp("address_list");
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
//                            mArrayList.clear();
//                            JSONObject jsonObject = new JSONObject(data);
//                            JSONArray jsonArray = new JSONArray(jsonObject.getString("address_list"));
//                            for (int i = 0; i < jsonArray.length(); i++) {
//                                mArrayList.add(new HashMap<>(TextUtil.jsonObjectToHashMap(jsonArray.get(i).toString())));
//                            }
//                            if (mArrayList.size() == 0) {
//                                tipsTextView.setVisibility(View.VISIBLE);
//                                tipsTextView.setText("没有收货地址\n\n点击添加");
//                                if (modelString.equals("choose")) {
//                                    mApplication.startActivity(mActivity, new Intent(mActivity, AddressAddActivity.class), NcApplication.CODE_ADDRESS_ADD);
//                                }
//                            } else {
//                                tipsTextView.setVisibility(View.GONE);
//                            }
//                            mSwipeRefreshLayout.setRefreshing(false);
//                            mAdapter.notifyDataSetChanged();
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                            getJsonFailure();
//                        }
//                    } else {
//                        getJsonFailure();
//                    }
//                } else {
//                    getJsonFailure();
//                }
//            }
//
//            @Override
//            public void onFailure(Throwable t, int errorNo, String strMsg) {
//                super.onFailure(t, errorNo, strMsg);
//                DialogUtil.cancel();
//                getJsonFailure();
//            }
//        });

	}

	//读取数据失败
	private void getJsonFailure() {

		DialogUtil.query(
				mActivity,
				"确认您的选择",
				"读取数据失败，是否重试？",
				new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						DialogUtil.cancel();
						getJson();
					}
				}, new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						DialogUtil.cancel();
						tipsTextView.setVisibility(View.VISIBLE);
						tipsTextView.setText("数据加载失败\n\n点击重试");
					}
				}
		);

	}

	//返回&销毁Activity
	private void returnActivity() {

		if (modelString.equals("choose")) {
			DialogUtil.query(
					mActivity,
					"确认您的选择",
					"取消选择收货地址",
					new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							DialogUtil.cancel();
							mApplication.finishActivity(mActivity);
						}
					}
			);
		} else {
			mApplication.finishActivity(mActivity);
		}

	}

}