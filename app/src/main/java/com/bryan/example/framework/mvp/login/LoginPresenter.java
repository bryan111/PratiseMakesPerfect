package com.bryan.example.framework.mvp.login;

/**
 * 这个类用来控制所有的界面显示,封装Activity所有需要的操作
 * @author Administrator
 *
 */
public interface LoginPresenter
{
    public void validateCredentials(String userName, String passWord);

}
