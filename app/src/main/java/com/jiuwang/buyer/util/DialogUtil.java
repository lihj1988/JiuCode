package com.jiuwang.buyer.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.jiuwang.buyer.R;



/*
*
* 作者：Yokey软件工作室
*
* 企鹅：1002285057
*
* 网址：www.yokey.top
*
* 作用：对话框工具类
*
* 更新：2016-04-02
*
*/

public class DialogUtil {

    public static Dialog dialog;
    public static ProgressDialog progressDialog;

    /*
    * 作用：关闭对话框
    * 更新：2016-04-02
    */
    public static void cancel() {

        try {
            if (dialog != null) {
                dialog.cancel();
            }
            if (progressDialog != null) {
                progressDialog.cancel();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /*
    * 作用：显示进度对话框
    * 更新：2016-04-02
    */
    public static void progress(Activity activity) {

        progressDialog = new ProgressDialog(activity);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("处理中...");
        progressDialog.show();

    }

    /*
    * 作用：显示进度对话框，自定义内容
    * 更新：2016-04-02
    */
    public static void progress(Activity activity, String title) {

        progressDialog = new ProgressDialog(activity);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage(title);
        progressDialog.show();

    }

    /*
    * 作用：显示对话框
    * 更新：2016-04-02
    */
    public static void query(Activity activity, CharSequence title, CharSequence content, View.OnClickListener clickListener) {

        try {
            dialog = new AlertDialog.Builder(activity).create();
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
            Window window = dialog.getWindow();
            window.setContentView(R.layout.dialog_query);
            window.clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
            TextView titleTextView = (TextView) window.findViewById(R.id.titleTextView);
            titleTextView.setText(title);
            TextView contentTextView = (TextView) window.findViewById(R.id.contentTextView);
            contentTextView.setText(content);
            TextView confirmTextView = (TextView) window.findViewById(R.id.confirmTextView);
            confirmTextView.setOnClickListener(clickListener);
            TextView cancelTextView = (TextView) window.findViewById(R.id.cancelTextView);
            cancelTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.cancel();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /*
    * 作用：显示对话框
    * 更新：2016-04-02
    */
    public static void query(Activity activity, CharSequence title, CharSequence content, View.OnClickListener clickListener, View.OnClickListener clickListener1) {

        try {
            dialog = new AlertDialog.Builder(activity).create();
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
            Window window = dialog.getWindow();
            window.setContentView(R.layout.dialog_query);
            window.clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
            TextView titleTextView = (TextView) window.findViewById(R.id.titleTextView);
            titleTextView.setText(title);
            TextView contentTextView = (TextView) window.findViewById(R.id.contentTextView);
            contentTextView.setText(content);
            TextView confirmTextView = (TextView) window.findViewById(R.id.confirmTextView);
            confirmTextView.setOnClickListener(clickListener);
            TextView cancelTextView = (TextView) window.findViewById(R.id.cancelTextView);
            cancelTextView.setOnClickListener(clickListener1);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /*
    * 作用：显示图片操作
    * 更新：2016-04-19
    */
    public static void image(Activity activity, View.OnClickListener take, View.OnClickListener photo) {

        try {
            dialog = new AlertDialog.Builder(activity).create();
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
            Window window = dialog.getWindow();
            window.setContentView(R.layout.dialog_image);
            window.clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
            TextView takeTextView = (TextView) window.findViewById(R.id.takeTextView);
            TextView photoTextView = (TextView) window.findViewById(R.id.photoTextView);
            takeTextView.setOnClickListener(take);
            photoTextView.setOnClickListener(photo);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}