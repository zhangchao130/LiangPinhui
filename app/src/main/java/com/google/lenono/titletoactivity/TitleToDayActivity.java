package com.google.lenono.titletoactivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;

import com.google.lenono.tiaozaoproject.R;

public class TitleToDayActivity extends AppCompatActivity {
    Toolbar toolbar;
    String toolbarName;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title_to_day);
        Intent intent=getIntent();
        toolbarName=intent.getStringExtra("name");
        initToolBar();
    }
    public void initToolBar(){
        toolbar= (Toolbar) findViewById(R.id.title_to_dayactivity_toolbar);
        toolbar.setNavigationIcon(R.drawable.title_icon_back_black);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        toolbar.setTitle(toolbarName);
    }}
