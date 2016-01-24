//package com.bryan.example.net.parse;
//
//import java.io.StringReader;
//
//import org.xmlpull.v1.XmlPullParser;
//
//import android.app.ProgressDialog;
//import android.content.Intent;
//import android.net.Uri;
//import android.os.Bundle;
//import android.util.Xml;
//import android.view.View;
//import android.widget.AdapterView;
//import android.widget.AdapterView.OnItemClickListener;
//import android.widget.ArrayAdapter;
//import android.widget.ListView;
//
//import com.bryan.example.base.BaseActivity;
//import com.bryan.example.net.parse.XmlPullParseNewsItemFactory.NewsItem;
//import com.bryan.example.net.rest.RestTask;
//import com.bryan.example.net.rest.RestTask.ResponseCallback;
//import com.bryan.example.net.rest.RestUtil;
//
///**
// * 解析XML并显示各个项的内容
// *
// */
//public class XmlPullParseActivity extends BaseActivity implements ResponseCallback
//{
//
//    private static final String TAG = "XmlPullParseActivity";
//    private static final String URI = "http://news.google.com/?output=rss";
//
//    private ListView mList;
//    private ArrayAdapter<NewsItem> mAdapter;
//    private ProgressDialog mProgress;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState)
//    {
//        super.onCreate(savedInstanceState);
//        mList = new ListView(this);
//        mAdapter = new ArrayAdapter<NewsItem>(this, android.R.layout.simple_list_item_1, android.R.id.text1);
//        mList.setAdapter(mAdapter);
//        mList.setOnItemClickListener(new OnItemClickListener()
//        {
//
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
//            {
//                NewsItem item = mAdapter.getItem(position);
//                Intent intent = new Intent(Intent.ACTION_VIEW);
//                intent.setData(Uri.parse(item.link));
//                startActivity(intent);
//            }
//        });
//
//        setContentView(mList);
//    }
//
//    @Override
//    protected void onResume()
//    {
//        super.onResume();
//        //获取RSS数据
//        try
//        {
//            RestTask task = RestUtil.obtainGetTask(URI);
//            task.setResponseCallback(this);
//            task.execute();
//            mProgress = ProgressDialog.show(this, "Searching...", "waitting for result...",true);
//        }
//        catch (Exception e)
//        {
//            print(TAG, e.getMessage());
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
//        //处理响应的数据
//        //使用android内置的SAX解析器进行解析
//        try
//        {
//            XmlPullParser parser = Xml.newPullParser();//实例化一个新的XmlPullParser
//            parser.setInput(new StringReader(response));//将数据源的输入流作为一个Reader
//            //从web服务器返回的数据已经是字符串了，所以将它封装成一个StringReader来让解析器解析。
//            //跳到第一个标签
//            parser.nextTag();
//
//            mAdapter.clear();
//            for (com.bryan.example.net.parse.XmlPullParseNewsItemFactory.NewsItem item:XmlPullParseNewsItemFactory.parseFeed(parser))
//            {
//                mAdapter.add(item);
//            }
//            mAdapter.notifyDataSetChanged();
//        }
//        catch (Exception e)
//        {
//            print(TAG, e.getMessage());
//        }
//
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
//        //显示错误
//        mAdapter.clear();
//        mAdapter.notifyDataSetChanged();
//        toast(error.getMessage());
//
//    }
//
//
//}
