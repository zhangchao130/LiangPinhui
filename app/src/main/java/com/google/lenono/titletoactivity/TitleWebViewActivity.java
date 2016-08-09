package com.google.lenono.titletoactivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.google.lenono.tiaozaoproject.R;

import java.net.URL;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TitleWebViewActivity extends AppCompatActivity {

    @Bind(R.id.title_webview_toolbar)
    Toolbar toolbar;
    @Bind(R.id.title_webview)
    WebView webView;
    @Bind(R.id.title_webview_tv)
    TextView tv;
    String urlPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title_web_view);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        urlPath = intent.getStringExtra("goodsUrl");
        tv.setText(intent.getStringExtra("name"));
        initToolbar();
        initWebView();
    }

    public void initToolbar() {
        toolbar.setNavigationIcon(R.drawable.title_icon_back_black);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void initWebView() {
        webView.loadUrl(urlPath);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }
}
