package com.jiuwang.buyer.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.app.NotificationCompat;

import com.jiuwang.buyer.R;
import com.jiuwang.buyer.activity.MainActivity;
import com.jiuwang.buyer.base.MyApplication;
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
    private NotificationCompat.Builder builder;

    private Intent updateIntent;
    private PendingIntent pendingIntent;

    private int notification_id = 0;
    private MyApplication apps;

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        apps = (MyApplication) getApplication();
        down_url = NetURL.BASEURL + apps.url_langde;
        app_name =getResources().getString( R.string.app_name);
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
                        builder.setContentIntent(pendingIntent);
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
        builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.mipmap.app_logo)//设置小图标
                .setTicker("开始下载").setContentTitle(app_name).setContentText("下载：0%");
        updateIntent = new Intent(this, MainActivity.class);
        pendingIntent = PendingIntent.getActivity(this, 0, updateIntent, 0);
        // 这里面的参数是通知栏view显示的内容
        builder.setContentIntent(pendingIntent);
        notificationManager.notify(notification_id, builder.build());
        /***
         * 在这里我们用自定的view来显示Notification
         */
		/*contentView = new RemoteViews(getPackageName(),
				R.layout.notification_item);
		contentView.setTextViewText(R.id.notificationTitle, "正在下载");
		contentView.setTextViewText(R.id.notificationPercent, "0%");
		contentView.setProgressBar(R.id.notificationProgress, 100, 0, false);

		notification.contentView = contentView;

		updateIntent = new Intent(this, MainActivity.class);
		updateIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		pendingIntent = PendingIntent.getActivity(this, 0, updateIntent, 0);

		notification.contentIntent = pendingIntent;

		notificationManager.notify(notification_id, notification);
*/
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
                builder.setContentIntent(pendingIntent);
				/*contentView.setTextViewText(R.id.notificationPercent,
						updateCount + "%");
				contentView.setProgressBar(R.id.notificationProgress, 100,
						updateCount, false);*/
                // show_view
                notificationManager.notify(notification_id, builder.build());

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
