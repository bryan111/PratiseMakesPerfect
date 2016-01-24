package com.bryan.example.net.webview;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.bryan.example.base.BaseActivity;

/**
 * 访问webView中当前显示内容的HTML源代码。读取或修改其中的某个值
 * 
 * 创建一个JavaScript接口。作为WebView和应用程序代码间的桥梁
 * 
 * webView.addJavascriptInterface会为javaScript绑定一个java对象。这样就可以在WebView中调用此java对象的方法。
 * （note:使用javaScript控制应用程序会存在安全威胁，在却是需要使用的时候在使用）
 * 
 * 
 *
 */
@SuppressLint("JavascriptInterface")
public class LoadJavaScriptActivity extends BaseActivity
{
    
    private WebViewClient mClient = new WebViewClient(){
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            //离开页面前，尝试通过javaScript获得电子邮件
            view.loadUrl(String.format(JS_GETELEMENT, ELEMENTID, ELEMENTID));
            return false;
        }
        
        public void onPageFinished(WebView view, String url) {
            //页面加载完成时，使用javaScript将地址注入页面中
            SharedPreferences sp = getPreferences(Activity.MODE_PRIVATE);
            view.loadUrl(String.format(JS_SETELEMENT, ELEMENTID,sp.getString(ELEMENTID, "")));
        }
    };
    
    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        WebView webView = new WebView(this);
        //启用JavaScript（默认不启用）
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(mClient);
        //将自定义接口关联到View上
        webView.addJavascriptInterface(new MyJavaScriptInterface(), "BRIDGE");
        
        setContentView(webView);
        webView.loadUrl("file:///android_asset/file/form.html");
        
    }
    
    private static final String JS_SETELEMENT = "javascript:document.getElementById('%s')"
            + ".value='%s'";
    private static final String JS_GETELEMENT = "javascript:window.BRIDGE.storeElement("
            + "'%s',document.getElementById('%s').value)";
    private static final String ELEMENTID = "emailAddress";
    
    @SuppressLint("NewApi")
    private void executeJavaScript(WebView webView, String script){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
        {
            webView.evaluateJavascript(script, null);
        }else {
            webView.loadUrl(script);
        }
    }
    
    private class MyJavaScriptInterface{
        //将一个元素存储到SP中
        public void storeElement(String id, String element){
            SharedPreferences.Editor editor = getPreferences(Activity.MODE_PRIVATE).edit();
            editor.putString(id, element);
            editor.apply();
            //如果元素是有效的就提示
            if (!TextUtils.isEmpty(element))
            {
                toast(element);
            }
        }
    }

}
