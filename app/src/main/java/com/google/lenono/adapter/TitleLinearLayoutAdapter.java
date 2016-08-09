package com.google.lenono.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.lenono.tiaozaoproject.R;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;

/**
 * Created by lenono on 2016-07-28.
 */
public class TitleLinearLayoutAdapter extends BaseAdapter {
    private Context context;
    private List<HashMap<String, Object>> gridlist;


    public TitleLinearLayoutAdapter(Context context, List<HashMap<String, Object>> gridlist) {
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
            convertview = View.inflate(context, R.layout.titlelinearlayout_headergridbiew_adapter, null);
            hodler.title_headergridview_iv = (ImageView) convertview.findViewById(R.id.titlelinearlayout_headergridbiew_adapter_iv);
            hodler.title_headergridview_tv_name = (TextView) convertview.findViewById(R.id.titlelinearlayout_headergridbiew_adapter_name);
            hodler.title_headergridview_tv_detail_name = (TextView) convertview.findViewById(R.id.titlelinearlayout_headergridbiew_adapter_name1);
            hodler.title_headergridview_tv_new_price = (TextView) convertview.findViewById(R.id.titlelinearlayout_headergridbiew_adapter_tv_new_price);
            hodler.title_headergridview_tv_old_price = (TextView) convertview.findViewById(R.id.titlelinearlayout_headergridbiew_adapter_tv_old_price);
            convertview.setTag(hodler);
        } else {
            hodler = (ViewHodler) convertview.getTag();
        }
        HashMap<String, Object> map = new HashMap<>();
        map = gridlist.get(i);
        Picasso.with(context).load(map.get("img").toString()).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_default_null_pic).into(hodler.title_headergridview_iv);

        hodler.title_headergridview_tv_name.setText(map.get("goods_name").toString());
        hodler.title_headergridview_tv_detail_name.setText(map.get("brand_name").toString());
        hodler.title_headergridview_tv_old_price.setText(map.get("original_price").toString());
        hodler.title_headergridview_tv_new_price.setText("￥"+map.get("getSell_price").toString());
        hodler.title_headergridview_tv_old_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        return convertview;
    }


    class ViewHodler {
        ImageView title_headergridview_iv;
        TextView title_headergridview_tv_name;
        TextView title_headergridview_tv_detail_name;
        TextView title_headergridview_tv_new_price;
        TextView title_headergridview_tv_old_price;
    }
}
