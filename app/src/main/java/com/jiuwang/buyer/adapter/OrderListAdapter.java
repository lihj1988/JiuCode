package com.jiuwang.buyer.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jiuwang.buyer.R;
import com.jiuwang.buyer.activity.BuySetup2Activity;
import com.jiuwang.buyer.base.MyApplication;
import com.jiuwang.buyer.bean.OrderBean;
import com.jiuwang.buyer.constant.Constant;
import com.jiuwang.buyer.util.AppUtils;
import com.jiuwang.buyer.util.MyToastView;

import java.util.List;


public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.ViewHolder> {

	private Activity mActivity;
	private MyApplication mApplication;
	private onItemChange mOnItemChange;
	private List<OrderBean> mArrayList;

	public OrderListAdapter(MyApplication application, Activity activity, List<OrderBean> arrayList) {
		this.mActivity = activity;
		this.mArrayList = arrayList;
		this.mApplication = application;
		this.mOnItemChange = null;
	}

	@Override
	public int getItemCount() {
		return mArrayList.size();
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {

		final OrderBean orderBean = mArrayList.get(position);

//		holder.mListView.setLayoutManager(new LinearLayoutManager(mActivity));
		AppUtils.initListView(MyApplication.getInstance(),holder.mListView,false,false);
		holder.mListView.setAdapter(new GoodsOrderListAdapter(mApplication, orderBean.getDetail_list()));
		holder.storeTextView.setText(orderBean.getGrouping_name_seller());
		holder.stateTextView.setText(orderBean.getNotes());

		String total = "共 <font color='#FF5001'>" + orderBean.getTotal_quantity() + "</font> 件商品";
		total += "，共 <font color='#FF5001'>￥ " + orderBean.getTotal_amount() + "</font> 元";

		holder.infoTextView.setText(Html.fromHtml(total));

		holder.optionTextView.setOnClickListener(null);
		holder.operaTextView.setOnClickListener(null);
		holder.mLinearLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

			}
		});
		switch (orderBean.getStatus()) {
			case Constant.ORDER_STATUS_UNPAY://未付款
				holder.rlDeal.setVisibility(View.VISIBLE);
				holder.optionTextView.setText("取消订单");
				holder.operaTextView.setText("去支付");
				holder.optionTextView.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						//取消订单
						MyToastView.showToast("开发中",mActivity);
					}
				});
				holder.operaTextView.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						//去付款
						Intent intentBuy2 = new Intent();
						intentBuy2.setClass(mActivity,BuySetup2Activity.class);
						intentBuy2.putExtra("data",orderBean);
						intentBuy2.putExtra("pay_sn","online");
						mActivity.startActivity(intentBuy2);
					}
				});
				break;
			case Constant.ORDER_STATUS_PAYED://已付款
				holder.rlDeal.setVisibility(View.VISIBLE);
				holder.optionTextView.setText("确认收货");
				holder.operaTextView.setText("退货/款");
				holder.optionTextView.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						//订单详细
//						MyToastView.showToast("开发中",mActivity);



					}
				});
				holder.operaTextView.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						//退货/款
						MyToastView.showToast("开发中",mActivity);
					}
				});
				break;
			case Constant.ORDER_STATUS_FINISH:
				holder.rlDeal.setVisibility(View.GONE);
				break;
			default:
				break;
		}
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup group, int viewType) {
		View view = LayoutInflater.from(group.getContext()).inflate(R.layout.item_list_order, group, false);
		return new ViewHolder(view);
	}

	public static class ViewHolder extends RecyclerView.ViewHolder {

		public LinearLayout mLinearLayout;
		public RelativeLayout rlDeal;
		public TextView storeTextView;
		public TextView stateTextView;
		public XRecyclerView mListView;
		public TextView infoTextView;
		public TextView optionTextView;
		public TextView operaTextView;

		public ViewHolder(View view) {
			super(view);

			mLinearLayout = (LinearLayout) view.findViewById(R.id.mainLinearLayout);
			storeTextView = (TextView) view.findViewById(R.id.storeTextView);
			stateTextView = (TextView) view.findViewById(R.id.stateTextView);
			mListView =  view.findViewById(R.id.mainListView);

			infoTextView = (TextView) view.findViewById(R.id.infoTextView);
			optionTextView = (TextView) view.findViewById(R.id.optionTextView);
			operaTextView = (TextView) view.findViewById(R.id.operaTextView);
			rlDeal = (RelativeLayout) view.findViewById(R.id.rlDeal);

		}

	}

	public void setOnItemChange(onItemChange itemChange) {
		this.mOnItemChange = itemChange;
	}

	public interface onItemChange {
		void onChange();
	}

}
