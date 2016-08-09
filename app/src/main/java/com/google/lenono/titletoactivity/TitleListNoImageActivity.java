package com.google.lenono.titletoactivity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.google.lenono.adapter.TitleLinearLayoutAdapter;
import com.google.lenono.common.Goods;
import com.google.lenono.sqlite.TZSQLiteDao;
import com.google.lenono.tiaozaoproject.GoodDetailActivity;
import com.google.lenono.tiaozaoproject.R;
import com.google.lenono.utils.InternetUtils;
import com.google.lenono.utils.jsonUtils.JsonUtils_SecondPage;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import idle_popupwindow.MyPopupWindowFirst;
import idle_popupwindow.MyPopupWindowFour;
import idle_popupwindow.MyPopupWindowSecond;
import idle_popupwindow.MyPopupWindowThird;

public class TitleListNoImageActivity extends AppCompatActivity implements View.OnClickListener {
    @Bind(R.id.title_to_list_tvbar)
    TextView tvbar;
    @Bind(R.id.title_to_list_tvfirst)
    TextView tv1;
    @Bind(R.id.title_to_list_tvsecond)
    TextView tv2;
    @Bind(R.id.title_to_list_tvthrid)
    TextView tv3;
    @Bind(R.id.title_to_list_tvfour)
    TextView tv4;
    @Bind(R.id.title_list_toolbar)
    Toolbar toolbar;
    @Bind(R.id.pull_refresh_grid_list)
    PullToRefreshGridView pullToRefreshGridView;


    MyPopupWindowFirst myPopupWindowFirst;
    MyPopupWindowThird myPopupWindowThird;
    MyPopupWindowSecond myPopupWindowSecond;
    MyPopupWindowFour myPopupWindowFour;
    String toolbarName, jsonurl, t1;
    JsonUtils_SecondPage jsonUtils_secondPage;
    TZSQLiteDao tzsqLiteDao;
    int index = 1;
    Handler handler;
    List<HashMap<String, Object>> hashMapList;
    TitleLinearLayoutAdapter titleLinearLayoutAdapter;
    List<Goods> goodsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title_list_no_image);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        toolbarName = intent.getStringExtra("name");
        jsonurl = intent.getStringExtra("url") + index;
        jsonUtils_secondPage=new JsonUtils_SecondPage();
        tzsqLiteDao=new TZSQLiteDao(getApplicationContext());
        initThread();
        initToolBar();
        initListener();
        initGridView();
    }

    public void initDatas() {
        for (int j = 0; j < goodsList.size(); j++) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("img", goodsList.get(j).getCover().toString());
            map.put("goods_name", goodsList.get(j).getGoods_name().toString());
            map.put("brand_name", goodsList.get(j).getBrand_name().toString());
            map.put("original_price", goodsList.get(j).getOriginal_price().toString());
            map.put("getSell_price", goodsList.get(j).getSell_price().toString());
            map.put("goods_url", goodsList.get(j).getGoodsUrl().toString());
            hashMapList.add(map);
        }
    }
    private void initThread() {
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == 15) {
                    initDatas();
                    titleLinearLayoutAdapter.notifyDataSetChanged();
                }
            }
        };
        new Thread(new Runnable() {
            @Override
            public void run() {
                String data = InternetUtils.getWebString(jsonurl);
                if (data != null) {
                    goodsList = new ArrayList<Goods>();
                    jsonUtils_secondPage = new JsonUtils_SecondPage();
                    goodsList = jsonUtils_secondPage.getJsonStr(data);
                    for (int i = 0; i < goodsList.size(); i++) {
                        tzsqLiteDao.insertGoods(goodsList.get(i));
                    }
                }
                handler.sendEmptyMessage(15);
            }
        }).start();
    }

    public void initToolBar() {
        toolbar.setNavigationIcon(R.drawable.title_icon_back_black);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        toolbar.setTitle("");
        tvbar.setText(toolbarName);
    }

    public void initGridView() {
        hashMapList = new ArrayList<>();
        GridView gridView = pullToRefreshGridView.getRefreshableView();
        initDate();
        gridView.setNumColumns(2);
        gridView.setHorizontalSpacing(2);
        gridView.setVerticalSpacing(2);
        pullToRefreshGridView.getLoadingLayoutProxy().setLastUpdatedLabel("刷新时间:" + t1);
        titleLinearLayoutAdapter = new TitleLinearLayoutAdapter(this, hashMapList);
        pullToRefreshGridView.setAdapter(titleLinearLayoutAdapter);
        pullToRefreshGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HashMap<String,Object> map= (HashMap<String, Object>) parent.getItemAtPosition(position);
                String jsonPath=map.get("goods_url").toString();
                Intent intent=new Intent(TitleListNoImageActivity.this, GoodDetailActivity.class);
                intent.putExtra("goodsUrl",jsonPath);
                startActivity(intent);
            }
        });
    }

    public void initDate() {
        long time = System.currentTimeMillis();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d1 = new Date(time);
        t1 = format.format(d1);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_to_list_tvfirst:
                myPopupWindowFirst.showAsDropDown(tv1);
                break;
            case R.id.title_to_list_tvsecond:
                myPopupWindowSecond.showAsDropDown(tv2);
                break;
            case R.id.title_to_list_tvthrid:
                myPopupWindowThird.showAsDropDown(tv3);
                break;
            case R.id.title_to_list_tvfour:
                myPopupWindowFour.showAsDropDown(tv4);
                break;
        }
    }
}
