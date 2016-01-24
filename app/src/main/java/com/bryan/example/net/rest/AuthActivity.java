//package com.bryan.example.net.rest;
//
//import java.net.Authenticator;
//import java.net.PasswordAuthentication;
//
//import android.app.ProgressDialog;
//import android.os.Bundle;
//import android.widget.TextView;
//
//import com.bryan.example.base.BaseActivity;
//import com.bryan.example.net.rest.RestTask.ResponseCallback;
//
///**
// * 使用Authenticator类进行授权,
// *
// *
// * 另外一种授权请移到RestUtil中。
// * @author bryan
// *
// */
//public class AuthActivity extends BaseActivity implements ResponseCallback
//{
//    private static final String URI = "http://httpbin.org/basic-auth/android/recipes";
//    private static final String USERNAME = "android";
//    private static final String PASSWORD = "recipes";
//
//    private TextView mResult;
//    private ProgressDialog mProgress;//这个效果太差了，要自定义。
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState)
//    {
//        super.onCreate(savedInstanceState);
//        mResult = new TextView(this);
//        setContentView(mResult);
//
//        /**
//         * 只需要调用一次 Authenticator.setDefault(new Authenticator())
//         * 后续的所有请求在认证时就都会用上这里提供的认证信息。
//         * 所以在请求时创建新的PasswordAuthentication实例，将正确的用户名和密码传入Authenticator类，
//         * 这样在进程中所有的URLConnection实例都会使用它
//         *
//         * 注意：在这个实例中，请求并没有关联认证信息，但执行请求后会得到一个已经认证的相应
//         *
//         */
//        Authenticator.setDefault(new Authenticator()
//        {
//            @Override
//            protected PasswordAuthentication getPasswordAuthentication()
//            {
//                return new PasswordAuthentication(USERNAME, PASSWORD.toCharArray());
//            }
//        });
//
//        try
//        {
//            RestTask task = RestUtil.obtainGetTask(URI);
//            task.setResponseCallback(this);
//            task.execute();
//
//            mProgress = ProgressDialog.show(this, "Searching", "Waiting For Results...",true);
//        }
//        catch (Exception e)
//        {
//            mResult.setText(e.getMessage());
//        }
//    }
//
//    @Override
//    public void onRequestSucess(String response)
//    {
//        if (mProgress != null)
//        {
//            mProgress.dismiss();
//            mProgress = null;
//        }
//        mResult.setText(response);
//    }
//
//    @Override
//    public void onRequestError(Exception error)
//    {
//        if (mProgress != null)
//        {
//            mProgress.dismiss();
//            mProgress = null;
//        }
//        mResult.setText(error.getMessage());
//    }
//
//}
