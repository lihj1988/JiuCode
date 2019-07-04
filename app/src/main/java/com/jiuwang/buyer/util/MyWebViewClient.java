package com.jiuwang.buyer.util;

import android.graphics.Bitmap;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

/**
 * Created by Administrator on 2017/8/31.
 */
public class MyWebViewClient extends WebViewClient {

    private static final String TAG = "MyWebViewClient";


    private ProgressBar pb;
    private int loadTime;
    public MyWebViewClient(ProgressBar pb, int loadTime) {
        this.pb = pb;
        this.loadTime=loadTime;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        view.loadUrl(url);
        return true;
    }

/*    @Override
    public boolean shouldOverrideUrlLoading(WebView webView, String url) {
      *//* webView.loadUrl(url);
        return true;*//*
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webView.loadUrl(url);
        } else {
            webView.loadUrl(url);
        }
        return false;

    }*/

    @Override
    public void onPageStarted(WebView webView, String url, Bitmap favicon) {
        pb.setVisibility(View.VISIBLE);
        super.onPageStarted(webView, url, favicon);
    }
    @Override
    public void onPageFinished(WebView webView, String url) {
        pb.setVisibility(View.GONE);
        webView.loadUrl("javascript:window.local_obj.showSource('<html>'+document.getElementsByTagName('html')[0].innerHTML+'</html>');");
        super.onPageFinished(webView, url);
    }

    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {

        return super.shouldInterceptRequest(view, request);
    }
}
