package com.google.lenono.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.lenono.tiaozaoproject.R;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2016/7/23.
 */
public class MyPopupWindowSecondAdapter extends BaseAdapter{
    private Context context;
    private List<HashMap<String,Object>> data;

    public MyPopupWindowSecondAdapter(Context context, List<HashMap<String, Object>> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertview, ViewGroup viewGroup) {
        ViewHodler viewHodler;
        if (convertview==null){
            convertview=View.inflate(context, R.layout.idle_popupwindow_second_adapter,null);
            viewHodler=new ViewHodler();
            viewHodler.iv=(ImageView)convertview.findViewById(R.id.idle_popupwindow_second_adapter_iv);
            viewHodler.tv=(TextView)convertview.findViewById(R.id.idle_popupwindow_second_adapter_tv);
            viewHodler.tv.setTextColor(Color.BLACK);
            convertview.setTag(viewHodler);
        }else {
            viewHodler=(ViewHodler) convertview.getTag();
        }
        viewHodler.tv.setText((data.get(i).get("name").toString()));
       int reID=(int)data.get(i).get("img");
       viewHodler.iv.setImageResource(reID);
        return convertview;
    }
    class ViewHodler{
        TextView tv;
        ImageView iv;
    }
}
