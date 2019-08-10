package com.jiuwang.buyer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jiuwang.buyer.R;
import com.jiuwang.buyer.adapter.InviteManagerAdapter;
import com.jiuwang.buyer.base.BaseActivity;
import com.jiuwang.buyer.bean.InviteBean;
import com.jiuwang.buyer.bean.InviteUserBean;
import com.jiuwang.buyer.constant.Constant;
import com.jiuwang.buyer.entity.InviteEntity;
import com.jiuwang.buyer.entity.InviteUserEntity;
import com.jiuwang.buyer.net.HttpUtils;
import com.jiuwang.buyer.util.CommonUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.reactivex.functions.Consumer;

/**
 * Created by lihj on 2019/7/23
 * desc:邀请人管理
 */

public class InviteManagerActivity extends BaseActivity {
	@Bind(R.id.topView)
	LinearLayout topView;
	@Bind(R.id.actionbar_text)
	TextView actionbarText;
	@Bind(R.id.onclick_layout_left)
	RelativeLayout onclickLayoutLeft;
	@Bind(R.id.onclick_layout_right)
	Button onclickLayoutRight;

	@Bind(R.id.tvTotalCount)
	TextView tvTotalCount;
	@Bind(R.id.tvProjectCount)
	TextView tvProjectCount;
	@Bind(R.id.listView)
	XRecyclerView listView;
	private int page = 1;
	private List<InviteBean> inviteBeanList;
	private List<InviteUserBean> inviteUserBeanList;
	private InviteManagerAdapter inviteManagerAdapter;
	private HashMap<String, String> hashMap;
	private InviteBean inviteBean;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_invitemanager);
		ButterKnife.bind(this);
		inviteBeanList = new ArrayList<>();
		hashMap = new HashMap<>();
		inviteUserBeanList = new ArrayList<>();
		initView();
		initData();
		initInviteData();

	}

	private void initData() {
		if (CommonUtil.getNetworkRequest(InviteManagerActivity.this)) {
			hashMap.put("act", "total");
			HttpUtils.invite(hashMap, new Consumer<InviteEntity>() {
				@Override
				public void accept(InviteEntity inviteEntity) throws Exception {
					if (Constant.HTTP_SUCCESS_CODE.equals(inviteEntity.getCode())) {
						if (inviteEntity.getData().size() > 0) {
							inviteBean = inviteEntity.getData().get(0);
							tvTotalCount.setText(inviteBean.getTotal_count());
							tvProjectCount.setText(inviteBean.getProject_count());
						} else {
							tvTotalCount.setText("0");
							tvProjectCount.setText("0");
						}
					} else if (Constant.HTTP_LOGINOUTTIME_CODE.equals(inviteEntity.getCode())) {
						startActivity(new Intent(InviteManagerActivity.this, LoginActivity.class));
						finish();
					}
				}
			}, new Consumer<Throwable>() {
				@Override
				public void accept(Throwable throwable) throws Exception {
					tvTotalCount.setText("0");
					tvProjectCount.setText("0");
				}
			});
		}
	}

	private void initInviteData() {
		if (CommonUtil.getNetworkRequest(InviteManagerActivity.this)) {
//			hashMap.put("act", "total");
			HttpUtils.inviteUser(new HashMap<String, String>(), new Consumer<InviteUserEntity>() {
				@Override
				public void accept(InviteUserEntity inviteEntity) throws Exception {
					if (Constant.HTTP_SUCCESS_CODE.equals(inviteEntity.getCode())) {
						if (inviteEntity.getData().size() > 0) {
							inviteUserBeanList.addAll(inviteEntity.getData());
						}
						setAdapter();
					} else if (Constant.HTTP_LOGINOUTTIME_CODE.equals(inviteEntity.getCode())) {
						startActivity(new Intent(InviteManagerActivity.this, LoginActivity.class));
						finish();
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
		inviteManagerAdapter = new InviteManagerAdapter(InviteManagerActivity.this, inviteUserBeanList);
		listView.setAdapter(inviteManagerAdapter);
	}

	private void initView() {
		setTopView(topView);
		actionbarText.setText("邀请人管理");
		onclickLayoutRight.setVisibility(View.INVISIBLE);

		onclickLayoutLeft.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

}
