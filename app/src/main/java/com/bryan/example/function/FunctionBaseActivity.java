package com.bryan.example.function;

import android.os.Bundle;
import android.view.View;

import com.bryan.example.base.BaseActivity;
import com.bryan.example.function.gongneng.IsNetConnectActivity;
import com.bryan.example.function.gongneng.SmsActivity;
import com.bryan.example.function.imageview.DragTouchActivity;
import com.bryan.example.function.imageview.RotateZoomImageViewActivity;
import com.bryan.example.function.notify.NotificationActivity;
import com.bryan.example.function.touch.DisallowActivity;
import com.bryan.example.function.touch.PanScrollActivity;
import com.bryan.example.function.touch.PanScrollActivity2;
import com.bryan.example.function.touch.RemoteScrollActivity;
import com.bryan.example.function.touch.TouchDelegateActivity;
import com.example.pratisemeanperfect.R;

public class FunctionBaseActivity extends BaseActivity
{
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_function);

        findViewById(R.id.singleTouch).setOnClickListener(this);
        findViewById(R.id.singleTouch2).setOnClickListener(this);
        findViewById(R.id.touchImageView).setOnClickListener(this);
        findViewById(R.id.touch_delegate).setOnClickListener(this);
        findViewById(R.id.touch_delegate_custom).setOnClickListener(this);
        findViewById(R.id.disallow).setOnClickListener(this);
        findViewById(R.id.drop).setOnClickListener(this);
        findViewById(R.id.connect).setOnClickListener(this);
        findViewById(R.id.sms).setOnClickListener(this);
        findViewById(R.id.notify).setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
        case R.id.singleTouch:
            go2Activity(PanScrollActivity.class);
            break;
        case R.id.singleTouch2:
            go2Activity(PanScrollActivity2.class);
            break;
        case R.id.touchImageView:
            go2Activity(RotateZoomImageViewActivity.class);
            break;
        case R.id.touch_delegate:
            go2Activity(TouchDelegateActivity.class);
            break;
        case R.id.touch_delegate_custom:
            go2Activity(RemoteScrollActivity.class);
            break;
        case R.id.disallow:
            go2Activity(DisallowActivity.class);
            break;
        case R.id.drop:
            go2Activity(DragTouchActivity.class);
            break;
        case R.id.connect:
            go2Activity(IsNetConnectActivity.class);
            break;
        case R.id.sms:
            go2Activity(SmsActivity.class);
            break;
        case R.id.notify:
            go2Activity(NotificationActivity.class);
            break;
        default:
            break;
        }
    }

}
