package com.ccc.demo.layout.vertical.dragtoslide.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ccc.demo.layout.vertical.dragtoslide.R;
import com.ccc.lib.layout.vertical.dragtoslide.view.VerticalWebView;


public class Fragment_WebView extends BaseFragment {

    private View progressBar;
    private VerticalWebView webview;
    private boolean hasInited = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_webview, container, false);
        webview = (VerticalWebView) rootView.findViewById(R.id.webView);
        progressBar = rootView.findViewById(R.id.progressbar);

        initView();
        return rootView;
    }

    public void initView() {
        if (null != webview) {
            hasInited = true;
            progressBar.setVisibility(View.GONE);
            webview.loadUrl("https://github.com/jeasonlzy0216");
        }
    }

    @Override
    public void goTop() {
        webview.goTop();
    }
}
