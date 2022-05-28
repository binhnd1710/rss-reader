package com.example.btl.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.webkit.WebView;

import com.example.btl.R;

public class WebviewActivity extends AppCompatActivity {
    public static final String EXTRA_URL = "url";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        WebView webView = (WebView) findViewById(R.id.web_view);
        String url = getIntent().getStringExtra(EXTRA_URL);
        webView.loadUrl(url);
    }
}
