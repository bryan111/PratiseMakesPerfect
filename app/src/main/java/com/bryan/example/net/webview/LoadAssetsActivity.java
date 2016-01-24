package com.bryan.example.net.webview;

import android.os.Build;
import android.os.Bundle;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;

import com.bryan.example.base.BaseActivity;
import com.example.pratisemeanperfect.R;

/**
 * WebView加载本地资源
 * 
 * 1-WebView加载Assets中的资源 2-WebView加载HTML代码（存在资源文件中或者常量都可以）
 * 
 * @author bryan
 *
 */
public class LoadAssetsActivity extends BaseActivity
{
    WebView web1, web2;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assets);
        web1 = (WebView) findViewById(R.id.web1);
        web2 = (WebView) findViewById(R.id.web2);

        // 必须启用缩放功能（默认会图片会放到最大，只能移动，需要设置才能缩小）
        web1.getSettings().setBuiltInZoomControls(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
        {
            // 3.0以上具有缩放功能的按钮，无需此按钮，3.0以上的版本支持手势进行缩放
            web1.getSettings().setDisplayZoomControls(false);
        }
        //解决有时候因为加载的页面过大只能显示一部分页面
        //这个方法可以让页面完整的显示到屏幕上   
        web1.getSettings().setUseWideViewPort(true);
        web1.getSettings().setLoadWithOverviewMode(true);
        //取消滚动条
        web1.setHorizontalScrollBarEnabled(false);
        web1.setVerticalScrollBarEnabled(false);
        // 加载asset中的资源文件
        web1.loadUrl("file:///android_asset/img/just_run.jpg");

        String htmlStr = "<h1>Header</h1><p>This is HTML text<br/> <i>Formatted in italics</i><br/>我是中文内容</p>";
        //这个方法可以将存储在字符串资源或变量中的原始HTML代码加载到视图中
//        中文内容导致乱码的问题（webview默认编码utf-8）
//        web2.loadData(htmlStr, "text/html", "utf-8");这个方法会导致中文乱码，或者图片无法显示等问题，以下api不会
        web2.loadDataWithBaseURL(null, htmlStr, "text/html", "utf-8", null);
    }

}
