package com.bryan.example.function.imageview;

import android.os.Bundle;

import com.bryan.example.base.BaseActivity;
import com.example.pratisemeanperfect.R;

public class RotateZoomImageViewActivity extends BaseActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        RotateZoomImageView iv = new RotateZoomImageView(this);
        iv.setImageResource(R.drawable.just_run);
        setContentView(iv);
    }
}
