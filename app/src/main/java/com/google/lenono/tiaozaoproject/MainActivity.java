package com.google.lenono.tiaozaoproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.lenono.fragment.CartFragment;
import com.google.lenono.fragment.IdleFragment;
import com.google.lenono.fragment.SellFragment;
import com.google.lenono.fragment.SellInfoFragment;
import com.google.lenono.fragment.TitleFragment;
import com.google.lenono.service.Idle_DownloadService;

import io.codetail.animation.SupportAnimator;


public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {
    private SupportAnimator mAnimator;
    private ImageView iv_bottom_search;
    TextView tv;
    int id;
    RadioButton rb_title, rb_idle, rb_sell, rb_cart, rb_sellinfo;
    RadioGroup rg_bottom;
    FragmentManager fm;
    FragmentTransaction transaction;
    TitleFragment titleFragment;
    IdleFragment idleFragment;
    CartFragment cartFragment;
    SellFragment sellFragment;
    SellInfoFragment sellInfoFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fm = getSupportFragmentManager();
        initView();
        initListener();
    }

    public void initView() {
        rg_bottom = (RadioGroup) findViewById(R.id.main_radiogroup);
        rb_title = (RadioButton) findViewById(R.id.main_rg_gb_title);
        rb_idle = (RadioButton) findViewById(R.id.main_rg_gb_idle);
        rb_sell = (RadioButton) findViewById(R.id.main_rg_gb_sell);
        rb_sellinfo = (RadioButton) findViewById(R.id.main_rg_gb_sellinfo);
        rb_cart = (RadioButton) findViewById(R.id.main_rg_gb_cart);
        //设置打开时 首页为选择
        rb_title.setEnabled(false);
        //设置首页的碎片
        titleFragment = new TitleFragment();
        transaction = fm.beginTransaction();
        transaction.replace(R.id.main_framelayout, titleFragment, "title");
        transaction.commit();
    }


    public void initListener() {
        rg_bottom.setOnCheckedChangeListener(this);
    }


    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (i) {
            case R.id.main_rg_gb_title:
                rb_title.setEnabled(false);
                rb_idle.setEnabled(true);
                rb_cart.setEnabled(true);
                rb_sell.setEnabled(true);
                rb_sellinfo.setEnabled(true);
                titleFragment = new TitleFragment();
                transaction = fm.beginTransaction();
                transaction.replace(R.id.main_framelayout, titleFragment, "title");
//                transaction.addToBackStack(null);
                transaction.commit();
                break;
            case R.id.main_rg_gb_idle:
                rb_title.setEnabled(true);
                rb_idle.setEnabled(false);
                rb_cart.setEnabled(true);
                rb_sell.setEnabled(true);
                rb_sellinfo.setEnabled(true);
                idleFragment = new IdleFragment();
                transaction = fm.beginTransaction();
                transaction.replace(R.id.main_framelayout, idleFragment, "idle");
//                transaction.addToBackStack(null);
                transaction.commit();
                Intent intent=new Intent(MainActivity.this, Idle_DownloadService.class);
                intent.putExtra("cate",-1);
                intent.putExtra("brand",-1);
                intent.putExtra("condition",-1);
                startService(intent);
                break;
            case R.id.main_rg_gb_sell:
                rb_title.setEnabled(true);
                rb_idle.setEnabled(true);
                rb_cart.setEnabled(true);
                rb_sell.setEnabled(false);
                rb_sellinfo.setEnabled(true);
                sellFragment = new SellFragment();
                transaction = fm.beginTransaction();
                transaction.replace(R.id.main_framelayout, sellFragment, "sell");
//                transaction.addToBackStack(null);
                transaction.commit();
                break;
            case R.id.main_rg_gb_cart:
                rb_title.setEnabled(true);
                rb_idle.setEnabled(true);
                rb_cart.setEnabled(false);
                rb_sell.setEnabled(true);
                rb_sellinfo.setEnabled(true);
                cartFragment = new CartFragment();
                transaction = fm.beginTransaction();
                transaction.replace(R.id.main_framelayout, cartFragment, "cart");
//                transaction.addToBackStack(null);
                transaction.commit();
                break;
            case R.id.main_rg_gb_sellinfo:
                rb_title.setEnabled(true);
                rb_idle.setEnabled(true);
                rb_cart.setEnabled(true);
                rb_sell.setEnabled(true);
                rb_sellinfo.setEnabled(false);
                sellInfoFragment = new SellInfoFragment();
                transaction = fm.beginTransaction();
                transaction.replace(R.id.main_framelayout, sellInfoFragment, "sellinfo");
//                transaction.addToBackStack(null);
                transaction.commit();
                break;
        }
    }


}
