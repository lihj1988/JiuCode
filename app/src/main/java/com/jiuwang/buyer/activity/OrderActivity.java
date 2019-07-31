package com.jiuwang.buyer.activity;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jiuwang.buyer.R;
import com.jiuwang.buyer.adapter.OrderListAdapter;
import com.jiuwang.buyer.base.BaseActivity;
import com.jiuwang.buyer.base.MyApplication;
import com.jiuwang.buyer.bean.OrderBean;
import com.jiuwang.buyer.fragment.OrderChildFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.support.design.widget.TabLayout.MODE_FIXED;
import static android.support.design.widget.TabLayout.OnTabSelectedListener;
import static android.support.design.widget.TabLayout.Tab;



/*
* 作用：我的实物订单
*/

public class OrderActivity extends BaseActivity {

	private static final String TAG = OrderActivity.class.getName();
	@Bind(R.id.mainTabLayout)
	TabLayout mTabLayout;

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
	ImageView ivFind;@Bind(R.id.main_viewpager)
	ViewPager main_viewpager;
	private Activity mActivity;
	private MyApplication mApplication;
	private FragmentManager mFragmentManager;
	private boolean firstBoolean;
	private TextView mTextView;
	private OrderListAdapter mAdapter;
	private String tag = "0";
	//公用变量
	public List<OrderBean> orderArrayList; //订单数组
	private int page = 1;
	private int position = 0;
	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			Bundle data = msg.getData();
			int position = data.getInt("position");
			if (orderArrayList != null) {
				orderArrayList.clear();
			} else {
				orderArrayList = new ArrayList<>();
			}
//			selectOrder(position);
		}
	};
	private MyReceiver myReceiver;
	private List<OrderChildFragment> fragmentList;

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
//		getJson(tag);
	}

	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		MyApplication.currentActivity = this;
		mFragmentManager = getFragmentManager();
		setContentView(R.layout.activity_order);
		ButterKnife.bind(this);
		initView();
		initData();
		initEven();
		myReceiver = new MyReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction("finish");
		registerReceiver(myReceiver, filter);

	}

	//初始化控件
	private void initView() {
		setTopView(topView);
		onclickLayoutRight.setVisibility(View.INVISIBLE);
//预加载

	}

	//初始化数据
	private void initData() {
		orderArrayList = new ArrayList<>();
		mActivity = this;
		mApplication = (MyApplication) getApplication();
		firstBoolean = true;
		actionbarText.setText("我的订单");

		final List<String> mTitleList = new ArrayList<>();
		mTitleList.add("全部");
		mTitleList.add("待支付");
		mTitleList.add("已支付");
		mTitleList.add("已完成");
		fragmentList = new ArrayList<>();
		for (int i = 0; i < mTitleList.size(); i++) {
			//添加tab
			mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(i)));
			OrderChildFragment orderChildFragment = new OrderChildFragment();
			Bundle bundle = new Bundle();
			bundle.putInt("position", i);
			orderChildFragment.setArguments(bundle);
			fragmentList.add(orderChildFragment);
		}
		mTabLayout.setTabMode(MODE_FIXED);
		//根据传进来的值设置位置
		position = mActivity.getIntent().getIntExtra("position", 0);

		main_viewpager.setOffscreenPageLimit(fragmentList.size());
		main_viewpager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
			//选中的item
			@Override
			public Fragment getItem(int position) {
				return fragmentList.get(position);
			}

			@Override
			public int getCount() {
				return fragmentList.size();
			}

			@Override
			public CharSequence getPageTitle(int position) {
				return mTitleList.get(position);
			}
		});
		//将TabLayout与Viewager绑定
		mTabLayout.setupWithViewPager(main_viewpager);
		mTabLayout.getTabAt(position).select();
	}

	//初始化事件
	private void initEven() {


		mTabLayout.addOnTabSelectedListener(new OnTabSelectedListener() {
			@Override
			public void onTabSelected(Tab tab) {


			}

			@Override
			public void onTabUnselected(Tab tab) {

			}

			@Override
			public void onTabReselected(Tab tab) {

			}
		});

//

	}

	//返回&销毁Activity
	private void returnActivity() {

		mApplication.finishActivity(mActivity);

	}



	@OnClick(R.id.onclick_layout_left)
	public void onViewClicked() {
		returnActivity();
	}


	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(myReceiver);
	}

	class MyReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			finish();
		}
	}

}
