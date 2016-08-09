package com.google.lenono.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.lenono.adapter.IdleFragmentAdapter;
import com.google.lenono.memorycache.MemoryCacheManager;
import com.google.lenono.service.Idle_DownloadService;
import com.google.lenono.sqlite.TZSQLiteDao;
import com.google.lenono.tiaozaoproject.GoodDetailActivity;
import com.google.lenono.tiaozaoproject.R;
import com.google.lenono.titletoactivity.TitleWebViewActivity;
import com.handmark.pulltorefresh.library.HeaderGridView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshHeadGridView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import idle_popupwindow.MyPopupWindowFirst;
import idle_popupwindow.MyPopupWindowFour;
import idle_popupwindow.MyPopupWindowSecond;
import idle_popupwindow.MyPopupWindowThird;


/**
 * Created by lenono on 2016-07-17.
 */
public class IdleFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener, PullToRefreshBase.OnRefreshListener2 {
    public static PullToRefreshHeadGridView pullToRefreshHeadGridView;
    public static List<HashMap<String, Object>> goodsList;
    public static IdleFragmentAdapter idleFragmentAdapter;
    TextView tv_first, tv_second, tv_third, tv_four;
    MyPopupWindowFirst myPopupWindowFirst;
    MyPopupWindowThird myPopupWindowThird;
    MyPopupWindowSecond myPopupWindowSecond;
    MyPopupWindowFour myPopupWindowFour;
    ImageView idle_header_imgview;
    String heder_imgurl = "http://uuyichu.com/api/jump_to_about/";
    TZSQLiteDao tzsqLiteDao;
    int indexPage = 1;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.idlefragment_item, null);
        tv_first = (TextView) view.findViewById(R.id.idle_tv_first);
        tv_second = (TextView) view.findViewById(R.id.idle_tv_second);
        tv_third = (TextView) view.findViewById(R.id.idle_tv_third);
        tv_four = (TextView) view.findViewById(R.id.idle_tv_four);
        tv_first.setOnClickListener(this);
        tv_second.setOnClickListener(this);
        tv_third.setOnClickListener(this);
        tv_four.setOnClickListener(this);
//       为了保存点击之后的状态，popup初始化要在按钮监听的外面
        myPopupWindowFirst = new MyPopupWindowFirst(getContext(), tv_first);
        myPopupWindowFirst.initPopWindow();

        myPopupWindowSecond = new MyPopupWindowSecond(getContext(), tv_second);
        myPopupWindowSecond.initPopupWindow();

        myPopupWindowThird = new MyPopupWindowThird(getContext(), tv_third);
        myPopupWindowThird.initPopupWindow();

        myPopupWindowFour = new MyPopupWindowFour(getContext());
        myPopupWindowFour.initPopupWindow();

        pullToRefreshHeadGridView = (PullToRefreshHeadGridView) view.findViewById(R.id.idle_pulltorefresh_gridview);
        pullToRefreshHeadGridView.setMode(PullToRefreshBase.Mode.BOTH);
        pullToRefreshHeadGridView.setAdapter(null);
        View view1 = View.inflate(getContext(), R.layout.idle_header_item, null);
//        获取头部图片控件
        idle_header_imgview = (ImageView) view1.findViewById(R.id.idle_header_item_imgview);
//        头部图片设置监听
        idle_header_imgview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), TitleWebViewActivity.class);
                intent.putExtra("goodsUrl", heder_imgurl);
                intent.putExtra("name", "官方寄卖");
                startActivity(intent);
            }
        });
        HeaderGridView headerGridView = pullToRefreshHeadGridView.getRefreshableView();
        headerGridView.setNumColumns(2);
        headerGridView.setBackgroundColor(Color.parseColor("#33A2A2A2"));
//        两列之间的距离
        headerGridView.setHorizontalSpacing(1);
        headerGridView.setVerticalSpacing(1);
        headerGridView.addHeaderView(view1);
//        initData();
        goodsList = new ArrayList<>();
        idleFragmentAdapter = new IdleFragmentAdapter(getContext(), goodsList);
        pullToRefreshHeadGridView.setAdapter(idleFragmentAdapter);
        headerGridView.setOnItemClickListener(this);
        pullToRefreshHeadGridView.setOnRefreshListener(this);
        return view;
    }


    //上面四个按钮的监听
    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.idle_tv_first:
                myPopupWindowFirst.showAsDropDown(tv_first);
                break;
            case R.id.idle_tv_second:
                myPopupWindowSecond.showAsDropDown(tv_second);
                break;
            case R.id.idle_tv_third:
                myPopupWindowThird.showAsDropDown(tv_third);
                break;
            case R.id.idle_tv_four:
                myPopupWindowFour.showAsDropDown(tv_four);
                break;

        }
    }

    //    headgridview的监听
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        HashMap<String, Object> map = (HashMap<String, Object>) parent.getItemAtPosition(position);
        String goodsUrl = map.get("goodsUrl").toString();
        Intent intent = new Intent(getContext(), GoodDetailActivity.class);
        intent.putExtra("goodsUrl", goodsUrl);
        startActivity(intent);
    }

    //pullto的下拉刷新
    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        Intent intent = new Intent(getContext(), Idle_DownloadService.class);
        intent.putExtra("indexPage", 1);
        getContext().startService(intent);
    }

    //pullto的上拉加载下一页
    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
        indexPage += 1;
        Intent intent = new Intent(getContext(), Idle_DownloadService.class);
        intent.putExtra("indexPage", indexPage);
        getContext().startService(intent);
    }
}
