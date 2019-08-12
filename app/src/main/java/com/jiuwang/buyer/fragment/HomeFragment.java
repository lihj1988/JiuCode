package com.jiuwang.buyer.fragment;

import android.app.Fragment;
import android.content.Context;
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

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.jiuwang.buyer.R;
import com.jiuwang.buyer.bean.AnnouncementBean;
import com.jiuwang.buyer.constant.Constant;
import com.jiuwang.buyer.entity.AnnouncementEntity;
import com.jiuwang.buyer.entity.BaseEntity;
import com.jiuwang.buyer.entity.LoginEntity;
import com.jiuwang.buyer.net.HttpUtils;
import com.jiuwang.buyer.redpakge.CustomDialog;
import com.jiuwang.buyer.redpakge.OnRedPacketDialogClickListener;
import com.jiuwang.buyer.redpakge.RedPacketEntity;
import com.jiuwang.buyer.redpakge.RedPacketViewHolder;
import com.jiuwang.buyer.util.CommonUtil;
import com.jiuwang.buyer.util.PreforenceUtils;
import com.jiuwang.buyer.view.ADInfo;
import com.jiuwang.buyer.view.AutoScrollRecyclerView;
import com.jiuwang.buyer.view.NoticeRecyclerViewAdapter;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * author：lihj
 * desc：
 * Created by lihj on  2019/8/8 16:20.
 */

public class HomeFragment extends Fragment {
	@Bind(R.id.actionbar_text)
	TextView actionbarText;
	@Bind(R.id.tvNotice)
	TextView tvNotice;
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
	private List<AnnouncementBean> announcementList;
	private NoticeRecyclerViewAdapter adapter;
	private List<String> data;
	int[] imageUrls_local = {R.drawable.play1,
			R.drawable.play2};
	private ArrayList<ADInfo> infos = new ArrayList<>();
	private ArrayList<Integer> localImages = new ArrayList<Integer>();

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
		View view = View.inflate(getActivity(), R.layout.fragment_home, null);
		ButterKnife.bind(this, view);
		initView();
		initData();
		for (int i = 0; i < imageUrls_local.length; i++) {
			ADInfo info = new ADInfo();
			//info.setUrl(imageUrls[i]);
			info.setId(imageUrls_local[i]);
			info.setContent("top-->" + i);
			infos.add(info);
		}
		ConvenientBanner imageCycleView = view.findViewById(R.id.icView);
//		imageCycleView.setImageResources(infos, mAdCycleViewListener);
//		imageCycleView.startImageCycle();


		//获取本地的图片
		for (int position = 0; position < 3; position++) {
			localImages.add(getResId("play" + (position + 1), R.drawable.class));
		}

		//开始自动翻页
		imageCycleView.setPages(new CBViewHolderCreator() {
			@Override
			public Object createHolder() {
				return new LocalImageHolderView();
			}
		}, localImages)
				//设置指示器是否可见
				.setPointViewVisible(true)
				//设置自动切换（同时设置了切换时间间隔）
				.startTurning(2000)
				//设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
				.setPageIndicator(new int[]{R.drawable.play_white, R.drawable.play_red})
				//设置指示器的方向（左、中、右）
				.setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
				//设置手动影响（设置了该项无法手动切换）
				.setManualPageable(true);

		return view;
	}

	private void initData() {
		announcementList = new ArrayList<>();
		data = new ArrayList<>();
		intDatas();


	}

	private void setAdapter() {
		adapter = new NoticeRecyclerViewAdapter(data);
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
				return 3 / displayMetrics.density;
			}


		};
		if (data != null) {
			startAuto();
		}

//		amRv.start();
	}


	public void initView() {
		actionbarText.setText("首页");
		onclickLayoutLeft.setVisibility(View.INVISIBLE);
		onclickLayoutRight.setVisibility(View.INVISIBLE);
//		tvNotice.setText("    " + getActivity().getResources().getString(R.string.notice));
	}

	public void intDatas() {

		HashMap<String, String> hashMap = new HashMap<>();
		HttpUtils.selectAnnouncement(hashMap, new Consumer<AnnouncementEntity>() {
			@Override
			public void accept(AnnouncementEntity announcementEntity) throws Exception {
				if (Constant.HTTP_SUCCESS_CODE.equals(announcementEntity.getCode())) {
					if (announcementEntity.getData().size() > 0) {
						announcementList.addAll(announcementEntity.getData());
					}
					String value = "";

					for (int i = 0; i < announcementList.size(); i++) {
						String newStr = "";
						if (announcementList.get(i).getUser_cd().length() == 11) {
							String replace = announcementList.get(i).getUser_cd().substring(3, 9);
							newStr = announcementList.get(i).getUser_cd().replace(replace, "******");
						} else {
							newStr = announcementList.get(i).getUser_cd();
						}

						if ("1".equals(announcementList.get(i).getAnnounce_type())) {
							value = "恭喜<font color=#FF5001 size=18px >" + "" + newStr + "" + "</font>在"+announcementList.get(i).getProject_name()+"中抢购成功";
//							value = "<font color=#ff6600 size=20px>积分:</font>";

						} else {
							value = "恭喜<font color='#FF5001'>" + "" + newStr + "</font> 提现" + "<font color='#FF5001'>" + announcementList.get(i).getAmount() + "</font> 元";
						}
						data.add(value);
					}
					if (adapter != null) {
						adapter.notifyDataSetChanged();
					} else {
						setAdapter();
					}

				} else if (Constant.HTTP_LOGINOUTTIME_CODE.equals(announcementEntity.getCode())) {

					CommonUtil.reLogin(PreforenceUtils.getStringData("loginInfo", "userID"), PreforenceUtils.getStringData("loginInfo", "password"), new CommonUtil.LoginCallBack() {
						@Override
						public void callBack(BaseEntity<LoginEntity> loginEntity) {
							intDatas();
						}

						@Override
						public void failCallBack(Throwable throwable) {

						}
					});

				}
			}
		}, new Consumer<Throwable>() {
			@Override
			public void accept(Throwable throwable) throws Exception {

			}
		});
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		ButterKnife.unbind(this);
		stopAuto();

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

	//item滚动步骤2：设置定时器自动滚动
	public void startAuto() {
		if (mAutoTask != null && !mAutoTask.isDisposed()) {
			mAutoTask.dispose();
		}
		mAutoTask = Observable.interval(1, 2, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Long>() {
			@Override
			public void accept(Long aLong) throws Exception {
				//滚动到指定item
				mScroller.setTargetPosition(aLong.intValue());
				amRv.getLayoutManager().startSmoothScroll(mScroller);
			}
		});
	}

	private void stopAuto() {
		if (mAutoTask != null && !mAutoTask.isDisposed()) {
			mAutoTask.dispose();
			mAutoTask = null;
		}
	}

	//为了方便改写，来实现复杂布局的切换
	private class LocalImageHolderView implements Holder<Integer> {
		private ImageView imageView;

		@Override
		public View createView(Context context) {
			//你可以通过layout文件来创建，不一定是Image，任何控件都可以进行翻页
			imageView = new ImageView(context);
			imageView.setScaleType(ImageView.ScaleType.FIT_XY);
			return imageView;
		}

		@Override
		public void UpdateUI(Context context, int position, Integer data) {
			imageView.setImageResource(data);
		}
	}

	/**
	 * 通过文件名获取资源id 例子：getResId("icon", R.drawable.class);
	 *
	 * @param variableName
	 * @param c
	 * @return
	 */
	public static int getResId(String variableName, Class<?> c) {
		try {
			Field idField = c.getDeclaredField(variableName);
			return idField.getInt(idField);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}


}
