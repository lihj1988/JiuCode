package com.jiuwang.buyer.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jiuwang.buyer.R;
import com.jiuwang.buyer.bean.ProjectDetailBean;
import com.jiuwang.buyer.constant.Constant;
import com.jiuwang.buyer.util.PreforenceUtils;

import java.util.List;

/**
 * Created by lihj on 2019/8/9
 * desc:
 */

public class ProjectDetailsAdapter extends RecyclerView.Adapter<ProjectDetailsAdapter.ViewHolder> {

	private Context context;
	private List<ProjectDetailBean> list;

	public ProjectDetailsAdapter(Context context, List<ProjectDetailBean> list) {
		this.context = context;
		this.list = list;
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(context).inflate(R.layout.item_my_project_record, parent, false);
		return new ProjectDetailsAdapter.ViewHolder(view);
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		if (Constant.ISWIN.equals(list.get(position).getIs_win())) {
			String loginName = PreforenceUtils.getStringData("loginInfo", "userID");
			if(list.get(position).getCreate_user().equals(loginName)){
				holder.tvIsWin.setText("已中奖");
				holder.tvIsWin.setTextColor(context.getResources().getColor(R.color.red));
				holder.tvUserName.setTextColor(context.getResources().getColor(R.color.red));
			}

		} else {
			holder.tvIsWin.setText("未中奖");
		}
		String replace = list.get(position).getCreate_user().substring(3, 9);
		String newStr = list.get(position).getCreate_user().replace(replace, "******");
		holder.tvUserName.setText(newStr);
	}

	@Override
	public int getItemCount() {
		return list.size();
	}

	public static class ViewHolder extends RecyclerView.ViewHolder {


		public TextView tvUserName;
		public TextView tvIsWin;


		public ViewHolder(View view) {
			super(view);
			tvUserName = view.findViewById(R.id.tvUserName);
			tvIsWin = view.findViewById(R.id.tvIsWin);

		}

	}
}
