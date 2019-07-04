package com.jiuwang.buyer.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jiuwang.buyer.R;
import com.jiuwang.buyer.adapter.ProjectListAdapter;
import com.jiuwang.buyer.base.BaseActivity;
import com.jiuwang.buyer.entity.ProjectBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lihj on 2019/6/25
 * desc: 抢购页面
 */

public class ProjectListActivity extends BaseActivity implements XRecyclerView.LoadingListener{


	private XRecyclerView projectListView;
	private int page = 0;
	private List<ProjectBean> projectList;
	private ProjectListAdapter projectListAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_project_list);
		projectListView = findViewById(R.id.projectListView);
		LinearLayoutManager layoutManager = new LinearLayoutManager(this);
		layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
		projectListView.setLayoutManager(layoutManager);
		projectListView.setLoadingMoreEnabled(true);
		projectListView.setPullRefreshEnabled(true);
		projectList = new ArrayList<>();

		projectListAdapter = new ProjectListAdapter(this,projectList);

//		projectListAdapter.setOnItemClickListener(new AddressListAdapter.onItemClickListener() {
//			@Override
//			public void onItemClick(int position) {
//				//跳转商品详情
//
//			}
//		});
	}

//	刷新
	@Override
	public void onRefresh() {
		page = 0;

	}
//	加载
	@Override
	public void onLoadMore() {
		page++;


	}
}
