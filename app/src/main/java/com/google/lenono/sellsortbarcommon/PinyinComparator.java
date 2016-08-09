package com.google.lenono.sellsortbarcommon;

import java.util.Comparator;

/**
 * Created by lenono on 2016-07-24.
 */
public class PinyinComparator implements Comparator<SellSortModel> {

    public int compare(SellSortModel o1, SellSortModel o2) {
        //这里主要是用来对ListView里面的数据根据ABCDEFG...来排序
        if (o2.getSortLetters().equals("#")) {
            return -1;
        } else if (o1.getSortLetters().equals("#")) {
            return 1;
        } else {
            return o1.getSortLetters().compareTo(o2.getSortLetters());
        }
    }
}
