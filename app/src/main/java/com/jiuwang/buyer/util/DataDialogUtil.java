package com.jiuwang.buyer.util;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;

/**
 * Created by Administrator on 2017/7/14 0014.
 */

public class DataDialogUtil {
    static  Activity mactivity;
    static String str;
    private String initDateTime;
    public DataDialogUtil(Activity activity, String initDateTime) {
        this.mactivity = activity;
        this.initDateTime = initDateTime;

    }

    public static void date(Activity activity, final TextView textView) {
        mactivity=activity;

        str = "";
        Calendar cal = Calendar.getInstance();
        final DatePickerDialog mDialog = new DatePickerDialog(activity, null,
                cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));


        //手动设置按钮
        mDialog.setButton(DialogInterface.BUTTON_POSITIVE, "完成", new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //通过mDialog.getDatePicker()获得dialog上的DatePicker组件，然后可以获取日期信息
                DatePicker datePicker = mDialog.getDatePicker();
                int year = datePicker.getYear();
                int month = datePicker.getMonth()+1;
                int day = datePicker.getDayOfMonth();
                str = year + "-" + month + "-" + day;
                if (month < 10 && day<10){
                    str = year + "-0" + month + "-0" + day;
                }else if (month < 10&& day>10){
                    str = year + "-0" + month + "-" + day;
                }else if (month > 10&& day < 10){
                    str = year + "-" + month + "-0" + day;
                }

                mDialog.dismiss();
                textView.setText(str);

//
            }
        });
        //取消按钮，如果不需要直接不设置即可
        mDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                System.out.println("BUTTON_NEGATIVE~~");
                mDialog.dismiss();
            }
        });
        mDialog.show();
    }



}
