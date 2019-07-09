package com.jiuwang.buyer.popupwindow;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jiuwang.buyer.R;
import com.jiuwang.buyer.adapter.ChooseItemAdapter;
import com.jiuwang.buyer.bean.GoodsBean;
import com.jiuwang.buyer.bean.SelectGoodsBean;
import com.jiuwang.buyer.constant.Constant;
import com.jiuwang.buyer.entity.BaseResultEntity;
import com.jiuwang.buyer.net.HttpUtils;
import com.jiuwang.buyer.util.AppUtils;
import com.jiuwang.buyer.util.DialogUtil;
import com.jiuwang.buyer.util.LogUtils;
import com.jiuwang.buyer.util.MyToastView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.functions.Consumer;

/**
 * author：lihj
 * desc：抢购选择商品
 * Created by lihj on  2019/7/4 14:15.
 */

public class ChooseItemPopupWindow extends PopupWindow {

	private static final String TAG = ChooseItemPopupWindow.class.getName();
	private WindowManager.LayoutParams params;
	private View mMenuView;
	private Activity context;
	private XRecyclerView itemRecyclerView;
	private TextView cancle;
	private List<SelectGoodsBean> selectGoodsList;
	private String project_id;

	public ChooseItemPopupWindow(Activity context, String project_id, List<SelectGoodsBean> selectGoodsList) {
		super(context);
		this.context = context;
		this.project_id = project_id;
		this.selectGoodsList = selectGoodsList;
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mMenuView = inflater.inflate(R.layout.popup_chooseitem, null);
		setContentView(mMenuView);
		this.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
		this.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
		setAnimationStyle(R.style.ActionSheetDialogAnimation);
		// 实例化一个ColorDrawable颜色为半透明
		ColorDrawable dw = new ColorDrawable(0xb0000000);
		// 设置SelectPicPopupWindow弹出窗体的背景
		setBackgroundDrawable(dw);
		setFocusable(true);
		setOutsideTouchable(false);
		params = this.context.getWindow().getAttributes();
		// 当弹出Popupwindow时，背景变半透明
		params.alpha = 0.7f;
		this.context.getWindow().setAttributes(params);
		setOnDismissListener(new OnDismissListener() {
			@Override
			public void onDismiss() {
				params = ChooseItemPopupWindow.this.context.getWindow()
						.getAttributes();
				params.alpha = 1f;
				ChooseItemPopupWindow.this.context.getWindow().setAttributes(
						params);
			}
		});

		initView(mMenuView);
	}

	private void initView(View mMenuView) {
		List<GoodsBean> goodsList = new ArrayList<>();
		for (int i = 0; i < 5; i++) {
			GoodsBean goodsBean = new GoodsBean();
			goodsBean.setGoods_name("待选" + (i + 1));
			goodsBean.setPic_url("");
			goodsList.add(goodsBean);
		}
		itemRecyclerView = mMenuView.findViewById(R.id.itemRecyclerView);
		AppUtils.initGridViewNothing(3, itemRecyclerView);
		final ChooseItemAdapter chooseItemAdapter = new ChooseItemAdapter(context, selectGoodsList, new ChooseItemAdapter.ItemOnClickListener() {
			@Override
			public void click(int position) {
				LogUtils.e(TAG, "点击了第" + (position + 1) + "条");
				//报名
				DialogUtil.progress(context);
				HashMap<String, String> hashMap = new HashMap<>();
				hashMap.put("act", Constant.ACTION_ACT_ADD);
				hashMap.put("aution_id", project_id);
				hashMap.put("goods_id", selectGoodsList.get(position).getId());
				HttpUtils.enroll(hashMap, new Consumer<BaseResultEntity>() {
					@Override
					public void accept(BaseResultEntity baseResultEntity) throws Exception {
						if (Constant.HTTP_SUCCESS_CODE.equals(baseResultEntity.getCode())) {

							Intent intent = new Intent();
							intent.setAction("refreshProject");
							context.sendBroadcast(intent);
						}
						DialogUtil.cancel();
						ChooseItemPopupWindow.this.dismiss();
						MyToastView.showToast(baseResultEntity.getMsg(), context);

					}
				}, new Consumer<Throwable>() {
					@Override
					public void accept(Throwable throwable) throws Exception {
						DialogUtil.cancel();
						MyToastView.showToast(context.getString(R.string.msg_error_operation), context);
					}
				});
			}
		});
		itemRecyclerView.setAdapter(chooseItemAdapter);
		cancle = mMenuView.findViewById(R.id.cancle);

		cancle.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dismiss();
			}
		});
	}

}
