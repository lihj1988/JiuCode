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
import com.jiuwang.buyer.bean.CarGoodsBean;
import com.jiuwang.buyer.constant.NetURL;
import com.jiuwang.buyer.util.AppUtils;
import com.jiuwang.buyer.util.CommonUtil;

import java.util.List;


/**
 * 作者：lihj
 * 作用：购买商品时的商品适配器
 */

public class GoodsBuyListAdapter extends RecyclerView.Adapter<GoodsBuyListAdapter.ViewHolder> {

    private Activity mActivity;
    private MyApplication mApplication;
    private List<CarGoodsBean> mArrayList;

    public GoodsBuyListAdapter(Activity activity, List<CarGoodsBean> mArrayList) {
        this.mActivity = activity;
        this.mArrayList = mArrayList;

    }

    @Override
    public int getItemCount() {
        return mArrayList.size();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final CarGoodsBean carGoodsBean = mArrayList.get(position);
        Double goods_price = Double.parseDouble(carGoodsBean.getSale_price());
        int goods_num = Integer.parseInt(carGoodsBean.getQuantity());
//        加载图片
//        mApplication.mFinalBitmap.display(holder.mImageView, hashMap.get("goods_image_url"));
        holder.nameTextView.setText(carGoodsBean.getGoods_name());
        String info = "￥ <font color='#FF5001'>" +  AppUtils.decimalFormat(goods_price,"0")+ " 元"+"</font><br>";
        info = info + " <font color='#FF5001'>" + goods_num + " 件"+ "</font><br>";
        info = info + "共 <font color='#FF5001'>" + AppUtils.decimalFormat((goods_price * goods_num),"0") + " 元"+ "</font>";
        holder.infoTextView.setText(Html.fromHtml(info));
        String pic_url[] = carGoodsBean.getPic_url().split("\\|");
        CommonUtil.loadImage(mActivity, NetURL.PIC_BASEURL+pic_url[0],holder.mImageView);

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

