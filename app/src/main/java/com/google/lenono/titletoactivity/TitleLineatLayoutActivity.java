package com.google.lenono.titletoactivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.lenono.adapter.IdleFragmentAdapter;
import com.google.lenono.adapter.TitleLinearLayoutAdapter;
import com.google.lenono.common.Goods;
import com.google.lenono.memorycache.MemoryCacheManager;
import com.google.lenono.sqlite.TZSQLiteDao;
import com.google.lenono.tiaozaoproject.GoodDetailActivity;
import com.google.lenono.tiaozaoproject.R;
import com.google.lenono.utils.InternetUtils;
import com.google.lenono.utils.jsonUtils.JsonUtils_SecondPage;
import com.handmark.pulltorefresh.library.HeaderGridView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshHeadGridView;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import idle_popupwindow.MyPopupWindowFirst;
import idle_popupwindow.MyPopupWindowFour;
import idle_popupwindow.MyPopupWindowSecond;
import idle_popupwindow.MyPopupWindowThird;

public class TitleLineatLayoutActivity extends AppCompatActivity implements View.OnClickListener {
    String t1;
    Toolbar toolbar;
    String toolbarName;
    TitleLinearLayoutAdapter titleLinearLayoutAdapter;
    PullToRefreshHeadGridView pullToRefreshHeadGridView;
    TextView ptv1, ptv2;
    ImageView iv;
    @Bind(R.id.title_to_linearlayout_tvbar)
    TextView tvbar;
    @Bind(R.id.title_to_linearlayout_first)
    TextView tv1;
    @Bind(R.id.title_to_linearlayout_second)
    TextView tv2;
    @Bind(R.id.title_to_linearlayout_thrid)
    TextView tv3;
    @Bind(R.id.title_to_linearlayout_four)
    TextView tv4;
    MyPopupWindowFirst myPopupWindowFirst;
    MyPopupWindowThird myPopupWindowThird;
    MyPopupWindowSecond myPopupWindowSecond;
    MyPopupWindowFour myPopupWindowFour;
    List<HashMap<String, Object>> hashMapList;
    String url,imgurl,title,content;
    List<Goods> goodsList;
    JsonUtils_SecondPage jsonUtils_secondPage;
    TZSQLiteDao tzsqLiteDao;
    int index = 1;
    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title_lineat_layout);
        ButterKnife.bind(this);
        tzsqLiteDao = new TZSQLiteDao(getApplicationContext());
        Intent intent = getIntent();
        toolbarName = intent.getStringExtra("name");
        url = intent.getStringExtra("url") + index;
        imgurl=intent.getStringExtra("imgurl");
        title=intent.getStringExtra("title");
        content=intent.getStringExtra("content");
        Log.i("aaaa",imgurl);
        initThread();
        initToolBar();
        initListener();
        initGridView();
    }

    public void initToolBar() {
        toolbar = (Toolbar) findViewById(R.id.title_linerlayout_toolbar);
        toolbar.setNavigationIcon(R.drawable.title_icon_back_black);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        toolbar.setTitle("");
        tvbar.setText(toolbarName);
    }

    public void initListener() {
        tv1.setOnClickListener(this);
        tv2.setOnClickListener(this);
        tv3.setOnClickListener(this);
        tv4.setOnClickListener(this);
        myPopupWindowFirst = new MyPopupWindowFirst(this, tv1);
        myPopupWindowFirst.initPopWindow();

        myPopupWindowSecond = new MyPopupWindowSecond(this, tv2);
        myPopupWindowSecond.initPopupWindow();

        myPopupWindowThird = new MyPopupWindowThird(this, tv3);
        myPopupWindowThird.initPopupWindow();

        myPopupWindowFour = new MyPopupWindowFour(this);
        myPopupWindowFour.initPopupWindow();
    }

    public void initGridView() {
        hashMapList = new ArrayList<>();
        pullToRefreshHeadGridView = (PullToRefreshHeadGridView) findViewById(R.id.pull_refresh_grid_linearlayout);
        pullToRefreshHeadGridView.setMode(PullToRefreshBase.Mode.BOTH);
        initDate();

        pullToRefreshHeadGridView.getLoadingLayoutProxy().setLastUpdatedLabel("刷新时间：" + t1);
        View view = View.inflate(this, R.layout.title_to_activity_item, null);
        iv = (ImageView) view.findViewById(R.id.title_to_activity_iv);
        Picasso.with(getApplicationContext()).load(imgurl).into(iv);
        ptv1 = (TextView) view.findViewById(R.id.title_to_activity_tv1);
        ptv2 = (TextView) view.findViewById(R.id.title_to_activity_iv2);
        ptv1.setText(title);
        ptv1.setBackgroundColor(Color.WHITE);
        ptv2.setText(content);
        ptv2.setBackgroundColor(Color.WHITE);
        HeaderGridView hgv = pullToRefreshHeadGridView.getRefreshableView();
        hgv.setNumColumns(2);
        hgv.setHorizontalSpacing(2);
        hgv.setVerticalSpacing(2);
        hgv.addHeaderView(view);
        titleLinearLayoutAdapter = new TitleLinearLayoutAdapter(this, hashMapList);
        // 初始化数据和数据源
        pullToRefreshHeadGridView.setAdapter(titleLinearLayoutAdapter);
        pullToRefreshHeadGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HashMap<String,Object> map= (HashMap<String, Object>) parent.getItemAtPosition(position);
                String jsonPath=map.get("goods_url").toString();
                Intent intent=new Intent(TitleLineatLayoutActivity.this, GoodDetailActivity.class);
                intent.putExtra("goodsUrl",jsonPath);
                startActivity(intent);
            }
        });
    }

    private void initDatas() {
        for (int j = 0; j < goodsList.size(); j++) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("img", goodsList.get(j).getCover().toString());
            map.put("goods_name",goodsList.get(j).getGoods_name().toString());
            map.put("brand_name",goodsList.get(j).getBrand_name().toString());
            map.put("original_price",goodsList.get(j).getOriginal_price().toString());
            map.put("getSell_price",goodsList.get(j).getSell_price().toString());
            map.put("goods_url",goodsList.get(j).getGoodsUrl().toString());
            hashMapList.add(map);
        }


    }

    public void initDate() {
        long time = System.currentTimeMillis();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d1 = new Date(time);
        t1 = format.format(d1);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_to_linearlayout_first:
                myPopupWindowFirst.showAsDropDown(tv1);
                break;
            case R.id.title_to_linearlayout_second:
                myPopupWindowSecond.showAsDropDown(tv2);
                break;
            case R.id.title_to_linearlayout_thrid:
                myPopupWindowThird.showAsDropDown(tv3);
                break;
            case R.id.title_to_linearlayout_four:
                myPopupWindowFour.showAsDropDown(tv4);
                break;

        }
    }


    public void initThread() {
        handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what==2){
                    initDatas();
                    titleLinearLayoutAdapter.notifyDataSetChanged();
                }
            }
        };
        new Thread(new Runnable() {
            @Override
            public void run() {
                String data = InternetUtils.getWebString(url);
                if (data != null) {
                    goodsList = new ArrayList<Goods>();
                    jsonUtils_secondPage = new JsonUtils_SecondPage();
                    goodsList = jsonUtils_secondPage.getJsonStr(data);
                    for (int i = 0; i < goodsList.size(); i++) {
                        tzsqLiteDao.insertGoods(goodsList.get(i));
                    }
                }
                handler.sendEmptyMessage(2);
            }
        }).start();
    }

}
