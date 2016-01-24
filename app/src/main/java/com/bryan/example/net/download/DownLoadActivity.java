package com.bryan.example.net.download;

import java.io.FileInputStream;

import android.R.integer;
import android.app.Activity;
import android.app.DownloadManager;
import android.app.DownloadManager.Request;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.ImageView;

import com.bryan.example.base.BaseActivity;
import com.bryan.example.constant.FilePathConstant;

/**
 * 使用DownLoadManager处理大文件的下载
 * api要求>=0
 * 
 * 从Android 2.3（API level 9）开始Android用系统服务(Service)的方式提供了Download Manager来优化处理长时间的下载操作。
 * Download Manager处理HTTP连接并监控连接中的状态变化以及系统重启来确保每一个下载任务顺利完成。
 * 在大多数涉及到下载的情况中使用Download Manager都是不错的选择，特别是当用户切换不同的应用以后下载需要在后台继续进行，
 * 以及当下载任务顺利完成非常重要的情况（DownloadManager对于断点续传功能支持很好）。
 * 
 * (当下载失败，设备改变，或者设备重启时，DownLoadManager已然会尝试继续下载)
 * 
 * @author bryan
 *
 */
public class DownLoadActivity extends BaseActivity
{
    private static final String DL_ID = "downloadId";
    private SharedPreferences prefs;

    private DownloadManager dm;
    private ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        iv = new ImageView(this);
        setContentView(iv);

        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
    }

    /**
     * 把所有的操作都放在onResume中，确保每次回到Activity中都可以确定下载的状态（比如pause了之后，所以放onResume会比较好）
     */
    @Override
    protected void onResume()
    {
        super.onResume();
        // 第一次启动时创建
        if (!prefs.contains(DL_ID))
        {
            // 开始下载
            Uri uri = Uri.parse("http://www.bigfoto.com/clouds-picture_small.jpg");
            // DownloadManager.Request 下载的内容。至少要包括下载的地址
            DownloadManager.Request request = new DownloadManager.Request(uri);
            // 设置下载所使用的网络类型
            request.setAllowedNetworkTypes(Request.NETWORK_MOBILE | Request.NETWORK_WIFI);
            // 当设备启用漫游模式时是否要下载
            request.setAllowedOverRoaming(false);
            // 在通知栏上显示的标题
            request.setTitle("Downloading......");
            // 通知栏中现实的描述
            request.setDescription("正在下载大图...");
            // 设置存储位置，如果不设置的话文件会下载到共享下载缓存中，系统可以随时删除文件回收空间
            request.setDestinationUri(Uri.fromFile(FilePathConstant.getRootFileDir()));// 设置存储外部存储器中的一个文件Uri
            // request.setDestinationInExternalFilesDir(context, dirType,
            // subPath)//设置外部存储器中的一个隐藏目录
            // request.setDestinationInExternalPublicDir(dirType,
            // subPath)//设置外部存储器中的一个公共目录
            long id = dm.enqueue(request);
            // 保存唯一的id，通过这个id可以获取下载的任务，进行查询或者删除等其他操作
            // enqueue返回的id可以用来引用管理器中的本次下载，为了随时都可以监测到下载状态将它保存到首选项中
            prefs.edit().putLong(DL_ID, id).apply();
        }
        else
        {
            // 下载已经开始，检查下载状态（当下载完成Activity刚好处在pause中，activity恢复的时候再去设置）
            queryDownLoadStatue();
        }
        // 注册广播。下载完成系统自动发广播，DownloadManager管理的。将下载完成的图片设置到ImageView上
        registerReceiver(receiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }

    private BroadcastReceiver receiver = new BroadcastReceiver()
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
//            long reference = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1); //可以判断下载的ID
            
            queryDownLoadStatue();
            print("DownLoadActivity", "下载完成");
        }
    };

    protected void onDestroy()
    {
        super.onDestroy();
        unregisterReceiver(receiver);
    };

    /**
     * 通过ID检查下载的状态
     */
    private void queryDownLoadStatue()
    {
        DownloadManager.Query query = new DownloadManager.Query();
        query.setFilterById(prefs.getLong(DL_ID, 0));
        Cursor c = dm.query(query);
        if (c.moveToFirst())
        {
            int status = c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS));
            switch (status)
            {
            case DownloadManager.STATUS_PAUSED:
            case DownloadManager.STATUS_PENDING:
            case DownloadManager.STATUS_RUNNING:
                // 什么也不做，下载依然进行
                break;
            case DownloadManager.STATUS_SUCCESSFUL:
                // 下载完成显示图片
                try
                {
                    // 访问下载的文件
                    ParcelFileDescriptor file = dm.openDownloadedFile(prefs.getLong(DL_ID, 0));
                    FileInputStream fis = new ParcelFileDescriptor.AutoCloseInputStream(file);
                    iv.setImageBitmap(BitmapFactory.decodeStream(fis));
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                break;
            case DownloadManager.STATUS_FAILED:
                // 清楚下载并稍后重试
                dm.remove(prefs.getLong(DL_ID, 0));
                prefs.edit().clear().apply();
                break;
            }
        }

    }

}
