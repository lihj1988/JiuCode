package com.jiuwang.buyer.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jiuwang.buyer.R;
import com.jiuwang.buyer.bean.InviteBean;
import com.jiuwang.buyer.bean.UserBean;

import java.util.List;

/**
 * Created by lihj on 2019/7/23
 * desc:
 */

public class InviteManagerAdapter extends RecyclerView.Adapter<InviteManagerAdapter.ViewHolder>{
	private Context context;
	private List<InviteBean> inviteBeanList;

	public InviteManagerAdapter(Context context, List<InviteBean> inviteBeanList) {
		this.context = context;
		this.inviteBeanList = inviteBeanList;
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_invitemanager, parent, false);
		return new InviteManagerAdapter.ViewHolder(view);
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		holder.tvUserName.setText(inviteBeanList.get(position).getUser_cd());
	}

	@Override
	public int getItemCount() {
		return inviteBeanList.size();
	}

	public static class ViewHolder extends RecyclerView.ViewHolder {

		public TextView tvUserName;

		public ViewHolder(View view) {
			super(view);
			tvUserName = (TextView) view.findViewById(R.id.tvUserName);
		}
	}
}
