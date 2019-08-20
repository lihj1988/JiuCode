package com.jiuwang.buyer.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jiuwang.buyer.R;
import com.jiuwang.buyer.bean.LotteryRecordBean;

import java.util.List;

/**
 * author：lihj
 * desc：抽奖记录
 * Created by lihj on  2019/8/20 13:36.
 */



public class LotteryRecordAdapter extends RecyclerView.Adapter<LotteryRecordAdapter.ViewHolder>{

	private List<LotteryRecordBean> recordBeanList;

	public LotteryRecordAdapter(List<LotteryRecordBean> recordBeanList) {
		this.recordBeanList = recordBeanList;
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lottery_record, parent, false);
		return new ViewHolder(view);
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		holder.period.setText(recordBeanList.get(position).getPeriod());
		holder.money.setText(recordBeanList.get(position).getAmount());
		holder.tvDate.setText(recordBeanList.get(position).getCreate_time());
	}

	@Override
	public int getItemCount() {
		return recordBeanList.size();
	}

	public static class ViewHolder extends RecyclerView.ViewHolder {

		public TextView money;
		public TextView tvDate;
		public TextView period;


		public ViewHolder(View view) {
			super(view);
			period = view.findViewById(R.id.period);
			money = view.findViewById(R.id.money);
			tvDate = view.findViewById(R.id.tvDate);

		}

	}
}


