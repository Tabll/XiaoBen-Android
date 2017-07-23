package com.wts.xb;

//import com.weitianshu.view.SlidingMenu;

import android.graphics.Bitmap;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class webViewClient extends WebViewClient{
	
	//public static String nowurl = "http://www.weitianshu.cn";
	
	 //��дshouldOverrideUrlLoading
	@Override
	public boolean shouldOverrideUrlLoading(WebView view, String url) {
		//nowurl = url;
		view.loadUrl(url);
		return true;//
		
	}
	
	@Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
		//nowurl = url;
        super.onPageStarted(view, url, favicon);
    }

    @Override
    public void onPageFinished(WebView view, String url) {
    	//nowurl = WebView.getUrl();
        super.onPageFinished(view, url);
        //SlidingMenu.NowUrl(view.getUrl());
    }

    @Override
    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
        super.onReceivedError(view, errorCode, description, failingUrl);
    }


}
