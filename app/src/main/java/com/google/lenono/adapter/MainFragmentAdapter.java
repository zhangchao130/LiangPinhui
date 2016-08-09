package com.google.lenono.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.widget.RelativeLayout;

import java.util.List;

/**
 * Created by lenono on 2016-07-17.
 */
public class MainFragmentAdapter extends FragmentPagerAdapter {
    List<Fragment> list;

    public MainFragmentAdapter(FragmentManager fm, List<Fragment> list) {
        super(fm);
        this.list = list;
    }


    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }
}
