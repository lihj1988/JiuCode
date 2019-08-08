package com.jiuwang.buyer.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
	private View mRedPacketDialogView;
	private RedPacketViewHolder mRedPacketViewHolder;
	private CustomDialog mRedPacketDialog;
	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
		View view = View.inflate(getActivity(), R.layout.fragment_home, null);
		ButterKnife.bind(this, view);
		initView();
		return view;
	}

	public void initView() {
		actionbarText.setText("首页");
		onclickLayoutLeft.setVisibility(View.INVISIBLE);
		onclickLayoutRight.setVisibility(View.INVISIBLE);
	}public void intDatas() {
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



	public void showDialog(){
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
