package com.google.lenono.memorycache;

import android.view.SurfaceHolder;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by lenono on 2016-07-18.
 */
public class WebCache {
    public static byte[] requestWebCache(String urlPath) {
        ByteArrayOutputStream baos;
        try {
            URL url = new URL(urlPath);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(10000);
            connection.connect();
            if (connection.getResponseCode() == 200) {
                InputStream is = connection.getInputStream();
                byte[] b = new byte[1024 * 4];
                int len = -1;
                baos = new ByteArrayOutputStream();
                while ((len = is.read(b, 0, b.length)) != -1) {
                    baos.write(b, 0, len);
                }

                is.close();
                byte[] result = baos.toByteArray();
                if (result != null) {
                    baos.close();
                }
                return result;
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void getWebCache(final String urlPath, final CallBack callBack) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                byte[] dataWeb = requestWebCache(urlPath);
                callBack.getResult(dataWeb);
            }
        }).start();
    }

    public interface CallBack {
        public void getResult(byte[] data);
    }
}
