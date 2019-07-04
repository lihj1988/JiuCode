package com.jiuwang.buyer.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiuwang.buyer.R;
import com.jiuwang.buyer.adapter.BasePagerAdapter;
import com.jiuwang.buyer.adapter.OrderListAdapter;
import com.jiuwang.buyer.base.MyApplication;
import com.jiuwang.buyer.bean.OrderBean;
import com.jiuwang.buyer.util.ControlUtil;
import com.jiuwang.buyer.util.DialogUtil;

import java.util.ArrayList;
import java.util.List;



/*
*
* 作者：Yokey软件工作室
*
* 企鹅：1002285057
*
* 网址：www.yokey.top
*
* 作用：我的实物订单
*
* 更新：2016-04-17
*
*/

public class OrderActivity extends AppCompatActivity {

	private Activity mActivity;
	private MyApplication mApplication;

	private boolean firstBoolean;

	private ImageView backImageView;
	private TextView titleTextView;

	private TabLayout mTabLayout;
	private ViewPager mViewPager;

	private TextView mTextView;
	private OrderListAdapter mAdapter;
	private SwipeRefreshLayout mSwipeRefreshLayout;
	private String tag = "0";
	//公用变量
	public List<OrderBean> orderArrayList; //订单数组

	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
			returnActivity();
			return true;
		}
		return super.dispatchKeyEvent(event);
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		getJson(tag);
	}

	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.activity_viewpager);
		initView();
		initData();
		initEven();
	}

	//初始化控件
	private void initView() {

		backImageView = (ImageView) findViewById(R.id.backImageView);
		titleTextView = (TextView) findViewById(R.id.titleTextView);

		mTabLayout = (TabLayout) findViewById(R.id.mainTabLayout);
		mViewPager = (ViewPager) findViewById(R.id.mainViewPager);
		mTextView = (TextView) findViewById(R.id.tipsTextView);
	}

	//初始化数据
	private void initData() {
		orderArrayList = new ArrayList<>();
		mActivity = this;
		mApplication = (MyApplication) getApplication();

		firstBoolean = true;

		titleTextView.setText("我的订单");

		List<View> mViewList = new ArrayList<>();
		mViewList.add(mActivity.getLayoutInflater().inflate(R.layout.include_list_view, null));
		mViewList.add(mActivity.getLayoutInflater().inflate(R.layout.include_list_view, null));
		mViewList.add(mActivity.getLayoutInflater().inflate(R.layout.include_list_view, null));
		mViewList.add(mActivity.getLayoutInflater().inflate(R.layout.include_list_view, null));
		mViewList.add(mActivity.getLayoutInflater().inflate(R.layout.include_list_view, null));
		mViewList.add(mActivity.getLayoutInflater().inflate(R.layout.include_list_view, null));
		List<String> mTitleList = new ArrayList<>();
		mTitleList.add("全部");
		mTitleList.add("待支付");
		mTitleList.add("待发货");
		mTitleList.add("待收货");
		mTitleList.add("待评价");
		mTitleList.add("已完成");
//		mTextView = new TextView[mTitleList.size()];
		mAdapter = new OrderListAdapter(mApplication, mActivity, orderArrayList);
		RecyclerView mListView = new RecyclerView(OrderActivity.this);
		mSwipeRefreshLayout = new SwipeRefreshLayout(OrderActivity.this);
//        for (int i = 0; i < mTitleList.size(); i++) {
//            mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(i)));
//            mTextView[i] = (TextView) mViewList.get(i).findViewById(R.id.tipsTextView);
//            mListView[i] = (RecyclerView) mViewList.get(i).findViewById(R.id.mainListView);
//            mSwipeRefreshLayout[i] = (SwipeRefreshLayout) mViewList.get(i).findViewById(R.id.mainSwipeRefreshLayout);
//            mAdapter[i] = new OrderListAdapter(mApplication, mActivity, mApplication.orderArrayList[i]);
//            mListView[i].setLayoutManager(new LinearLayoutManager(this));
//            mListView[i].setAdapter(mAdapter[i]);
//        }
		ControlUtil.setTabLayout(mActivity, mTabLayout, new BasePagerAdapter(mViewList, mTitleList), mViewPager);
		mTabLayout.setTabMode(TabLayout.MODE_FIXED);

		//根据传进来的值设置位置
		int position = mActivity.getIntent().getIntExtra("position", 0);
		mViewPager.setCurrentItem(position);
		setControl();


	}

	//初始化事件
	private void initEven() {

		backImageView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				returnActivity();
			}
		});


		mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
			@Override
			public void onRefresh() {
				new Handler().postDelayed(new Runnable() {
					@Override
					public void run() {
						//根据已选标签进行获取数据
						getJson(tag);
					}
				}, 1000);
			}
		});


		if (mTextView.getText().toString().equals("订单数据加载失败\n\n点击重试")) {
			mTextView.setText("加载中...");
			getJson(tag);
		}

		mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
			@Override
			public void onTabSelected(TabLayout.Tab tab) {
				tag = tab.getPosition() + "";
			}

			@Override
			public void onTabUnselected(TabLayout.Tab tab) {

			}

			@Override
			public void onTabReselected(TabLayout.Tab tab) {

			}
		});

