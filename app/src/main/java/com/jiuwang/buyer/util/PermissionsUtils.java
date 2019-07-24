package com.jiuwang.buyer.util;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jiuwang.buyer.R;
import com.jiuwang.buyer.constant.Constant;

import java.util.ArrayList;
import java.util.List;

/**
 * 权限工具类
 */

public class PermissionsUtils {


    private final int mRequestCode = 10112;//权限请求码
    public static boolean showSystemSetting = true;

    private PermissionsUtils() {
    }

    private static PermissionsUtils permissionsUtils;
    private IPermissionsResult mPermissionsResult;

    public static PermissionsUtils getInstance() {
        if (permissionsUtils == null) {
            permissionsUtils = new PermissionsUtils();
        }
        return permissionsUtils;
    }

    public void chekPermissions(Activity context, String[] permissions, @NonNull IPermissionsResult permissionsResult) {
        mPermissionsResult = permissionsResult;
        if (Build.VERSION.SDK_INT < 23) {//6.0才用动态权限
            permissionsResult.passPermissons();
            return;
        }

        //创建一个mPermissionList，逐个判断哪些权限未授予，未授予的权限存储到mPerrrmissionList中
        List<String> mPermissionList = new ArrayList<>();
        //逐个判断你要的权限是否已经通过
        for (int i = 0; i < permissions.length; i++) {
            if (ContextCompat.checkSelfPermission(context, permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                mPermissionList.add(permissions[i]);//添加还未授予的权限
            }
        }
        //申请权限
        if (mPermissionList.size() > 0) {//有权限没有通过，需要申请
            ActivityCompat.requestPermissions(context, permissions, Constant.REQUEST_WRITE_READ_PERMISSION);
        } else {
            //说明权限都已经通过，可以做你想做的事情去
            permissionsResult.passPermissons();
            return;
        }
    }

    //请求权限后回调的方法
    //参数： requestCode  是我们自己定义的权限请求码
    //参数： permissions  是我们请求的权限名称数组
    //参数： grantResults 是我们在弹出页面后是否允许权限的标识数组，数组的长度对应的是权限名称数组的长度，数组的数据0表示允许权限，-1表示我们点击了禁止权限

    public void onRequestPermissionsResult(Activity activity, int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        boolean hasPermissionDismiss = false;//有权限没有通过
        if (mRequestCode == requestCode) {
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] == -1) {
                    hasPermissionDismiss = true;
                }
            }
            //如果有权限没有被允许
            if (hasPermissionDismiss) {
                if (showSystemSetting) {
                    showSystemPermissionsSettingDialog(activity);//跳转到系统设置权限页面，或者直接关闭页面，不让他继续访问
                } else {
                    mPermissionsResult.forbitPermissons();
                }
            } else {
                //全部权限通过，可以进行下一步操作。。。
                mPermissionsResult.passPermissons();
            }
        }

    }

    private void showSystemPermissionsSettingDialog(final Activity activity) {
        final String mPackName = activity.getPackageName();
        final AlertDialog dialog = new AlertDialog.Builder(activity, R.style.styletest).create();
        dialog.show();
        Window window = dialog.getWindow();
        window.setContentView(R.layout.dialog_normal_one_button);
        RelativeLayout relative_button2 = (RelativeLayout) window.findViewById(R.id.relative_button2);
        Button bt1 = (Button) window.findViewById(R.id.bt1_quxiao);
        Button bt2 = (Button) window.findViewById(R.id.bt2_queding);
        TextView title = (TextView) window.findViewById(R.id.title);
        title.setText("设置");
        TextView tv_context = (TextView) window
                .findViewById(R.id.tv_content);
        tv_context.setText("已禁用权限，请手动授予");
        relative_button2.setVisibility(View.VISIBLE);
        bt1.setText("取消");
        bt2.setText("确定");
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPermissionsResult.forbitPermissons();
                dialog.cancel();
            }
        });
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri packageURI = Uri.parse("package:" + mPackName);
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, packageURI);
                activity.startActivity(intent);
                activity.finish();
                dialog.cancel();
            }
        });
    }

    public interface IPermissionsResult {
        void passPermissons();

        void forbitPermissons();
    }
}

