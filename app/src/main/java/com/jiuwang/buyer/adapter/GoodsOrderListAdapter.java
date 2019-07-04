package com.jiuwang.buyer.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jiuwang.buyer.R;
import com.jiuwang.buyer.base.MyApplication;
import com.jiuwang.buyer.bean.OrderBean;

import java.util.List;


/*
*
* 作者：Yokey软件工作室
*
* 企鹅：1002285057
*
* 网址：www.yokey.top
*
* 作用：购买商品时的商品适配器
*
* 更新：2016-04-17
*
*/

public class GoodsOrderListAdapter extends RecyclerView.Adapter<GoodsOrderListAdapter.ViewHolder> {

    private MyApplication mApplication;
    private List<OrderBean.DetailListBean> mArrayList;

    public GoodsOrderListAdapter(MyApplication application, List<OrderBean.DetailListBean> arrayList) {
        this.mArrayList = arrayList;
        this.mApplication = application;
    }

    @Override
    public int getItemCount() {
        return mArrayList.size();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final OrderBean.DetailListBean detailBean =  mArrayList.get(position);
        Double goods_price = Double.parseDouble(detailBean.getGoods_price());
        int goods_num = Integer.parseInt(detailBean.getGoods_num());
        //图片加载
//        mApplication.mFinalBitmap.display(holder.mImageView, hashMap.get("goods_image_url"));
        holder.nameTextView.setText(detailBean.getGoods_name());
        String info = "￥ <font color='#FF5001'>" + goods_price + "</font><br>";
        info = info + "x <font color='#FF5001'>" + goods_num + "</font><br>";
        info = info + "共 <font color='#FF5001'>" + (goods_price * goods_num) + "</font>";
        holder.infoTextView.setText(Html.fromHtml(info));

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup group, int viewType) {
        View view = LayoutInflater.from(group.getContext()).inflate(R.layout.item_list_goods_order, group, false);
        return new ViewHolder(view);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public RelativeLayout mRelativeLayout;
        public ImageView mImageView;
        public TextView nameTextView;
        public TextView infoTextView;

        public ViewHolder(View view) {
            super(view);

            mRelativeLayout = (RelativeLayout) view.findViewById(R.id.mainRelativeLayout);
            mImageView = (ImageView) view.findViewById(R.id.mainImageView);
            nameTextView = (TextView) view.findViewById(R.id.nameTextView);
            infoTextView = (TextView) view.findViewById(R.id.infoTextView);

        }

    }

}

