package com.bryan.example.net;

import android.os.Bundle;
import android.view.View;

import com.bryan.example.base.BaseActivity;
import com.bryan.example.net.download.DownLoadActivity;
import com.bryan.example.net.download.WebImageActivity;
import com.bryan.example.net.webview.LoadAssetsActivity;
import com.bryan.example.net.webview.LoadJavaScriptActivity;
import com.bryan.example.net.webview.LoadUrlActivity;
import com.example.pratisemeanperfect.R;

public class NetActivityBase extends BaseActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net_base);
        findViewById(R.id.load_url).setOnClickListener(this);
        findViewById(R.id.load_assets).setOnClickListener(this);
        findViewById(R.id.load_java_script).setOnClickListener(this);
        findViewById(R.id.load_img).setOnClickListener(this);
        findViewById(R.id.load_back).setOnClickListener(this);
        findViewById(R.id.load_get).setOnClickListener(this);
        findViewById(R.id.load_post).setOnClickListener(this);
        findViewById(R.id.load_post_composite).setOnClickListener(this);
        findViewById(R.id.load_auth).setOnClickListener(this);
        findViewById(R.id.load_json).setOnClickListener(this);
        findViewById(R.id.load_xml).setOnClickListener(this);

    }
    

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
        case R.id.load_url:
            go2Activity(LoadUrlActivity.class);
            break;
        case R.id.load_assets:
            go2Activity(LoadAssetsActivity.class);
            break;
        case R.id.load_java_script:
            go2Activity(LoadJavaScriptActivity.class);
            break;
        case R.id.load_img:
            go2Activity(WebImageActivity.class);
            break;
        case R.id.load_back:
            go2Activity(DownLoadActivity.class);
            break;
        case R.id.load_get:
//            go2Activity(SearchActivity.class);
            break;
        case R.id.load_post:
//            go2Activity(SearchPostActivity.class);
            break;
        case R.id.load_post_composite:
//            go2Activity(SearchPostCompositeActivity.class);
            break;
        case R.id.load_auth:
//            go2Activity(AuthActivity.class);
            break;
        case R.id.load_json:
//            go2Activity(JsonParseActivity.class);
            break;
        case R.id.load_xml:
//            go2Activity(XmlParseActivty.class);
            break;
        default:
            break;
        }
    }
}
