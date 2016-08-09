package com.google.lenono.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.lenono.tiaozaoproject.R;
import com.squareup.picasso.Picasso;

/**
 * Created by Administrator on 2016/7/27.
 */
@SuppressLint("ValidFragment")
public class GoodDetailViewpagerPicFragment extends Fragment {
    private ImageView iv;
    private Context context;
    private String picUrl;

    public GoodDetailViewpagerPicFragment(Context context, String picUrl) {
        this.context = context;
        this.picUrl = picUrl;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab1, null);
        iv = (ImageView) view.findViewById(R.id.gd_viewpager_item);
        Picasso.with(context).load(picUrl).fit().into(iv);
        //从本地拿图片..iv设置图片
        return view;
    }
}
