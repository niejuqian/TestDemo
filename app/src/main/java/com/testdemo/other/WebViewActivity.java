package com.testdemo.other;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.testdemo.R;

/**
 * @AUTHOR：niejq@1dian.la
 * @DATETIME：2017 02 13 16:17
 * @DESC：
 */
public class WebViewActivity extends Activity{
    WebView webView;
    private String loadUrl = "http://sx1.1dian.la:9807/Recommend/index.html?token=4370382920170213151314&uid=100011&type=1&tab=user";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keyboard);
        webView= (WebView) findViewById(R.id.webview);
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setUseWideViewPort(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        settings.setLoadWithOverviewMode(true);
        settings.setDomStorageEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webView.loadUrl(loadUrl);
    }
}
