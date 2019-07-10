package com.jiuwang.buyer.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
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
import com.jiuwang.buyer.util.MyToastView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by lihj on 2019/6/25
 * desc:
 */

public class ProjectListAdapter extends RecyclerView.Adapter<ProjectListAdapter.ViewHolder> {

	private static final String TAG = ProjectListAdapter.class.getName();
	private Context context;
	private List<ProjectBean> projectList;
	private ProjectItemOnClickListener projectItemOnClickListener;

	public ProjectListAdapter(Context context, List<ProjectBean> projectList, ProjectItemOnClickListener projectItemOnClickListener) {
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

		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			if (CommonUtil.getTimeCompareSize(CommonUtil.getNowTime(), projectList.get(position).getStart_time()) != 1) {
				holder.llTime.setVisibility(View.VISIBLE);
				holder.tvTimeName.setText("距离开始：");
				long currentTime = System.currentTimeMillis();
				//转成Date
				Date date = new Date(currentTime);
				//获取当前时间戳
				date.getTime();
				//定义 yyyy-MM-dd HH:mm:ss的格式

				//格林尼治+或-
				df.setTimeZone(TimeZone.getTimeZone("GMT+8"));
				Date parse = df.parse(projectList.get(position).getStart_time());
				long time = parse.getTime();

				long l = time - currentTime;
//				LogUtils.e(TAG, CommonUtil.longToString(l) + "");
				holder.tvDay.setText(String.valueOf(CommonUtil.longToString(l)[0]).length()==1?"0"+String.valueOf(CommonUtil.longToString(l)[0]):String.valueOf(CommonUtil.longToString(l)[0]));
				holder.tvHour.setText(String.valueOf(CommonUtil.longToString(l)[1]).length()==1?"0"+String.valueOf(CommonUtil.longToString(l)[1]):String.valueOf(CommonUtil.longToString(l)[1]));
				holder.tvMin.setText(String.valueOf(CommonUtil.longToString(l)[2]).length()==1?"0"+String.valueOf(CommonUtil.longToString(l)[2]):String.valueOf(CommonUtil.longToString(l)[2]));
				holder.tvSec.setText(String.valueOf(CommonUtil.longToString(l)[3]).length()==1?"0"+String.valueOf(CommonUtil.longToString(l)[3]):String.valueOf(CommonUtil.longToString(l)[3]));
				exeTimer( l,  position,holder);
				holder.llItem.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {

						MyToastView.showToast("活动还没有开始",context);
					}
				});
			} else {
				if(!"".equals(projectList.get(position).getStop_time())){
					if (CommonUtil.getTimeCompareSize(CommonUtil.getNowTime(), projectList.get(position).getStop_time()) != 1) {
						holder.llTime.setVisibility(View.VISIBLE);
						holder.tvTimeName.setText("距离结束：");
						long currentTime = System.currentTimeMillis();
						//转成Date
						Date date = new Date(currentTime);
						//获取当前时间戳
						date.getTime();
						//定义 yyyy-MM-dd HH:mm:ss的格式
						//格林尼治+或-
						df.setTimeZone(TimeZone.getTimeZone("GMT+8"));
						Date parse = df.parse(projectList.get(position).getStop_time());
						long time = parse.getTime();
						long l = time - currentTime;
//					LogUtils.e(TAG, CommonUtil.longToString(l) + "");
						holder.tvDay.setText(String.valueOf(CommonUtil.longToString(l)[0]).length()==1?"0"+String.valueOf(CommonUtil.longToString(l)[0]):String.valueOf(CommonUtil.longToString(l)[0]));
						holder.tvHour.setText(String.valueOf(CommonUtil.longToString(l)[1]).length()==1?"0"+String.valueOf(CommonUtil.longToString(l)[1]):String.valueOf(CommonUtil.longToString(l)[1]));
						holder.tvMin.setText(String.valueOf(CommonUtil.longToString(l)[2]).length()==1?"0"+String.valueOf(CommonUtil.longToString(l)[2]):String.valueOf(CommonUtil.longToString(l)[2]));
						holder.tvSec.setText(String.valueOf(CommonUtil.longToString(l)[3]).length()==1?"0"+String.valueOf(CommonUtil.longToString(l)[3]):String.valueOf(CommonUtil.longToString(l)[3]));
						exeTimer( l,  position,holder);
						holder.llItem.setOnClickListener(new View.OnClickListener() {
							@Override
							public void onClick(View v) {

								projectItemOnClickListener.itemOnClick(position);
							}
						});
					} else {
						holder.llTime.setVisibility(View.INVISIBLE);
						holder.tvTimeName.setText("已结束");
						holder.llItem.setOnClickListener(new View.OnClickListener() {
							@Override
							public void onClick(View v) {

								MyToastView.showToast("活动已结束",context);
							}
						});
					}
				}else {
					holder.llTime.setVisibility(View.INVISIBLE);
					holder.tvTimeName.setText("距离结束：");
				}



			}
			holder.tvProjectName.setText(projectList.get(position).getProject_name());
			holder.tvSalePeice.setText(projectList.get(position).getSale_price());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		holder.llItem.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				projectItemOnClickListener.itemOnClick(position);
			}
		});
