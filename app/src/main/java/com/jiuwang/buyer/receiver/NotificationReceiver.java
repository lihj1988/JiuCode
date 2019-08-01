package com.jiuwang.buyer.receiver;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;

import com.jiuwang.buyer.R;
import com.jiuwang.buyer.activity.MainActivity;
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
//		sendSimpleNotification(context,notificationManager);
//		NotificationCompat.Builder builder = new NotificationCompat.Builder(MyApplication.getInstance());
//		builder.setSmallIcon(R.mipmap.app_logo)//设置小图标
//				.setTicker("2222222").setContentTitle("q111111").setContentText("通知标内容");
//		Intent updateIntent = new Intent(MyApplication.getInstance().getApplicationContext(), MainActivity.class);
//		PendingIntent pendingIntent = PendingIntent.getActivity(MyApplication.getInstance().getApplicationContext(), 0, updateIntent, 0);
//		// 这里面的参数是通知栏view显示的内容
//		builder.setContentIntent(pendingIntent);
//		notificationManager.notify(1000, builder.build());

	}
	public void sendSimpleNotification(Context context,NotificationManager nm) {
		Notification notification;
		Notification.Builder builder;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			builder=new Notification.Builder(context,"low");
		}else {
			builder=new Notification.Builder(context);
		}
		//设置标题
		builder.setContentTitle("设置标题");
		//设置内容
		builder.setContentText("内容是............");
		//设置状态栏显示的图标，建议图标颜色透明
		builder.setSmallIcon(R.mipmap.app_logo);
		// 设置通知灯光（LIGHTS）、铃声（SOUND）、震动（VIBRATE）、（ALL 表示都设置）
		builder.setDefaults(Notification.DEFAULT_ALL);
		//灯光三个参数，颜色（argb）、亮时间（毫秒）、暗时间（毫秒）,灯光与设备有关
		builder.setLights(Color.RED, 200, 200);
		// 铃声,传入铃声的 Uri（可以本地或网上）我这没有铃声就不传了
		builder.setSound(Uri.parse("")) ;
		// 震动，传入一个 long 型数组，表示 停、震、停、震 ... （毫秒）
		builder.setVibrate(new long[]{200, 200, 0, 0, 0, 0});
		// 通知栏点击后自动消失
		builder.setAutoCancel(true);
		// 简单通知栏设置 Intent
//		builder.setContentIntent(setPendingIntent("简单通知"));
		builder.setPriority(Notification.PRIORITY_HIGH);

		//设置下拉之后显示的图片
		builder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.app_logo));
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			NotificationChannel channel = new NotificationChannel("my_channel_01", "我是渠道名字", NotificationManager.IMPORTANCE_DEFAULT);
			channel.enableLights(true);//是否在桌面icon右上角展示小红点
			channel.setLightColor(Color.GREEN);//小红点颜色
			channel.setShowBadge(false); //是否在久按桌面图标时显示此渠道的通知
			nm.createNotificationChannel(channel);
		}
		notification=builder.build();
		nm.notify(11212,notification);

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
//		builder.setAutoCancel(true);
//		builder = new Notification.Builder(context, CALENDAR_ID);
		PendingIntent pendingIntent = PendingIntent.getActivity(context, 11,
				new Intent(context, MainActivity.class), 0);
		notification = builder.setSmallIcon(R.mipmap.app_logo)

//                .setChannelId(CALENDAR_ID)
				.build();
		notification.flags |= Notification.FLAG_AUTO_CANCEL;
		return notification;
	}
}
