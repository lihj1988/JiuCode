package com.jiuwang.buyer.fragment;

import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Gravity;
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
import com.jiuwang.buyer.activity.LoginActivity;
import com.jiuwang.buyer.adapter.ProjectListAdapter;
import com.jiuwang.buyer.base.MyApplication;
import com.jiuwang.buyer.bean.ProjectBean;
import com.jiuwang.buyer.constant.Constant;
import com.jiuwang.buyer.entity.ProjectDetailsEntity;
import com.jiuwang.buyer.entity.ProjectEntity;
import com.jiuwang.buyer.entity.SelectGoodsEntity;
import com.jiuwang.buyer.net.HttpUtils;
import com.jiuwang.buyer.popupwindow.ChooseItemPopupWindow;
import com.jiuwang.buyer.util.CommonUtil;
import com.jiuwang.buyer.util.LoadingDialog;
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
import io.reactivex.functions.Consumer;


/**
 * Created by lihj on 2019/7/3
 * desc:
 */

public class ProjectFragment extends Fragment implements XRecyclerView.LoadingListener {

	private static final String TAG = ProjectFragment.class.getName();
	@Bind(R.id.actionbar_text)
	TextView actionbarText;
	@Bind(R.id.stateTextView)
	TextView stateTextView;
	@Bind(R.id.return_img)
	ImageView returnImg;
	@Bind(R.id.onclick_layout_left)
	RelativeLayout onclickLayoutLeft;
	@Bind(R.id.onclick_layout_right)
	Button onclickLayoutRight;
	@Bind(R.id.iv_find)
	ImageView ivFind;
	@Bind(R.id.xRecyclerView)
	XRecyclerView projectListView;
	private int page = 1;
	private String is_part = "";
	private String id = "";
	private List<ProjectBean> projectList;
	private ProjectListAdapter projectListAdapter;

