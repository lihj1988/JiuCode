package com.jiuwang.buyer.receiver;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;


public class MyReceiver extends BroadcastReceiver {
    private static final String TAG = "MyReceiver";
    private NotificationManager nm;

    @Override
    public void onReceive(Context context, Intent intent) {
//        Boolean isTuisongChecked = PreforenceUtils.getcheckOff();
        if (null == nm) {
//           if(isTuisongChecked) {
               nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//           }else {
//               return;
//           }
        }

        Bundle bundle = intent.getExtras();
        //Logger.d(TAG, "onReceive - " + intent.getAction() + ", extras: " + AndroidUtil.printBundle(bundle));

//        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
//            Log.d(TAG, "JPush用户注册成功");
//
//        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
//            //Logger.d(TAG, "接受到推送下来的自定义消息");
//
//        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
//            //Logger.d(TAG, "接受到推送下来的通知");
//
//            receivingNotification(context, bundle);
//
//        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
//            //Logger.d(TAG, "用户点击打开了通知");
//            openNotification(context, bundle);
//        } else {
//            //Logger.d(TAG, "Unhandled intent - " + intent.getAction());
//        }
//    }
//
//    private void receivingNotification(Context context, Bundle bundle) {
//        String title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
//        String message = bundle.getString(JPushInterface.EXTRA_ALERT);
//        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
//    }
//
//    private void openNotification(Context context, Bundle bundle) {
//        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
//        JSONObject obj;
//        JSONObject strObject;
//        String notificationtype, messageID, detailVarietyName, messageTime, newsUrl, newsTitle, newsAuthor;
//        try {
//            obj = new JSONObject(extras);
//            strObject = CommonUtil.getStringJsonObjectValue(obj, "strObject");
//            notificationtype = CommonUtil.getStringNodeValue(strObject, "notificationtype");
//            messageID = CommonUtil.getStringNodeValue(strObject, "detailVarietyID");
//            detailVarietyName = CommonUtil.getStringNodeValue(strObject, "detailVarietyName");
//            messageTime = CommonUtil.getStringNodeValue(strObject, "messageTime");
//            newsUrl = CommonUtil.getStringNodeValue(strObject, "newsUrl");
//            newsTitle = CommonUtil.getStringNodeValue(strObject, "newsTitle");
//            newsAuthor = CommonUtil.getStringNodeValue(strObject, "newsAuthor");
//        } catch (Exception e) {
//            //Logger.w(TAG, "Unexpected: extras is not a valid json", e);
//            return;
//        }
//        Intent mIntent = null;
//        Bundle mBundle = new Bundle();
//        if (CommonUtil.getAppSatus(context, "com.standardbuy")) {
//            if ("PushInformationSubscribed".equals(notificationtype)) { //信息定制类推送
//               /* mIntent = new Intent(context, MessageAllDetailedActivity.class);*/
//                mBundle.putString("detailVarietyId", messageID);
//                mBundle.putString("detailVarietyName", detailVarietyName);
//                mBundle.putString("is_myMessageOrMessage", "3");//"1"是从我的短信列表点进来的，"2"从短信定制列表点进来的 ,"3"从推送中过来
//                //LogUtils.writeLogToFile("有后台启动", MyApplication.getInstance().filePath);
//            } else if ("PushSystemNotification".equals(notificationtype)) {//重大通知类推送
//                /*mIntent = new Intent(context, ChoseCountryActivity.class);*/
//                mBundle.putInt("nature_orMajorActivity", 4);//4我的页面通知
//                mBundle.putString("is_notice", "1");
//            } else if ("PushNewsNotification".equals(notificationtype)) {//新闻通知类推送
//                mIntent = new Intent(context, NewsNotificationActivity.class);
//                mIntent.putExtra("title", "新闻通知");
//                mBundle.putString("url_html", newsUrl);
//                mBundle.putString("newsTitle", newsTitle);
//                mBundle.putString("newsAuthor", newsAuthor);
//            }
//            mIntent.putExtras(mBundle);
//            Intent mainIntent = new Intent(context, MainActivity.class);
//            mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            Intent[] intents = {mainIntent, mIntent};
//            context.startActivities(intents);
//        } else {
//            //如果app进程已经被杀死，先重新启动app，将DetailActivity的启动参数传入Intent中，参数经过
//            //SplashActivity传入MainActivity，此时app的初始化已经完成，在MainActivity中就可以根据传入//参数跳转到DetailActivity中去了
//            Intent launchIntent = context.getPackageManager().
//                    getLaunchIntentForPackage("com.standardbuy");
//            launchIntent.setFlags(
//                    Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
//            if ("PushInformationSubscribed".equals(notificationtype)) { //信息定制类推送
//                mBundle.putString("flat_push", "PushInformationSubscribed");
//                mBundle.putString("detailVarietyId", messageID);
//                mBundle.putString("detailVarietyName", detailVarietyName);
//                mBundle.putString("is_myMessageOrMessage", "3");//"1"是从我的短信列表点进来的，"2"从短信定制列表点进来的 ,"3"从推送中过来
//                //LogUtils.writeLogToFile("无后台启动", MyApplication.getInstance().filePath);
//            } else if ("PushSystemNotification".equals(notificationtype)) {//重大通知类推送
//                mBundle.putInt("nature_orMajorActivity", 4);//4我的页面通知
//                mBundle.putString("is_notice", "1");
//            } else if ("PushNewsNotification".equals(notificationtype)) {//新闻通知类推送
//                mBundle.putString("flat_push", "PushNewsNotification");
//                mBundle.putString("url_html", newsUrl);
//                mBundle.putString("newsTitle", newsTitle);
//                mBundle.putString("newsAuthor", newsAuthor);
//            }
//            launchIntent.putExtras(mBundle);
//            context.startActivity(launchIntent);
//        }
    }
}
