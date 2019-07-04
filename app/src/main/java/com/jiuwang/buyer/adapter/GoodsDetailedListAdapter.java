package com.jiuwang.buyer.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jiuwang.buyer.R;
import com.jiuwang.buyer.activity.GoodsDetailsActivty;
import com.jiuwang.buyer.base.MyApplication;

import java.util.ArrayList;
import java.util.HashMap;


/*
*
* 作者：Yokey软件工作室
*
* 企鹅：1002285057
*
* 网址：www.yokey.top
*
* 作用：商品详细页 推荐商品 适配器
*
* 更新：2016-04-03
*
*/

public class GoodsDetailedListAdapter extends RecyclerView.Adapter<GoodsDetailedListAdapter.ViewHolder> {

    private Activity mActivity;
    private MyApplication mApplication;
    private ArrayList<HashMap<String, String>> mArrayList;

    public GoodsDetailedListAdapter(MyApplication application, Activity activity, ArrayList<HashMap<String, String>> arrayList) {
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
//        图片
//        mApplication.mFinalBitmap.display(holder.mImageView, hashMap.get("goods_image_url"));
        holder.nameTextView.setText(hashMap.get("goods_name"));
        String temp = "￥ " + hashMap.get("goods_promotion_price");
        holder.priceTextView.setText(temp);

        holder.mLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mActivity, GoodsDetailsActivty.class);
                intent.putExtra("goods_id", hashMap.get("goods_id"));
                mApplication.startActivity(mActivity, intent);
            }
        });

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup group, int viewType) {
        View view = LayoutInflater.from(group.getContext()).inflate(R.layout.item_list_goods_detailed, group, false);
        return new ViewHolder(view);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public LinearLayout mLinearLayout;
        public ImageView mImageView;
        public TextView nameTextView;
        public TextView priceTextView;

        public ViewHolder(View view) {
            super(view);

            mLinearLayout = (LinearLayout) view.findViewById(R.id.mainLinearLayout);
            mImageView = (ImageView) view.findViewById(R.id.mainImageView);
            nameTextView = (TextView) view.findViewById(R.id.nameTextView);
            priceTextView = (TextView) view.findViewById(R.id.priceTextView);

        }

    }

}