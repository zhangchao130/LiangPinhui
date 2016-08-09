package com.google.lenono.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

/**
 * Created by lenono on 2016-07-18.
 */
public class CompressionImage {
    public static Bitmap getCompressionBitmap(byte[] data, int picWidth, int picHeight) {
        Bitmap initBitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
        Log.i("aaa", "原始图片字节数：" + initBitmap.getByteCount());
        BitmapFactory.Options options = new BitmapFactory.Options();
        //设置为true options只获得了图片的宽高
        options.inJustDecodeBounds = true;
        Bitmap afterBitmap = BitmapFactory.decodeByteArray(data, 0, data.length, options);
        options.inSampleSize = calculateSampleSize(options, picWidth, picHeight);
        options.inJustDecodeBounds = false;
        Bitmap resultBitmap = BitmapFactory.decodeByteArray(data, 0, data.length, options);
        Log.i("aaa", "结果图片的大小：" + resultBitmap.getByteCount());
        return resultBitmap;
    }

    public static int calculateSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        //原始大小
        int width = options.outWidth;
        int height = options.outHeight;
        int initsampleSize = 1;
        if (width > reqWidth || height > reqHeight) {
            int reqWidthRadio = Math.round((float) width / reqWidth);
            int reqHeightRadio = Math.round((float) height / reqHeight);
            initsampleSize = reqWidthRadio < reqHeightRadio ? reqWidthRadio : reqHeightRadio;
        }
        Log.i("aaa", "压缩比：" + initsampleSize);
        return initsampleSize;


    }


}
