package com.jiuwang.buyer.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiuwang.buyer.R;
import com.jiuwang.buyer.base.MyApplication;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * 作者：lihj
 * 作用：购买商品时的商品适配器
 */

public class GoodsBuyListAdapter extends RecyclerView.Adapter<GoodsBuyListAdapter.ViewHolder> {

    private Activity mActivity;
    private MyApplication mApplication;
    private ArrayList<HashMap<String, String>> mArrayList;

    public GoodsBuyListAdapter(MyApplication application, Activity activity, ArrayList<HashMap<String, String>> arrayList) {
        this.mActivity = activity;
        this.mArrayList = arrayList;
        this.mApplication = application;
    }

    @Override
    public int getItemCount() {
        return mArrayList.size();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final HashMap<String, String> hashMap = mArrayList.get(position);
        Double goods_price = Double.parseDouble(hashMap.get("goods_price"));
        int goods_num = Integer.parseInt(hashMap.get("goods_num"));
//        加载图片
//        mApplication.mFinalBitmap.display(holder.mImageView, hashMap.get("goods_image_url"));
        holder.nameTextView.setText(hashMap.get("goods_name"));
        String info = "￥ <font color='#FF5001'>" + goods_price + "</font><br>";
        info = info + "x <font color='#FF5001'>" + goods_num + "</font><br>";
        info = info + "共 <font color='#FF5001'>" + (goods_price * goods_num) + "</font>";
        holder.infoTextView.setText(Html.fromHtml(info));

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup group, int viewType) {
        View view = LayoutInflater.from(group.getContext()).inflate(R.layout.item_list_goods_buy, group, false);
        return new ViewHolder(view);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView mImageView;
        public TextView nameTextView;
        public TextView infoTextView;

        public ViewHolder(View view) {
            super(view);

            mImageView = (ImageView) view.findViewById(R.id.mainImageView);
            nameTextView = (TextView) view.findViewById(R.id.nameTextView);
            infoTextView = (TextView) view.findViewById(R.id.infoTextView);

        }

    }

}

