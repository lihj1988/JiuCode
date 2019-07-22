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

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by lihj on 2019/7/22
 * desc:
 */

public class EditAccountActivity extends BaseActivity {
	@Bind(R.id.topView)
	LinearLayout topView;
	@Bind(R.id.actionbar_text)
	TextView actionbarText;
	@Bind(R.id.onclick_layout_left)
	RelativeLayout onclickLayoutLeft;
	@Bind(R.id.onclick_layout_right)
	Button onclickLayoutRight;
	@Bind(R.id.etAccount)
	EditText etAccount;
	@Bind(R.id.btnBind)
	TextView btnBind;
	private String accountType;
	private String editType;
	private String account;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_account);
		ButterKnife.bind(this);
		initData();
		initView();
	}

	private void initData() {
		Intent intent = getIntent();
		accountType = intent.getStringExtra("accountType");
		editType = intent.getStringExtra("editType");
		account = intent.getStringExtra("account");
	}

	private void initView() {
		setTopView(topView);
		onclickLayoutRight.setVisibility(View.INVISIBLE);
		actionbarText.setText("我的账号");
		etAccount.setText(account);
		if ("0".equals(editType)) {
			btnBind.setText("绑定");
		} else {
			btnBind.setText("保存");
		}
	}

	@OnClick({R.id.onclick_layout_left, R.id.btnBind})
	public void onViewClicked(View view) {
		switch (view.getId()) {
			case R.id.onclick_layout_left:
				finish();
				break;
			case R.id.btnBind:
				//绑定、修改账号
				Intent intent = new Intent();
				intent.putExtra("account",etAccount.getText().toString().trim());
				if ("0".equals(accountType)) {
					setResult(RESULT_OK,intent);
				} else {
					setResult(RESULT_OK,intent);
				}
				finish();
				break;
		}
	}
}
