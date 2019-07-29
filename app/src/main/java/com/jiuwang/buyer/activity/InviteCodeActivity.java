package com.jiuwang.buyer.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jiuwang.buyer.R;
import com.jiuwang.buyer.base.BaseActivity;
import com.jiuwang.buyer.camera.zxing.encoding.EncodingHandler;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by lihj on 2019/7/22
 * desc:
 */

public class InviteCodeActivity extends BaseActivity {
	@Bind(R.id.topView)
	LinearLayout topView;
	@Bind(R.id.actionbar_text)
	TextView actionbarText;
	@Bind(R.id.onclick_layout_left)
	RelativeLayout onclickLayoutLeft;
	@Bind(R.id.onclick_layout_right)
	Button onclickLayoutRight;
	@Bind(R.id.ivInviteCode)
	ImageView ivInviteCode;
	@Bind(R.id.tvInviteCode)
	TextView tvInviteCode;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_invitecode);
		ButterKnife.bind(this);
		setTopView(topView);
		onclickLayoutRight.setVisibility(View.INVISIBLE);
		actionbarText.setText("我的邀请码");
		Intent intent = getIntent();
		String invite_code = intent.getStringExtra("invite_code");
		JSONObject object = new JSONObject();
		try {
			object.put("type","inviteCode");
			object.put("text",invite_code);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		tvInviteCode.setText(invite_code);
		Bitmap bitmapLogo = BitmapFactory.decodeResource(getResources(), R.mipmap.app_logo);
		//生成二维码
		Bitmap qrCode = EncodingHandler.createQRCode(object.toString(), 650, 650, bitmapLogo);
		ivInviteCode.setImageBitmap(qrCode);
	}

	@OnClick(R.id.onclick_layout_left)
	public void onViewClicked() {
		finish();
	}
}
