package com.jiuwang.buyer.util;

import android.app.Activity;
import android.widget.Toast;

/*
*
* 作者：Yokey软件工作室
*
* 企鹅：1002285057
*
* 网址：www.yokey.top
*
* 作用：Toast 工具类 为了防止多个 Toast
*
* 更新：2016-04-02
*
*/

public class ToastUtil {

    private static Toast toast = null; //全局的Toast

    /*
    * 作用：显示一个 Toast
    * 更新：2016-04-02
    */
    public static void show(Activity activity, String string) {

        if (toast == null) {
            toast = Toast.makeText(activity, string, Toast.LENGTH_SHORT);
        } else {
            toast.setText(string);
            toast.setDuration(Toast.LENGTH_SHORT);
        }

        toast.show();

    }

    /*
    * 作用：显示失败 Toast
    * 更新：2016-04-02
    */
    public static void showFailure(Activity activity) {

        String content = "失败了,请重试...";

        if (toast == null) {
            toast = Toast.makeText(activity, content, Toast.LENGTH_SHORT);
        } else {
            toast.setText(content);
            toast.setDuration(Toast.LENGTH_SHORT);
        }

        toast.show();

    }

    /*
    * 作用：显示网络链接失败 Toast
    * 更新：2016-04-02
    */
    public static void showFailureNetwork(Activity activity) {

        String content = "网络链接失败...";

        if (toast == null) {
            toast = Toast.makeText(activity, content, Toast.LENGTH_SHORT);
        } else {
            toast.setText(content);
            toast.setDuration(Toast.LENGTH_SHORT);
        }

        toast.show();

    }

    /*
    * 作用：显示失败 Toast
    * 更新：2016-04-02
    */
    public static void showSuccess(Activity activity) {

        String content = "成功...";

        if (toast == null) {
            toast = Toast.makeText(activity, content, Toast.LENGTH_SHORT);
        } else {
            toast.setText(content);
            toast.setDuration(Toast.LENGTH_SHORT);
        }

        toast.show();

    }

}