//		CommonUtil.loadImage(context, NetURL.PIC_BASEURL+projectList.get(position).getPic_url(),holder.ivPic);

	}

	@Override
	public int getItemCount() {
		return projectList.size();
	}

	public static class ViewHolder extends RecyclerView.ViewHolder {

		public ImageView ivPic;
		public TextView tvProjectName;
		public TextView tvDay;
		public TextView tvHour;
		public TextView tvMin;
		public TextView tvSec;
		public TextView tvTimeName;
		public TextView tvSalePeice;
		public LinearLayout llItem;
		public LinearLayout llTime;


		public ViewHolder(View view) {
			super(view);

			ivPic = view.findViewById(R.id.ivPic);
			tvProjectName = view.findViewById(R.id.tvProjectName);
			tvDay = view.findViewById(R.id.tvDay);
			tvHour = view.findViewById(R.id.tvHour);
			tvTimeName = view.findViewById(R.id.tvTimeName);
			tvSalePeice = view.findViewById(R.id.tvSalePeice);
			tvMin = view.findViewById(R.id.tvMin);
			tvSec = view.findViewById(R.id.tvSec);
			llItem = view.findViewById(R.id.llItem);
			llTime = view.findViewById(R.id.llTime);

		}

	}

	public interface ProjectItemOnClickListener {
		abstract void itemOnClick(int position);

	}



	private void exeTimer(long time, final int position,ProjectListAdapter.ViewHolder holder) {
		new MyCountDownTimer(time, 1000,position,holder) {


			@Override
			public void onFinish() {
				//Log.v("CountDownTimerTest", "onFinish");

				projectList.get(position).setStatus_name("已结束");
				notifyDataSetChanged();
				Intent intent = new Intent();
				intent.setAction("refreshProject");
				context.sendBroadcast(intent);

			}
		}.start();

	}

	public class MyCountDownTimer extends CountDownTimer {

		private int position;
		private ProjectListAdapter.ViewHolder holder;

		public MyCountDownTimer(long millisInFuture, long countDownInterval, int position,ProjectListAdapter.ViewHolder holder) {
			super(millisInFuture, countDownInterval);
			this.position = position;
			this.holder = holder;
		}

		@Override
		public void onTick(long millisUntilFinished) {
			// Log.v("CountDownTimerTest", "onTick millisUntilFinished = " + millisUntilFinished);

			// Log.v("CountDownTimerTest", "onTick millisUntilFinished = " + millisUntilFinished);
//			projectList.get(position).end_date = millisUntilFinished + "";
			//if (position == 0) {
//			notifyItemChanged(position);
//			notifyDataSetChanged();
			//}
			long[] longs = CommonUtil.longToString(millisUntilFinished);
			holder.tvDay.setText(String.valueOf(longs[0]).length()==1?"0"+String.valueOf(longs[0]):String.valueOf(longs[0]));
			holder.tvHour.setText(String.valueOf(longs[1]).length()==1?"0"+String.valueOf(longs[1]):String.valueOf(longs[1]));
			holder.tvMin.setText(String.valueOf(longs[2]).length()==1?"0"+String.valueOf(longs[2]):String.valueOf(longs[2]));
			holder.tvSec.setText(String.valueOf(longs[3]).length()==1?"0"+String.valueOf(longs[3]):String.valueOf(longs[3]));

		}

		@Override
		public void onFinish() {
			//Log.v("CountDownTimerTest", "onFinish");

			projectList.get(position).setStatus_name("已结束");
			notifyDataSetChanged();

		}

	}


}
