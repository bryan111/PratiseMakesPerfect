package com.bryan.example.function.gongneng;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.text.TextUtils;
import android.widget.Toast;

/**
 * 短信接收
 * 需要权限： <uses-permission android:name="android.permission.READ_SMS"/>
 * @author bryan
 *
 */
public class SmsReceiver extends BroadcastReceiver
{

    @Override
    public void onReceive(Context context, Intent intent)
    {
        Bundle bundle = intent.getExtras();
        Object[] messages = (Object[]) bundle.get("pdus");
        SmsMessage[] sms = new SmsMessage[messages.length];
        //为每个收到的pdu创建短信
        for (int i = 0; i < messages.length; i++)
        {
            sms[i] = SmsMessage.createFromPdu((byte[]) messages[i]);
        }
        
        for (SmsMessage msg : sms)
        {
            //检查短信是否来自我们一直的发送方
            if (TextUtils.equals(msg.getOriginatingAddress(), "12"))
            {
                //让其他应用处理此消息
                abortBroadcast();
                
                //显示我们自己的通知
                Toast.makeText(context, msg.getMessageBody(), Toast.LENGTH_SHORT).show();
            }
        }
    }

}
