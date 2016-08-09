package com.google.lenono.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.lenono.tiaozaoproject.R;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2016/7/26.
 */
public class GoodDetailListViewAdapter extends BaseAdapter{   //scrollview里不能嵌套listview,要不然就把在scrollview里的listview给定高度
    private Context context;
    private List<HashMap<String,String>> data;

    public GoodDetailListViewAdapter(Context context, List<HashMap<String,String>> data) {
        this.context = context;
        this.data=data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null){
            convertView=View.inflate(context, R.layout.gd_params_listview_item,null);
            holder=new ViewHolder();
            holder.tv1= (TextView) convertView.findViewById(R.id.gd_lvitem_key_tv);
            holder.tv2= (TextView) convertView.findViewById(R.id.gd_lvitem_value_tv);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        HashMap<String,String> map=data.get(position);

        holder.tv1.setText(map.get("key"));
        holder.tv2.setText(map.get("value"));
        return convertView;
    }

    class ViewHolder{
        TextView tv1;
        TextView tv2;
    }
}
