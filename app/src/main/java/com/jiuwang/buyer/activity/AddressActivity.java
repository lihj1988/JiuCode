package com.jiuwang.buyer.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jiuwang.buyer.R;
import com.jiuwang.buyer.adapter.AddressListAdapter;
import com.jiuwang.buyer.appinterface.DialogClickInterface;
import com.jiuwang.buyer.base.BaseActivity;
import com.jiuwang.buyer.base.MyApplication;
import com.jiuwang.buyer.bean.AddressBean;
import com.jiuwang.buyer.constant.Constant;
import com.jiuwang.buyer.entity.AddressEntity;
import com.jiuwang.buyer.entity.BaseResultEntity;
import com.jiuwang.buyer.net.HttpUtils;
import com.jiuwang.buyer.util.AppUtils;
import com.jiuwang.buyer.util.DialogUtil;
import com.jiuwang.buyer.util.LogUtils;
import com.jiuwang.buyer.util.MyToastView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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

	private static final String TAG = AddressActivity.class.getName();

	@Bind(R.id.addressListView)
	XRecyclerView addressListView;
	@Bind(R.id.stateTextView)
	TextView stateTextView;
	@Bind(R.id.tipsTextView)
	TextView tipsTextView;
	@Bind(R.id.topView)
	LinearLayout topView;
	@Bind(R.id.actionbar_text)
	TextView actionbarText;
	@Bind(R.id.onclick_layout_left)
	RelativeLayout onclickLayoutLeft;
	@Bind(R.id.onclick_layout_right)
	Button onclickLayoutRight;
	private Activity mActivity;
	private MyApplication mApplication;

	private String modelString;

	private List<AddressBean> mArrayList;
	private AddressListAdapter addressListAdapter;
	private int page = 1;
	private AddressBroadCast addressBroadCast;
	private FinishBroadCast finishBroadCast;
	private String type;

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
		EventBus.getDefault().register(this);
		initView();
		initData();
		addressBroadCast = new AddressBroadCast();

		IntentFilter filter = new IntentFilter();
		filter.addAction("refreshAddress");
		registerReceiver(addressBroadCast, filter);

		finishBroadCast = new FinishBroadCast();
		IntentFilter filterFinish = new IntentFilter();
		filterFinish.addAction("addressFinish");
		registerReceiver(finishBroadCast, filter);
	}

	private void initView() {
		setTopView(topView);
		mActivity = this;
		mApplication = (MyApplication) getApplication();

		modelString = mActivity.getIntent().getStringExtra("model");
		type = mActivity.getIntent().getStringExtra("type");

		actionbarText.setText("收货地址");
		onclickLayoutRight.setText("添加地址");
		mArrayList = new ArrayList<>();
		AppUtils.initListView(AddressActivity.this, addressListView, true, false);
		Drawable dividerDrawable = ContextCompat.getDrawable(AddressActivity.this, R.drawable.divider_sample);
		addressListView.addItemDecoration(addressListView.new DividerItemDecoration(dividerDrawable));
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
				} else if (Constant.HTTP_LOGINOUTTIME_CODE.equals(addressEntity.getCode())) {
					MyToastView.showToast(addressEntity.getMsg(), AddressActivity.this);
					Intent intent = new Intent(AddressActivity.this, LoginActivity.class);
					startActivity(intent);
					finish();
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

		addressListAdapter = new AddressListAdapter(AddressActivity.this,mArrayList,type);
		addressListView.setAdapter(addressListAdapter);
		addressListAdapter.setOnItemClickListener(new AddressListAdapter.onItemClickListener() {
			@Override
			public void onItemClick(int position) {
				LogUtils.e(TAG, "onItemClick--" + position + "");
				if ("choose".equals(modelString)) {
					LogUtils.e(TAG, "选择地址");
					Intent intent = new Intent();
					intent.putExtra("address", mArrayList.get(position - 1));
					setResult(RESULT_OK, intent);
//					onActivityResult(MyApplication.CODE_CHOOSE_ADDRESS,RESULT_OK,intent);
					finish();
				} else {
					Intent intent = new Intent();
					intent.putExtra("address", mArrayList.get(position - 1));
//					setResult(RESULT_OK,intent);
				}
			}
		});
		addressListAdapter.setOnItemLongClickListener(new AddressListAdapter.onItemLongClickListener() {
			@Override
			public void onItemLongClick(final int position) {
				LogUtils.e(TAG, "onItemLongClick--" + position + "");

				AppUtils.showNormalDialog(AddressActivity.this, "确认您的选择", "删除收货地址", "取消", "确定", new DialogClickInterface() {
					@Override
					public void nagtiveOnClick() {


					}

					@Override
					public void onClick() {
						HashMap<String, String> hashMap = new HashMap<>();
						hashMap.put("act", Constant.ACTION_ACT_DELETE);
						hashMap.put("id", mArrayList.get(position - 1).getId());
						HttpUtils.addressInfo(hashMap, new Consumer<BaseResultEntity>() {
							@Override
							public void accept(BaseResultEntity baseResultEntity) throws Exception {

							}
						}, new Consumer<Throwable>() {
							@Override
							public void accept(Throwable throwable) throws Exception {

							}
						});
					}
				});

			}
		});
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

	@OnClick({R.id.onclick_layout_left, R.id.onclick_layout_right})
	public void onViewClicked(View view) {
		switch (view.getId()) {
			case R.id.onclick_layout_left:
				returnActivity();
				break;
			case R.id.onclick_layout_right:
				Intent intent = new Intent(AddressActivity.this, AddressAddActivity.class);
				intent.putExtra("mode", "add");
				if (type != null) {
					intent.putExtra("type", type);
				}
				startActivity(intent);
				break;
		}
	}

	@Override
	public void onRefresh() {
		page = 1;
		initData();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(addressBroadCast);
		unregisterReceiver(finishBroadCast);
		EventBus.getDefault().unregister(this);
	}

	@Override
	public void onLoadMore() {
		page++;
	}


	class AddressBroadCast extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			initData();
		}
	}

	class FinishBroadCast extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			AddressActivity.this.finish();
		}
	}

	@Subscribe(threadMode = ThreadMode.MAIN)
	public void  exit(String order){
		if("addressFinish".equals(order)){
			AddressActivity.this.finish();
		}
	}
}