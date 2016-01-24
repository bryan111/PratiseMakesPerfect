package com.bryan.example.function.gongneng;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.bryan.example.base.BaseActivity;
import com.bryan.example.utils.NetUtils;

/**
 * 判断网络是否连接并且是否为wifi连接
 * 
 * @author bryan
 *
 */
public class IsNetConnectActivity extends BaseActivity
{

    @Override
    protected void onCreate(android.os.Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Button button = new Button(this);
        button.setText("检测");
        button.setOnClickListener(new OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                if (NetUtils.isNetWorkReachable(IsNetConnectActivity.this))
                {
                    if (NetUtils.isWifiReachable(IsNetConnectActivity.this))
                    {
                        toast("当前网络连接正常，并且wifi连接状态下");
                    }
                    else
                    {
                        toast("当前网络连接正常，没有处在wifi连接状态下");
                    }
                }
                else
                {
                    toast("当前网络未连接");
                }

            }
        });
        setContentView(button);
    }

}