	private View rootView;
	private ProjectReceiver projectReceiver;
	private ChooseItemPopupWindow chooseItemPopupWindow;
	private LoadingDialog loadingDialog;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
		rootView = View.inflate(getActivity(), R.layout.fragment_project_list, null);
		ButterKnife.bind(this, rootView);
		if (!EventBus.getDefault().isRegistered(this)) {
			EventBus.getDefault().register(this);
		}
		projectList = new ArrayList<>();
		initView();
		projectReceiver = new ProjectReceiver();
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction("refreshProject");
		getActivity().registerReceiver(projectReceiver, intentFilter);
		loadingDialog = new LoadingDialog(getActivity());

//		Timer timer = new Timer();
//		TimerTask timerTask = new TimerTask() {
//			@Override
//			public void run() {
//				Intent intent = new Intent();
//				intent.setAction("com.jiuwang.buyer.receiver.NotificationReceiver");
//				getActivity().sendBroadcast(intent);
//			}
//		};
//		timer.schedule(timerTask,0,5000);
		return rootView;
	}

	private void initView() {
		actionbarText.setText("限时抢购");
		onclickLayoutLeft.setVisibility(View.INVISIBLE);
		onclickLayoutRight.setVisibility(View.INVISIBLE);
		LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
		layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
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
		stateTextView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				stateTextView.setText(getActivity().getString(R.string.loading));
				initData();
			}
		});
		initData();
	}

	private void setAdapter() {
		projectListAdapter = new ProjectListAdapter(getActivity(), projectList, new ProjectListAdapter.ProjectItemOnClickListener() {
			@Override
			public void itemOnClick(final int position) {
				LogUtils.e(TAG, "点击了第" + (position + 1) + "条");
				if (CommonUtil.getNetworkRequest(getActivity())) {
					if (chooseItemPopupWindow != null) {
						chooseItemPopupWindow.dismiss();
					}
					loadingDialog.show();
					HashMap<String, String> hashMap = new HashMap<>();
					hashMap.put("project_id", projectList.get(position).getProject_id());
					HttpUtils.selectChooseGoods(hashMap, new Consumer<SelectGoodsEntity>() {

						@Override
						public void accept(SelectGoodsEntity selectGoodsEntity) throws Exception {
							loadingDialog.dismiss();
							if (Constant.HTTP_SUCCESS_CODE.equals(selectGoodsEntity.getCode())) {
								if (selectGoodsEntity.getData() != null && selectGoodsEntity.getData().size() > 0) {

									chooseItemPopupWindow = new ChooseItemPopupWindow(MyApplication.currentActivity, projectList.get(position).getId(), selectGoodsEntity.getData());
									// 显示窗口
									chooseItemPopupWindow.showAtLocation(rootView, Gravity.BOTTOM
											| Gravity.CENTER_HORIZONTAL, 0, 0); // 设置layout在PopupWindow中显示的位置
								} else {
									MyToastView.showToast("没有可选择的商品", getActivity());
								}
							} else if (Constant.HTTP_LOGINOUTTIME_CODE.equals(selectGoodsEntity.getCode())) {
								MyToastView.showToast(selectGoodsEntity.getMsg(), getActivity());
								Intent intent = new Intent(getActivity(), LoginActivity.class);
								startActivity(intent);
								getActivity().finish();
							} else {
								MyToastView.showToast(selectGoodsEntity.getMsg(), getActivity());
							}

						}
					}, new Consumer<Throwable>() {
						@Override
						public void accept(Throwable throwable) throws Exception {
							loadingDialog.dismiss();
							MyToastView.showToast(getActivity().getString(R.string.msg_error), getActivity());
						}
					});
				}
			}
		});
		projectListView.setAdapter(projectListAdapter);

	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		ButterKnife.unbind(this);
		getActivity().unregisterReceiver(projectReceiver);
		EventBus.getDefault().unregister(this);
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

	public void initData() {
		HashMap<String, String> map = new HashMap<>();
		map.put("currPage", String.valueOf(page));
		map.put("pageSize", Constant.PAGESIZE);

//		if (!"".equals(is_part)) {
//			map.put("is_part", is_part);
//		}
//		if (!"".equals(id)) {
//			map.put("id", id);
//		}
		HttpUtils.selectProjectList(map, new Consumer<ProjectEntity>() {
			@Override
			public void accept(ProjectEntity projectEntity) throws Exception {
				if (page == 1) {
					projectList.clear();
				}
				if (Constant.HTTP_SUCCESS_CODE.equals(projectEntity.getCode())) {
					projectList.addAll(projectEntity.getData());

				} else if (Constant.HTTP_LOGINOUTTIME_CODE.equals(projectEntity.getCode())) {

					Intent intent = new Intent(getActivity(), LoginActivity.class);
					startActivity(intent);
					getActivity().finish();
				}
				new Handler() {
					@Override
					public void handleMessage(Message msg) {

						if (projectList != null && projectList.size() > 0) {
							stateTextView.setVisibility(View.GONE);
							if (projectListAdapter != null) {
								projectListAdapter.notifyDataSetChanged();
							} else {
								setAdapter();
							}
							if (page == 1) {
								projectListView.refreshComplete();
							} else {
								projectListView.loadMoreComplete();
							}
						} else {
							stateTextView.setVisibility(View.VISIBLE);
							stateTextView.setText(getActivity().getString(R.string.nothing));
						}
					}
				}.sendEmptyMessageDelayed(0, 500);
			}
		}, new Consumer<Throwable>() {
			@Override
			public void accept(Throwable throwable) throws Exception {
				MyToastView.showToast("请求失败", getActivity());
				stateTextView.setVisibility(View.VISIBLE);
				stateTextView.setText(getActivity().getString(R.string.load_fail));
			}
		});
	}

	class ProjectReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			onRefresh();
		}
	}

	@Subscribe(threadMode = ThreadMode.MAIN)
	public void isWinning(HashMap<String, String> map) {
		is_part = map.get("is_part");
		map.put("act", "4");
//		Intent intent = new Intent();
//		intent.setAction("com.jiuwang.buyer.receiver.NotificationReceiver");
//		getActivity().sendBroadcast(intent);
		//如果当前项目已报名 查询是否中奖
		///admin/project/aution_action.jsp?id=A2019080700002449&is_part=1&act=4
		if ("1".equals(is_part)) {
			HttpUtils.isWin(map, new Consumer<ProjectDetailsEntity>() {
				@Override
				public void accept(final ProjectDetailsEntity projectDetailsEntity) throws Exception {

					new Handler() {
						@Override
						public void handleMessage(Message msg) {
							if (Constant.HTTP_SUCCESS_CODE.equals(projectDetailsEntity.getCode())) {
								if (Constant.ISWIN.equals(projectDetailsEntity.getData().get(0).getIs_win())) {
									Intent intent = new Intent();
									intent.setAction("com.jiuwang.buyer.receiver.NotificationReceiver");
									getActivity().sendBroadcast(intent);
								}

							} else if (Constant.HTTP_LOGINOUTTIME_CODE.equals(projectDetailsEntity.getCode())) {

							}
						}
					}.sendEmptyMessageDelayed(0, 1000);
				}
			}, new Consumer<Throwable>() {
				@Override
				public void accept(Throwable throwable) throws Exception {

				}
			});
		}

	}

}
