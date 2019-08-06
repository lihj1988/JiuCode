package com.jiuwang.buyer.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jiuwang.buyer.R;
import com.jiuwang.buyer.bean.SelectGoodsBean;
import com.jiuwang.buyer.util.AppUtils;
import com.jiuwang.buyer.util.CommonUtil;
import com.jiuwang.buyer.constant.NetURL;

import java.util.List;

/**
 * Created by lihj on 2019/7/4
 * desc: 抢购选择商品适配器
 */

public class ChooseItemAdapter extends RecyclerView.Adapter<ChooseItemAdapter.ViewHolder> {

	private Context context;

	private ItemOnClickListener itemOnClickListener;
	private List<SelectGoodsBean> selectGoodsList;
	public ChooseItemAdapter(Context context, List<SelectGoodsBean> selectGoodsList,ItemOnClickListener itemOnClickListener) {
		this.context = context;
		this.selectGoodsList = selectGoodsList;
		this.itemOnClickListener = itemOnClickListener;
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(context).inflate(R.layout.item_choose, parent, false);
		return new ViewHolder(view);
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, final int position) {
		CommonUtil.loadImage(context, NetURL.PIC_BASEURL+selectGoodsList.get(position).getPic_url(),holder.ivGoodsImg);
		holder.tvGoodsName.setText(selectGoodsList.get(position).getGoods_name());
		if(selectGoodsList.get(position).getPic_url().split(",").length>1){
			CommonUtil.loadImage(context,NetURL.PIC_BASEURL+selectGoodsList.get(position).getPic_url().split(",")[0],holder.ivGoodsImg);
		}else {
			CommonUtil.loadImage(context,NetURL.PIC_BASEURL+selectGoodsList.get(position).getPic_url(),holder.ivGoodsImg);
		}
		holder.llItem.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				itemOnClickListener.click(position);
			}
		});

	}

	@Override
	public int getItemCount() {
		return selectGoodsList.size();
	}

	public static class ViewHolder extends RecyclerView.ViewHolder {
		public ImageView ivGoodsImg;
		public TextView tvGoodsName;
		public LinearLayout llItem;

		public ViewHolder(View view) {
			super(view);
			ivGoodsImg = view.findViewById(R.id.ivGoodsImg);
			tvGoodsName = view.findViewById(R.id.tvGoodsName);
			llItem = view.findViewById(R.id.llItem);
		}
	}

	public interface ItemOnClickListener {
		abstract void click(int position);

	}
}
