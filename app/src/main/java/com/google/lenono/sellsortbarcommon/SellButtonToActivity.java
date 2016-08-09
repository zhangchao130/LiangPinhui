package com.google.lenono.sellsortbarcommon;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.google.lenono.tiaozaoproject.R;

public class SellButtonToActivity extends AppCompatActivity {
    private Toolbar toolbar;
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_button_to);
        toolbar= (Toolbar) findViewById(R.id.sell_button_activity_toolbar);
        toolbar.setNavigationIcon(R.drawable.title_icon_back_black);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        toolbar.setTitle("");


        btn= (Button) findViewById(R.id.tiaoguo);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
