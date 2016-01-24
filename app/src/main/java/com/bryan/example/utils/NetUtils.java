package com.bryan.example.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetUtils
{

    /**
     * 使用网络资源要时时刻刻的检测网络是否可访问。当不能访问的时候需要去通知用户。
     * ConnectivityManager还能得知应用程序网络连接的类型。这样就能判断大文件是否要下载，是否处于漫游等。
     */
    /**
     * 检查网络是否连通
     */
    public static boolean isNetWorkReachable(Context context)
    {
        final ConnectivityManager mManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        // 如果没有可用的数据网络会返回null
        NetworkInfo current = mManager.getActiveNetworkInfo();
        if (current == null)
        {
            return false;
        }

        // 有可用的数据网络还需要检查他的稳定性
        return (current.getState() == NetworkInfo.State.CONNECTED);
    }

    /**
     * 判断连接的类型是否是wifi连接
     */
    public static boolean isWifiReachable(Context context)
    {
        ConnectivityManager mManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo current = mManager.getActiveNetworkInfo();
        if (current == null)
        {
            return false;
        }
        
        return (current.getType() == ConnectivityManager.TYPE_WIFI);
    }

}
