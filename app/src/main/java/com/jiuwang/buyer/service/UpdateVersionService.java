package com.jiuwang.buyer.service;

import android.app.ProgressDialog;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.jiuwang.buyer.base.MyApplication;
import com.jiuwang.buyer.constant.NetURL;
import com.jiuwang.buyer.util.PreforenceUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;

import java.io.File;

import okhttp3.Call;

/**
 * Created by Administrator on 2018/9/3.
 * 软件版本更新服务
 */

public class UpdateVersionService extends Service {
    public static String TAG = "UpdateVersionService";
    ProgressDialog progressDialog;//下载进度条
    ConnectivityManager manager;
    boolean flag = false;//判断网络标志
    ProgressDialog pd;
    private String userCode,token;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        userCode = PreforenceUtils.getStringData("loginInfo", "userCode");
        token = PreforenceUtils.getStringData("loginInfo", "token");
        progressDialog = new ProgressDialog(MyApplication.currentActivity);
        progressDialog.setCancelable(true);
//        progressDialog.getWindow().setType((WindowManager.LayoutParams.TYPE_SYSTEM_ALERT));
        progressDialog.setMax(100);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        //得到网络连接信息
        manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        //去进行判断网络是否连接
        if (manager.getActiveNetworkInfo() != null) {
            flag = manager.getActiveNetworkInfo().isAvailable();
            if (flag) {

                updateVersion();//版本检测
            } else {
                Toast.makeText(MyApplication.getInstance(), "网络连接异常，请检查网络连接。", Toast.LENGTH_SHORT).show();
            }
        }

        return super.onStartCommand(intent, flags, startId);
    }


    //请求是否更新版本
    public void updateVersion() {
//        if (CommonUtil.getNetworkRequest(MyApplication.getInstance())) {
//            OkHttpUtils.post().url(NetURL.VERSIONNUM)
//                    .addParams("userCode", userCode)
//                    .addParams("token", token)
//                    .build().execute(new StringCallback() {
//                @Override
//                public void onError(Call call, Exception e, int id) {
//                    MyToastView.showToast( "获取数据失败！",MyApplication.getInstance());
//                }
//
//                @Override
//                public void onResponse(String response, int id) {
//                    try {
//                        JSONObject jsonObject = new JSONObject(response);
//                        String code = jsonObject.getString("msgcode");
//                        String msg = jsonObject.getString("msg");
//                        if (code.equals("1")) {
//                            JSONObject result = jsonObject.getJSONObject("result");
//                            String version_android = result.getString("version_android");//服务器软件版本
//                            final String downUrl = result.getString("download_url");//软件下载地址
//
//                            //判断获取到的版本号是否大于当前版本号，不大于的话，提示最新版本，若大于 提示下载升级
//                            String appcode = MyApplication.getAppVersionNCode(MyApplication.getInstance());//设备软件的当前版本
//                            if (!appcode.equals(version_android)) {
//                                final AlertDialog dlg = new AlertDialog.Builder(MyApplication.currentActivity, R.style.dialog_bottom_style).create();
//                                dlg.show();
//                                dlg.setCanceledOnTouchOutside(false);
//                                dlg.setCancelable(false);
//                                Window window = dlg.getWindow();
//                                window.setContentView(R.layout.dialog_normal);
//                                TextView bt1 = (TextView) window.findViewById(R.id.bt1_quxiao);
//                                TextView bt2 = (TextView) window.findViewById(R.id.bt2_queding);
//                                TextView textView= (TextView) window.findViewById(R.id.text1);
//                                TextView title= (TextView) window.findViewById(R.id.title);
//                                title.setText("版本升级");
//                                textView.setVisibility(View.VISIBLE);
//                                textView.setText("检测到新版本，请升级最新版本！");
//                                bt1.setOnClickListener(new View.OnClickListener() {//取消
//                                    @Override
//                                    public void onClick(View view) {
//                                        dlg.cancel();
//                                    }
//                                });
//                                bt2.setOnClickListener(new View.OnClickListener() {//确定
//                                    @Override
//                                    public void onClick(View view) {
//                                        progressDialog.show();
//                                        uploadVersion(downUrl);
//                                        dlg.cancel();
//                                    }
//                                });
//                            }
//                        }
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//
//                    }
//                }
//            });
//        }
    }

    //下载软件
    private void uploadVersion(String url) {
        OkHttpUtils.post().tag("down").url(NetURL.BASEURL + url).build()
                .execute(new FileCallBack(NetURL.directory, "jbmj.apk") {
            @Override
            public void inProgress(float progress, long total, int id) {
                super.inProgress(progress, total, id);
                progressDialog.setProgress((int) (100 * progress));
            }

            @Override
            public void onResponse(File response, int id) {
                progressDialog.dismiss();
                Toast.makeText(MyApplication.getInstance(), "下载完成！", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                intent.setDataAndType(Uri.fromFile(new File(NetURL.directory + "jbmj.apk")),
                        "application/vnd.android.package-archive");
               MyApplication.getInstance().startActivity(intent);
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                progressDialog.dismiss();
                Toast.makeText(MyApplication.getInstance(), "下载失败！", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }


}
