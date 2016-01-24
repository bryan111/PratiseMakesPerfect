package com.bryan.example.view;

import android.app.ActivityManager;
import android.os.Bundle;
import android.view.View;

import com.bryan.example.base.BaseActivity;
import com.bryan.example.view.dialog.CustomDailogActivity;
import com.bryan.example.view.drawer.DrawerActivity;
import com.bryan.example.view.notifycation.NotificationActvity;
import com.bryan.example.view.slidingtablayout.ActionTabsActivity;
import com.example.pratisemeanperfect.R;

/**
 * 视图基类
 */
public class ViewBaseActivity extends BaseActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        findViewById(R.id.view_tagwidget_btn).setOnClickListener(this);
        findViewById(R.id.view_dialog_btn).setOnClickListener(this);
        findViewById(R.id.view_notify_btn).setOnClickListener(this);
        findViewById(R.id.drawer).setOnClickListener(this);
        findViewById(R.id.tabs).setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
        case R.id.view_tagwidget_btn:
            // go2Activity(TabwidgetActivity.class);
//            go2Activity(ActivityMain.class);
            break;
        case R.id.view_dialog_btn:
            go2Activity(CustomDailogActivity.class);
            break;
        case R.id.view_notify_btn:
            go2Activity(NotificationActvity.class);
            break;
        case R.id.drawer:
            go2Activity(DrawerActivity.class);
            break;
        case R.id.tabs:
            go2Activity(ActionTabsActivity.class);
            break;

        default:
            break;
        }
    }
}
