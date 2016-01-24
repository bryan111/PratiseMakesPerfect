package com.bryan.example.net.download;

import android.os.Bundle;

import com.bryan.example.base.BaseActivity;
import com.example.pratisemeanperfect.R;

public class WebImageActivity extends BaseActivity
{
    private WebImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webimageview);
        imageView = (WebImageView) findViewById(R.id.webimage);
        imageView.setPlaceHolderImage(R.drawable.ic_launcher);
        imageView.setImageUrl(getResources().getString(R.string.imgfuji));


    }

}
