package com.jiuwang.buyer.adapter;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jiuwang.buyer.R;
import com.jiuwang.buyer.base.MyApplication;
import com.jiuwang.buyer.bean.OrderBean;
import com.jiuwang.buyer.constant.Constant;
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

		holder.mListView.setLayoutManager(new LinearLayoutManager(mActivity));
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
			case Constant.ORDER_STATUS_UNPAY:
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
					}
				});
				break;
			case Constant.ORDER_STATUS_PAYED:
				holder.rlDeal.setVisibility(View.VISIBLE);
				holder.optionTextView.setText("订单详细");
				holder.operaTextView.setText("退货/款");
				holder.optionTextView.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						//订单详细
						MyToastView.showToast("开发中",mActivity);
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

//            switch (orderBean.getOrder_state()) {
//                case "0":
//                    holder.optionTextView.setText("删除订单");
//                    holder.operaTextView.setText("订单详细");
//                    holder.optionTextView.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            ToastUtil.show(mActivity, "开发中");
//                        }
//                    });
//                    holder.operaTextView.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            Intent intent = new Intent(mActivity, OrderDetailedActivity.class);
//                            intent.putExtra("order_id", orderBean.getOrder_id());
//                            mApplication.startActivity(mActivity, intent);
//                        }
//                    });
//                    break;
//                case "1":
//                    holder.optionTextView.setText("取消订单");
//                    holder.operaTextView.setText("去支付");
//                    holder.optionTextView.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            DialogUtil.query(
//                                    mActivity,
//                                    "确认您的选择",
//                                    "取消订单",
//                                    new View.OnClickListener() {
//                                        @Override
//                                        public void onClick(View view) {
//                                            DialogUtil.cancel();
//                                            DialogUtil.progress(mActivity);
////                                            KeyAjaxParams ajaxParams = new KeyAjaxParams(mApplication);
////                                            ajaxParams.putAct("member_order");
////                                            ajaxParams.putOp("order_cancel");
////                                            ajaxParams.put("order_id", orderHashMap.get("order_id"));
////                                            mApplication.mFinalHttp.post(mApplication.apiUrlString, ajaxParams, new AjaxCallBack<Object>() {
////                                                @Override
////                                                public void onSuccess(Object o) {
////                                                    super.onSuccess(o);
////                                                    if (TextUtil.isJson(o.toString())) {
////                                                        String error = mApplication.getJsonError(o.toString());
////                                                        if (TextUtil.isEmpty(error)) {
////                                                            String data = mApplication.getJsonData(o.toString());
////                                                            if (data.equals("1")) {
////                                                                ToastUtil.showSuccess(mActivity);
////                                                                if (mOnItemChange != null) {
////                                                                    mOnItemChange.onChange();
////                                                                }
////                                                            } else {
////                                                                ToastUtil.showFailure(mActivity);
////                                                            }
////                                                        } else {
////                                                            ToastUtil.showFailure(mActivity);
////                                                        }
////                                                    } else {
////                                                        ToastUtil.showFailure(mActivity);
////                                                    }
////                                                }
////
////                                                @Override
////                                                public void onFailure(Throwable t, int errorNo, String strMsg) {
////                                                    super.onFailure(t, errorNo, strMsg);
////                                                    ToastUtil.showFailure(mActivity);
////                                                    DialogUtil.cancel();
////                                                }
////                                            });
//                                        }
//                                    }
//                            );
//                        }
//                    });
//                    holder.operaTextView.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
////                            Intent intent = new Intent(mActivity, BuySetup2Activity.class);
////                            intent.putExtra("pay_sn", hashMap.get("pay_sn"));
////                            intent.putExtra("payment_code", orderHashMap.get("payment_code"));
////                            mApplication.startActivity(mActivity, intent);
//                        }
//                    });
//                    break;
//                case "2":
//                    holder.optionTextView.setText("订单详细");
//                    holder.operaTextView.setText("退货/款");
//                    holder.optionTextView.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            Intent intent = new Intent(mActivity, OrderDetailedActivity.class);
//                            intent.putExtra("order_id", orderBean.getOrder_id());
//                            mApplication.startActivity(mActivity, intent);
//                        }
//                    });
//                    holder.operaTextView.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            ToastUtil.show(mActivity, "开发中...");
//                        }
//                    });
//                    break;
//                case "3":
//                    holder.optionTextView.setText("查看物流");
//                    holder.operaTextView.setText("确认收货");
//                    holder.optionTextView.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            ToastUtil.show(mActivity, "开发中");
//                        }
//                    });
//                    holder.operaTextView.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            DialogUtil.query(
//                                    mActivity,
//                                    "确认您的选择",
//                                    "请确认您已经收到货品，确认收货，货款会支付给卖家",
//                                    new View.OnClickListener() {
//                                        @Override
//                                        public void onClick(View view) {
//                                            DialogUtil.cancel();
//                                            DialogUtil.progress(mActivity);
////                                            KeyAjaxParams ajaxParams = new KeyAjaxParams(mApplication);
////                                            ajaxParams.putAct("member_order");
////                                            ajaxParams.putOp("order_receive");
////                                            ajaxParams.put("order_id", orderHashMap.get("order_id"));
////                                            mApplication.mFinalHttp.post(mApplication.apiUrlString, ajaxParams, new AjaxCallBack<Object>() {
////                                                @Override
////                                                public void onSuccess(Object o) {
////                                                    super.onSuccess(o);
////                                                    DialogUtil.cancel();
////                                                    if (TextUtil.isJson(o.toString())) {
////                                                        String error = mApplication.getJsonError(o.toString());
////                                                        if (TextUtil.isEmpty(error)) {
////                                                            String data = mApplication.getJsonData(o.toString());
////                                                            if (data.equals("1")) {
////                                                                ToastUtil.showSuccess(mActivity);
////                                                                if (mOnItemChange != null) {
////                                                                    mOnItemChange.onChange();
////                                                                }
////                                                            } else {
////                                                                ToastUtil.showFailure(mActivity);
////                                                            }
////                                                        } else {
////                                                            ToastUtil.showFailure(mActivity);
////                                                        }
////                                                    } else {
////                                                        ToastUtil.showFailure(mActivity);
////                                                    }
////                                                }
////
////                                                @Override
////                                                public void onFailure(Throwable t, int errorNo, String strMsg) {
////                                                    super.onFailure(t, errorNo, strMsg);
////                                                    ToastUtil.showFailure(mActivity);
////                                                    DialogUtil.cancel();
////                                                }
////                                            });
//                                        }
//                                    }
//                            );
//                        }
//                    });
//                    break;
//                case "4":
//                    holder.optionTextView.setText("退款/货");
//                    holder.optionTextView.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            ToastUtil.show(mActivity, "开发中...");
//                        }
//                    });
////                    if (orderHashMap.get("evaluation_state").equals("0")) {
////                        holder.operaTextView.setText("评价");
////                        holder.operaTextView.setOnClickListener(new View.OnClickListener() {
////                            @Override
////                            public void onClick(View view) {
////                                Intent intent = new Intent(mActivity, OrderEvaluateActivity.class);
////                                intent.putExtra("order_id", orderHashMap.get("order_id"));
////                                mApplication.startActivity(mActivity, intent);
////                            }
////                        });
////                    } else {
////                        holder.operaTextView.setText("已评价");
////                    }
//                    break;
//                default:
//                    break;
//            }

//        } catch (JSONException e) {
//            e.printStackTrace();
//        }

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
		public RecyclerView mListView;
		public TextView infoTextView;
		public TextView optionTextView;
		public TextView operaTextView;

		public ViewHolder(View view) {
			super(view);

			mLinearLayout = (LinearLayout) view.findViewById(R.id.mainLinearLayout);
			storeTextView = (TextView) view.findViewById(R.id.storeTextView);
			stateTextView = (TextView) view.findViewById(R.id.stateTextView);
			mListView = (RecyclerView) view.findViewById(R.id.mainListView);
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
