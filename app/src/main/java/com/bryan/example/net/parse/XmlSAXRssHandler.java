package com.bryan.example.net.parse;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * 解析XML共有三方式：
 * 1-DOM
 * 2-SAX
 * 3-Pull
 * 
 * 最容易实现的是SAX方式。同时也是内存效率最高的。
 * SAX的解析通过遍历XML数据来实现，并在每个元素的开头和结尾产生回掉事件
 * 
 * 基于流。需要或许整个文档，然后把它解析成项数据。加入文档很大的话就需要较大的内存。
 * 
 * @author bryan
 *
 */
public class XmlSAXRssHandler extends DefaultHandler
{
    public class NewsItem{
        public String title;
        public String link;
        public String desc;
        @Override
        public String toString()
        {
            return title;
        }
    }
    
    private StringBuffer buf;
    private ArrayList<NewsItem> feedItems;
    private NewsItem item;
    
    private boolean inItem = false;
    
    public ArrayList<NewsItem>  getParsedItems(){
        return feedItems;
    }
    
    /**
     * 在每个元素开始时调用
     */
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
    {
        if ("channel".equals(localName))
        {
            feedItems = new ArrayList<XmlSAXRssHandler.NewsItem>();
        }else if ("item".equals(localName)) {
            item = new NewsItem();
            inItem = true;
        }else if ("title".equals(localName) && inItem)
        {
            buf = new StringBuffer();
        }else if ("link".equals(localName) & inItem) {
            buf = new StringBuffer();
        }else if ("description".equals(localName) & inItem) {
            buf = new StringBuffer();
        }
    }
    
    /**
     * 在每个元素结束是调用
     */
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException
    {
        if ("item".equals(localName))
        {
            feedItems.add(item);
            inItem = false;
        }else if ("title".equals(localName) && inItem) {
            item.title = buf.toString();
        }else if ("link".equals(localName) && inItem) {
            item.link = buf.toString();
        }else if ("description".equals(localName) && inItem)
        {
            item.desc = buf.toString();
        }
        
        buf = null;
    }
    
    /**
     * 调用元素中的字符数据
     */
    @Override
    public void characters(char[] ch, int start, int length) throws SAXException
    {
        //处理缓存已经初始化的情况。
        if (buf != null)
        {
            for (int i = start; i < start+length; i++)
            {
                buf.append(ch[i]);
            }
        }
    }

//    
//    <!-- <rss version="2.0">
//    RSS的基本格式就是这样
//    <channel>
//        <item>
//            <title></title>
//            <link></link>
//            <description></description>
//        </item>
//          <item>
//            <title></title>
//            <link></link>
//            <description></description>
//        </item>
//          <item>
//            <title></title>
//            <link></link>
//            <description></description>
//        </item>
//    </channel>
//    
//</rss> -->

}
