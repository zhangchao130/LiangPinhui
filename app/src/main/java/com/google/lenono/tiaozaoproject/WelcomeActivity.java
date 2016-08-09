package com.google.lenono.tiaozaoproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.lenono.service.TitleToToDayActivityService;
import com.google.lenono.utils.InternetUtils;

public class WelcomeActivity extends AppCompatActivity {
    ImageView imageView;
    boolean isOpenInternet;
    private String show_detail ="http://uuyichu.com/api/home/data/v4";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        imageView = (ImageView) findViewById(R.id.welcome_iv);
        Animation animation = new AlphaAnimation(0.8f, 1f);
        animation.setDuration(1000);
        imageView.setAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                isOpenInternet = InternetUtils.isConnect(WelcomeActivity.this);
                if (isOpenInternet) {
                    //打开网络开始下载数据
                    Intent intent=new Intent(WelcomeActivity.this, TitleToToDayActivityService.class);
                    intent.putExtra("urlpath",show_detail);
                    startService(intent);
                }
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (!isOpenInternet) {
                    Toast.makeText(WelcomeActivity.this, "请打开网络", Toast.LENGTH_LONG).show();
                }
                isFristLogin();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private void isFristLogin() {
        SharedPreferences spf = getSharedPreferences("isFristLogin", MODE_PRIVATE);
        boolean isLogin = spf.getBoolean("isLogin", false);
        if (!isLogin) {
            Intent guideintent = new Intent(WelcomeActivity.this, GuideActivity.class);
            startActivity(guideintent);
            finish();
        } else {
            Intent mainintent = new Intent(WelcomeActivity.this, MainActivity.class);
            startActivity(mainintent);
            finish();
        }

    }
}
