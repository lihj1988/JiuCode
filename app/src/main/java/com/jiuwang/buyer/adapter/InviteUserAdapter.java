package com.jiuwang.buyer.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jiuwang.buyer.R;
import com.jiuwang.buyer.bean.InviteUserBean;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by lihj on 2019/8/11
 * desc:
 */

public class InviteUserAdapter extends BaseAdapter {
	private Context context;
	private List<InviteUserBean> userBeanList;

	public InviteUserAdapter(Context context, List<InviteUserBean> userBeanList) {
		this.context = context;
		this.userBeanList = userBeanList;
	}

	@Override
	public int getCount() {
		return userBeanList.size();
	}

	@Override
	public Object getItem(int position) {
		return userBeanList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if(convertView==null){
			convertView = View.inflate(context, R.layout.item_invitemanager, null);
			viewHolder = new ViewHolder(convertView);
			convertView.setTag(viewHolder);
		}else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.tvUserName.setText(userBeanList.get(position).getUser_cd());
		viewHolder.tvTime.setText(userBeanList.get(position).getCreate_time());
		return convertView;
	}

	static class ViewHolder {
		@Bind(R.id.tvUserName)
		TextView tvUserName;
		@Bind(R.id.tvTime)
		TextView tvTime;

		ViewHolder(View view) {
			ButterKnife.bind(this, view);
		}
	}
}
