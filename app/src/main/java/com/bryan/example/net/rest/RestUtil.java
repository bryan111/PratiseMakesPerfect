//package com.bryan.example.net.rest;
//
//import java.io.File;
//import java.io.IOException;
//import java.net.HttpURLConnection;
//import java.net.MalformedURLException;
//import java.net.URL;
//import java.net.URLConnection;
//import java.util.List;
//
//import org.apache.http.NameValuePair;
//
//import android.util.Base64;
//import cn.volley.toolbox.Authenticator;
//
///**
// * 向RestUtil中添加基本授权
// *
// * 两种方式实现
// * 1-在每个请求中直接添加
// * 2-全局使用名为Authenticator的类
// * @author bryan
// *
// */
//public class RestUtil
//{
//    public static RestTask obtainGetTask(String url) throws MalformedURLException, IOException{
//        HttpURLConnection conn = (HttpURLConnection) (new URL(url)).openConnection();
//        conn.setReadTimeout(10000);
//        conn.setConnectTimeout(15000);
//        conn.setDoInput(true);
//        RestTask task = new RestTask(conn);
//        return task;
//    }
//
//
//    /**
//     * 授权
//     * @param url
//     * @return
//     * @throws IOException
//     * @throws MalformedURLException
//     */
//    public static  RestTask obtainAuthenticatedGetTask(String url,String userName,String password) throws MalformedURLException, IOException{
//        HttpURLConnection conn = (HttpURLConnection) (new URL(url)).openConnection();
//        conn.setReadTimeout(10000);
//        conn.setConnectTimeout(15000);
//        conn.setDoInput(true);
//        //在请求中直接添加
//        attachAuthentication(conn, userName, password);
//        RestTask task = new RestTask(conn);
//        return task;
//    }
//
//
//
//    public static RestTask obtainFormPostTask(String url, List<NameValuePair> formData) throws MalformedURLException, IOException{
//       HttpURLConnection conn = (HttpURLConnection) (new URL(url)).openConnection();
//       conn.setReadTimeout(10000);
//       conn.setConnectTimeout(15000);
//       conn.setDoOutput(true);
//
//       RestTask task = new RestTask(conn);
//       task.setmFormBody(formData);
//       return task;
//    }
//
//    public static RestTask obtainAuthenticatedFormPostTask(String url, List<NameValuePair> formData,String userName,String password)
//            throws MalformedURLException, IOException
//    {
//        HttpURLConnection conn = (HttpURLConnection) (new URL(url)).openConnection();
//        conn.setReadTimeout(10000);
//        conn.setConnectTimeout(15000);
//        conn.setDoOutput(true);
//
//        attachAuthentication(conn, userName, password);
//
//        RestTask task = new RestTask(conn);
//        task.setmFormBody(formData);
//        return task;
//    }
//
//
//    public static RestTask obtainMultipartPostTask(String url, List<NameValuePair> formPart, File file,String fileName) throws MalformedURLException, IOException
//    {
//        HttpURLConnection conn = (HttpURLConnection) (new URL(url)).openConnection();
//        conn.setReadTimeout(10000);
//        conn.setConnectTimeout(15000);
//        conn.setDoOutput(true);
//
//        RestTask task = new RestTask(conn);
//        task.setmFormBody(formPart);
//        task.setUploadFile(file, fileName);
//        return task;
//    }
//
//    /**
//     * 基本授权是作为头字段加入到一个HTTP请求中，此方法是在每条请求前添加授权，使用Authenticator类可以只授权一次，其他请求就可以使用他们。具体看情况使用
//     *
//     * 头字段包含："Authorization","Basic"+用户名和密码的Base64编码字符串
//     *
//     */
//    private static void attachAuthentication(URLConnection connection, String userName, String password){
//        //添加基本授权头
//        String userpassword = userName +":"+password;
//        //Base64.NO_WRAP  标志以确保编码器没有新增额外的行
//        String encodedAuthorization = Base64.encodeToString(userpassword.getBytes(), Base64.NO_WRAP);
//        connection.setRequestProperty("Authorization", "Basic"+encodedAuthorization);
//    }
//}
