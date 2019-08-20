package com.jiuwang.buyer.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;

import com.jiuwang.buyer.R;
import com.jiuwang.buyer.activity.MainActivity;
import com.jiuwang.buyer.base.MyApplication;
import com.jiuwang.buyer.constant.Constant;
import com.jiuwang.buyer.constant.NetURL;
import com.jiuwang.buyer.util.AppUtils;
import com.jiuwang.buyer.util.FileUtil;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class UpdateService extends Service {
	private static final int TIMEOUT = 10 * 1000;// 超时
	private String down_url;
	private static final int DOWN_OK = 1;
	private static final int DOWN_ERROR = 0;

	private String app_name;

	private NotificationManager notificationManager;


	private Intent updateIntent;
	private PendingIntent pendingIntent;

	private int notification_id = 0;
	private MyApplication apps;
	public String CALENDAR_ID = "channel_02";
	private Notification.Builder builder;

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		apps = (MyApplication) getApplication();
		down_url = NetURL.BASEURL + Constant.downLoadUrl;
		app_name = getResources().getString(R.string.app_name);
		/*app_name = intent.getStringExtra("app_name");
        down_url=intent.getStringExtra("down_url");*/
		FileUtil.createFile(app_name);
		createNotification();
		createThread();
		return super.onStartCommand(intent, flags, startId);
	}

	/***
	 * 开线程下载
	 */
	public void createThread() {
		final Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
					case DOWN_OK:
						AppUtils.getReadUriPermission();
						stopService(updateIntent);
						break;
					case DOWN_ERROR:
						builder.setContentTitle(app_name).setContentText("下载失败");
//						builder.setContentIntent(pendingIntent);
						notificationManager.notify(notification_id, createNotification(MyApplication.getInstance().getApplicationContext(), notificationManager, "", "下载失败"));

						break;
					default:
						stopService(updateIntent);
						break;
				}

			}

		};

		final Message message = new Message();

		new Thread(new Runnable() {
			@Override
			public void run() {

				try {
					long downloadSize = downloadUpdateFile(down_url,
							FileUtil.updateFile.toString());
					if (downloadSize > 0) {
						// 下载成功
						message.what = DOWN_OK;
						handler.sendMessage(message);
					}
				} catch (Exception e) {
					e.printStackTrace();
					message.what = DOWN_ERROR;
					handler.sendMessage(message);
				}

			}
		}).start();
	}

	/***
	 * 创建通知栏
	 */
	//RemoteViews contentView;
	public void createNotification() {
		notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        updateIntent = new Intent(this, MainActivity.class);
		pendingIntent = PendingIntent.getActivity(this, 0, updateIntent, 0);
		notificationManager.notify(notification_id, createNotification(MyApplication.getInstance().getApplicationContext(), notificationManager, "开始下载", "下载：0%"));

	}

	private Notification createNotification(Context context, NotificationManager notificationManager, String ticker, String contentText) {
		Notification notification;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			NotificationChannel notificationChannel = new NotificationChannel(CALENDAR_ID, "123",
					NotificationManager.IMPORTANCE_DEFAULT);
			// 设置渠道描述
			notificationChannel.setDescription("通知组");
			// 是否绕过请勿打扰模式
			notificationChannel.canBypassDnd();
			// 设置绕过请勿打扰模式
			notificationChannel.setBypassDnd(true);
			notificationChannel.enableLights(true);
			notificationChannel.setSound(null, null);
			notificationChannel.setLightColor(Color.RED);
			// 设置通知出现时的震动（如果 android 设备支持的话）
//            notificationChannel.enableVibration(true);
//			notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400,
//					300, 200, 400});
			notificationManager.createNotificationChannel(notificationChannel);
			if (builder != null) {
				builder.setTicker(ticker).setContentTitle(app_name).setContentText(contentText);
			} else {
				builder = new Notification.Builder(context, CALENDAR_ID).setTicker(ticker).setContentTitle(app_name).setContentText(contentText);
			}

		} else {
			if (builder != null) {
				builder.setTicker(ticker).setContentTitle(app_name).setContentText(contentText);
			} else {
				builder = new Notification.Builder(context).setTicker(ticker).setContentTitle(app_name).setContentText(contentText);
			}
			// 设置通知灯光（LIGHTS）、铃声（SOUND）、震动（VIBRATE）、（ALL 表示都设置）
//            builder.setDefaults(Notification.DEFAULT_ALL);

		}
//		builder.setContentIntent(pendingIntent);
		notification = builder.setSmallIcon(R.mipmap.app_logo)

//                .setChannelId(CALENDAR_ID)
				.build();
		notification.flags |= Notification.FLAG_AUTO_CANCEL;

		return notification;
	}

	/***
	 * 下载文件
	 *
	 * @return
	 * @throws MalformedURLException
	 */
	public long downloadUpdateFile(String down_url, String file)
			throws Exception {
		int down_step = 1;// 提示step
		int totalSize;// 文件总大小
		int downloadCount = 0;// 已经下载好的大小
		int updateCount = 0;// 已经上传的文件大小
		InputStream inputStream;
		OutputStream outputStream;

		URL url = new URL(down_url);
		HttpURLConnection httpURLConnection = (HttpURLConnection) url
				.openConnection();
		httpURLConnection.setConnectTimeout(TIMEOUT);
		httpURLConnection.setReadTimeout(TIMEOUT);
		// 获取下载文件的size
		totalSize = httpURLConnection.getContentLength();
		if (httpURLConnection.getResponseCode() == 404) {
			throw new Exception("fail!");
		}
		inputStream = httpURLConnection.getInputStream();
		outputStream = new FileOutputStream(file, false);// 文件存在则覆盖掉
		byte buffer[] = new byte[1024];
		int readsize = 0;
		while ((readsize = inputStream.read(buffer)) != -1) {
			outputStream.write(buffer, 0, readsize);
			downloadCount += readsize;// 时时获取下载到的大小
			/**
			 * 每次增张5%
			 */
			if (updateCount == 0
					|| (downloadCount * 100 / totalSize - down_step) >= updateCount) {
				updateCount += down_step;

				// 改变通知栏
				builder.setContentTitle("正在下载...").setContentText(updateCount
						+ "%" + "");
//				builder.setContentIntent(pendingIntent);
				notificationManager.notify(notification_id, createNotification(MyApplication.getInstance().getApplicationContext(), notificationManager, "正在下载...", updateCount + "%" + ""));

			}

		}
		if (httpURLConnection != null) {
			httpURLConnection.disconnect();
		}
		inputStream.close();
		outputStream.close();
		return downloadCount;

	}

}
