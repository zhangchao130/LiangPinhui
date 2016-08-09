package com.google.lenono.utils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by lenono on 2016-07-16.
 */
public class InternetUtils {
    public static boolean isConnect(Activity activity){
        boolean flag=false;

        ConnectivityManager manager= (ConnectivityManager) activity.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if(manager!=null){
            NetworkInfo info=manager.getActiveNetworkInfo();
            if(info!=null&&info.isAvailable()){
                flag=true;
            }
        }
        return flag;
    }

    public static String getWebString(String url){   //获取网络字符串数据
        ResponseBody responseBody=getOkHttp(url);
        String content= null;
        try {
            if(responseBody!=null){
                content = responseBody.string();
            }else{
                Log.i("internet","网络异常");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return content;
    }

    public static byte[] getWebByte(String url){   //获取网络字节数据
        ResponseBody responseBody=getOkHttp(url);
        byte[] bytes=null;
        try {
            if(responseBody!=null){
                bytes = responseBody.bytes();
            }else{
                Log.i("internet","网络异常");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bytes;
    }

    public static ResponseBody getOkHttp(String url){   //辅助，从网络请求数据
        ResponseBody responseBody=null;
        OkHttpClient client=new OkHttpClient();
        Request request=new Request.Builder().url(url).build();
        try {
            Response response=client.newCall(request).execute();
            if (response.isSuccessful()) {   //返回码在200-300间（可为200，也就是网络访问的返回码为200）
                responseBody= response.body();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return responseBody;
    }
}
