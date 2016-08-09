package com.google.lenono.tiaozaoproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;

import com.google.lenono.adapter.GuideActivityAdapter;

import java.util.ArrayList;
import java.util.List;

public class GuideActivity extends AppCompatActivity {
    ViewPager viewPager;
    LayoutInflater layputInflater;
    List<View> views;
    GuideActivityAdapter gudieActivityAdapter;
    ImageButton button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        initView();
    }

    private void initView() {
        viewPager = (ViewPager) findViewById(R.id.guide_viewpager);
        views = new ArrayList<>();
        layputInflater = layputInflater.from(this);
        View v = layputInflater.inflate(R.layout.guide_viewpager01, null);
        View v2 = layputInflater.inflate(R.layout.guide_viewpager02, null);
        View v3 = layputInflater.inflate(R.layout.guide_viewpager03, null);
        View v4 = layputInflater.inflate(R.layout.guide_viewpager04, null);
        views.add(v);
        views.add(v2);
        views.add(v3);
        views.add(v4);
        gudieActivityAdapter = new GuideActivityAdapter(views);
        viewPager.setAdapter(gudieActivityAdapter);
        button = (ImageButton) v4.findViewById(R.id.guide_viewpager04_button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setLogin();
                Intent intent = new Intent(GuideActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    public void setLogin() {
        SharedPreferences spf = getSharedPreferences("isFristLogin", MODE_PRIVATE);
        SharedPreferences.Editor editor = spf.edit();
        editor.putBoolean("isLogin", true);
        editor.commit();
    }

}
