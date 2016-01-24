//package com.bryan.example.net.rest;
//
//import java.io.File;
//import java.io.FileOutputStream;
//import java.util.ArrayList;
//import java.util.List;
//
//import org.apache.http.NameValuePair;
//import org.apache.http.message.BasicNameValuePair;
//
//import android.app.ProgressDialog;
//import android.content.Context;
//import android.graphics.Bitmap;
//import android.graphics.Bitmap.CompressFormat;
//import android.graphics.BitmapFactory;
//import android.os.Bundle;
//import android.view.ViewGroup;
//import android.view.ViewGroup.LayoutParams;
//import android.widget.ScrollView;
//import android.widget.TextView;
//
//import com.bryan.example.base.BaseActivity;
//import com.bryan.example.net.rest.RestTask.ProgressCallback;
//import com.bryan.example.net.rest.RestTask.ResponseCallback;
//import com.example.pratisemeanperfect.R;
//
///**
// * 复合POST,同时上传原始的二进制数据和一些名-值表单数据
// *
// */
//public class SearchPostCompositeActivity extends BaseActivity implements ResponseCallback,ProgressCallback
//{
//
//
//    private static final String POST_URI = "http://httpbin.org/post";
//
//    private TextView mResult;
//    private ProgressDialog mProgress;//这个效果太差了，要自定义。
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState)
//    {
//        super.onCreate(savedInstanceState);
//        ScrollView scrollView = new ScrollView(this);
//        mResult = new TextView(this);
//        //必须去设置它的宽高否则应该是不现实的
//        scrollView.addView(mResult,new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
//        setContentView(scrollView);
//
//        //创建请求
//        try
//        {
//            //要POST的文件
//            Bitmap image = BitmapFactory.decodeResource(getResources(), R.drawable.just_run);
//            File imageFile = new File(getExternalCacheDir(),"image.png");
//            //把图片写到一个文件中
//            FileOutputStream out = new FileOutputStream(imageFile);
//            image.compress(CompressFormat.PNG, 0, out);
//            out.flush();
//            out.close();
//
//            List<NameValuePair> parameters = new ArrayList<NameValuePair>();
//            parameters.add(new BasicNameValuePair("title", "Android Recipes"));
//            parameters.add(new BasicNameValuePair("summary", "Learn Android Quickly"));
//            parameters.add(new BasicNameValuePair("authors", "smith"));
//            RestTask task = RestUtil.obtainMultipartPostTask(POST_URI, parameters,imageFile,"avatarImage");
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
//
//
//}
