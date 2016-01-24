package com.bryan.example.net.download;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * 使用AsyncTask后台下载数据，可以很方便的让需要长时间运行操作的线程在后台运行
 * 它通过一个内部线程池管理线程的并发！
 * 除了管理线程外，在操作执行，中，后都会提供回掉方法。用于提供UI的更新
 * 
 * @author bryan
 *
 */
public class WebImageView extends ImageView
{
    private Drawable mImage, mPlaceHolder;

    public WebImageView(Context context)
    {
        super(context);
    }

    public WebImageView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public WebImageView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
    }

    public void setPlaceHolderImage(Drawable drawable)
    {
        mPlaceHolder = drawable;
        if (mImage == null)
        {
            setImageDrawable(mPlaceHolder);
        }
    }
    
    public void setPlaceHolderImage(int resId){
        mPlaceHolder = getResources().getDrawable(resId);
        if (mImage == null)
        {
            setImageDrawable(mPlaceHolder);
        }
    }
    
    public void setImageUrl(String url){
        //后台下载
        DownLoadTask task = new DownLoadTask();
        task.execute(url);
    }
    
    /**
     * AsyncTask有三个参数Params, Progress, Result
     *  -Params 通过execute传过来的参数类型
     *  -Progress 不会使用就设置成Void
     *  -Result 后台操作要返回类型（本例为一个Bitmap）
     * 
     * note:UI的更新只能发生在UI线程
     * @author bryan
     *
     */
    private class DownLoadTask extends AsyncTask<String, Void, Bitmap>{
        
        /**
         * 运行在UI线程
         */
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
        }
        
        /**
         * 运行在后台
         * 定义了在后台需要大量运行的操作
         */
        @Override
        protected Bitmap doInBackground(String... params)
        {
            //TODO 方法被遗弃
//            String url = params[0];//获得传过来的参数
//
//
//            try
//            {
//                URLConnection conn = new URL(url).openConnection();
//                InputStream is = conn.getInputStream();
//                BufferedInputStream bis = new BufferedInputStream(is);
//
////                ByteArrayBuffer baf = new ByteArrayBuffer(50);
//                int current = 0;
//                while ((current = bis.read()) != -1)
//                {
//                    baf.append(current);
//                }
//                byte[] imageData = baf.toByteArray();
//                return BitmapFactory.decodeByteArray(imageData, 0, imageData.length);
//            }
//            catch (Exception e)
//            {
//                //下载过程中如果出现异常就返回null
//                return null;
//            }
            return null;
        }
        
        /**
         * 运行在UI线程
         */
        @Override
        protected void onPostExecute(Bitmap result)
        {
            mImage = new BitmapDrawable(getResources(), result);
            if (mImage != null)
            {
                setImageDrawable(mImage);
            }
        }
    }

}
