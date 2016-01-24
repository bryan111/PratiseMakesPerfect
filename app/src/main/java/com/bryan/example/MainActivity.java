package com.bryan.example;

import android.os.Bundle;
import android.view.View;

import com.bryan.example.base.BaseActivity;
import com.bryan.example.bmob.test.BmobSettingActivity;
import com.bryan.example.datasave.DataSaveMainActivity;
import com.bryan.example.framework.FrameWorkActivity;
import com.bryan.example.function.FunctionBaseActivity;
import com.bryan.example.net.NetActivityBase;
import com.bryan.example.view.ViewBaseActivity;
import com.example.pratisemeanperfect.R;

public class MainActivity extends BaseActivity
{
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        findViewById(R.id.main_framework_btn).setOnClickListener(this);
        findViewById(R.id.main_view_btn).setOnClickListener(this);
        findViewById(R.id.main_function_btn).setOnClickListener(this);
        findViewById(R.id.main_data_btn).setOnClickListener(this);
        findViewById(R.id.bmob).setOnClickListener(this);
        findViewById(R.id.net).setOnClickListener(this);

    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
        case R.id.main_framework_btn:
            go2Activity(FrameWorkActivity.class);
            break;
        case R.id.main_view_btn:
            go2Activity(ViewBaseActivity.class);
            break;
        case R.id.main_function_btn:
            go2Activity(FunctionBaseActivity.class);
            break;
        case R.id.main_data_btn:
            go2Activity(DataSaveMainActivity.class);
            break;
        case R.id.bmob:
            go2Activity(BmobSettingActivity.class);
            break;
        case R.id.net:
            go2Activity(NetActivityBase.class);
            break;
        default:
            break;
        }
    }

}
