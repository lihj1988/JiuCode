package com.jiuwang.buyer.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jiuwang.buyer.R;
import com.jiuwang.buyer.bean.BalanceBean;
import com.jiuwang.buyer.constant.Constant;

import java.util.List;

/**
 * Created by lihj on 2019/7/18
 * desc:
 */

public class BalanceListAdapter extends RecyclerView.Adapter<BalanceListAdapter.ViewHolder> {

	private Context context;
	private List<BalanceBean> balanceList;
	private ItemOnClickListener itemOnClickListener;

	public BalanceListAdapter(Context context, List<BalanceBean> balanceList, ItemOnClickListener itemOnClickListener) {
		this.context = context;
		this.balanceList = balanceList;
		this.itemOnClickListener = itemOnClickListener;
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_balance_details, parent, false);
		return new BalanceListAdapter.ViewHolder(view);
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, final int position) {


		holder.tvFundType.setText(balanceList.get(position).getFund_type_name());


		holder.tvTime.setText(balanceList.get(position).getCreate_time());
		holder.tvAmount.setText("金额：" + balanceList.get(position).getAmount());
		holder.llItem.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				itemOnClickListener.itemOnClick(position);
			}
		});

		switch (balanceList.get(position).getPay_mode()) {
			case Constant.PAY_MODE_ALI:
				holder.tvPayMode.setText("支付宝");
				break;
			case Constant.PAY_MODE_WX:
				holder.tvPayMode.setText("微信");
				break;
			case Constant.PAY_MODE_BALANCE:
				holder.tvPayMode.setText("余额");
				break;

		}
	}

	@Override
	public int getItemCount() {
		return balanceList.size();
	}

	public static class ViewHolder extends RecyclerView.ViewHolder {


		public TextView tvFundType;
		public TextView tvTime;
		public TextView tvAmount;
		public TextView tvPayMode;
		public LinearLayout llItem;

		public ViewHolder(View view) {
			super(view);


			tvFundType = view.findViewById(R.id.tvFundType);
			tvTime = view.findViewById(R.id.tvTime);
			tvAmount = view.findViewById(R.id.tvAmount);
			tvPayMode = view.findViewById(R.id.tvPayMode);
			llItem = view.findViewById(R.id.llItem);

		}

	}

	public interface ItemOnClickListener {
		abstract void itemOnClick(int position);

	}
}
