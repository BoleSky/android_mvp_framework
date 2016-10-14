package com.bolesky.base.adapter;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by xiaoyong.cui
 * on 2016/10/14
 * E-Mail:hellocui@aliyun.com
 */

public class FragmentTabPagerAdapter extends FragmentPagerAdapter {

    private Fragment[] mFragmentList;          //fragment列表
    private int[] mListTitle;               //tab名的列表
    private Context context;

    public FragmentTabPagerAdapter(FragmentManager fm, Fragment[] listFragment, int[] listTitle, Context context) {
        super(fm);
        mFragmentList = listFragment;
        mListTitle = listTitle;
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList == null ? null : mFragmentList[position];
    }

    @Override
    public int getCount() {
        return mFragmentList == null ? 0 : mFragmentList.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return context.getResources().getString(mListTitle[position]);
    }
}
