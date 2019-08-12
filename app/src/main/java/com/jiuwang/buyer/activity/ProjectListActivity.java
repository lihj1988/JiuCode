package com.jiuwang.buyer.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jiuwang.buyer.R;
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

import io.reactivex.functions.Consumer;

/**
 * Created by lihj on 2019/6/25
 * desc: 抢购页面
 */

public class ProjectListActivity extends BaseActivity implements XRecyclerView.LoadingListener{


	private XRecyclerView projectListView;
	private int page = 0;
	private List<ProjectBean> projectList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_project_list);
		projectListView = findViewById(R.id.projectListView);
		AppUtils.initListView(ProjectListActivity.this,projectListView,true,true);
		projectList = new ArrayList<>();
		initData();
	}

	private void initData() {

		HashMap<String, String> map = new HashMap<>();
		map.put("currPage", String.valueOf(page));
		map.put("pageSize", Constant.PAGESIZE);
		map.put("act","");
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
				new Handler(){
					@Override
					public void handleMessage(Message msg) {

						if (projectList != null && projectList.size() > 0) {

						} else {

						}
					}
				}.sendEmptyMessageDelayed(0,500);
			}
		}, new Consumer<Throwable>() {
			@Override
			public void accept(Throwable throwable) throws Exception {
				MyToastView.showToast("请求失败", ProjectListActivity.this);

			}
		});

	}


	//	刷新
	@Override
	public void onRefresh() {
		page = 0;
		initData();
	}
//	加载
	@Override
	public void onLoadMore() {
		page++;
		initData();
	}
}
