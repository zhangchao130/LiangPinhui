package com.google.lenono.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by lenono on 2016-07-16.
 */
public class GuideActivityAdapter extends PagerAdapter {
    List<View> views;

    public GuideActivityAdapter(List<View> views) {
        this.views = views;
    }

    @Override
    public int getCount() {
        return views.size();

    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(views.get(position));
        return views.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(views.get(position));
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
