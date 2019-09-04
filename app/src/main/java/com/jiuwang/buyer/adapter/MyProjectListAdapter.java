package com.jiuwang.buyer.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jiuwang.buyer.R;
import com.jiuwang.buyer.bean.ProjectBean;
import com.jiuwang.buyer.constant.Constant;
import com.jiuwang.buyer.util.CommonUtil;

import java.util.List;

/**
 * Created by lihj on 2019/6/25
 * desc:抢购项目适配器
 */

public class MyProjectListAdapter extends RecyclerView.Adapter<MyProjectListAdapter.ViewHolder> {

	private static final String TAG = MyProjectListAdapter.class.getName();
	private Context context;
	private List<ProjectBean> projectList;
	private ProjectItemOnClickListener projectItemOnClickListener;

	public MyProjectListAdapter(Context context, List<ProjectBean> projectList, ProjectItemOnClickListener projectItemOnClickListener) {
		this.context = context;
		this.projectList = projectList;
		this.projectItemOnClickListener = projectItemOnClickListener;
	}

	@Override
	public MyProjectListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(context).inflate(R.layout.item_my_project_list, parent, false);
		return new ViewHolder(view);
	}

	@Override
	public void onBindViewHolder(final MyProjectListAdapter.ViewHolder holder, final int position) {

		if (Constant.ISWIN.equals(projectList.get(position).getIs_win())) {
			holder.tvReport.setText("已中奖");
			holder.tvReport.setTextColor(context.getResources().getColor(R.color.red));
		} else {
			holder.tvReport.setText("未中奖");
		}

		holder.tvProjectName.setText(projectList.get(position).getProject_name());
		holder.tvSalePeice.setText("￥" + CommonUtil.decimalFormat(Double.parseDouble(projectList.get(position).getSale_price().equals("") ? "0" : projectList.get(position).getSale_price()), "0") + "");

		holder.llItem.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				projectItemOnClickListener.itemOnClick(position);
			}
		});
		CommonUtil.loadImage(context,R.drawable.ic_project,holder.ivPic);
//		CommonUtil.loadImage(context, NetURL.PIC_BASEURL+projectList.get(position).getPic_url(),holder.ivPic);

	}

	@Override
	public int getItemCount() {
		return projectList == null ? 0 : projectList.size();

	}

	public static class ViewHolder extends RecyclerView.ViewHolder {

		public ImageView ivPic;
		public TextView tvProjectName;
		public TextView tvSalePeice;
		public TextView tvReport;
		public LinearLayout llItem;

		public ViewHolder(View view) {
			super(view);
			ivPic = view.findViewById(R.id.ivPic);
			tvProjectName = view.findViewById(R.id.tvProjectName);
			tvSalePeice = view.findViewById(R.id.tvSalePeice);
			llItem = view.findViewById(R.id.llItem);
			tvReport = view.findViewById(R.id.tvReport);
		}

	}

	public interface ProjectItemOnClickListener {
		abstract void itemOnClick(int position);

	}
}
