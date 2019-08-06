package com.jiuwang.buyer.adapter;

import android.content.Context;
import android.content.Intent;
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
import com.jiuwang.buyer.activity.LoginActivity;
import com.jiuwang.buyer.activity.OrderDetailedActivity;
import com.jiuwang.buyer.base.MyApplication;
import com.jiuwang.buyer.bean.OrderBean;
import com.jiuwang.buyer.constant.Constant;
import com.jiuwang.buyer.entity.BaseResultEntity;
import com.jiuwang.buyer.net.CommonHttpUtils;
import com.jiuwang.buyer.util.AppUtils;
import com.jiuwang.buyer.util.MyToastView;

import java.util.HashMap;
import java.util.List;

/**
 * 订单适配器
 */
public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.ViewHolder> {

	private Context context;
	private onItemChange mOnItemChange;

	private List<OrderBean> mArrayList;

	public OrderListAdapter(Context context, List<OrderBean> arrayList) {

		this.mArrayList = arrayList;
		this.context = context;
		this.mOnItemChange = null;
	}

	@Override
	public int getItemCount() {
		return mArrayList == null ? 0 : mArrayList.size();
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, final int position) {

		final OrderBean orderBean = mArrayList.get(position);

//		holder.mListView.setLayoutManager(new LinearLayoutManager(mActivity));
		AppUtils.initListView(MyApplication.getInstance(), holder.mListView, false, false);
		holder.mListView.setAdapter(new GoodsOrderListAdapter(context, orderBean.getDetail_list()));
		holder.storeTextView.setText(orderBean.getGrouping_name_seller());
		holder.stateTextView.setText(orderBean.getNotes());
		holder.tvOrderId.setText(orderBean.getId());
		holder.stateTextView.setText(orderBean.getStatus_name());

		String total = "共 <font color='#FF5001'>" + orderBean.getTotal_quantity() + "</font> 件商品";
		total += "，共 <font color='#FF5001'>￥ " + orderBean.getTotal_amount() + "</font> 元";

		holder.infoTextView.setText(Html.fromHtml(total));
		holder.rlDeal.setVisibility(View.GONE);

		holder.optionTextView.setOnClickListener(null);
		holder.operaTextView.setOnClickListener(null);
		holder.mLinearLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent();
				intent.putExtra("data",orderBean);
				intent.setClass(context, OrderDetailedActivity.class);
				context.startActivity(intent);
			}
		});
		if (!"1".equals(orderBean.getOrder_type())) {
			switch (orderBean.getStatus()) {
				case Constant.ORDER_STATUS_UNPAY://未付款
					holder.rlDeal.setVisibility(View.VISIBLE);
					holder.optionTextView.setText("取消订单");
					holder.optionTextView.setVisibility(View.INVISIBLE);
					holder.operaTextView.setText("去支付");
					holder.optionTextView.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View view) {
							//取消订单
//						MyToastView.showToast("开发中",context);
							orderInfo(orderBean,Constant.ACTION_ACT_CANCLE);
						}
					});
					holder.operaTextView.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View view) {
							//去付款
							Intent intentBuy2 = new Intent();
							intentBuy2.setClass(context, BuySetup2Activity.class);
							intentBuy2.putExtra("data", orderBean);
							intentBuy2.putExtra("pay_sn", "online");
							context.startActivity(intentBuy2);
						}
					});
					break;
				case Constant.ORDER_STATUS_PAYED://已付款
					holder.rlDeal.setVisibility(View.VISIBLE);
					holder.optionTextView.setVisibility(View.GONE);
					holder.operaTextView.setText("退货/款");
					holder.operaTextView.setVisibility(View.INVISIBLE);
					holder.operaTextView.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View view) {
							//退货/款
							orderInfo(orderBean,"");
						}
					});
					break;
				case Constant.ORDER_STATUS_SEND:
					holder.rlDeal.setVisibility(View.VISIBLE);
					holder.optionTextView.setVisibility(View.GONE);
					holder.operaTextView.setText("确认收货");
					holder.operaTextView.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View view) {
							//确认收货
							MyToastView.showToast("开发中", context);
							orderInfo(orderBean,"");
						}
					});
					break;
				case Constant.ORDER_STATUS_FINISH:
					holder.rlDeal.setVisibility(View.GONE);
					break;
				default:
					break;
			}
		}else {
			switch (orderBean.getStatus()) {
				case Constant.ORDER_STATUS_UNPAY:
					holder.rlDeal.setVisibility(View.GONE);
					break;
				case Constant.ORDER_STATUS_PAYED:
					holder.rlDeal.setVisibility(View.GONE);
					break;
				case Constant.ORDER_STATUS_SEND:
					holder.rlDeal.setVisibility(View.VISIBLE);
					holder.optionTextView.setVisibility(View.GONE);
					holder.operaTextView.setText("确认收货");
					holder.operaTextView.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View view) {
							//确认收货
							MyToastView.showToast("开发中", context);
							orderInfo(orderBean,"");
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

	}

	private void orderInfo(OrderBean orderBean,String act) {
		HashMap<String, String> map = new HashMap<>();
		map.put("id", orderBean.getId());
		map.put("act", act);
		CommonHttpUtils.orderInfo(map, new CommonHttpUtils.CallingBack() {
			@Override
			public void successBack(BaseResultEntity baseResultEntity) {
				if (Constant.HTTP_SUCCESS_CODE.equals(baseResultEntity.getCode())) {
					Intent intent = new Intent();
					intent.setAction("refreshOrder");
					context.sendBroadcast(intent);

				} else if (Constant.HTTP_LOGINOUTTIME_CODE.equals(baseResultEntity.getCode())) {
					MyToastView.showToast(baseResultEntity.getMsg(),context);
					context.startActivity(new Intent(context,LoginActivity.class));
					MyApplication.currentActivity.finish();
				}else {
					MyToastView.showToast(baseResultEntity.getMsg(),context);
				}
			}

			@Override
			public void failBack() {
				MyToastView.showToast(context.getResources().getString(R.string.msg_error_operation),context);
			}
		});
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
		public TextView tvOrderId;

		public ViewHolder(View view) {
			super(view);

			mLinearLayout = (LinearLayout) view.findViewById(R.id.mainLinearLayout);
			storeTextView = (TextView) view.findViewById(R.id.storeTextView);
			stateTextView = (TextView) view.findViewById(R.id.stateTextView);
			mListView = view.findViewById(R.id.mainListView);

			infoTextView = (TextView) view.findViewById(R.id.infoTextView);
			optionTextView = (TextView) view.findViewById(R.id.optionTextView);
			operaTextView = (TextView) view.findViewById(R.id.operaTextView);
			tvOrderId = (TextView) view.findViewById(R.id.tvOrderId);
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
