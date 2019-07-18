package com.jiuwang.buyer.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jiuwang.buyer.R;
import com.jiuwang.buyer.adapter.BalanceListAdapter;
import com.jiuwang.buyer.base.BaseActivity;
import com.jiuwang.buyer.bean.BalanceBean;
import com.jiuwang.buyer.constant.Constant;
import com.jiuwang.buyer.entity.BalanceEntity;
import com.jiuwang.buyer.entity.BaseEntity;
import com.jiuwang.buyer.entity.LoginEntity;
import com.jiuwang.buyer.net.HttpUtils;
import com.jiuwang.buyer.util.AppUtils;
import com.jiuwang.buyer.util.CommonUtil;
import com.jiuwang.buyer.util.LogUtils;
import com.jiuwang.buyer.util.PreforenceUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;

/**
 * Created by lihj on 2019/7/18
 * desc:
 */

public class BalanceListActivity extends BaseActivity implements XRecyclerView.LoadingListener {
	private static final String TAG = BalanceListActivity.class.getName();
	@Bind(R.id.topView)
	LinearLayout topView;
	@Bind(R.id.actionbar_text)
	TextView actionbarText;
	@Bind(R.id.onclick_layout_left)
	RelativeLayout onclickLayoutLeft;
	@Bind(R.id.onclick_layout_right)
	Button onclickLayoutRight;
	@Bind(R.id.xrvBalabceDetails)
	XRecyclerView xrvBalabceDetails;
	private int page = 1;
	private List<BalanceBean> balanceList;
	private BalanceListAdapter balanceListAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_balance_list);
		ButterKnife.bind(this);
		balanceList = new ArrayList<>();
		initView();
		initData();

	}

	private void initData() {

		if (CommonUtil.getNetworkRequest(BalanceListActivity.this)) {
			HashMap<String, String> hashMap = new HashMap<>();
			hashMap.put("currPage", String.valueOf(page));
			hashMap.put("pageSize", Constant.PAGESIZE);
			HttpUtils.fund(hashMap, new Consumer<BalanceEntity>() {
				@Override
				public void accept(BalanceEntity balanceEntity) throws Exception {
					if (Constant.HTTP_SUCCESS_CODE.equals(balanceEntity.getCode())) {

						if (page == 1) {
							balanceList.clear();
						}
						if (balanceEntity.getData() != null) {

							balanceList.addAll(balanceEntity.getData());
						}


						if (balanceListAdapter != null) {
							balanceListAdapter.notifyDataSetChanged();
						} else {
							setAdapter();
						}
						if (page == 1) {
							xrvBalabceDetails.refreshComplete();
						} else {
							xrvBalabceDetails.loadMoreComplete();
						}

					}
					if (Constant.HTTP_LOGINOUTTIME_CODE.equals(balanceEntity.getCode())) {
						CommonUtil.login(PreforenceUtils.getStringData("loginInfo", "userID"), PreforenceUtils.getStringData("loginInfo", "password"), new CommonUtil.LoginCallBack() {
							@Override
							public void callBack(BaseEntity<LoginEntity> loginEntity) {

							}

							@Override
							public void failCallBack(Throwable throwable) {

							}
						});
					} else {

					}
				}
			}, new Consumer<Throwable>() {
				@Override
				public void accept(Throwable throwable) throws Exception {

				}
			});
		}

	}

	private void setAdapter() {
		balanceListAdapter = new BalanceListAdapter(BalanceListActivity.this, balanceList, new BalanceListAdapter.ItemOnClickListener() {
			@Override
			public void itemOnClick(int position) {
				LogUtils.e(TAG,position+"");
				Intent intent = new Intent();
				intent.setClass(BalanceListActivity.this,BalanceDetailsActivity.class);
				intent.putExtra("data",balanceList.get(position));
				startActivity(intent);
			}
		});
		xrvBalabceDetails.setAdapter(balanceListAdapter);
	}

	private void initView() {
		setTopView(topView);
		actionbarText.setText("明细");
		onclickLayoutRight.setVisibility(View.INVISIBLE);
		AppUtils.initListView(BalanceListActivity.this, xrvBalabceDetails, true, true);
		Drawable dividerDrawable = ContextCompat.getDrawable(BalanceListActivity.this, R.drawable.divider_sample_low);
		xrvBalabceDetails.addItemDecoration(xrvBalabceDetails.new DividerItemDecoration(dividerDrawable));
		xrvBalabceDetails.setLoadingListener(this);
		xrvBalabceDetails.refresh();
	}

	@OnClick(R.id.onclick_layout_left)
	public void onViewClicked() {
		finish();
	}

	@Override
	public void onRefresh() {
		page = 1;

		initData();
	}

	@Override
	public void onLoadMore() {
		page++;
		initData();
	}
}
