package com.jiuwang.buyer.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jiuwang.buyer.R;
import com.jiuwang.buyer.activity.GoodsDetailsActivty;
import com.jiuwang.buyer.bean.GoodsBean;
import com.jiuwang.buyer.constant.Constant;
import com.jiuwang.buyer.constant.NetURL;
import com.jiuwang.buyer.entity.BaseResultEntity;
import com.jiuwang.buyer.net.HttpUtils;
import com.jiuwang.buyer.util.CommonUtil;
import com.jiuwang.buyer.util.DialogUtil;
import com.jiuwang.buyer.util.MyToastView;

import java.util.HashMap;
import java.util.List;

import io.reactivex.functions.Consumer;


/**
 *
 */
public class GoodsAdapter extends RecyclerView.Adapter<GoodsAdapter.ViewHolder> {

	public List<GoodsBean> datas = null;
	public Activity context = null;


	public GoodsAdapter(Activity context, List<GoodsBean> datas) {
		this.context = context;
		this.datas = datas;
	}

	//创建新View，被LayoutManager所调用
	@Override
	public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
		View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_home, viewGroup, false);
		return new ViewHolder(view);
	}

	//将数据与界面进行绑定的操作
	@Override
	public void onBindViewHolder(ViewHolder viewHolder, final int position) {
		viewHolder.tvGoodsName.setText(datas.get(position).getGoods_name());
//		viewHolder.tvProductArea.setText(datas.get(position).getProducer());
		viewHolder.tvPrice.setText("¥" + datas.get(position).getPrice() + "元");
		if (datas.get(position).getSale_count() == null) {
			viewHolder.tvOrderCount.setText("销量" + 0 + "笔");
		} else {
			viewHolder.tvOrderCount.setText("销量" + datas.get(position).getSale_count() + "笔");
		}

		if ("".equals(datas.get(position).getSale_price())) {
			viewHolder.tvCheapPrice.setVisibility(View.GONE);
		} else {
			viewHolder.tvCheapPrice.setVisibility(View.VISIBLE);
			viewHolder.tvPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
			viewHolder.tvCheapPrice.setText("¥" + datas.get(position).getSale_price());
		}
		viewHolder.llItem.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
//				MyToastView.showToast("点击第"+(position+1)+"条",context);
				Intent intent = new Intent();
				intent.setClass(context, GoodsDetailsActivty.class);
				intent.putExtra("goods", datas.get(position));
				context.startActivity(intent);

			}
		});
		viewHolder.addCar.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				addCar(datas.get(position).getId());
			}
		});
		String[] split = datas.get(position).getPic_url().split(",");
		CommonUtil.loadImage(context, NetURL.PIC_BASEURL + split[0], viewHolder.ivGoodsImg);
	}

	//获取数据的数量
	@Override
	public int getItemCount() {
		return datas.size();
	}

	//自定义的ViewHolder，持有每个Item的的所有界面元素
	public static class ViewHolder extends RecyclerView.ViewHolder {

		public ImageView ivGoodsImg;
		public ImageView addCar;
		public TextView tvGoodsName;
		public TextView tvProductArea;
		public TextView tvPrice;
		public TextView tvOrderCount;
		public TextView tvCheapPrice;
		public LinearLayout llItem;

		public ViewHolder(View view) {
			super(view);
			ivGoodsImg = view.findViewById(R.id.ivGoodsImg);
			addCar = view.findViewById(R.id.addCar);
			tvGoodsName = view.findViewById(R.id.tvGoodsName);
			tvProductArea = view.findViewById(R.id.tvProductArea);
			tvPrice = view.findViewById(R.id.tvPrice);
			tvCheapPrice = view.findViewById(R.id.tvCheapPrice);
			tvOrderCount = view.findViewById(R.id.tvOrderCount);
			llItem = view.findViewById(R.id.llItem);

		}
	}

	public void addCar(String goods_id) {

		HashMap<String, String> map = new HashMap<>();
		map.put("act", Constant.ACTION_ACT_ADD);
		map.put("goods_id", goods_id);
		map.put("quantity", 1 + "");
		DialogUtil.progress(context);
		HttpUtils.addCar(map, new Consumer<BaseResultEntity>() {
			@Override
			public void accept(BaseResultEntity baseResultEntity) throws Exception {
				DialogUtil.cancel();
				if (Constant.HTTP_SUCCESS_CODE.equals(baseResultEntity.getCode())) {
					Intent intent = new Intent();
					intent.setAction("refreshCar");
					context.sendBroadcast(intent);

				} else {

				}
				MyToastView.showToast(baseResultEntity.getMsg(), context);
			}
		}, new Consumer<Throwable>() {
			@Override
			public void accept(Throwable throwable) throws Exception {
				DialogUtil.cancel();
				MyToastView.showToast("请求失败", context);
			}
		});

	}
}