//        for (OrderListAdapter adapter : mAdapter) {
//            adapter.setOnItemChange(new OrderListAdapter.onItemChange() {
//                @Override
//                public void onChange() {
//                    getJson();
//                }
//            });
//        }

	}

	//获取JSON数据
	private void getJson(String tag) {

		if (firstBoolean) {
			DialogUtil.progress(mActivity);
			firstBoolean = false;
		}

//        KeyAjaxParams ajaxParams = new KeyAjaxParams(mApplication);
//        ajaxParams.putAct("member_order");
//        ajaxParams.putOp("order_list");
//
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
//                            JSONArray jsonArray = new JSONArray(jsonObject.getString("order_group_list"));
//                            for (ArrayList arrayList : mApplication.orderArrayList) {
//                                arrayList.clear();
//                            }
//                            for (int i = 0; i < jsonArray.length(); i++) {
//                                mApplication.orderArrayList[0].add(new HashMap<>(TextUtil.jsonObjectToHashMap(jsonArray.get(i).toString())));
//                            }
//                            for (int i = 0; i < mApplication.orderArrayList[0].size(); i++) {
//                                try {
//                                    JSONArray order_list = new JSONArray(mApplication.orderArrayList[0].get(i).get("order_list"));
//                                    jsonObject = (JSONObject) order_list.get(0);
//                                    switch (jsonObject.getString("order_state")) {
//                                        case "10":
//                                            mApplication.orderArrayList[1].add(mApplication.orderArrayList[0].get(i));
//                                            break;
//                                        case "20":
//                                            mApplication.orderArrayList[2].add(mApplication.orderArrayList[0].get(i));
//                                            break;
//                                        case "30":
//                                            mApplication.orderArrayList[3].add(mApplication.orderArrayList[0].get(i));
//                                            break;
//                                        case "40":
//                                            if (jsonObject.getString("evaluation_state").equals("0")) {
//                                                mApplication.orderArrayList[4].add(mApplication.orderArrayList[0].get(i));
//                                            } else {
//                                                mApplication.orderArrayList[5].add(mApplication.orderArrayList[0].get(i));
//                                            }
//                                            break;
//                                    }
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                }
//                            }
//                            setControl();
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

	//设置控件状态
	private void setControl() {

		if (orderArrayList.size() == 0) {
			mTextView.setVisibility(View.VISIBLE);
			mTextView.setText("暂无订单");
		} else {
			mTextView.setVisibility(View.GONE);
		}


		mSwipeRefreshLayout.setRefreshing(false);
		mAdapter.notifyDataSetChanged();

	}

	//读取JSON数据失败
	private void getJsonFailure() {


		mTextView.setText("订单数据加载失败\n\n点击重试");


	}

	//返回&销毁Activity
	private void returnActivity() {

		mApplication.finishActivity(mActivity);

	}

}
