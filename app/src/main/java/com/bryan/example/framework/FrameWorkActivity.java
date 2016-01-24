package com.bryan.example.framework;

import android.os.Bundle;
import android.view.View;

import com.bryan.example.base.BaseActivity;
import com.bryan.example.framework.mvp.login.LoginActivity;
import com.example.pratisemeanperfect.R;

public class FrameWorkActivity extends BaseActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_framework);
        initViews();
    }

    private void initViews()
    {
        findViewById(R.id.framework_mvp_btn).setOnClickListener(this);

    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
        case R.id.framework_mvp_btn:
            go2Activity(LoginActivity.class);
            break;

        default:
            break;
        }
    }

}
