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
import com.jiuwang.buyer.util.CommonUtil;

import java.util.List;

/**
 * Created by lihj on 2019/6/25
 * desc:
 */

public class ProjectListAdapter  extends RecyclerView.Adapter<ProjectListAdapter.ViewHolder> {

	private Context context;
	private List<ProjectBean> projectList;
	private ProjectItemOnClickListener projectItemOnClickListener;
	public ProjectListAdapter(Context context, List<ProjectBean> projectList,ProjectItemOnClickListener projectItemOnClickListener) {
		this.context = context;
		this.projectList = projectList;
		this.projectItemOnClickListener = projectItemOnClickListener;
	}

	@Override
	public ProjectListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(context).inflate(R.layout.item_project_list, parent, false);
		return new ViewHolder(view);
	}

	@Override
	public void onBindViewHolder(ProjectListAdapter.ViewHolder holder, final int position) {
		holder.tvProjectName.setText(projectList.get(position).getProject_name());

		holder.llItem.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				projectItemOnClickListener.itemOnClick(position);
			}
		});
		holder.tvSalePeice.setText(projectList.get(position).getSale_price());

		if(CommonUtil.getTimeCompareSize(CommonUtil.getNowTime(),projectList.get(position).getStart_time())==1){
			holder.tvTimeName.setText("距离开始：");

		}else {
			holder.tvTimeName.setText("距离结束：");

		}

//		CommonUtil.loadImage(context, NetURL.PIC_BASEURL+projectList.get(position).getPic_url(),holder.ivPic);

	}

	@Override
	public int getItemCount() {
		return projectList.size();
	}
	public static class ViewHolder extends RecyclerView.ViewHolder {

		public ImageView ivPic;
		public TextView tvProjectName;
		public TextView tvHour;
		public TextView tvMin;
		public TextView tvSec;
		public TextView tvTimeName;
		public TextView tvSalePeice;
		public LinearLayout llItem;


		public ViewHolder(View view) {
			super(view);

			ivPic = view.findViewById(R.id.ivPic);
			tvProjectName = view.findViewById(R.id.tvProjectName);
			tvHour = view.findViewById(R.id.tvHour);
			tvTimeName = view.findViewById(R.id.tvTimeName);
			tvSalePeice = view.findViewById(R.id.tvSalePeice);
			tvMin = view.findViewById(R.id.tvMin);
			tvSec = view.findViewById(R.id.tvSec);
			llItem = view.findViewById(R.id.llItem);

		}

	}

	public interface ProjectItemOnClickListener {
		abstract void itemOnClick(int position);

	}


}
