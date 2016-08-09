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
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.lenono.adapter.TitleLinearLayoutAdapter;
import com.google.lenono.adapter.TitleViewPagerToActivityAdapter;
import com.google.lenono.common.Goods;
import com.google.lenono.common.ViewGoods;
import com.google.lenono.common.goodDetail_model.GoodDetail;
import com.google.lenono.sqlite.TZSQLiteDao;
import com.google.lenono.tiaozaoproject.GoodDetailActivity;
import com.google.lenono.tiaozaoproject.R;
import com.google.lenono.utils.InternetUtils;
import com.google.lenono.utils.jsonUtils.JsonUtils_FirstPage;
import com.google.lenono.utils.jsonUtils.JsonUtils_SecondPage;
import com.handmark.pulltorefresh.library.HeaderGridView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshHeadGridView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TitleToActivity extends AppCompatActivity {
    int index = 1;
    String viewpagerurl_one = "http://uuyichu.com/api/goods/album_v4/?alias=gifts&cate=-1&brand=-1&condition=-1&sale=0&source=0&page=";
    String viewpagerurl_two = "http://uuyichu.com/api/goods/album_v4/?alias=nanshi&cate=-1&brand=-1&condition=-1&sale=0&source=0&page=";
    String viewpagerurl_three = "http://uuyichu.com/api/goods/album_v4/?alias=taiyangjing&cate=-1&brand=-1&condition=-1&sale=0&source=0&page=";
    String viewpagerurl_four = "http://uuyichu.com/api/goods/album_v4/?alias=qszc&cate=-1&brand=-1&condition=-1&sale=0&source=0&page=";
    String viewpagerurl_five = "http://uuyichu.com/api/goods/album_v4/?alias=shipinfourthirteen&cate=-1&brand=-1&condition=-1&sale=0&source=0&page=";
    String viewpagerurl_six1 = "http://uuyichu.com/haibaicai/spu/list?page=";
    String viewpagerurl_six2 = "&alias=briefcasegabriellarocha";
    String s;
    Toolbar toolbar;
    TextView tv1, tv2;
    ImageView iv;
    PullToRefreshHeadGridView pullToRefreshHeadGridView;
    Intent intent;
    String t1;
    @Bind(R.id.title_to_activity_toolbar_tv)
    TextView tvbar;
    String bannerUrl;
    TZSQLiteDao tzsqLiteDao;
    List<ViewGoods> viewGoodsList;
    List<HashMap<String, Object>> viewHs;
    JsonUtils_FirstPage jsonUtils_firstPage;
    TitleLinearLayoutAdapter titleLinearLayoutAdapter;
    TitleViewPagerToActivityAdapter titleViewPagerToActivityAdapter;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.title_to_activity);
        ButterKnife.bind(this);
        tzsqLiteDao = new TZSQLiteDao(getApplicationContext());
        intent = getIntent();
        s = intent.getStringExtra("position");
        initToolbar();
        initGridView();
        startThread();
    }

    // 设置toolbar
    public void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.title_to_activity_toolbar);
        toolbar.setTitle("");
        if (s.equals("0")) {
            tvbar.setText("这是一个适合送礼的清单");
            bannerUrl = viewpagerurl_one + index;
        } else if (s.equals("1")) {
            tvbar.setText("男士专场");
            bannerUrl = viewpagerurl_two + index;

        } else if (s.equals("2")) {
            tvbar.setText("出门自拍必备的太阳镜");
            bannerUrl = viewpagerurl_three + index;

        } else if (s.equals("3")) {
            tvbar.setText("2016春夏 轻奢专场");
            bannerUrl = viewpagerurl_four + index;

        } else if (s.equals("4")) {
            tvbar.setText("饰品专场 点亮你的造型");
            bannerUrl = viewpagerurl_five + index;

        } else {
            tvbar.setText("暖意春天 女士通勤包首选");
            bannerUrl = viewpagerurl_six1 + index + viewpagerurl_six2;
        }
        Log.i("aaa", s);
        toolbar.setNavigationIcon(R.drawable.title_icon_back_black);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    //设置滑动gridview
    public void initGridView() {
        viewHs = new ArrayList<>();
        if (!s.equals("5")) {
            pullToRefreshHeadGridView = (PullToRefreshHeadGridView) findViewById(R.id.pull_refresh_grid);
            pullToRefreshHeadGridView.setMode(PullToRefreshBase.Mode.BOTH);
            initDate();
            pullToRefreshHeadGridView.getLoadingLayoutProxy().setLastUpdatedLabel("刷新时间：" + t1);
            View view = View.inflate(this, R.layout.title_to_activity_item, null);
            iv = (ImageView) view.findViewById(R.id.title_to_activity_iv);
            tv1 = (TextView) view.findViewById(R.id.title_to_activity_tv1);
            tv2 = (TextView) view.findViewById(R.id.title_to_activity_iv2);
            initIv();
            HeaderGridView hgv = pullToRefreshHeadGridView.getRefreshableView();
            hgv.setNumColumns(2);
            hgv.setHorizontalSpacing(2);
            hgv.setVerticalSpacing(2);
            hgv.addHeaderView(view);
            hgv.setFocusable(false);
            titleLinearLayoutAdapter = new TitleLinearLayoutAdapter(this, viewHs);
            // 初始化数据和数据源
            pullToRefreshHeadGridView.setAdapter(titleLinearLayoutAdapter);
            pullToRefreshHeadGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    HashMap<String,Object> map= (HashMap<String, Object>) parent.getItemAtPosition(position);
                    String jsonPath=map.get("jsonurl").toString();
                    Log.i("aaaa","position:"+position+"  id:"+id+"json="+jsonPath);
                    Intent intent=new Intent(TitleToActivity.this,GoodDetailActivity.class);
                    intent.putExtra("goodsUrl",jsonPath);
                    startActivity(intent);
            }
            });
        } else {
            pullToRefreshHeadGridView = (PullToRefreshHeadGridView) findViewById(R.id.pull_refresh_grid);
            pullToRefreshHeadGridView.setMode(PullToRefreshBase.Mode.BOTH);
            initDate();
            pullToRefreshHeadGridView.getLoadingLayoutProxy().setLastUpdatedLabel("刷新时间：" + t1);
            View view = View.inflate(this, R.layout.title_to_activity_item, null);
            iv = (ImageView) view.findViewById(R.id.title_to_activity_iv);
            tv1 = (TextView) view.findViewById(R.id.title_to_activity_tv1);
            tv2 = (TextView) view.findViewById(R.id.title_to_activity_iv2);
            initIv();
            HeaderGridView hgv = pullToRefreshHeadGridView.getRefreshableView();
            hgv.setNumColumns(2);
            hgv.setHorizontalSpacing(2);
            hgv.setVerticalSpacing(2);
            hgv.addHeaderView(view);
            titleViewPagerToActivityAdapter = new TitleViewPagerToActivityAdapter(this, viewHs);
            // 初始化数据和数据源
            pullToRefreshHeadGridView.setAdapter(titleViewPagerToActivityAdapter);
            pullToRefreshHeadGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    HashMap<String,Object> map= (HashMap<String, Object>) parent.getItemAtPosition(position);
                    String jsonPath=map.get("jsonurl").toString();
                    Log.i("aaaa","position:"+position+"  id:"+id+"json="+jsonPath);
//                    Intent intent=new Intent(TitleToActivity.this,GoodDetailActivity.class);
//                    intent.putExtra("goodsUrl",jsonPath);
//                    startActivity(intent);
                }
            });

        }
    }

    private void initDatas() {
        if (!s.equals("5")) {
            for (int i = 0; i < viewGoodsList.size(); i++) {
                HashMap<String, Object> map = new HashMap<>();
                map.put("img", viewGoodsList.get(i).getCover().toString());
                map.put("goods_name", viewGoodsList.get(i).getGoods_name().toString());
                map.put("brand_name","");
                map.put("original_price", viewGoodsList.get(i).getOri_price_cn().toString());
                map.put("getSell_price", viewGoodsList.get(i).getSell_price_cn().toString());
                map.put("jsonurl",viewGoodsList.get(i).getJsonUrl().toString());
                viewHs.add(map);
            }
            titleLinearLayoutAdapter.notifyDataSetChanged();
        } else {
            for (int i = 0; i < viewGoodsList.size(); i++) {
                HashMap<String, Object> map = new HashMap<>();
                map.put("img", viewGoodsList.get(i).getCover().toString());
                map.put("goods_name", viewGoodsList.get(i).getGoods_name().toString());
                map.put("logo", viewGoodsList.get(i).getMall_logo().toString());
                map.put("original_price", viewGoodsList.get(i).getOri_price_cn().toString());
                map.put("getSell_price", viewGoodsList.get(i).getSell_price_cn().toString());
                map.put("jsonurl",viewGoodsList.get(i).getJsonUrl().toString());
                viewHs.add(map);
            }
            titleViewPagerToActivityAdapter.notifyDataSetChanged();
        }
    }

    public void startThread() {
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == 3) {
                    initDatas();
                }
            }
        };
        new Thread(new Runnable() {
            @Override
            public void run() {
                String data = InternetUtils.getWebString(bannerUrl);
                Log.i("aaaa", "开启线程" + bannerUrl);
                jsonUtils_firstPage = new JsonUtils_FirstPage();
                if (data != null) {
                    jsonUtils_firstPage.setViewGoods(data, s);
                    viewGoodsList=new ArrayList<ViewGoods>();
                    viewGoodsList = jsonUtils_firstPage.getViewGoodsList();
                    for (int i = 0; i < viewGoodsList.size(); i++) {
                        Log.i("aaaa", "添加goods");
                        tzsqLiteDao.insterViewGoods(viewGoodsList.get(i));
                    }
                }
                handler.sendEmptyMessage(3);
            }
        }).start();
    }

    public void initDate() {
        long time = System.currentTimeMillis();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d1 = new Date(time);
        t1 = format.format(d1);
    }

    public void initIv() {
        if (s.equals("0")) {
            iv.setImageResource(R.drawable.gifts);
            tv1.setText("这是一个适合送礼的清单");
            tv2.setText("这是一个适合送礼的清单，我们帮你挑选了那些全新全套，精致美丽的宝贝，勇敢的送给她。Go...");
        } else if (s.equals("1")) {
            iv.setImageResource(R.drawable.men_400);
            tv1.setText("男士专场");
            tv2.setText("不浮夸但要彰显品味，男士时尚单品推荐，皮带、钱包、公文包，送男票、送朋友、送老爸。");

        } else if (s.equals("2")) {
            iv.setImageResource(R.drawable.taiyangjing_750_400);
            tv1.setText("出门自拍必备的太阳镜");
            tv2.setText("出行自拍必备，风靡时尚圈的人气太阳镜，戴出精彩新“视”界，回头率暴增，自拍更是美不胜收");

        } else if (s.equals("3")) {
            iv.setImageResource(R.drawable.qingshe_cover_v2);
            tv1.setText("2016春夏 轻奢专场");
            tv2.setText("2016春夏，良衣汇推出轻奢专场，ASH，COACH，CALVEN KLEIN，KATE SPADE，MICHAEL KORS，MCM等，更多精品为您呈现。");

        } else if (s.equals("4")) {
            iv.setImageResource(R.drawable.shipin_cover_v3);
            tv1.setText("饰品专场 点亮你的造型");
            tv2.setText("饰品专场，Dior，Bottega Veneta，Gucci，Valentino等。款式繁多，可满足各种造型。");

        } else if(s.equals("5")){
            iv.setImageResource(R.drawable.gabr_cover);
            tv1.setText("暖意春天 女士通勤包首选");
            tv2.setText("Gabriella Rocha设计的通勤包，实用大方，款式简练，配色和谐不缺单调性，现正60%OFF，欢迎选购。");
        }else{}

    }
}
