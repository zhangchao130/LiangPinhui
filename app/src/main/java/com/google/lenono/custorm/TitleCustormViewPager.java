package com.google.lenono.custorm;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by lenono on 2016-07-18.
 */
public class TitleCustormViewPager extends ViewPager {
    public TitleCustormViewPager(Context context) {
        super(context);
    }

    public TitleCustormViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        getParent().requestDisallowInterceptTouchEvent(true);
        return super.onTouchEvent(ev);
    }
}
