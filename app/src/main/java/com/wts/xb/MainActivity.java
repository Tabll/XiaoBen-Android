package com.wts.xb;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

public class MainActivity extends Activity {

    private WebView webView;

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

//      getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);//透明状态栏
//      getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);//透明导航栏
//      this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去除标题

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.initWebView();//打开网站

        // Example of a call to a native method
        TextView tv = (TextView) findViewById(R.id.sample_text);
        //tv.setText(stringFromJNI());
    }

    /*
     * 初始化WebView
     */
    @SuppressWarnings("deprecation")
    private void initWebView(){
        this.webView=(WebView)this.findViewById(R.id.webView01);//从布局文件中扩展webView
        this.webView.setWebViewClient(new webViewClient());//为WebView设置WebViewClient处理某些操作
        this.webView.setWebChromeClient(new WebChromeClient()  );

//      webView.getSettings().setRenderPriority(RenderPriority.HIGH);//提高渲染的优先级
        webView.getSettings().setBlockNetworkImage(false);//把图片加载放在最后来加载渲染
//    	webView.setHorizontalScrollBarEnabled(false);//水平时隐藏滚动条
        webView.setVerticalScrollBarEnabled(false);//垂直时隐藏滚动条
//    	webView.setScrollBarSize(View.SCROLLBARS_OUTSIDE_OVERLAY);
        this.webView.loadUrl("https://www.tabll.cn");//加载地址
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);//支持JS
        webView.setDownloadListener(new MyWebViewDownLoadListener());//监听下载
    }

    //下载功能
    private class MyWebViewDownLoadListener implements DownloadListener {
        @Override
        public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype,
                                    long contentLength) {
            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }
    }

    //后退功能
    public boolean onKeyDown(int keyCode,KeyEvent event){
        if(webView.canGoBack() && keyCode == KeyEvent.KEYCODE_BACK){
//    		exit();
            webView.goBack();//goBack()表示返回webView的上一页面
            return true;
        }
        return false;
    }

    public void change(View view){
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, XbActivity.class);
        MainActivity.this.startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.web_main, menu);
        return true;
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    //public native String stringFromJNI();
}
