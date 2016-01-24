package com.bryan.example.view.notifycation;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bryan.example.base.BaseActivity;
import com.bryan.example.view.ViewBaseActivity;
import com.example.pratisemeanperfect.R;

public class NotificationActvity extends BaseActivity
{
    NotificationManager manager;
    Notification.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notify1);
        findViewById(R.id.view_notify1_btn).setOnClickListener(this);
        findViewById(R.id.view_notify2_btn).setOnClickListener(this);
        findViewById(R.id.view_toast_btn).setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
        case R.id.view_notify1_btn:



            break;
        case R.id.view_toast_btn:
            showCustomToast();
            break;

        case R.id.view_notify2_btn:
            showNotification2();
            break;
        default:
            break;
        }
    }

    /**
     *
     */
    @SuppressLint("NewApi")
    private void showNotification2()
    {
        Intent intent = new Intent(NotificationActvity.this, ViewBaseActivity.class);

        PendingIntent ctnIntent = PendingIntent.getActivity(NotificationActvity.this, 0, intent, 0);

        builder.setContentIntent(ctnIntent);

        builder.setContentTitle("new notification is coming!");

        builder.setContentText("hello world");

        builder.setSmallIcon(R.drawable.ic_action_person);

        builder.setTicker("有通知了");// 第一次出现在状态栏的内容

        builder.setDefaults(Notification.DEFAULT_VIBRATE); // 震动需要授权(声音、闪光、震动)

        // Uri uri = Uri.parse("file:///mnt/sdcard/xxx.mp3");//设置声音
        //
        // builder.setSound(uri);

        Notification notification = getNotificationBuilder().build(); // 仅仅限于在高版本4.1中使用

        // notification.defaults = Notification.DEFAULT_SOUND; // 低版本使用

        manager.notify(1000, notification);
    }

    /**
     * Notification.Builder显示自定义的Notification
     */
    private void showNotification3()
    {
        // TODO Auto-generated method stub

    }

    @SuppressLint("NewApi")
    private Notification.Builder getNotificationBuilder()
    {
        if (builder == null)
        {
            builder = new Notification.Builder(this);
        }
        return builder;
    }

    private NotificationManager getNotificationManager()
    {
        if (manager == null)
        {
            manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return manager;
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        getNotificationManager().cancelAll();
    }

    /**
     * 展示自定义的toast
     */
    private void showCustomToast()
    {
        View layout = getLayoutInflater().inflate(R.layout.item_custom_toast, null);// 布局
        TextView showTv = (TextView) layout.findViewById(R.id.toast_show_tv);
        showTv.setText("this is my custom toast");

        Toast toast = new Toast(this);
        toast.setDuration(Toast.LENGTH_SHORT);// 展示时间
        toast.setView(layout);// 展示布局
        toast.setGravity(Gravity.BOTTOM, 0, 0);// 显示位置，可设置偏移量

        toast.show();

    }

}
