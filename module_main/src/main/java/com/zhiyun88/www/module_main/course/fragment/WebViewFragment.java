package com.zhiyun88.www.module_main.course.fragment;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.wb.baselib.base.fragment.LazyFragment;
import com.wb.baselib.log.LogTools;
import com.zhiyun88.www.module_main.R;

public class WebViewFragment extends LazyFragment {
    private WebView course_wb;
    private String url;
    public static WebViewFragment newInstcace(String url){
        WebViewFragment courseWebViewFragment=new WebViewFragment();
        Bundle bundle=new Bundle();
        bundle.putString("url",url);
        courseWebViewFragment.setArguments(bundle);
        return courseWebViewFragment;
    }
    @Override
    public boolean isLazyFragment() {
        return false;
    }

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        setContentView(R.layout.main_webview_fragment);
        url=getArguments().getString("url");
        course_wb=getViewById(R.id.course_wb);
//        course_wb.loadUrl(url);//加载url
        LogTools.e("--->>"+url);
        course_wb.loadData(url, "text/html", "UTF-8");
        WebSettings webSettings=course_wb.getSettings();
        webSettings.setJavaScriptEnabled(true);//允许使用js
        webSettings.setJavaScriptEnabled(true);//支持javascript
        webSettings.setUseWideViewPort(true);//适配屏幕
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setBuiltInZoomControls(false);//支持放大缩小
        webSettings.setDisplayZoomControls(false);//隐藏放大缩小的按钮
        webSettings.setDomStorageEnabled(true);//支持Html5标签
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);//不使用缓存，只从网络获取数据.
        //支持屏幕缩放
        webSettings.setSupportZoom(false);
        webSettings.setBuiltInZoomControls(false);
        //设置不用系统浏览器打开,直接显示在当前Webview
        course_wb.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }
}
