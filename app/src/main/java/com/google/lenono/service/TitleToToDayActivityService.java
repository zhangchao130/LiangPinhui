package com.google.lenono.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import com.google.lenono.common.Album;
import com.google.lenono.common.Banner;
import com.google.lenono.common.FlashSale;
import com.google.lenono.common.Recommend;
import com.google.lenono.fragment.TitleFragment;
import com.google.lenono.sqlite.TZSQLiteDao;
import com.google.lenono.utils.InternetUtils;
import com.google.lenono.utils.jsonUtils.JsonUtils_FirstPage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TitleToToDayActivityService extends Service {
    private String urlpath;
    private JsonUtils_FirstPage jsonUtils_firstPage;
    private TZSQLiteDao tzsqLiteDao;

    List<FlashSale> getFlashSale;
    List<Recommend> getRecommend;
    HashMap<String,List<Album>> getAlbumhs;
    public TitleToToDayActivityService() {
        jsonUtils_firstPage = new JsonUtils_FirstPage();
    }


    @Override
    public void onCreate() {
        tzsqLiteDao = new TZSQLiteDao(getApplicationContext());

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        urlpath = intent.getStringExtra("urlpath");
        new Thread(new Runnable() {
            @Override
            public void run() {
                String data = InternetUtils.getWebString(urlpath);
                Log.i("aaa", urlpath + "");
                if (data != null) {
                    Log.i("aaa", "解析成功");
                    jsonUtils_firstPage.getJsonStr(data);
                    getFlashSale = new ArrayList<>();
                    getRecommend = new ArrayList<Recommend>();
                    getAlbumhs=new HashMap<String, List<Album>>();
                    getFlashSale = jsonUtils_firstPage.getFlashSaleList();
                    getRecommend = jsonUtils_firstPage.getRecommendList();
                    getAlbumhs=jsonUtils_firstPage.getAlbum();
                    for (int j = 0; j < getRecommend.size(); j++) {
                        tzsqLiteDao.insertRecommend(getRecommend.get(j));
                    }
                    for (int i = 0; i < getFlashSale.size(); i++) {
                        tzsqLiteDao.insertFlashSale(getFlashSale.get(i));
                    }
                    for(String key:getAlbumhs.keySet()){
                        List<Album> list=getAlbumhs.get(key);
                        for(int i=0;i<list.size();i++){
                            tzsqLiteDao.insertAlbum(list.get(i));
                        }
                    }

                }
            }
        }).start();
        return START_REDELIVER_INTENT;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
