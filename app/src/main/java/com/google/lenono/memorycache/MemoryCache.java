package com.google.lenono.memorycache;

import android.graphics.Bitmap;
import android.nfc.tech.NfcB;
import android.util.LruCache;

/**
 * Created by lenono on 2016-07-18.
 */
public class MemoryCache {
    LruCache<String, Bitmap> lruCache;

    public MemoryCache() {
        final int maxMemory = (int) Runtime.getRuntime().maxMemory();
        int cachememory = maxMemory / 8;
        lruCache = new LruCache<String, Bitmap>(cachememory) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes() * value.getHeight() / 1024;
            }
        };
    }

    public synchronized void addBitmapToMemory(String urlPath, Bitmap bitmap) {
        if (urlPath != null) {
            if (bitmap != null) {
                lruCache.put(urlPath, bitmap);
            }
        }
    }

    public Bitmap getBitmapFromMemory(String urlPath) {
        Bitmap bitmap = null;
        if (urlPath != null) {
            bitmap = lruCache.get(urlPath);
            return bitmap;
        }
        return null;
    }
    public void memoryClear(){
        if (lruCache.size()>0){
            lruCache.evictAll();
        }
    }
}
