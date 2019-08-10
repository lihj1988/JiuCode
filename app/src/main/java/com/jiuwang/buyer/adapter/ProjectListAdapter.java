package com.jiuwang.buyer.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
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
import com.jiuwang.buyer.util.MyToastView;

import org.greenrobot.eventbus.EventBus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by lihj on 2019/6/25
 * desc:抢购项目适配器
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
	public void onBindViewHolder(final ProjectListAdapter.ViewHolder holder, final int position) {
		holder.type = "";
		try {
			if (Constant.PROJECTIS_NOT_PART.equals(projectList.get(position).getIs_part())) {
				holder.tvReport.setText("未报名");
				holder.tvReport.setTextColor(context.getResources().getColor(R.color.brown));
//				projectItemOnClickListener.itemOnClick(position);
			} else {
				holder.tvReport.setText("已报名");
				holder.tvReport.setTextColor(context.getResources().getColor(R.color.red));
//				holder.tvReport.setTextColor(context.getColor(R.color.red));
			}
			if ("2".equals(projectList.get(position).getStatus())) {
				holder.llTime.setVisibility(View.INVISIBLE);
				holder.tvTimeName.setText("已结束");
			} else if ("1".equals(projectList.get(position).getStatus())) {

				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				long l = 0L;
				// 1 结束时间小于开始时间 2 开始时间与结束时间相同 3 结束时间大于开始时间
//				if (CommonUtil.getTimeCompareSize(CommonUtil.getNowTime(), projectList.get(position).getStart_time()) != 1) {
//					holder.llTime.setVisibility(View.VISIBLE);
//					holder.tvTimeName.setText("距离开始：");
				holder.type = "1";
//					long currentTime = System.currentTimeMillis();
//					//转成Date
//					Date date = new Date(currentTime);
//					//获取当前时间戳
//					date.getTime();
//					//定义 yyyy-MM-dd HH:mm:ss的格式
//					//格林尼治+或-
//					df.setTimeZone(TimeZone.getTimeZone("GMT+8"));
//					Date parse = df.parse(projectList.get(position).getStart_time());
//					long time = parse.getTime();
//					l = time - currentTime;
//					holder.llItem.setOnClickListener(new View.OnClickListener() {
//						@Override
//						public void onClick(View v) {
//
//							MyToastView.showToast("活动还没有开始", context);
//						}
//					});
//				} else if (!"".equals(projectList.get(position).getStop_time())) {
				if (CommonUtil.getTimeCompareSize(projectList.get(position).getServr_time(), projectList.get(position).getStop_time()) != 1) {
					holder.llTime.setVisibility(View.VISIBLE);
					holder.tvTimeName.setText("距离结束：");
					holder.type = "2";
					long currentTime = System.currentTimeMillis();
					//转成Date
					Date date = new Date(currentTime);
					//获取当前时间戳
					date.getTime();
					//定义 yyyy-MM-dd HH:mm:ss的格式
					//格林尼治+或-
					df.setTimeZone(TimeZone.getTimeZone("GMT+8"));
					Date parse_stop_time = df.parse(projectList.get(position).getStop_time());
						Date parse_server_time = df.parse(projectList.get(position).getServr_time());
					long time_stop_time = parse_stop_time.getTime();
						long time_servert_time = parse_server_time.getTime();
					//计算时间差
					l = time_stop_time - time_servert_time;
					holder.llItem.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							if (Constant.PROJECTIS_NOT_PART.equals(projectList.get(position).getIs_part())) {
//
								projectItemOnClickListener.itemOnClick(position);
							} else {
//										//
								MyToastView.showToast("该活动您已经报过名了！", context);
							}

						}
					});
				} else {
					l = 0;
					holder.llTime.setVisibility(View.INVISIBLE);
					holder.tvTimeName.setText("已结束");
					holder.llItem.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {

							MyToastView.showToast("活动已结束", context);
						}
					});
				}
//				} else {
//					holder.llTime.setVisibility(View.INVISIBLE);
//					holder.tvTimeName.setText("距离结束：");
//				}

				//将前一个缓存清除
				if (holder.countDownTimer != null) {
					holder.countDownTimer.cancel();
				}
				if (l > 0) {
//
					holder.countDownTimer = new CountDownTimer(l, 1000) {
						@Override
						public void onTick(long l) {
							long[] longs = CommonUtil.longToString(l);
							holder.tvDay.setText(String.valueOf(longs[0]).length() == 1 ? "0" + String.valueOf(longs[0]) : String.valueOf(longs[0]));
							holder.tvHour.setText(String.valueOf(longs[1]).length() == 1 ? "0" + String.valueOf(longs[1]) : String.valueOf(longs[1]));
							holder.tvMin.setText(String.valueOf(longs[2]).length() == 1 ? "0" + String.valueOf(longs[2]) : String.valueOf(longs[2]));
							holder.tvSec.setText(String.valueOf(longs[3]).length() == 1 ? "0" + String.valueOf(longs[3]) : String.valueOf(longs[3]));
						}

						@Override
						public void onFinish() {

							if (holder.type.equals("1")) {

							} else {
//								if (holder.countDownTimer != null) {
//									holder.countDownTimer.cancel();
//								}
								projectList.get(position).setStatus_name("已结束");
								projectList.get(position).setStatus("2");
							}
							//倒计时结束后 本地刷新数据
							new Handler() {
								@Override
								public void handleMessage(Message msg) {
									Intent intent = new Intent();
									intent.setAction("refreshProject");
									context.sendBroadcast(intent);
									HashMap<String, String> map = new HashMap<>();
									map.put("id",projectList.get(position).getId());
									map.put("project_name",projectList.get(position).getProject_name());
									map.put("is_part",projectList.get(position).getIs_part());
									EventBus.getDefault().post(map);
								}
							}.sendEmptyMessageDelayed(0, 500);
						}
					}.start();
				} else {
					if (holder.countDownTimer != null) {
						holder.countDownTimer.cancel();
					}
				}
				holder.tvProjectName.setText(projectList.get(position).getProject_name());
				holder.tvSalePeice.setText("￥" + CommonUtil.decimalFormat(Double.parseDouble(projectList.get(position).getSale_price().equals("") ? "0" : projectList.get(position).getSale_price()), "0") + "");
			}

		} catch (ParseException e) {
			e.printStackTrace();
		}
//		CommonUtil.loadImage(context, NetURL.PIC_BASEURL+projectList.get(position).getPic_url(),holder.ivPic);

	}

	@Override
	public int getItemCount() {
		return projectList == null ? 0 : projectList.size();

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
		public TextView tvReport;
		public LinearLayout llItem;
		public LinearLayout llTime;
		public CountDownTimer countDownTimer;
		public Handler handler;
		public String type;//项目时候开始  用于倒计时的显示刷新  1 未开始 2 已开始


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
			tvReport = view.findViewById(R.id.tvReport);

		}

	}
	public interface ProjectItemOnClickListener {
		abstract void itemOnClick(int position);
	}
}
