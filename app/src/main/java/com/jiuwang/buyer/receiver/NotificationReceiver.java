package com.jiuwang.buyer.receiver;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;

import com.jiuwang.buyer.R;
import com.jiuwang.buyer.activity.MainActivity;
import com.jiuwang.buyer.activity.MyProjectActivity;
import com.jiuwang.buyer.base.MyApplication;

/**
 * author：lihj
 * desc：
 * Created by lihj on  2019/8/1 14:10.
 */

public class NotificationReceiver extends BroadcastReceiver {
	public  String CALENDAR_ID = "channel_01";
	@Override
	public void onReceive(Context context, Intent intent) {
		NotificationManager notificationManager = (NotificationManager) MyApplication.getInstance().getSystemService(Context.NOTIFICATION_SERVICE);
		notificationManager.notify(1,createNotification(context,notificationManager));

	}

	private  Notification createNotification(Context context, NotificationManager notificationManager) {
		Notification notification;Notification.Builder builder;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			NotificationChannel notificationChannel = new NotificationChannel(CALENDAR_ID, "123",
					NotificationManager.IMPORTANCE_DEFAULT);
			// 设置渠道描述
			notificationChannel.setDescription("通知组");
			// 是否绕过请勿打扰模式
			notificationChannel.canBypassDnd();
			// 设置绕过请勿打扰模式
			notificationChannel.setBypassDnd(true);
			// 桌面Launcher的消息角标
			notificationChannel.canShowBadge();
			// 设置显示桌面Launcher的消息角标
			notificationChannel.setShowBadge(true);
			// 设置通知出现时声音，默认通知是有声音的
//			notificationChannel.setSound(null, null);
			// 设置通知出现时的闪灯（如果 android 设备支持的话）
			notificationChannel.enableLights(true);
			notificationChannel.setLightColor(Color.RED);
			// 设置通知出现时的震动（如果 android 设备支持的话）
			notificationChannel.enableVibration(true);
			notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400,
					300, 200, 400});
			notificationManager.createNotificationChannel(notificationChannel);
			builder = new Notification.Builder(context, CALENDAR_ID);
		}else {
			builder=new Notification.Builder(context);
			// 设置通知灯光（LIGHTS）、铃声（SOUND）、震动（VIBRATE）、（ALL 表示都设置）
			builder.setDefaults(Notification.DEFAULT_ALL);
		}
		builder.setTicker("中奖啦").setContentTitle("中奖通知").setContentText("您抢购的商品已中奖，请前往订单中查看！");
//		builder.setAutoCancel(true);
//		builder = new Notification.Builder(context, CALENDAR_ID);
		PendingIntent pendingIntent = PendingIntent.getActivity(context, 11,
				new Intent(context, MyProjectActivity.class), 0);
		builder.setContentIntent(pendingIntent);
		notification = builder.setSmallIcon(R.mipmap.app_logo)

//                .setChannelId(CALENDAR_ID)
				.build();
		notification.flags |= Notification.FLAG_AUTO_CANCEL;
		return notification;
	}
}
