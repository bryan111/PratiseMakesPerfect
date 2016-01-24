package com.bryan.example.constant;

import java.io.File;

import android.net.Uri;
import android.os.Environment;

/**
 * 管理文件的存储路径
 * 
 * @author bryan
 * 
 */
public class FilePathConstant
{

    public static final Uri FILE_URI = Uri.parse(Environment.getExternalStorageDirectory()+ "/PratiseMeanPerfect/");

    // 获得文件存储的根目录
    public static File getRootFileDir()
    {

        File rooFile = new File(Environment.getExternalStorageDirectory(), "PratiseMeanPerfect");
        // Environment.getExternalStorageDirectory()获得外部文件存储的根目录
        if (!rooFile.exists())
        {
            rooFile.mkdirs();
        }
        return rooFile;
    }

}
