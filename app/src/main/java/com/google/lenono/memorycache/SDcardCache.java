package com.google.lenono.memorycache;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * Created by lenono on 2016-07-18.
 */
public class SDcardCache {
    private static final File SD_Root = Environment.getExternalStorageDirectory();
    private static final String SD_fileName = "TzBitmap";
    boolean isMounted = false;
    private static File FILE_CACHE = null;

    public SDcardCache() {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            isMounted = true;
            FILE_CACHE = new File(SD_Root, SD_fileName);
            if (FILE_CACHE != null) {
                FILE_CACHE.mkdirs();
            }
        } else {
            Log.i("aaa", "sd卡挂载出错");
        }
    }

    public synchronized void saveToSDcard(byte[] data, String urlPath) {
        FileOutputStream fos = null;
        if (isMounted) {
            if (!FILE_CACHE.exists()) {
                return;
            } else {
                String fielName = urlPath.substring(urlPath.lastIndexOf("/") + 1);
                File saveFile = new File(FILE_CACHE, fielName);
                try {
                    fos = new FileOutputStream(saveFile);
                    fos.write(data, 0, data.length);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public Bitmap getBitmap(String urlPath){
        Bitmap bitmap=null;
        if (urlPath!=null){
            String fileName=urlPath.substring(urlPath.lastIndexOf("/")+1);
            File getFile=new File(FILE_CACHE,fileName);
            if (getFile.exists()){
                bitmap= BitmapFactory.decodeFile(getFile.getAbsolutePath());
            }
            return bitmap;
        }
        return null;
    }
    public void SDcardClear(){
        if (isMounted){
            File[] files=FILE_CACHE.listFiles();
            for (File file:files){
                file.delete();
            }
        }
    }

}
