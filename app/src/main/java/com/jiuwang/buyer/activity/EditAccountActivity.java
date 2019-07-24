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
import com.jiuwang.buyer.net.HttpUtils;
import com.jiuwang.buyer.util.CommonUtil;
import com.jiuwang.buyer.util.MyToastView;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;

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
	@Bind(R.id.etAccountName)
	EditText etAccountName;
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
				//绑定账号
				String account = etAccount.getText().toString().trim();
				String accountName = etAccountName.getText().toString().trim();
				if ("".equals(accountName)) {
					MyToastView.showToast("请填写姓名", EditAccountActivity.this);
					return;
				}
				if ("".equals(account)) {
					MyToastView.showToast("请填写账号", EditAccountActivity.this);
					return;
				}
				if (CommonUtil.getNetworkRequest(EditAccountActivity.this)) {
					bind(account, accountName);
				}
				break;
		}
	}

	private void bind(final String account, final String accountName) {
		HashMap<String, String> hashMap = new HashMap<>();
		hashMap.put("act", Constant.ACTION_ACT_UPDATA);
		hashMap.put("account_no", account);
		hashMap.put("account_name", accountName);
		HttpUtils.userInfo(hashMap, new Consumer<BaseResultEntity>() {
			@Override
			public void accept(BaseResultEntity baseResultEntity) throws Exception {
				if(Constant.HTTP_SUCCESS_CODE.equals(baseResultEntity.getCode())){
					Intent intent = new Intent();
					intent.putExtra("account", account);
					intent.putExtra("account_name", accountName);
					if ("0".equals(accountType)) {
						setResult(RESULT_OK, intent);
					} else {
						setResult(RESULT_OK, intent);
					}
					finish();
				}else if(Constant.HTTP_LOGINOUTTIME_CODE.equals(baseResultEntity.getCode())){
					startActivity(new Intent(EditAccountActivity.this,LoginActivity.class));
				}
				MyToastView.showToast(baseResultEntity.getMsg(), EditAccountActivity.this);
			}
		}, new Consumer<Throwable>() {
			@Override
			public void accept(Throwable throwable) throws Exception {
				MyToastView.showToast(getResources().getString(R.string.msg_error_operation), EditAccountActivity.this);
			}
		});

	}
}
