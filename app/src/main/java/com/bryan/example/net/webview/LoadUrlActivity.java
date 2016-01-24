package com.bryan.example.net.webview;

import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.bryan.example.base.BaseActivity;

/**
 * 介绍webview的常规用法，据题内容可以参考印象笔记中的笔记
 * 
 * 加载一个URL
 * 
 * WebView事件的拦截 WebViewClient和WebChromeClient->让webView获得事件的回掉并且可以自定义WebView的行为
 * 
 * @author bryan
 *
 */
public class LoadUrlActivity extends BaseActivity
{
    private static final String URL = "https://www.baidu.com/index.php?tn=49029047_oem_dg&ch=33";

    private WebView webView;
    
    private WebViewClient mClient = new WebViewClient()
    {
//        public boolean shouldOverrideUrlLoading(WebView view, String url)
//        {
//            // 此方法的默认实现是会返回false,告诉客户端将URL将URL传递给WebView而不是应用程序
//            // 这个方法的功能是判断传入的URL是否要在WebView中加载。放置离开baidu的网站。（默认实现都会在WebView中加载，因为默认都返回false）
//            Uri request = Uri.parse(url);
//            // request.getAuthority()返回一个URL的主机域名部分
//            if (TextUtils.equals(request.getAuthority(), "m.baidu.com"))
//            {
//                // 允许加载
//                return false;
//            }
//            toast("Sorry, Buddy!!");
//            // 如果访问到其他的网站，会通知到用户。返回true,并且通知WebViewClient应用程序已经处理了这个URL，不允许WebView加载它
//            return true;
//        }
        
        //页面加载的时候可能会很慢，可以重写这个类的方法，给个提示什么的。。。
    };

    

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        webView = new WebView(this);
        // 启用JavaScript支持。默认是禁用的。如果要打开的页面中包含javaScript，就要启用它。否则打开的页面是空白的
        webView.getSettings().setJavaScriptEnabled(true);
        // 如果单纯的加载一个URL系统会默认把请求发给ActivityManager，处理。ActivityManager会打开默认的浏览器
        // 设置一个WebViewClient，WebView可以自己处理所有的URL请求
        webView.setWebViewClient(mClient);
        /**
         * webview还有许多其他的设置，可以参考印象笔记
         */
        // 设置滚动条风格
        webView.setScrollBarStyle(WebView.SCROLLBAR_POSITION_DEFAULT);
        webView.loadUrl(URL);

        setContentView(webView);
    }

    /**
     * 为了支持回退功能，需要覆写onKeyDown
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack())
        {
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
