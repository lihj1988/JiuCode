package com.jiuwang.buyer.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jiuwang.buyer.R;
import com.jiuwang.buyer.adapter.AddressListAdapter;
import com.jiuwang.buyer.base.BaseActivity;
import com.jiuwang.buyer.base.MyApplication;
import com.jiuwang.buyer.bean.AddressBean;
import com.jiuwang.buyer.constant.Constant;
import com.jiuwang.buyer.entity.AddressEntity;
import com.jiuwang.buyer.net.HttpUtils;
import com.jiuwang.buyer.util.AppUtils;
import com.jiuwang.buyer.util.DialogUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;


/**
 * 作者:lihj
 * 作用：我的收货地址
 */

public class AddressActivity extends BaseActivity implements XRecyclerView.LoadingListener {

	@Bind(R.id.backImageView)
	ImageView backImageView;
	@Bind(R.id.titleTextView)
	TextView titleTextView;
	@Bind(R.id.moreImageView)
	ImageView moreImageView;
	@Bind(R.id.addressListView)
	XRecyclerView addressListView;
	@Bind(R.id.stateTextView)
	TextView stateTextView;
	@Bind(R.id.tipsTextView)
	TextView tipsTextView;
	@Bind(R.id.topView)
	LinearLayout topView;
	private Activity mActivity;
	private MyApplication mApplication;

	private String modelString;

	private List<AddressBean> mArrayList;
	private AddressListAdapter addressListAdapter;
	private int page = 1;

	@Override
	protected void onActivityResult(int req, int res, Intent data) {
		super.onActivityResult(req, res, data);
		if (res == RESULT_OK) {
			switch (req) {
				case MyApplication.CODE_ADDRESS_ADD:
//					getJson();
					break;
				case MyApplication.CODE_ADDRESS_EDIT:
//					getJson();
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
		setContentView(R.layout.activity_address);
		ButterKnife.bind(this);
		initView();
		initData();
	}

	private void initView() {
		setTopView(topView);
		mActivity = this;
		mApplication = (MyApplication) getApplication();

		modelString = mActivity.getIntent().getStringExtra("model");

		titleTextView.setText("收货地址");
		moreImageView.setImageResource(R.drawable.ic_action_add);
		mArrayList = new ArrayList<>();
		AppUtils.initListView(AddressActivity.this, addressListView, true, false);
		addressListAdapter = new AddressListAdapter(mArrayList);
		addressListView.setAdapter(addressListAdapter);
		addressListView.setLoadingListener(this);
		addressListView.refresh();
	}

	//初始化数据
	private void initData() {
		HashMap<String, String> map = new HashMap<>();
		map.put("act", "");
		HttpUtils.selectAddressList(map, new Consumer<AddressEntity>() {
			@Override
			public void accept(AddressEntity addressEntity) throws Exception {
				if (page == 1) {
					mArrayList.clear();
				}
				if (Constant.HTTP_SUCCESS_CODE.equals(addressEntity.getCode())) {

					mArrayList.addAll(addressEntity.getData());
					if (addressListAdapter != null) {
						addressListAdapter.notifyDataSetChanged();
					} else {
						setAdapter();
					}
					if (page == 1) {
						addressListView.refreshComplete();
					} else {
						addressListView.loadMoreComplete();
					}
					tipsTextView.setVisibility(View.GONE);
				}
			}
		}, new Consumer<Throwable>() {
			@Override
			public void accept(Throwable throwable) throws Exception {
				getJsonFailure();
			}
		});
	}

	public void setAdapter() {


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

		if ("choose".equals(modelString)) {
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

	@OnClick({R.id.backImageView, R.id.tipsTextView})
	public void onViewClicked(View view) {
		switch (view.getId()) {
			case R.id.backImageView:
				returnActivity();
				break;
			case R.id.tipsTextView:
				break;
		}
	}

	@Override
	public void onRefresh() {
		page = 1;
		initData();
	}

	@Override
	public void onLoadMore() {
		page++;
	}
}