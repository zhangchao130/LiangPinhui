package com.google.lenono.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.hardware.SensorEventListener;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.lenono.fragment.CartFragment;
import com.google.lenono.sqlite.TZSQLiteDao;
import com.google.lenono.tiaozaoproject.GoodDetailOrderActivity;
import com.google.lenono.tiaozaoproject.R;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.zip.ZipEntry;

/**
 * Created by lenono on 2016-08-02.
 */
public class CartFragmentListViewAdapter extends BaseAdapter {
    List<HashMap<String, Object>> map;
    Context context;
    Activity activity;

    public CartFragmentListViewAdapter(Activity activity, Context context, List<HashMap<String, Object>> map) {
        this.activity = activity;
        this.context = context;
        this.map = map;
    }

    @Override
    public int getCount() {
        return map.size();
    }

    @Override
    public Object getItem(int position) {
        return map.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.cart_lisetview_item, null);
            holder = new ViewHolder();
            holder.iv = (ImageView) convertView.findViewById(R.id.cart_listview_item_iv);
            holder.tv_info = (TextView) convertView.findViewById(R.id.cart_listview_item_tvinfo);
            holder.tv_name = (TextView) convertView.findViewById(R.id.cart_listview_item_tvname);
            holder.tv_price = (TextView) convertView.findViewById(R.id.cart_listview_item_tvprice);
            holder.btn = (Button) convertView.findViewById(R.id.cart_listview_item_btn);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        HashMap<String, Object> map1 = map.get(position);
        holder.tv_name.setText(map1.get("good_name").toString());
//        holder.tv_info.setText(map1.get("good_info").toString());
        holder.tv_price.setText("￥" + map1.get("good_price").toString());
        holder.tv_price.setTextColor(Color.RED);
        Picasso.with(context).load(map1.get("good_img").toString()).into(holder.iv);
        SharedPreferences spf = activity.getSharedPreferences("isSelectDelete", Context.MODE_PRIVATE);
        boolean flg = spf.getBoolean("isDelete", false);
        final String name = holder.tv_name.getText().toString();
        if (map1.get("good_name").toString() != null) {
            if (!flg) {
                holder.btn.setText("支付");
//                holder.btn.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        GoodDetailOrderActivity goodDetailOrderActivity=new GoodDetailOrderActivity();
//                        goodDetailOrderActivity.pay(v);
//                    }
//                });
                holder.btn.setBackgroundResource(R.drawable.cart_button_nord);
            } else {
                holder.btn.setText("删除");
                holder.btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {
                        v.setBackgroundResource(R.color.red);
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                TZSQLiteDao tzsqLiteDao = new TZSQLiteDao(activity);
                                tzsqLiteDao.delete("user_order", "good_name=?", new String[]{name});

                            }
                        }).start();
                    }
                });
            }
        }
        return convertView;
    }

    static class ViewHolder {
        ImageView iv;
        TextView tv_name, tv_info, tv_price;
        Button btn;
    }
}
