package com.bryan.example.function.touch;

import android.os.Bundle;

import com.bryan.example.base.BaseActivity;

/**
 * 触摸事件代理Activity
 * 
 * @author bryan
 *
 */
public class TouchDelegateActivity extends BaseActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        TouchDelegateLayout layout = new TouchDelegateLayout(this);
        setContentView(layout);
    }

}
