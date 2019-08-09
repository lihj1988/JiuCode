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
import com.jiuwang.buyer.adapter.ProjectDetailsAdapter;
import com.jiuwang.buyer.base.BaseActivity;
import com.jiuwang.buyer.bean.ProjectBean;
import com.jiuwang.buyer.bean.ProjectDetailBean;
import com.jiuwang.buyer.constant.Constant;
import com.jiuwang.buyer.entity.ProjectDetailsEntity;
import com.jiuwang.buyer.net.HttpUtils;
import com.jiuwang.buyer.util.AppUtils;
import com.jiuwang.buyer.util.MyToastView;

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

public class MyProjectDetailsActivity extends BaseActivity {


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
	@Bind(R.id.tvProjectName)
	TextView tvProjectName;
	@Bind(R.id.tvReport)
	TextView tvReport;
	@Bind(R.id.tvSalePeice)
	TextView tvSalePeice;


	private List<ProjectDetailBean> projectList;
	private ProjectBean projectBean;
	private String id;
	private ProjectDetailsAdapter projectDetailsAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_project_deltail);
		ButterKnife.bind(this);

		AppUtils.initListView(MyProjectDetailsActivity.this, projectListView, false, false);
		projectList = new ArrayList<>();
		Intent intent = getIntent();
		projectBean = (ProjectBean) intent.getSerializableExtra("data");
		id = projectBean.getId();
		initView();
		initData();

	}

	private void initView() {
		setTopView(topView);
		actionbarText.setText("抢购详情");
		onclickLayoutLeft.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		onclickLayoutRight.setVisibility(View.INVISIBLE);
		tvProjectName.setText(projectBean.getProject_name());
		if(Constant.ISWIN.equals(projectBean.getIs_win())){
			tvReport.setText("已中奖");
			tvReport.setTextColor(getResources().getColor(R.color.red));
		}else {
			tvReport.setText("未中奖");
		}
		tvSalePeice.setText(projectBean.getSale_price());
	}

	private void initData() {

		HashMap<String, String> map = new HashMap<>();

		map.put("is_part", "1");
		map.put("id", id);
		map.put("act", "4");
		HttpUtils.isWin(map, new Consumer<ProjectDetailsEntity>() {
			@Override
			public void accept(ProjectDetailsEntity projectEntity) throws Exception {

				if (Constant.HTTP_SUCCESS_CODE.equals(projectEntity.getCode())) {
					projectList.addAll(projectEntity.getData());

				} else if (Constant.HTTP_LOGINOUTTIME_CODE.equals(projectEntity.getCode())) {

					Intent intent = new Intent(MyProjectDetailsActivity.this, LoginActivity.class);
					startActivity(intent);
					finish();
				}

				if (projectList != null && projectList.size() > 0) {
					setAdapter();
				}

			}
		}, new Consumer<Throwable>() {
			@Override
			public void accept(Throwable throwable) throws Exception {
				MyToastView.showToast("请求失败", MyProjectDetailsActivity.this);

			}
		});

	}

	public void setAdapter() {
		projectDetailsAdapter = new ProjectDetailsAdapter(MyProjectDetailsActivity.this, projectList);
		projectListView.setAdapter(projectDetailsAdapter);
	}


}
