package com.google.lenono.memorycache;


import android.graphics.Bitmap;
import android.os.Handler;
import android.widget.ImageView;

import com.google.lenono.utils.CompressionImage;

import java.io.ByteArrayOutputStream;

/**
 * Created by lenono on 2016-07-18.
 */
public class MemoryCacheManager {
    WebCache webCache = new WebCache();
    SDcardCache sDcardCache = new SDcardCache();
    MemoryCache memoryCache = new MemoryCache();
    Handler handler = new Handler();

    public void getCache(final String urlPath, final ImageView imageView, final int picWidth, final int picHeight) {
        Bitmap bitmap = null;
        if (memoryCache.getBitmapFromMemory(urlPath) != null) {
            bitmap = memoryCache.getBitmapFromMemory(urlPath);
            imageView.setImageBitmap(bitmap);
        } else if (sDcardCache.getBitmap(urlPath) != null) {
            bitmap = sDcardCache.getBitmap(urlPath);
            imageView.setImageBitmap(bitmap);
            memoryCache.addBitmapToMemory(urlPath, bitmap);
        } else {
            webCache.getWebCache(urlPath, new WebCache.CallBack() {
                @Override
                public void getResult(byte[] data) {
                    final Bitmap bitmap1 = CompressionImage.getCompressionBitmap(data, picWidth, picHeight);
                    byte[] compressiondata = getbyteFromBitmap(bitmap1);
                    sDcardCache.saveToSDcard(compressiondata, urlPath);
                    memoryCache.addBitmapToMemory(urlPath, bitmap1);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            imageView.setImageBitmap(bitmap1);
                        }
                    });

                }
            });
        }
    }

    public byte[] getbyteFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }
}
