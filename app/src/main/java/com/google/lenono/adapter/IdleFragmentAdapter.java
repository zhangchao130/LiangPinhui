package com.google.lenono.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.lenono.memorycache.MemoryCacheManager;
import com.google.lenono.tiaozaoproject.R;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2016/7/22.
 */
public class IdleFragmentAdapter extends BaseAdapter {
    private Context context;
    private List<HashMap<String, Object>> gridlist;

    public IdleFragmentAdapter(Context context, List<HashMap<String, Object>> gridlist) {
        this.context = context;
        this.gridlist = gridlist;
    }

    @Override
    public int getCount() {
        return gridlist.size();
    }

    @Override
    public Object getItem(int i) {
        return gridlist.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertview, ViewGroup viewGroup) {
        ViewHodler hodler;
        if (convertview == null) {
            hodler = new ViewHodler();
            convertview = View.inflate(context, R.layout.idle_headergridview_adapter, null);
            hodler.idle_headergridview_tv_money = (TextView) convertview.findViewById(R.id.idle_headergridview_adapter_tv_money);
            hodler.idle_headergridview_iv = (ImageView) convertview.findViewById(R.id.idle_headergridview_adapter_iv);
            hodler.idle_headergridview_tv_name = (TextView) convertview.findViewById(R.id.idle_headergridview_adapter_tv_name);
            hodler.idle_headergridview_tv_detail_name = (TextView) convertview.findViewById(R.id.idle_headergridview_adapter_tv_detail_name);
            hodler.idle_headergridview_tv_new_price = (TextView) convertview.findViewById(R.id.idle_headergridview_adapter_tv_new_price);
            hodler.idle_headergridview_tv_old_price = (TextView) convertview.findViewById(R.id.idle_headergridview_adapter_tv_old_price);
            hodler.idle_headergridview_tv_money.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            hodler.idle_headergridview_tv_old_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            convertview.setTag(hodler);
        } else {
            hodler = (ViewHodler) convertview.getTag();
        }
        HashMap<String, Object> map = new HashMap<>();
        map = gridlist.get(i);
        hodler.idle_headergridview_tv_name.setText(map.get("brand_name").toString());
        hodler.idle_headergridview_tv_detail_name.setText(map.get("goods_name").toString());
        hodler.idle_headergridview_tv_new_price.setText(map.get("sell_price").toString());
        hodler.idle_headergridview_tv_old_price.setText(map.get("original_price").toString());
        String imgUrl = map.get("cover").toString();

        Picasso.with(context).load(imgUrl).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_default_null_pic).into(hodler.idle_headergridview_iv);

        hodler.idle_headergridview_tv_old_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        return convertview;
    }


    class ViewHodler {
        ImageView idle_headergridview_iv;
        TextView idle_headergridview_tv_name;
        TextView idle_headergridview_tv_detail_name;
        TextView idle_headergridview_tv_new_price;
        TextView idle_headergridview_tv_old_price;
        TextView idle_headergridview_tv_money;
    }
}
