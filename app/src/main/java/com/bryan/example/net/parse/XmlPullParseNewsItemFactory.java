package com.bryan.example.net.parse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;


//
//<rss version="2.0">
//RSS的基本格式就是这样
//<channel>
//  <item>
//      <title></title>
//      <link></link>
//      <description></description>
//  </item>
//    <item>
//      <title></title>
//      <link></link>
//      <description></description>
//  </item>
//    <item>
//      <title></title>
//      <link></link>
//      <description></description>
//  </item>
//</channel>
//
//</rss>


/**
 * android框架中提供了另外一种高效的解析XML方式  XmlPullParse
 * 
 * 解析过程也是基于流的，在解析开始之前并不需要加载整个XML数据结构。因此在解析大文档的时候也不需要太多内存
 * 
 * Pull解析工作原理：把数据流作为一系列的事件来处理。
 * 应用程序调用next()方法或该方法的一个或多个指定变体来告诉解析器处理下一个事件
 * 
 * 
 * @author bryan
 *
 */
public class XmlPullParseNewsItemFactory
{
    /**
     * 数据模型类
     */
    public static class NewsItem{
        public String title;
        public String link;
        public String desc;
        @Override
        public String toString()
        {
            return title;
        }
    }
    
    /**
     * 将RSS源解析成一个NewsItem元素的列表
     * @throws IOException 
     * @throws XmlPullParserException 
     */
    public static ArrayList<NewsItem> parseFeed(XmlPullParser parser) throws XmlPullParserException, IOException{
        List<NewsItem> items = new ArrayList<XmlPullParseNewsItemFactory.NewsItem>();
        
        while (parser.next() != XmlPullParser.END_TAG)
        {
            if (parser.getEventType() != XmlPullParser.START_TAG)
            {
                continue;
            }
            
            if (parser.getName().equals("rss") || parser.getName().equals("channel"))
            {
                //跳过这些元素但允许解析他们内部的元素
            }else if (parser.getName().equals("item")) {
                //只解析能够转化成NewItem的内容
                NewsItem newsItem = readItem(parser);
                items.add(newsItem);
            }else {
                //跳过不感兴趣的元素
                skip(parser);
            }
        }
        //返回解析后的表
        return (ArrayList<NewsItem>) items;
    }
    
    
    /**
     * 将每个<item>元素解析为一个NewsItem
     * @throws IOException 
     * @throws XmlPullParserException 
     */
    private static NewsItem readItem(XmlPullParser parser) throws XmlPullParserException, IOException
    {
        NewsItem item = new NewsItem();
      
        //确保开头是有效的<item>元素
        parser.require(XmlPullParser.START_TAG, null, "item");
        while (parser.next() != XmlPullParser.END_TAG)
        {
            if (parser.getEventType() != XmlPullParser.START_TAG)
            {
                continue;
            }
            
            String name = parser.getName();
            if (name.equals("title"))
            {
                parser.require(XmlPullParser.START_TAG, null, "title");
                item.title = readText(parser);
                parser.require(XmlPullParser.END_TAG, null, "title");
            }else if (name.equals("link")) {
                parser.require(XmlPullParser.START_TAG, null, "link");
                item.link = readText(parser);
                parser.require(XmlPullParser.END_TAG, null, "link");
            }else if (name.equals("description")) {
                parser.require(XmlPullParser.START_TAG, null, "description");
                item.desc = readText(parser);
                parser.require(XmlPullParser.END_TAG, null, "description");
            }else {
                //跳过其他元素以及他们的子元素
                skip(parser);
            }
        }
        return item;
    }
    

    /**
     * 读取当前元素的文本内容，该内容是start和end标签之间包含的数据
     * @throws IOException 
     * @throws XmlPullParserException 
     */
    private static String readText(XmlPullParser parser) throws XmlPullParserException, IOException
    {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT)
        {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }
    
    /**
     * 辅助方法，跳过当前元素以及该元素的子元素
     * @throws XmlPullParserException 
     * @throws IOException 
     */
    private static void skip(XmlPullParser parser) throws XmlPullParserException, IOException{
        if (parser.getEventType() != XmlPullParser.START_TAG)
        {
            throw new IllegalStateException();
        }
        
        /**
         * 新的标签，计数器会+1，到对应标签结尾时计数器减1，
         * 并且在end标签与开始标签匹配时返回
         */
        int depth = 1;
        while (depth != 0)
        {
            switch (parser.next())
            {
            case XmlPullParser.END_TAG:
                depth--;
                break;
            case XmlPullParser.START_TAG:
                depth++;
                break;

            default:
                break;
            }
        }
    }
    
}
