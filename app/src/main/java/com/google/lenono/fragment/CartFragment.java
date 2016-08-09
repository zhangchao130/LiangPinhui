package com.google.lenono.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.google.lenono.adapter.CartFragmentListViewAdapter;
import com.google.lenono.sqlite.TZSQLiteDao;
import com.google.lenono.tiaozaoproject.R;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by lenono on 2016-07-17.
 */
public class CartFragment extends Fragment implements View.OnClickListener {
    Button idle_btn;
    Button taotao_btn;
    Button cart_compile;
    ListView lv;
    View view;
    List<HashMap<String, Object>> hashMapList, maps;
    TZSQLiteDao tzsqLiteDao;
    public CartFragmentListViewAdapter cartFragmentListViewAdapter;
    SharedPreferences spf;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.cartfragment_item, null);
        spf = getActivity().getSharedPreferences("isSelectDelete", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = spf.edit();
        editor.putBoolean("isDelete", false);
        editor.commit();
        initLayout();
        initData();
        return view;

    }

    public void initLayout() {
        idle_btn = (Button) view.findViewById(R.id.cart_idle_btn);
        taotao_btn = (Button) view.findViewById(R.id.cart_taotao_btn);
        cart_compile = (Button) view.findViewById(R.id.cart_compile);
        lv = (ListView) view.findViewById(R.id.cartfragment_listview);
        idle_btn.setBackgroundColor(Color.RED);
        idle_btn.setOnClickListener(this);
        cart_compile.setOnClickListener(this);
        taotao_btn.setOnClickListener(this);
        tzsqLiteDao = new TZSQLiteDao(getActivity());
    }

    public void initData() {
        hashMapList = new ArrayList<>();
        hashMapList = tzsqLiteDao.getUserInfo("user_order", new String[]{"good_name", "good_info", "good_price", "good_img"}, null, null, null);
        if (!hashMapList.isEmpty()) {
            cartFragmentListViewAdapter = new CartFragmentListViewAdapter(getActivity(), getContext(), hashMapList);
            lv.setAdapter(cartFragmentListViewAdapter);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cart_idle_btn:
                taotao_btn.setBackgroundResource(R.drawable.cart_button_nord);
                idle_btn.setBackgroundColor(Color.RED);
                idle_btn.setEnabled(false);
                taotao_btn.setEnabled(true);
                initData();
                break;
            case R.id.cart_taotao_btn:
                idle_btn.setBackgroundResource(R.drawable.cart_button_nord);
                taotao_btn.setBackgroundColor(Color.RED);
                taotao_btn.setEnabled(false);
                idle_btn.setEnabled(true);
                break;
            case R.id.cart_compile:
                if (cart_compile.getText().equals("编辑")) {
                    cart_compile.setText("完成");
                    SharedPreferences.Editor editor = spf.edit();
                    editor.putBoolean("isDelete", true);
                    editor.commit();
                    cartFragmentListViewAdapter.notifyDataSetChanged();
                } else {
                    cart_compile.setText("编辑");
                    SharedPreferences.Editor editor = spf.edit();
                    editor.putBoolean("isDelete", false);
                    editor.commit();
                    hashMapList.removeAll(hashMapList);
                    maps=new ArrayList<>();
                    maps = tzsqLiteDao.getUserInfo("user_order", new String[]{"good_name", "good_info", "good_price", "good_img"}, null, null, null);
                    hashMapList.addAll(maps);
                    cartFragmentListViewAdapter.notifyDataSetChanged();
                }
                break;
        }
    }

}
