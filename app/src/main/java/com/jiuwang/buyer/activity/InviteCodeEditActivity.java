package com.jiuwang.buyer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jiuwang.buyer.R;
import com.jiuwang.buyer.base.BaseActivity;
import com.jiuwang.buyer.constant.Constant;
import com.jiuwang.buyer.entity.BaseResultEntity;
import com.jiuwang.buyer.net.CommonHttpUtils;
import com.jiuwang.buyer.util.LoadingDialog;
import com.jiuwang.buyer.util.MyToastView;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by lihj on 2019/7/22
 * desc:
 */

public class InviteCodeEditActivity extends BaseActivity {
	@Bind(R.id.topView)
	LinearLayout topView;
	@Bind(R.id.actionbar_text)
	TextView actionbarText;
	@Bind(R.id.onclick_layout_left)
	RelativeLayout onclickLayoutLeft;
	@Bind(R.id.onclick_layout_right)
	Button onclickLayoutRight;
	@Bind(R.id.etInviteCode)
	EditText etInviteCode;
	@Bind(R.id.btnNext)
	TextView btnNext;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_invitecode);
		ButterKnife.bind(this);
		setTopView(topView);
		onclickLayoutRight.setVisibility(View.INVISIBLE);
		actionbarText.setText("我的邀请码");

	}

	@OnClick({R.id.onclick_layout_left, R.id.btnNext})
	public void onViewClicked(View view) {
		switch (view.getId()) {
			case R.id.onclick_layout_left:
				finish();
				break;
			case R.id.btnNext:
				String trim = etInviteCode.getText().toString().trim();
				if("".equals(trim)){
					MyToastView.showToast("请填写邀请码",InviteCodeEditActivity.this);
					return;
				}
				if(trim.contains(" ")){
					MyToastView.showToast("填写邀请码不能有空格",InviteCodeEditActivity.this);
					return;
				}
				//绑定邀请码
				if (Constant.IS_LOGIN) {
					final LoadingDialog loadingDialog = new LoadingDialog(InviteCodeEditActivity.this);
					loadingDialog.show();
					HashMap<String, String> map = new HashMap<>();
					map.put("act", "invite");
					map.put("from", trim);
					CommonHttpUtils.ref_action(map, new CommonHttpUtils.CallingBack() {
						@Override
						public void successBack(BaseResultEntity baseResultEntity) {
							loadingDialog.dismiss();

							MyToastView.showToast(baseResultEntity.getMsg(), InviteCodeEditActivity.this);
							if (Constant.HTTP_LOGINOUTTIME_CODE.equals(baseResultEntity.getCode())) {
								startActivity(new Intent(InviteCodeEditActivity.this, LoginActivity.class));
								finish();
							}else if (Constant.HTTP_SUCCESS_CODE.equals(baseResultEntity.getCode())) {
								Intent intent = new Intent();
								intent.setAction("minerefresh");
								sendBroadcast(intent);
								finish();
							}

						}

						@Override
						public void failBack() {
							loadingDialog.dismiss();
							MyToastView.showToast(getResources().getString(R.string.msg_error_operation), InviteCodeEditActivity.this);
						}
					});
				}
				break;
		}
	}


}
