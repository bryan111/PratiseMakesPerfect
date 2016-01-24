//package com.bryan.example.net.rest;
//
//import android.app.ProgressDialog;
//import android.os.Bundle;
//import android.view.ViewGroup;
//import android.view.ViewGroup.LayoutParams;
//import android.widget.ScrollView;
//import android.widget.TextView;
//
//import com.bryan.example.base.BaseActivity;
//import com.bryan.example.net.rest.RestTask.ProgressCallback;
//import com.bryan.example.net.rest.RestTask.ResponseCallback;
//
///**
// * 简单的Get请求。参数直接跟在网址后面，这里没有就不加参数了
// * @author bryan
// *
// */
//public class SearchActivity extends BaseActivity implements ProgressCallback, ResponseCallback
//{
//
//    private static final String SEARCH_URI = "http://httpbin.org/get";
//
//    private TextView mResult;
//    private ProgressDialog mProgress;//这个效果太差了，要自定义。
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState)
//    {
//        super.onCreate(savedInstanceState);
//        ScrollView scrollView = new ScrollView(this);
//        mResult = new TextView(this);
//        scrollView.addView(mResult,new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
//        setContentView(scrollView);
//
//        //创建请求
//        try
//        {
//            //简单的GET请求
//            RestTask task = RestUtil.obtainGetTask(SEARCH_URI);
//            task.setmProgressCallback(this);
//            task.setResponseCallback(this);
//            task.execute();
//
//            //向用户展示进度
//            mProgress = ProgressDialog.show(this, "Searching", "Waiting For Results...",true);
//        }
//        catch (Exception e)
//        {
//            mResult.setText(e.getMessage());
//        }
//    }
//
//
//    @Override
//    public void onRequestSucess(String response)
//    {
//        //结束进度条
//        if (mProgress != null)
//        {
//            mProgress.dismiss();
//            mProgress = null;
//        }
//        //处理返回数据，把结果展示出来
//        mResult.setText(response);
//    }
//
//    @Override
//    public void onRequestError(Exception error)
//    {
//      //结束进度条
//        if (mProgress != null)
//        {
//            mProgress.dismiss();
//            mProgress = null;
//        }
//        //处理返回数据，把结果展示出来
//        mResult.setText(error.getMessage());
//
//    }
//
//    @Override
//    public void onProgressUpdate(int progress)
//    {
//        if (progress > 0)
//        {
//            if (mProgress != null)
//            {
//                mProgress.dismiss();
//                mProgress = null;
//            }
//            //更新用户进度
//            mResult.setText(String.format("Download Progress : %d%%", progress));
//        }
//    }
//
//}
