package com.auaf.gapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;

public class WebViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);


        WebView myWebView = (WebView) findViewById(R.id.webview);
        myWebView.loadUrl("https://www.jobs.af/");

    }
}
