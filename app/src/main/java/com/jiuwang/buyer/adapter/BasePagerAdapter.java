package com.jiuwang.buyer.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/*
*
* 作者：Yokey软件工作室
*
* 企鹅：1002285057
*
* 网址：www.yokey.top
*
* 作用：基础 ViewPager 适配器
*
* 更新：2016-04-03
*
*/

public class BasePagerAdapter extends PagerAdapter {

    private List<View> mViewList;
    private List<String> mTitleList;

    public BasePagerAdapter(List<View> mViewList, List<String> mTitleList) {
        this.mViewList = mViewList;
        this.mTitleList = mTitleList;
    }

    @Override
    public int getCount() {
        return mViewList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(mViewList.get(position));
        return mViewList.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mViewList.get(position));
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitleList.get(position);
    }

}
