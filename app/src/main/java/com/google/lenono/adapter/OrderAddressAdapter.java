package com.google.lenono.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.lenono.tiaozaoproject.R;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2016/8/2.
 */
public class OrderAddressAdapter extends BaseAdapter{
    private Context context;
    private List<HashMap<String, Object>> list;

    public OrderAddressAdapter(Context context,List<HashMap<String, Object>> list) {
        this.context=context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder=null;
        if(convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.gd_order_have_address,null);
            holder=new ViewHolder();
            holder.tv_nickname= (TextView) convertView.findViewById(R.id.gd_order_address_nickname);
            holder.tv_phone= (TextView) convertView.findViewById(R.id.gd_order_address_phone);
            holder.tv_address_basic= (TextView) convertView.findViewById(R.id.gd_order_address_basic);
            holder.tv_address_detail= (TextView) convertView.findViewById(R.id.gd_order_address_detail);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }

        HashMap<String,Object> map=list.get(position);

        holder.tv_nickname.setText(map.get("nickname").toString());
        holder.tv_phone.setText(map.get("phone_num").toString());
        holder.tv_address_basic.setText(map.get("address").toString());
        holder.tv_address_detail.setText(map.get("address_detail").toString());

        return convertView;
    }

    class ViewHolder{
        TextView tv_nickname;
        TextView tv_phone;
        TextView tv_address_basic;
        TextView tv_address_detail;
    }
}
