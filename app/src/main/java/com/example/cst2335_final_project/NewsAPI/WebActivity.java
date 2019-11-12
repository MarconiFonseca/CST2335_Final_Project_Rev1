package com.example.cst2335_final_project.NewsAPI;

import android.os.Bundle;
import android.webkit.WebView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cst2335_final_project.R;

public class WebActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        final String url = getIntent().getStringExtra("url");

        WebView webView = findViewById(R.id.activity_web_wv);
        webView.loadUrl(url);
    }
}
