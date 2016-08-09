package com.google.lenono.sellinfotoacitivuty;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.google.lenono.tiaozaoproject.AddressEditActivity;
import com.google.lenono.tiaozaoproject.R;

public class SellinfosellodorActivity extends AppCompatActivity {
    Toolbar toolbar;
    TextView tv;
    String toolbarName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sellinfosellodor);
        Intent intent = getIntent();
        toolbarName = intent.getStringExtra("name");
        initView();
    }
    public void initView() {
        toolbar = (Toolbar) findViewById(R.id.sellinfo_idle_toolbar);
        tv = (TextView) findViewById(R.id.sellinfo_idle_toolbar_tv);
        toolbar.setNavigationIcon(R.drawable.title_icon_back_black);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        toolbar.setTitle("");
        tv.setText(toolbarName);
    }


}
