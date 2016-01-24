//package com.bryan.example.net.parse;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import android.app.ProgressDialog;
//import android.os.Bundle;
//import android.view.ViewGroup;
//import android.view.ViewGroup.LayoutParams;
//import android.widget.ScrollView;
//import android.widget.TextView;
//
//import com.bryan.example.base.BaseActivity;
//import com.bryan.example.net.rest.RestTask;
//import com.bryan.example.net.rest.RestUtil;
//import com.bryan.example.net.rest.RestTask.ProgressCallback;
//import com.bryan.example.net.rest.RestTask.ResponseCallback;
//
///**
// * Json解析的类
// *
// * 将已经格式化的字符串数据生成一个新的JsonObject或者JsonArray，然后就可以使用一系列的存取器方法获得这些对象
// * 的原始数据或者内嵌的JsonObject或者JsonArray
// *
// * Json的Get方法，在找不到对应的key的时候会跑出JsonException，不太友好
// * Opt方法，在找不到的时候会返回null，有重载的对象，可以输入默认值，在找不到的时候会自动返回默认值。
// *
// */
//public class JsonParseActivity extends BaseActivity implements ProgressCallback, ResponseCallback
//{
//
//    private static final String SEARCH_URI = "http://httpbin.org/get";
//
//    private TextView mResult;
//    private ProgressDialog mProgress;// 这个效果太差了，要自定义。
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState)
//    {
//        super.onCreate(savedInstanceState);
//        ScrollView scrollView = new ScrollView(this);
//        mResult = new TextView(this);
//        scrollView.addView(mResult, new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
//        setContentView(scrollView);
//
//        // 创建请求
//        try
//        {
//            // 简单的GET请求
//            RestTask task = RestUtil.obtainGetTask(SEARCH_URI);
//            task.setmProgressCallback(this);
//            task.setResponseCallback(this);
//            task.execute();
//
//            // 向用户展示进度
//            mProgress = ProgressDialog.show(this, "Searching", "Waiting For Results...", true);
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
//        // 结束进度条
//        if (mProgress != null)
//        {
//            mProgress.dismiss();
//            mProgress = null;
//        }
//        // 返回Json的字符串
//        /**
//         * 开始解析Json
//         */
//        try
//        {
//            //使用opt方法会比get方法好
//            JSONObject object = new JSONObject(response);
//            String url = object.optString("url","error");
//            String origin = object.optString("origin","error");
//
//            JSONObject headers =  object.optJSONObject("headers");
//            StringBuffer sb = new StringBuffer();
//            sb.append("url = ").append(url).append("\norigin = ").append(origin).append("\nheaders=").append(headers);
//            mResult.setText(sb.toString());
//        }
//        catch (JSONException e)
//        {
//            mResult.setText(e.getMessage());
//        }
//
//
//    }
//
//    @Override
//    public void onRequestError(Exception error)
//    {
//        // 结束进度条
//        if (mProgress != null)
//        {
//            mProgress.dismiss();
//            mProgress = null;
//        }
//        // 处理返回数据，把结果展示出来
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
//            // 更新用户进度
//            mResult.setText(String.format("Download Progress : %d%%", progress));
//        }
//    }
//
//}
