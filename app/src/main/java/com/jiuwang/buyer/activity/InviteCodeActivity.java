package com.jiuwang.buyer.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jiuwang.buyer.R;
import com.jiuwang.buyer.base.BaseActivity;
import com.jiuwang.buyer.camera.zxing.encoding.EncodingHandler;
import com.jiuwang.buyer.constant.NetURL;
import com.jiuwang.buyer.util.LoadingDialog;
import com.jiuwang.buyer.util.LogUtils;
import com.jiuwang.buyer.util.MyToastView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import butterknife.Bind;
import butterknife.ButterKnife;

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
	private LoadingDialog mLoadingDialog;
	//长按后显示的 Item
	final String[] items = new String[]{"保存图片"};
	//图片转成Bitmap数组
	final Bitmap[] bitmap = new Bitmap[1];

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_invitecode);
		ButterKnife.bind(this);
		setTopView(topView);
		onclickLayoutRight.setVisibility(View.INVISIBLE);
		actionbarText.setText("邀请码、下载码");
		Intent intent = getIntent();
		String invite_code = intent.getStringExtra("invite_code");
		JSONObject object = new JSONObject();
		try {
			object.put("type","inviteCode");
			object.put("text",invite_code);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		tvInviteCode.setText("我的邀请码："+invite_code);
//		tvInviteCode.setText("");
		Bitmap bitmapLogo = BitmapFactory.decodeResource(getResources(), R.mipmap.app_logo);

		//生成二维码
		Bitmap qrCode = EncodingHandler.createQRCode(NetURL.BASEURL + "/download.jsp", 650, 650, bitmapLogo);
		bitmap[0] = qrCode;
		ivInviteCode.setImageBitmap(qrCode);
		ivInviteCode.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View view) {
				//弹出的“保存图片”的Dialog
				AlertDialog.Builder builder = new AlertDialog.Builder(InviteCodeActivity.this);
				builder.setItems(items, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						switch (which) {
							case 0:
								if (bitmap[0] != null) {
									saveImageToGallery(InviteCodeActivity.this, bitmap[0]);
								} else {

								}
						}
					}
				});
				builder.show();
				return true;
			}
		});
	}

	public static void saveImageToGallery(Context context, Bitmap bmp) {
		// 首先保存图片 创建文件夹
		// 获取内置SD卡路径
		String sdCardPath = Environment.getExternalStorageDirectory().getPath();
		// 图片文件路径
		File file = new File(sdCardPath);
		File[] files = file.listFiles();
		for (int i = 0; i < files.length; i++) {
			File file1 = files[i];
			String name = file1.getName();
			if (name.endsWith("rzg_download.png")) {
				boolean flag = file1.delete();
				LogUtils.e("1111", "删除 + " + flag);
			}
		}

		//图片文件名称
		String fileName = sdCardPath + "/rzg_download.png";
		file = new File(fileName);
		try {
			FileOutputStream fos = new FileOutputStream(file);
			bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
			fos.flush();
			fos.close();
		} catch (Exception e) {
			LogUtils.e("111", e.getMessage());
			e.printStackTrace();
		}

		// 其次把文件插入到系统图库
		String path = file.getAbsolutePath();
		try {
			MediaStore.Images.Media.insertImage(context.getContentResolver(), path, fileName, null);
		} catch (FileNotFoundException e) {
			LogUtils.e("333", e.getMessage());
			e.printStackTrace();
		}

		// 最后通知图库更新
		Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
		Uri uri = Uri.fromFile(file);
		intent.setData(uri);
		context.sendBroadcast(intent);
		file.delete();
		MyToastView.showToast("保存成功", context);
	}

	public void onViewClicked() {
		finish();
	}
}
