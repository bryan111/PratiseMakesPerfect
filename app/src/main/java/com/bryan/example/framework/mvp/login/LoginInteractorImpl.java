package com.bryan.example.framework.mvp.login;

import android.os.Handler;
import android.text.TextUtils;

/**
 * 这个类用来实现具体交互的方法
 * 
 */
public class LoginInteractorImpl implements LoginInteractor
{

    @Override
    public void login(final String userName, final String passWord, final OnLoginFinishListener listener)
    {
        // 模拟耗时操作，发起请求后返回回来再通过回调函数回调回去，回调函数封装了所有可能发生的情况
        new Handler().postAtTime(new Runnable()
        {

            @Override
            public void run()
            {

                boolean error = false;
                if (TextUtils.isEmpty(userName))
                {
                    listener.onUserNameError();
                    error = true;
                }
                if (TextUtils.isEmpty(passWord))
                {
                    listener.onPassWordError();
                    error = true;
                }
                if (!error)
                {
                    listener.onSuccess();
                }
            }
        }, 2000);
    }

}
