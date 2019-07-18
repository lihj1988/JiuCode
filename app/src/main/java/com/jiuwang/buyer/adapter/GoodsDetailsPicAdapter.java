package com.jiuwang.buyer.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.jiuwang.buyer.R;
import com.jiuwang.buyer.constant.NetURL;
import com.jiuwang.buyer.util.CommonUtil;

import java.util.List;


/**
 * author：lihj
 * desc：
 * Created by lihj on  2019/7/18 14:10.
 */

public class GoodsDetailsPicAdapter extends BaseAdapter {
	private Context context;
	private List<String> picList;

	public GoodsDetailsPicAdapter(Context context, List<String> picList) {
		this.context = context;
		this.picList = picList;
	}

	@Override
	public int getCount() {
		return picList.size();
	}

	@Override
	public Object getItem(int i) {
		return picList.get(i);
	}

	@Override
	public long getItemId(int i) {
		return 0;
	}

	@Override
	public View getView(int position, View view, ViewGroup viewGroup) {
		ViewHolder viewHolder = null;
		if (view == null) {
			view = View.inflate(context, R.layout.item_goods_details_pic, null);
			viewHolder = new ViewHolder();

			viewHolder.ivPic = view.findViewById(R.id.ivPic);
			view.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) view.getTag();
		}
		CommonUtil.loadImage(context, NetURL.PIC_BASEURL + picList.get(position), viewHolder.ivPic);
		return view;
	}

	class ViewHolder {
		public ImageView ivPic;
	}
}
