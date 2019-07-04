package com.jiuwang.buyer.fragment;

import android.app.Fragment;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jiuwang.buyer.R;
import com.jiuwang.buyer.adapter.ProjectListAdapter;
import com.jiuwang.buyer.entity.ProjectBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.jiuwang.buyer.R.id.actionbar_text;


/**
 * Created by lihj on 2019/7/3
 * desc:
 */

public class ProjectFragment extends Fragment implements XRecyclerView.LoadingListener {

	@Bind(actionbar_text)
	TextView actionbarText;
	@Bind(R.id.return_img)
	ImageView returnImg;
	@Bind(R.id.onclick_layout_left)
	RelativeLayout onclickLayoutLeft;
	@Bind(R.id.onclick_layout_right)
	Button onclickLayoutRight;
	@Bind(R.id.iv_find)
	ImageView ivFind;
	@Bind(R.id.projectListView)
	XRecyclerView projectListView;
	private int page = 1;
	private List<ProjectBean> projectList;
	private ProjectListAdapter projectListAdapter;
	Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			if (projectListAdapter != null) {
				projectListAdapter.notifyDataSetChanged();
			} else {
				setAdapter();
			}

			if(page==1){
				projectListView.refreshComplete();
			}else {
				projectListView.loadMoreComplete();
			}


		}
	};
	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
		View view = View.inflate(getActivity(), R.layout.fragment_project_list, null);
		ButterKnife.bind(this, view);
		projectList = new ArrayList<>();
		initView();
		return view;
	}

	private void initView() {
		actionbarText.setText("限时抢购");
		onclickLayoutLeft.setVisibility(View.INVISIBLE);
		onclickLayoutRight.setVisibility(View.INVISIBLE);
		LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
		layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//		StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(1,
//				StaggeredGridLayoutManager.VERTICAL);
//		layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
		projectListView.setLayoutManager(layoutManager);
		Drawable dividerDrawable = ContextCompat.getDrawable(getActivity(), R.drawable.divider_sample);
		projectListView.addItemDecoration(projectListView.new DividerItemDecoration(dividerDrawable));
		projectListView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
		projectListView.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
		projectListView.setArrowImageView(R.drawable.iconfont_downgrey);

		projectListView.setLoadingMoreEnabled(true);
		projectListView.setPullRefreshEnabled(true);
		projectListView.setLoadingListener(this);
		projectListView.refresh();
		initData();
	}

	private void setAdapter() {
		projectListAdapter = new ProjectListAdapter(getActivity(), projectList);
		projectListView.setAdapter(projectListAdapter);
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		ButterKnife.unbind(this);
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

	private void initData() {
		if (page == 1) {
			projectList.clear();
		}
		for (int i = 0; i < (page-1) + 5; i++) {
			ProjectBean projectBean = new ProjectBean();
			projectBean.setNotes("deac" + page);
			projectBean.setEnd_time("2019-07-04 12:00:00");
			projectBean.setStart_time("2019-07-04 11:00:00");
			projectBean.setGoods_name("11111");
			projectBean.setPrice("256.00");
			projectBean.setPic_url("");
			projectList.add(projectBean);
		}
		handler.sendEmptyMessageDelayed(0,2000);


	}
}
