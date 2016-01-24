package com.bryan.example.function.gongneng;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.telephony.gsm.SmsManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.bryan.example.base.BaseActivity;

/**
 * 短信发送 需要权限 ： <uses-permission android:name="android.permission.SEND_SMS"/>
 * 
 *
 */
public class SmsActivity extends BaseActivity
{
    // 发送目标设备号码
    private static final String RECIPIENT_ADDRES = "10086";
    private static final String ACTION_SENT = "com.bryan.example.SENT";
    private static final String ACTION_DELIVERED = "com.bryan.example.DELIVERED";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Button button = new Button(this);
        button.setText("给10086发送18");
        button.setOnClickListener(new OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                sendSms("12");
            }
        });
        setContentView(button);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        //监控操作的状态
        registerReceiver(sent, new IntentFilter(ACTION_SENT));
        registerReceiver(delivered, new IntentFilter(ACTION_DELIVERED));
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        //确保在位于后台时接收器不会被激活
        unregisterReceiver(sent);
        unregisterReceiver(delivered);
    }

    private void sendSms(String message)
    {
        PendingIntent sIntent = PendingIntent.getBroadcast(this, 0, new Intent(ACTION_SENT), 0);
        PendingIntent dIntent = PendingIntent.getBroadcast(this, 0, new Intent(ACTION_DELIVERED), 0);

        // 发送短信
        SmsManager manager = SmsManager.getDefault();
        manager.sendTextMessage(RECIPIENT_ADDRES, null, message, sIntent, dIntent);
    }

    private BroadcastReceiver sent = new BroadcastReceiver()
    {

        @Override
        public void onReceive(Context context, Intent intent)
        {
            switch (getResultCode())
            {
            case Activity.RESULT_OK:
                toast("发送成功");
                break;
            case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
            case SmsManager.RESULT_ERROR_NO_SERVICE:
            case SmsManager.RESULT_ERROR_NULL_PDU:
            case SmsManager.RESULT_ERROR_RADIO_OFF:
                toast("发送失败");
                break;
            }
        }
    };

    private BroadcastReceiver delivered = new BroadcastReceiver()
    {

        @Override
        public void onReceive(Context context, Intent intent)
        {
            switch (getResultCode())
            {
            case Activity.RESULT_OK:
                toast("传递成功");

                break;
            case Activity.RESULT_CANCELED:
                toast("传递失败");
                break;
            }
        }
    };
}
