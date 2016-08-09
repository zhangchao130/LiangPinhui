package com.google.lenono.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import com.google.lenono.common.Goods;
import com.google.lenono.fragment.IdleFragment;
import com.google.lenono.memorycache.MemoryCache;
import com.google.lenono.memorycache.MemoryCacheManager;
import com.google.lenono.memorycache.SDcardCache;
import com.google.lenono.sqlite.TZSQLiteDao;
import com.google.lenono.utils.InternetUtils;
import com.google.lenono.utils.jsonUtils.JsonUtils_SecondPage;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Idle_DownloadService extends Service {
//    String firstUrl="http://uuyichu.com/api/goods/list_v4/?cate=-1&brand=-1&condition=-1&sale=0&source=0&page=";
    String url1="http://uuyichu.com/api/goods/list_v4/?cate=";
    String url2="&brand=";
    String url3="&condition=";
    String url4="&sale=0&source=0&page=";
    int cate2=0;
    int brand2=0;
    int condition2=0;
    public Idle_DownloadService() {

    }
    TZSQLiteDao tzsqLiteDao;
    Handler handler;
    @Override
    public void onCreate() {
        super.onCreate();
        handler=new Handler();
        tzsqLiteDao=new TZSQLiteDao(getApplicationContext());
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int indexPage=intent.getIntExtra("indexPage",1);
        if (indexPage==1){
            tzsqLiteDao.deleteAll("goods");
            int cate=intent.getIntExtra("cate",-1);
            int brand=intent.getIntExtra("brand",-1);
            int condition=intent.getIntExtra("condition",-1);
            String idleUrl=url1+cate+url2+brand+url3+condition+url4+indexPage;
            loadThreadData(idleUrl);
            cate2=cate;
            brand2=brand;
            condition2=condition;
        }
        if (indexPage!=1){
            String idleUrl=url1+cate2+url2+brand2+url3+condition2+url4+indexPage;
            loadThreadData(idleUrl);
        }

        return START_REDELIVER_INTENT;
    }
    public void loadThreadData(final String url){
        new Thread(){
            @Override
            public void run() {
                super.run();
                List<Goods> goodsList=new ArrayList<Goods>();
                TZSQLiteDao tzsqLiteDao=new TZSQLiteDao(getApplicationContext());
                byte[] bs= InternetUtils.getWebByte(url);
                Log.i("bbb","网址"+url);
                if (bs!=null){
                    try {
                        String strurl=new String(bs,"utf-8");
                        JsonUtils_SecondPage jsonUtils_secondPage=new JsonUtils_SecondPage();
                        goodsList= jsonUtils_secondPage.getJsonStr(strurl);
                        for (Goods goods:goodsList){
                           tzsqLiteDao.insertGoods(goods);

                        }
                        readGoods();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }
    public void readGoods(){
        List<HashMap<String,Object>> goodList=tzsqLiteDao.getTiaoZao("goods",new String[]{"goods_name","brand_name","original_price","sell_price","cover","goodsUrl"},null,null,null);
       IdleFragment.goodsList.clear();
        IdleFragment.goodsList.addAll(goodList);
       handler.post(new Runnable() {
           @Override
           public void run() {
               IdleFragment.idleFragmentAdapter.notifyDataSetChanged();
               IdleFragment.pullToRefreshHeadGridView.onRefreshComplete();
           }
       });

    }
}
