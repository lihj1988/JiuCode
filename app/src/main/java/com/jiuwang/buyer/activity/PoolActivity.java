package com.jiuwang.buyer.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jiuwang.buyer.R;
import com.jiuwang.buyer.adapter.LotteryRecordAdapter;
import com.jiuwang.buyer.base.BaseActivity;
import com.jiuwang.buyer.bean.LotteryRecordBean;
import com.jiuwang.buyer.bean.PoolBean;
import com.jiuwang.buyer.constant.Constant;
import com.jiuwang.buyer.entity.BaseEntity;
import com.jiuwang.buyer.entity.LoginEntity;
import com.jiuwang.buyer.entity.LotteryEntity;
import com.jiuwang.buyer.entity.PoolEntity;
import com.jiuwang.buyer.net.HttpUtils;
import com.jiuwang.buyer.redpakge.CustomDialog;
import com.jiuwang.buyer.redpakge.OnRedPacketDialogClickListener;
import com.jiuwang.buyer.redpakge.RedPacketEntity;
import com.jiuwang.buyer.redpakge.RedPacketViewHolder;
import com.jiuwang.buyer.util.AppUtils;
import com.jiuwang.buyer.util.CommonUtil;
import com.jiuwang.buyer.util.MyToastView;
import com.jiuwang.buyer.util.PreforenceUtils;
import com.jiuwang.buyer.view.ZzHorizontalProgressBar;

import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;

/**
 * author：lihj
 * desc：
 * Created by lihj on  2019/8/19 10:33.
 */

public class PoolActivity extends BaseActivity {
	@Bind(R.id.topView)
	LinearLayout topView;
	@Bind(R.id.actionbar_text)
	TextView actionbarText;
	@Bind(R.id.onclick_layout_left)
	RelativeLayout onclickLayoutLeft;
	@Bind(R.id.onclick_layout_right)
	Button onclickLayoutRight;
	@Bind(R.id.tvPb)
	TextView tvPb;
	@Bind(R.id.pb)
	ZzHorizontalProgressBar pb;
	@Bind(R.id.btnLottery)
	Button btnLottery;
	@Bind(R.id.xRecyclerView)
	XRecyclerView xRecyclerView;
	private List<PoolBean> data;
	private View mRedPacketDialogView;
	private RedPacketViewHolder mRedPacketViewHolder;
	private CustomDialog mRedPacketDialog;
	private List<LotteryRecordBean> lotteryRecordBeanList;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pool);
		ButterKnife.bind(this);
		initView();
		initData(false);
		initLotteryRecord();

	}

	private void initLotteryRecord() {
		HttpUtils.lotteryInfo(new HashMap<String, String>(), new Consumer<LotteryEntity>() {
			@Override
			public void accept(LotteryEntity lotteryEntity) throws Exception {
				if(Constant.HTTP_SUCCESS_CODE.equals(lotteryEntity.getCode())){
					lotteryRecordBeanList = lotteryEntity.getData();
					if(lotteryRecordBeanList!=null){
						LotteryRecordAdapter lotteryRecordAdapter = new LotteryRecordAdapter(lotteryRecordBeanList);
						xRecyclerView.setAdapter(lotteryRecordAdapter);
					}

				}
			}
		}, new Consumer<Throwable>() {
			@Override
			public void accept(Throwable throwable) throws Exception {

			}
		});
	}

	private void initView() {
		setTopView(topView);
		actionbarText.setText("奖池");
		onclickLayoutRight.setVisibility(View.INVISIBLE);
		AppUtils.initListView(PoolActivity.this,xRecyclerView,false,false);

	}

	private void initData(final boolean isCheck) {
		if (CommonUtil.getNetworkRequest(PoolActivity.this)) {
			HashMap<String, String> hashMap = new HashMap<>();
			hashMap.put("act", "getpercent");
			HttpUtils.poolInfo(hashMap, new Consumer<PoolEntity>() {
				@Override
				public void accept(final PoolEntity poolEntity) throws Exception {
					if (Constant.HTTP_LOGINOUTTIME_CODE.equals(poolEntity.getCode())) {
						CommonUtil.reLogin(PreforenceUtils.getStringData("loginInfo", "userID"), PreforenceUtils.getStringData("loginInfo", "password"), new CommonUtil.LoginCallBack() {
							@Override
							public void callBack(BaseEntity<LoginEntity> loginEntity) {
								new Handler(){
									@Override
									public void handleMessage(Message msg) {
										initData(isCheck);
									}
								}.sendEmptyMessageDelayed(0,2000);
							}

							@Override
							public void failCallBack(Throwable throwable) {

							}
						});
					} else if (Constant.HTTP_SUCCESS_CODE.equals(poolEntity.getCode())) {
						data = poolEntity.getData();
						double totalAmount = Double.parseDouble(data.get(0).getTotal_amount());
						double amount = Double.parseDouble(data.get(0).getAmount());
						double percent = totalAmount / amount;
						if (percent > 1) {
							percent = 1;
						}
						pb.setProgress((int) (percent * 100));
						tvPb.setText((int) (percent * 100) + "%");

						if(!isCheck){
							if("0".equals(data.get(0).getStatus())){
								new Handler() {
									@Override
									public void handleMessage(Message msg) {

										initData(isCheck);
									}
								}.sendEmptyMessageDelayed(0, 5000);
							}

						}else {
							if("0".equals(data.get(0).getStatus())){
								MyToastView.showToast(poolEntity.getMsg(),PoolActivity.this);
							}else if("1".equals(data.get(0).getStatus())){
								showDialog();
							}

						}

					} else {

					}
				}
			}, new Consumer<Throwable>() {
				@Override
				public void accept(Throwable throwable) throws Exception {

				}
			});
		}
	}

	@OnClick({R.id.onclick_layout_left,R.id.btnLottery})
	public void onViewClicked(View view) {
		switch (view.getId()){

			case R.id.onclick_layout_left:
				finish();
				break;
			case R.id.btnLottery:
				initData(true);
				break;
		}
	}
	public void showDialog() {
		RedPacketEntity entity = new RedPacketEntity();
		entity.setName("活动即将开始！");
		entity.setAvatar("http://upload.51qianmai.com/20171205180511192.png！");
		entity.setRemark("");
		showRedPacketDialog(entity);
	}

	public void showRedPacketDialog(RedPacketEntity entity) {
		if (mRedPacketDialogView == null) {
			mRedPacketDialogView = View.inflate(PoolActivity.this, R.layout.dialog_red_packet, null);
			mRedPacketViewHolder = new RedPacketViewHolder(PoolActivity.this, mRedPacketDialogView);
			mRedPacketDialog = new CustomDialog(PoolActivity.this, mRedPacketDialogView, R.style.custom_dialog);
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
