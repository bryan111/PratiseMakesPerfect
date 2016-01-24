//package com.bryan.example.net.parse;
//
//import java.io.StringReader;
//
//import javax.xml.parsers.SAXParser;
//import javax.xml.parsers.SAXParserFactory;
//
//import org.xml.sax.InputSource;
//
//import android.app.ProgressDialog;
//import android.content.Intent;
//import android.net.Uri;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.AdapterView;
//import android.widget.AdapterView.OnItemClickListener;
//import android.widget.ArrayAdapter;
//import android.widget.ListView;
//
//import com.bryan.example.base.BaseActivity;
//import com.bryan.example.net.parse.XmlSAXRssHandler.NewsItem;
//import com.bryan.example.net.rest.RestTask;
//import com.bryan.example.net.rest.RestTask.ResponseCallback;
//import com.bryan.example.net.rest.RestUtil;
//
///**
// * 解析XML并显示各个项的内容
// *
// */
//public class XmlParseActivty extends BaseActivity implements ResponseCallback
//{
//
//    private static final String TAG = "XmlParseActivty";
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
//        mAdapter = new ArrayAdapter<XmlSAXRssHandler.NewsItem>(this, android.R.layout.simple_list_item_1, android.R.id.text1);
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
//            SAXParserFactory factory = SAXParserFactory.newInstance();
//            SAXParser p = factory.newSAXParser();
//            XmlSAXRssHandler parser = new XmlSAXRssHandler();
//            p.parse(new InputSource(new StringReader(response)), parser);
//            mAdapter.clear();
//            for (NewsItem item:parser.getParsedItems())
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
