package com.jiuwang.buyer.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jiuwang.buyer.R;
import com.jiuwang.buyer.redpakge.CustomDialog;
import com.jiuwang.buyer.redpakge.OnRedPacketDialogClickListener;
import com.jiuwang.buyer.redpakge.RedPacketEntity;
import com.jiuwang.buyer.redpakge.RedPacketViewHolder;
import com.jiuwang.buyer.view.AutoScrollRecyclerView;
import com.jiuwang.buyer.view.NoticeRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.disposables.Disposable;

/**
 * author：lihj
 * desc：
 * Created by lihj on  2019/8/8 16:20.
 */

public class HomeFragment extends Fragment {
	@Bind(R.id.actionbar_text)
	TextView actionbarText;
	@Bind(R.id.onclick_layout_left)
	RelativeLayout onclickLayoutLeft;
	@Bind(R.id.onclick_layout_right)
	Button onclickLayoutRight;
	@Bind(R.id.ivRedPackage)
	ImageView ivRedPackage;
	@Bind(R.id.am_rv)
	AutoScrollRecyclerView amRv;
	private LinearSmoothScroller mScroller;
	private Disposable mAutoTask;
	private View mRedPacketDialogView;
	private RedPacketViewHolder mRedPacketViewHolder;
	private CustomDialog mRedPacketDialog;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
		View view = View.inflate(getActivity(), R.layout.fragment_home, null);
		ButterKnife.bind(this, view);
		initView();
		initData();
		return view;
	}

	private void initData() {
		List<String> data = new ArrayList<>();

		data.add("恭喜15******891参与抢购项目中的茅台 立省260元");
		data.add("恭喜15******871提现100元");
		data.add("恭喜13******891参与抢购项目中的茅台 立省260元");
		data.add("恭喜18******841提现100元");
		data.add("恭喜15******865参与抢购项目中的茅台 立省260元");
		data.add("恭喜15******451提现100元");
		data.add("恭喜15******878参与抢购项目中的茅台 立省260元");
		data.add("恭喜15******236提现200元");
		data.add("恭喜15******896提现100元");
		data.add("恭喜15******675提现1136元");


		NoticeRecyclerViewAdapter adapter = new NoticeRecyclerViewAdapter(data);
		amRv.setLayoutManager(new LinearLayoutManager(getActivity()));
		amRv.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
		amRv.setAdapter(adapter);
		//item滚动步骤1：自定义LinearSmoothScroller，重写方法，滚动item至顶部，控制滚动速度
		mScroller = new LinearSmoothScroller(getActivity()) {

			//将移动的置顶显示
			@Override
			protected int getVerticalSnapPreference() {
				return LinearSmoothScroller.SNAP_TO_START;
			}

			//控制速度，这里注意当速度过慢的时候可能会形成流式的效果，因为这里是代表移动像素的速度，
			// 当定时器中每隔的2秒之内正好或者还未移动一个item的高度的时候会出现，前一个还没移动完成又继续移动下一个了，就形成了流滚动的效果了
			// 这个问题后续可通过重写另外一个方法来进行控制，暂时就先这样了
			@Override
			protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
				return 3f / displayMetrics.density;
			}
		};
		amRv.start();
	}

	public void initView() {
		actionbarText.setText("首页");
		onclickLayoutLeft.setVisibility(View.INVISIBLE);
		onclickLayoutRight.setVisibility(View.INVISIBLE);
	}

	public void intDatas() {
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		ButterKnife.unbind(this);
	}

	@OnClick(R.id.ivRedPackage)
	public void onViewClicked() {
		showDialog();
	}


	public void showDialog() {
		RedPacketEntity entity = new RedPacketEntity("剩余抽奖次数：10次", "http://upload.51qianmai.com/20171205180511192.png", "");
		showRedPacketDialog(entity);
	}

	public void showRedPacketDialog(RedPacketEntity entity) {
		if (mRedPacketDialogView == null) {
			mRedPacketDialogView = View.inflate(getActivity(), R.layout.dialog_red_packet, null);
			mRedPacketViewHolder = new RedPacketViewHolder(getActivity(), mRedPacketDialogView);
			mRedPacketDialog = new CustomDialog(getActivity(), mRedPacketDialogView, R.style.custom_dialog);
			mRedPacketDialog.setCancelable(false);
		}

		mRedPacketViewHolder.setData(entity);
		mRedPacketViewHolder.setOnRedPacketDialogClickListener(new OnRedPacketDialogClickListener() {
			@Override
			public void onCloseClick() {
				mRedPacketDialog.dismiss();
			}

			@Override
			public void onOpenClick() {
				//领取红包,调用接口

			}
		});

		mRedPacketDialog.show();
	}

}
