package com.jiuwang.buyer.goods.adaper;

import android.content.Context;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jiuwang.buyer.R;
import com.jiuwang.buyer.adapter.BuyListAdapter;
import com.jiuwang.buyer.constant.NetURL;
import com.jiuwang.buyer.util.AppUtils;
import com.jiuwang.buyer.util.CommonUtil;

import java.util.List;

/**
 * Created by lihj on 2019/7/24
 * desc:
 */

public class GoodsInfoPicAdapter extends RecyclerView.Adapter<GoodsInfoPicAdapter.ViewHolder>{

	private Context context;
	private List<String> images;

	public GoodsInfoPicAdapter(Context context, List<String> images) {
		this.context = context;
		this.images = images;
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_goods_details_img, parent, false);
		return new GoodsInfoPicAdapter.ViewHolder(view);
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		CommonUtil.loadImage(context, NetURL.PIC_BASEURL + images.get(position), holder.ivPic);
	}

	@Override
	public int getItemCount() {
		return images.size();
	}

	public static class ViewHolder extends RecyclerView.ViewHolder {

		public ImageView ivPic;

		public ViewHolder(View view) {
			super(view);
			ivPic = view.findViewById(R.id.iv);

		}

	}
}
