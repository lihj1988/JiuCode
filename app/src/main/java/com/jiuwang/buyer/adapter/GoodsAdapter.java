package com.jiuwang.buyer.adapter;

import android.content.Context;
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
import com.jiuwang.buyer.util.CommonUtil;
import com.jiuwang.buyer.util.NetURL;

import java.util.List;


/**
 *
 */
public class GoodsAdapter extends RecyclerView.Adapter<GoodsAdapter.ViewHolder> {

	public List<GoodsBean> datas = null;
	public Context context = null;


	public GoodsAdapter( Context context,List<GoodsBean> datas) {
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
		viewHolder.tvProductArea.setText(datas.get(position).getProducer());
		viewHolder.tvPrice.setText("¥"+datas.get(position).getPrice()+"元");
		if(datas.get(position).getOrder_count()==null){
			viewHolder.tvOrderCount.setText("销量"+0+"笔");
		}else {
			viewHolder.tvOrderCount.setText("销量"+datas.get(position).getOrder_count()+"笔");
		}

		if("".equals(datas.get(position).getSale_price())){
			viewHolder.tvCheapPrice.setVisibility(View.GONE);
		}else {
			viewHolder.tvCheapPrice.setVisibility(View.VISIBLE);
			viewHolder.tvPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG );
			viewHolder.tvCheapPrice.setText(datas.get(position).getSale_price());
		}
		viewHolder.llItem.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
//				MyToastView.showToast("点击第"+(position+1)+"条",context);
				Intent intent = new Intent();
				intent.setClass(context, GoodsDetailsActivty.class);
				intent.putExtra("goods",datas.get(position));
				context.startActivity(intent);
			}
		});
		CommonUtil.loadImage(context, NetURL.PIC_BASEURL+datas.get(position).getPic_url(),viewHolder.ivGoodsImg);
	}

	//获取数据的数量
	@Override
	public int getItemCount() {
		return datas.size();
	}

	//自定义的ViewHolder，持有每个Item的的所有界面元素
	public static class ViewHolder extends RecyclerView.ViewHolder {

		public ImageView ivGoodsImg;
		public TextView tvGoodsName;
		public TextView tvProductArea;
		public TextView tvPrice;
		public TextView tvOrderCount;
		public TextView tvCheapPrice;
		public LinearLayout llItem;

		public ViewHolder(View view) {
			super(view);
			ivGoodsImg = view.findViewById(R.id.ivGoodsImg);
			tvGoodsName = view.findViewById(R.id.tvGoodsName);
			tvProductArea = view.findViewById(R.id.tvProductArea);
			tvPrice = view.findViewById(R.id.tvPrice);
			tvCheapPrice = view.findViewById(R.id.tvCheapPrice);
			tvOrderCount = view.findViewById(R.id.tvOrderCount);
			llItem = view.findViewById(R.id.llItem);

		}
	}
}
