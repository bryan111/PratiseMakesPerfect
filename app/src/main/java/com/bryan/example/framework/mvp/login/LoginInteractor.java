package com.bryan.example.framework.mvp.login;

/**
 *
 *这个类用来封装与用户进行交互的方法
 */
public interface LoginInteractor
{
    /***
     * 登陆
     * @param userName  用户名
     * @param passWord  密码
     * @param listener  登陆完成的回调
     */
    public void login(String userName, String passWord, OnLoginFinishListener listener);

}
