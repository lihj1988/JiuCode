package com.jiuwang.buyer.util;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;

import com.jiuwang.buyer.R;
import com.jiuwang.buyer.adapter.BasePagerAdapter;


/*
*
* 作者：Yokey软件工作室
*
* 企鹅：1002285057
*
* 网址：www.yokey.top
*
* 作用：控件工具类
*
* 更新：2016-04-02
*
*/

public class ControlUtil {

    /*
    * 作用：设置 TabLayout
    * 更新：2016-04-02
    */
    public static void setTabLayout(Activity activity, TabLayout mTabLayout, BasePagerAdapter mAdapter, ViewPager mViewPager) {

        mViewPager.setAdapter(mAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabsFromPagerAdapter(mAdapter);
        mTabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(activity, R.color.app_main_color));
        mTabLayout.setTabTextColors(ContextCompat.getColor(activity, R.color.greyAdd), ContextCompat.getColor(activity, R.color.app_main_color));
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

    }
    /*
    * 作用：设置 TabLayout
    * 更新：2016-04-02
    */
    public static void setTabLayout(Activity activity, TabLayout mTabLayout) {

        mTabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(activity, R.color.app_main_color));
        mTabLayout.setTabTextColors(ContextCompat.getColor(activity, R.color.greyAdd), ContextCompat.getColor(activity, R.color.app_main_color));
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

    }

    /*
    * 作用：强制隐藏键盘
    * 更新：2016-04-02
    */
    public static void hideKeyboard(Activity activity, View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(view, InputMethodManager.SHOW_FORCED);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /*
    * 作用：设置 WebView
    * 更新：2016-04-02
    */
    public static void setWebView(WebView webView) {

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setDatabaseEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);

    }

}
