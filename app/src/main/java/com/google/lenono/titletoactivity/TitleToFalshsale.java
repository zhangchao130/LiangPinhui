package com.google.lenono.titletoactivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.lenono.tiaozaoproject.R;

import org.w3c.dom.Text;

public class TitleToFalshsale extends AppCompatActivity {
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title_to_falshsale);
    tv= (TextView) findViewById(R.id.title_falshsale_tv);
        Intent intent=getIntent();
        String str=intent.getStringExtra("name");
        tv.setText(str);
    }
}
