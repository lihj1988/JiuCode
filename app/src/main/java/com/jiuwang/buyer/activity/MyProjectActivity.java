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
import com.jiuwang.buyer.adapter.MyProjectListAdapter;
import com.jiuwang.buyer.base.BaseActivity;
import com.jiuwang.buyer.bean.ProjectBean;
import com.jiuwang.buyer.constant.Constant;
import com.jiuwang.buyer.entity.BaseEntity;
import com.jiuwang.buyer.entity.LoginEntity;
import com.jiuwang.buyer.entity.ProjectEntity;
import com.jiuwang.buyer.net.HttpUtils;
import com.jiuwang.buyer.util.AppUtils;
import com.jiuwang.buyer.util.CommonUtil;
import com.jiuwang.buyer.util.MyToastView;
import com.jiuwang.buyer.util.PreforenceUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.reactivex.functions.Consumer;

/**
 * Created by lihj on 2019/6/25
 * desc: 我的历史抢购页面
 */

public class MyProjectActivity extends BaseActivity implements XRecyclerView.LoadingListener {


	@Bind(R.id.actionbar_text)
	TextView actionbarText;
	@Bind(R.id.onclick_layout_left)
	RelativeLayout onclickLayoutLeft;
	@Bind(R.id.onclick_layout_right)
	Button onclickLayoutRight;
	@Bind(R.id.projectListView)
	XRecyclerView projectListView;
	@Bind(R.id.topView)
	LinearLayout topView;
	private int page = 1;
	private List<ProjectBean> projectList;
	private MyProjectListAdapter myProjectListAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_project_list);
		ButterKnife.bind(this);

		projectList = new ArrayList<>();
		initView();
		initData();
	}

	private void initView() {
		actionbarText.setText("我的抢购");
		onclickLayoutLeft.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		onclickLayoutRight.setVisibility(View.INVISIBLE);
		setTopView(topView);
		AppUtils.initListView(MyProjectActivity.this, projectListView, true, true);
		Drawable dividerDrawable = ContextCompat.getDrawable(this, R.drawable.divider_sample);
		projectListView.addItemDecoration(projectListView.new DividerItemDecoration(dividerDrawable));
		projectListView.setLoadingListener(this);
	}

	private void initData() {

		HashMap<String, String> map = new HashMap<>();
		map.put("currPage", String.valueOf(page));
		map.put("pageSize", Constant.PAGESIZE);
		map.put("is_part", "1");
		map.put("status", "2");
		HttpUtils.selectProjectList(map, new Consumer<ProjectEntity>() {
			@Override
			public void accept(ProjectEntity projectEntity) throws Exception {
				if (page == 1) {
					projectList.clear();
				}
				if (Constant.HTTP_SUCCESS_CODE.equals(projectEntity.getCode())) {
					projectList.addAll(projectEntity.getData());

				} else if (Constant.HTTP_LOGINOUTTIME_CODE.equals(projectEntity.getCode())) {

					CommonUtil.reLogin(PreforenceUtils.getStringData("loginInfo", "userID"), PreforenceUtils.getStringData("loginInfo", "password"), new CommonUtil.LoginCallBack() {
						@Override
						public void callBack(BaseEntity<LoginEntity> loginEntity) {
							initData();
						}

						@Override
						public void failCallBack(Throwable throwable) {

						}
					});
				}

				if (projectList != null && projectList.size() > 0) {
					if (myProjectListAdapter != null) {
						myProjectListAdapter.notifyDataSetChanged();
					} else {
						setAdapter();
					}
				}
				if (page == 1) {
					projectListView.refreshComplete();
				} else {
					projectListView.loadMoreComplete();
				}
			}
		}, new Consumer<Throwable>() {
			@Override
			public void accept(Throwable throwable) throws Exception {
				MyToastView.showToast("请求失败", MyProjectActivity.this);

			}
		});

	}

	public void setAdapter() {
		//跳转详情页面
		myProjectListAdapter = new MyProjectListAdapter(MyProjectActivity.this, projectList, new MyProjectListAdapter.ProjectItemOnClickListener() {
			@Override
			public void itemOnClick(int position) {
				//跳转详情页面
				Intent intent = new Intent();
				intent.setClass(MyProjectActivity.this, MyProjectDetailsActivity.class);
				intent.putExtra("data", projectList.get(position));
				startActivity(intent);

			}
		});
		projectListView.setAdapter(myProjectListAdapter);

	}

	//	刷新
	@Override
	public void onRefresh() {
		page = 1;
		initData();
	}

	//	加载
	@Override
	public void onLoadMore() {
		page++;
		initData();
	}
}
