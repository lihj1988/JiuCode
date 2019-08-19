package com.jiuwang.buyer.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jiuwang.buyer.R;
import com.jiuwang.buyer.base.BaseActivity;
import com.jiuwang.buyer.constant.Constant;
import com.jiuwang.buyer.entity.BaseEntity;
import com.jiuwang.buyer.entity.LoginEntity;
import com.jiuwang.buyer.entity.PoolEntity;
import com.jiuwang.buyer.net.HttpUtils;
import com.jiuwang.buyer.util.CommonUtil;
import com.jiuwang.buyer.util.PreforenceUtils;

import java.util.HashMap;

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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pool);
		ButterKnife.bind(this);
		initView();
		initData();
	}

	private void initView() {
		setTopView(topView);
		actionbarText.setText("奖池");
	}

	private void initData() {
		if (CommonUtil.getNetworkRequest(PoolActivity.this)) {
			HttpUtils.poolInfo(new HashMap<String, String>(), new Consumer<PoolEntity>() {
				@Override
				public void accept(PoolEntity poolEntity) throws Exception {
					if (Constant.HTTP_LOGINOUTTIME_CODE.equals(poolEntity.getCode())) {
						CommonUtil.reLogin(PreforenceUtils.getStringData("loginInfo", "userID"), PreforenceUtils.getStringData("loginInfo", "password"), new CommonUtil.LoginCallBack() {
							@Override
							public void callBack(BaseEntity<LoginEntity> loginEntity) {
								initData();
							}

							@Override
							public void failCallBack(Throwable throwable) {

							}
						});
					} else if (Constant.HTTP_SUCCESS_CODE.equals(poolEntity.getCode())) {

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

	@OnClick(R.id.onclick_layout_left)
	public void onViewClicked() {
		finish();
	}
}
